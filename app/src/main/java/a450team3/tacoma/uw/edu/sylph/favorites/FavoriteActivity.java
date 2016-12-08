package a450team3.tacoma.uw.edu.sylph.favorites;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import a450team3.tacoma.uw.edu.sylph.navigation.NavActivity;
import a450team3.tacoma.uw.edu.sylph.R;
import a450team3.tacoma.uw.edu.sylph.authenticate.LoginActivity;
import a450team3.tacoma.uw.edu.sylph.player.YoutubePlayerActivity;

/**
 * Activity for holding and interacting with favorites list.
 */
public class FavoriteActivity extends NavActivity
        implements FavoriteFragment.OnListFragmentInteractionListener{

    /** String to be used for YouTube Code*/
    public static final String YOUTUBE_CODE = "a450team3.tacoma.uw.edu.YOUTUBECODE";

    /** Google Sign In Account Info */
    protected GoogleSignInAccount mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_favorite, frameLayout);

        //Gets the account signed in as
        mAccount = (GoogleSignInAccount) getIntent().getExtras().get(LoginActivity.ACCOUNT_CODE);

        if(savedInstanceState == null ||
                getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_favorite, favoriteFragment).commit();
        }
    }

    /**
     * Takes the user to the youtube player of their selected Favorite.
     * @param favorite The Selected favorite Item.
     */
    @Override
    public void onListFragmentInteraction(Favorite favorite) {
        Intent intent = new Intent(this, YoutubePlayerActivity.class);
        String youtubeCode = extractYoutubeURLCode(favorite.getUrl());
        intent.putExtra(YOUTUBE_CODE, youtubeCode);
        startActivity(intent);


    }

    /**
     * Returns the Youtube code for a video. This method seems weird, but this code is
     * what's needed. 12 used because, the codes are 11 digits and the substring() method
     * is not inclusive.
     * @param url The full URL (https://...)
     * @return The code for the video/
     */
    public String extractYoutubeURLCode(String url) {
        int index = url.indexOf("="); //Youtube players uses Youtube code to get video
        // Plus one and plus twelve used to get the code as substring.
        return url.substring(index + 1, index + 12); //Seemingly magic number
    }
}
