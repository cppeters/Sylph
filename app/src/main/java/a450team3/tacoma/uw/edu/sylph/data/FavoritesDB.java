package a450team3.tacoma.uw.edu.sylph.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import a450team3.tacoma.uw.edu.sylph.R;
import a450team3.tacoma.uw.edu.sylph.favorites.Favorite;

/**
 * Class to handle Creation and data in the sqlLite database for favorites.
 * Created by andyb on 12/7/2016.
 */

public class FavoritesDB  {

    /**Version of the database*/
    public static final int DB_VERSION = 1;

    /**Name of the Database */
    public static final String DB_NAME = "FavoritesDB";

    /**Helper for creating DB and managing version */
    private FavoriteDBHelper mFavDBHelper;

    /**The database. */
    private SQLiteDatabase mFavSQLiteDB;

    /** String for accessing Account column. */
    private String mDBAccountString;

    /** String for accessing Title column. */
    private String mDBTitleString;

    /** String for accessing Description column. */
    private String mDBDescString;

    /** String for accessing URL column. */
    private String mDBURLString;

    /** The name of the Favorites table. */
    private static final String FAVORITES_TABLE = "Favorites";

    /**
     * Constructor for FavoritesDB class. Takes Context to access data from app.
     * @param context The Context of the app, allows access to data.
     */
    public FavoritesDB (Context context) {
        mFavDBHelper = new FavoriteDBHelper(context, DB_NAME, null, DB_VERSION);
        mFavSQLiteDB = mFavDBHelper.getWritableDatabase();
        mDBAccountString = context.getString(R.string.fav_db_account);
        mDBTitleString = context.getString(R.string.fav_db_title);
        mDBDescString = context.getString(R.string.fav_db_description);
        mDBURLString = context.getString(R.string.fav_db_url);
    }

    /**
     * Inserts a Favorite to the Database.
     * @param favorite The favorite to be inserted.
     * @return true if inserted, false if not.
     */
    public boolean insertFavToDB(Favorite favorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBAccountString, favorite.getAccount());
        contentValues.put(mDBTitleString, favorite.getTitle());
        contentValues.put(mDBDescString, favorite.getDescription());
        contentValues.put(mDBURLString, favorite.getUrl());
        long rowId = mFavSQLiteDB.insert("Favorites", null, contentValues);
        return rowId != -1; //If insert fails, rowID will equal -1
    }

    /**
     * Closes DB if necessary.
     */
    public void closeFavDB() {
        mFavSQLiteDB.close();
    }

    /**
     * Deletes all entries in Favorites table.
     */
    public void deleteFavorites() {
        mFavSQLiteDB.delete(FAVORITES_TABLE, null, null);
    }

    /**
     * Queries local DB to get list of logged in account's favorites.
     * @param signedInAccount The currently signed in Account.
     * @return A list of Favorites in DB.
     */
    public List<Favorite> getFavorites(String signedInAccount) {
        String[] columns = {
                mDBAccountString, mDBTitleString, mDBDescString, mDBURLString
        };
        String[] selectArgs = {signedInAccount};

        Cursor cursor = mFavSQLiteDB.query(
                FAVORITES_TABLE,
                columns,
                mDBAccountString + " = ?",
                selectArgs,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        List<Favorite> list = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            String account = cursor.getString(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String url = cursor.getString(3);
            Favorite favorite = new Favorite(account, title, desc, url);
            list.add(favorite);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Inner class to help creation and version control of the database.
     */
    private class FavoriteDBHelper extends SQLiteOpenHelper {

        /** SQL string for creating the SQLite DB. */
        private final String create_FavDB_SQL;

        /**SQL String for dropping the SQLite DB*/
        private final String drop_FavDB_SQL;

        /** Member Variable to access Context in onUpgrade. */
        private Context mContext;

        /**
         * Auto generated constructor for Helper.
         * @param context Context the helper is called from. Overall Context for app
         * @param name Name of the DB.
         * @param factory Cursor factory for DB. Will likely be null.
         * @param version Version of the DB, will be updtaed.
         */
        public FavoriteDBHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
            create_FavDB_SQL = context.getString(R.string.create_FavDB_SQL);
            drop_FavDB_SQL = context.getString(R.string.drop_FavDB_SQL);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(create_FavDB_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(drop_FavDB_SQL);
            onCreate(db);
            //Make sure to update Strings if changing column names.
            mDBAccountString = mContext.getString(R.string.fav_db_account);
            mDBTitleString = mContext.getString(R.string.fav_db_title);
            mDBDescString = mContext.getString(R.string.fav_db_description);
            mDBURLString = mContext.getString(R.string.fav_db_url);
        }
    }
}
