package a450team3.tacoma.uw.edu.sylph;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import a450team3.tacoma.uw.edu.sylph.player.PlaySavedActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Daniel on 12/7/2016.
 */
@RunWith(AndroidJUnit4.class)
public class test2 {
    @Rule
    public ActivityTestRule<PlaySavedActivity> mActivityRule = new ActivityTestRule<>(
            PlaySavedActivity.class);

    @Test
    public void testPauseButton(){
        //  mActivityRule.getActivity();
        PlaySavedActivity mock = mActivityRule.getActivity();
        mock.mediaPlayer.start();
        onView(withId(R.id.media_pause)).perform(click());
        onView(withText("Pause the Audio"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testPlayButton(){
        //  mActivityRule.getActivity();
        PlaySavedActivity mock = mActivityRule.getActivity();
        mock.mediaPlayer.pause();
        onView(withId(R.id.media_play)).perform(click());
        onView(withText("Play the Audio"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testNextButton(){
        //  mActivityRule.getActivity();
        PlaySavedActivity mock = mActivityRule.getActivity();
        mock.mediaPlayer.pause();
        onView(withId(R.id.media_ff)).perform(click());
        onView(withText("Play the Next Song"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

}
