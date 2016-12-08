package a450team3.tacoma.uw.edu.sylph;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import a450team3.tacoma.uw.edu.sylph.player.SelectionActivity;

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
public class testing {

    @Rule
    public ActivityTestRule<SelectionActivity> mActivityRule = new ActivityTestRule<>(
            SelectionActivity.class);


    @Test
    public void testYoutubePlayButton(){
              //  mActivityRule.getActivity();
        onView(withId(R.id.play_video_youtube)).perform(click());
        onView(withText("Play the Video in Youtube"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testConvertVideoButton(){
       // mActivityRule.getActivity();
        onView(withId(R.id.convert_video)).perform(click());
        onView(withText("Convert Youtube video to mp3"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAudioFilePlayButton(){
       // mActivityRule.getActivity();
        onView(withId(R.id.play_local)).perform(click());
        onView(withText("Play the mp3 file"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }
}
