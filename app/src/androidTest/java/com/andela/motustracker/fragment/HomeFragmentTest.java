package com.andela.motustracker.fragment;

import android.support.test.rule.ActivityTestRule;

import com.andela.motustracker.R;
import com.andela.motustracker.activity.MainActivity;

import junit.framework.TestCase;

import org.junit.Rule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Spykins on 14/04/16.
 */
public class HomeFragmentTest extends TestCase {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public void testStartService() throws Exception {
        //onView(withText("START TRACKING")).check(matches(withText("START TRACKING"))).perform(click());
        onView(withId(R.id.button)).perform(click());


    }

    public void testStopService() throws Exception {

    }
}