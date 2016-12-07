package a450team3.tacoma.uw.edu.sylph;

import org.junit.Before;
import org.junit.Test;

import a450team3.tacoma.uw.edu.sylph.favorites.Favorite;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Testing class for Favorites Class.
 * Created by andyb on 12/6/2016.
 */

public class FavoriteTest {

    private Favorite testFav;

    @Before
    public void initFav() {
        testFav = new Favorite("andybleich@gmail.com", "Title", "Description",
                "http://www.youtube.com/watch?v=aFSD2IvXecs");
        //Url for Stunner dan le sac vs Scroobius Pip
    }

    @Test
    public void TestFavoriteConstructor() {
        assertNotNull(new Favorite("andybleich@gmail.com", "Title", "Description",
                "http://www.youtube.com/watch?v=aFSD2IvXecs"));
    }

    @Test
    public void TestGetters () {
        assertEquals("Title equals", "Title", testFav.getTitle());
        assertEquals("Account Equals", "andybleich@gmail.com", testFav.getAccount());
        assertEquals("Description Equals", "Description", testFav.getDescription());
        assertEquals("Url Equals", "http://www.youtube.com/watch?v=aFSD2IvXecs", testFav.getUrl());
    }

}
