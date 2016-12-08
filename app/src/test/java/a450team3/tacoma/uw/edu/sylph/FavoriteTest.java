package a450team3.tacoma.uw.edu.sylph;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import a450team3.tacoma.uw.edu.sylph.favorites.Favorite;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Testing class for Favorites Class.
 * Mockito Testing is not playing nicely. 
 * Created by andyb on 12/6/2016.
 */

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class FavoriteTest {

    @Mock
    JSONArray mockJsonArray;

    @Mock
    Favorite mockFavorite;

    /**Favorite for testing on */
    private Favorite testFav;

    /**List for adding favorites to. */
    private List<Favorite> favoriteList;

    /**JSON string that should provide good results. */
    private String goodJSONString = "{\"Account\":\"andybleich@gmail.com\",\"Title\":\"Ninjabread Man -\""
            + "Game Grumps\",\"Description\":\"You cant catch me. Nor should you want to.\",\"URL\":" +
            "\"https:\\/\\/www.youtube.com\\/watch?v=7gA_PU4VuhQ\"}";

    /**
     * Initializes favorites.
     */
    @Before
    public void initFav() {
        testFav = new Favorite("andybleich@gmail.com", "Title", "Description",
                "http://www.youtube.com/watch?v=aFSD2IvXecs");
        //Url for Stunner dan le sac vs Scroobius Pip
    }

    /**
     * Tests Favorite constructor.
     */
    @Test
    public void TestFavoriteConstructor() {
        assertNotNull(new Favorite("andybleich@gmail.com", "Title", "Description",
                "http://www.youtube.com/watch?v=aFSD2IvXecs"));
    }

    /**
     * Tests all getters for Favorite.
     */
    @Test
    public void TestGetters () {
        assertEquals("Title equals", "Title", testFav.getTitle());
        assertEquals("Account Equals", "andybleich@gmail.com", testFav.getAccount());
        assertEquals("Description Equals", "Description", testFav.getDescription());
        assertEquals("Url Equals", "http://www.youtube.com/watch?v=aFSD2IvXecs", testFav.getUrl());
    }


    /**
     * Tests parse method with bad json.
     */
    @Test
    public void TestJsonBadJson (){
        String string = Favorite.parseFavoriteJSON("thisIsABadStringForJSON", favoriteList);
        System.out.print(string);
        assertEquals(true, string.startsWith("Unable to"));
    }

    /**
     * Tests parse method with good Json.
     */
    @Test
    public void TestJsonGoodJson() {
        when(mockJsonArray.length()).thenReturn(1);
        String string = Favorite.parseFavoriteJSON(goodJSONString, favoriteList);
        assertNull(string);
    }

    /**
     * Tests equals method.
     */
    @Test
    public void TestEquals() {
        Favorite testFavEqual = new Favorite("andybleich@gmail.com", "Title", "Description",
                "http://www.youtube.com/watch?v=aFSD2IvXecs");
        Favorite testFavNotEqual = new Favorite("test@gmail.com", "Title", "Description",
                "http://www.youtube.com/watch?v=aFSD2IvXecs");
        assertTrue(testFav.equals(testFavEqual));
        assertFalse(testFav.equals(testFavNotEqual));

    }




}
