package a450team3.tacoma.uw.edu.sylph.favorites;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import a450team3.tacoma.uw.edu.sylph.R;
import a450team3.tacoma.uw.edu.sylph.authenticate.LoginActivity;
import a450team3.tacoma.uw.edu.sylph.data.FavoritesDB;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FavoriteFragment extends Fragment {

    /** Number of Columns for the list. */
    private int mColumnCount = 1;

    /** URL for the php file, used for retrieving favorites. */
    private static final String FAVORITES_URL =
            "http://cssgate.insttech.washington.edu/~_450team3/getFavorites.php?cmd=favorites";

    private static final String ADD_FAVORITES_URL =
            "http://cssgate.insttech.washington.edu/~_450team3/addFavorite.php?";

    /** List of favorites for creating fragment. */
    private List<Favorite> mFavoritesList;

    /** Database holding local data of Favorites. */
    private FavoritesDB mFavoritesDB;

    /** Recycler View for cycling fragments */
    private RecyclerView mRecyclerView;

    /** Listener for List interaction */
    private OnListFragmentInteractionListener mListener;

    /** Email for the account currently logged in. Acquired from Intent Extras. */
    private GoogleSignInAccount mAccount;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        mAccount = (GoogleSignInAccount) bundle.get(LoginActivity.ACCOUNT_CODE);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            syncDB();
            //Use local db no matter what
            if (mFavoritesDB == null) {
                mFavoritesDB = new FavoritesDB(getActivity());
            }
            if (mFavoritesList == null) {
                mFavoritesList = mFavoritesDB.getFavorites(mAccount.getEmail());
            }
            mRecyclerView.setAdapter(
                    new MyFavoriteRecyclerViewAdapter(mFavoritesList, mListener));

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Favorite favorite);
    }

    /**
     * Runs in the background to download the Favorites data from the database.
     */
    public class DownloadFavoriteTask extends AsyncTask<String, Void, String> {

        /**
         * Downoalds the data as a background thread.
         * @param urls urls to download String data from.
         * @return Returns the data or an error.
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;

            for (String string: urls) {
                try {
                    URL urlObj = new URL(string);
                    urlConnection = (HttpURLConnection) urlObj.openConnection();
                    InputStream contentFromUrl = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(contentFromUrl));

                    String s = "";
                    while ((s = reader.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    Log.e("FavoriteFragment", response);
                    response = "Unable to download favorites. Reasons: " + e.getMessage();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
            return response;
        }

        @Override
        public void onPostExecute(String result) {
            //We got something wrong here
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();
                return;
            }
            mFavoritesList = new ArrayList<>();
            result = Favorite.parseFavoriteJSON(result, mFavoritesList);
            if (result != null) {
                Log.e("Favorite Fragment", "Something has gone wrong with JSON.");
            }
            if (!mFavoritesList.isEmpty()) {

                if (mFavoritesDB == null) {
                    mFavoritesDB = new FavoritesDB(getActivity());
                }
                mFavoritesDB.deleteFavorites();
                for (int i = 0; i < mFavoritesList.size(); i++) {
                    Favorite favorite = mFavoritesList.get(i);
                    mFavoritesDB.insertFavToDB(favorite);
                }
            }
        }
    }

    /**
     * Method to download data from online database if available, and sync with local DB
     * in async task.
     */
    public void syncDB() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadFavoriteTask dFT = new DownloadFavoriteTask();
            dFT.execute(new String[]{FAVORITES_URL});
        }
    }

    public void addToFavorites(Favorite favorite) {

        String url = createAddFavURL(favorite);
        AddFavoritesTask aFT = new AddFavoritesTask();
        aFT.execute(url);
    }

    private String createAddFavURL(Favorite favorite) {
        StringBuilder sb = new StringBuilder(ADD_FAVORITES_URL);
        try {
            sb.append("account=");
            sb.append(URLEncoder.encode(favorite.getAccount(), "UTF-8"));

            sb.append("&title=");
            sb.append(URLEncoder.encode(favorite.getTitle(), "UTF-8"));

            sb.append("&description");
            sb.append(URLEncoder.encode(favorite.getDescription(), "UTF-8"));

            sb.append("&url");
            sb.append(URLEncoder.encode(favorite.getUrl(), "UTF-8"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong with the url. " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return sb.toString();
    }

    /**
     * Async Task to add a Favorite to the online Database.
     */
    public class AddFavoritesTask extends AsyncTask <String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String response = "";
            HttpURLConnection urlConnection = null;
            for (String s : urls) {
                try {
                    URL urlObject = new URL(s);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String str = "";
                    while ((str = reader.readLine()) != null) {
                        response += str;
                    }
                } catch (Exception e) {
                    response = "Unable to add favorites. " + e.getMessage();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }

            return response;
        }

        @Override
        public void onPostExecute (String result) {
            try {
                Log.i("FavoriteFragment", result);
                JSONObject jsonObject = new JSONObject(result);
                String status =  jsonObject.getString("result");
                if (status.equals("success")) {
                    Toast.makeText(getContext(),
                            "Favorite added successfully!" ,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),
                            "Favorite add failed. Error: " + jsonObject.get("error"),
                            Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Toast.makeText(getContext(), "Something has gone wrong with the data: "
                        + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("FavoriteFragment", e.getMessage());

            }
            syncDB();
        }
    }
}
