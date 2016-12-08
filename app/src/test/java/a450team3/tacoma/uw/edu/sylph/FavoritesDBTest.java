package a450team3.tacoma.uw.edu.sylph;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import a450team3.tacoma.uw.edu.sylph.data.FavoritesDB;
import a450team3.tacoma.uw.edu.sylph.favorites.Favorite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Class to handle testing of FavroitesDB.
 * Mockito Testing was not nice, and did not function well.
 * Created by andyb on 12/7/2016.
 */

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class FavoritesDBTest {

    @Mock
    Context mockContext;

    @Mock
    FavoritesDB mFavDb;

    @Mock
    SQLiteDatabase mockSqlDB;

    @Mock
    FavoritesDB.FavoriteDBHelper mockSqlDBHelper;

    /** String for accessing Account column. */
    private String mDBAccountString = "Account";

    /** String for accessing Title column. */
    private String mDBTitleString = "Title";

    /** String for accessing Description column. */
    private String mDBDescString = "Description";

    /** String for accessing URL column. */
    private String mDBURLString = "URL";

    /** Account signed in for testing. */
    private String signedInAccount = "test@gmail.com";

    /** Favorites used for testing */
    private Favorite testFav1, testFav2;

    /** List of Favorites */
    private List<Favorite> mList;


    /**
     * Setup method for DB Test.
     */
    @Before
    public void before() {
        when(mockContext.getString(R.string.fav_db_account)).thenReturn(mDBAccountString);
        when(mockContext.getString(R.string.fav_db_title)).thenReturn(mDBTitleString);
        when(mockContext.getString(R.string.fav_db_description)).thenReturn(mDBDescString);
        when(mockContext.getString(R.string.fav_db_url)).thenReturn(mDBURLString);
        when(mockContext.getString(R.string.create_FavDB_SQL)).thenReturn(
                "CREATE TABLE IF NOT EXISTS Favorites" +
                "    (Account VARCHAR(255), Title TEXT, Description TEXT, URL TEXT) ");
        when(mockContext.getString(R.string.drop_FavDB_SQL))
                .thenReturn("DROP TABLE IF EXISTS Favorites");
        when(mockSqlDBHelper.getWritableDatabase()).thenReturn(mockSqlDB);
        when(mFavDb.insertFavToDB(testFav1)).thenReturn(true);
        when(mFavDb.getFavorites(signedInAccount)).thenReturn(mList);
        mList = new ArrayList<>();
        testFav1 = new Favorite(signedInAccount, "Stunner", "Pip",
                "http://www.youtube.com/watch?v=aFSD2IvXecs");
        testFav2 = new Favorite(signedInAccount, "Beat That my Heart Skipped", "Pip",
                "https://www.youtube.com/watch?v=ESvYRR1Fyug");
    }

    /**
     * Tests insert to DB method
     */
    @Test
    public void insertFavToDBTest() {
        assertEquals(false, mFavDb.insertFavToDB(testFav1));

    }

    /**
     * Tests getFavorites method.
     */
    @Test
    public void getFavoritesTest() {
        mList.add(testFav1);
        mList.add(testFav2);

        List<Favorite> favoriteList = mFavDb.getFavorites(signedInAccount);

        assertTrue(testFav1.equals(favoriteList.get(0)));
        assertTrue(testFav2.equals(favoriteList.get(1)));
    }






}
