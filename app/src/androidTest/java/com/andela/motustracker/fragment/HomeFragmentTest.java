package com.andela.motustracker.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.andela.motustracker.R;
import com.andela.motustracker.activity.MainActivity;
import com.andela.motustracker.helper.AppContext;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Spykins on 14/04/16.
 */
public class HomeFragmentTest extends ActivityInstrumentationTestCase2 {
    //@Rule
    //public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public HomeFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Test
    public void testStartService() throws Exception {
        //onView(withText("START TRACKING")).check(matches(withText("START TRACKING"))).perform(click());
        onView(withId(R.id.button)).perform(click());

        if(isButtonTracking()) {
            onView(withText("Stop Tracking")).check(matches(isDisplayed()));

        } else {
            onView(withText("Start Tracking")).check(matches(isDisplayed()));
        }
        onView(withId(R.id.id_settings_actionBar)).perform(click());

        if(isButtonTracking()) {
            onView(withText("OK")).perform(click());
            //onView(withText("cannot change App settings")).check(matches(isDisplayed()));

        } else {
            onView(withText("Tracking Time")).perform(click());
            //onView(withId(R.id.number_picker)).perform(scrollTo()).check(matches(isClickable()));
        }

    }

    private boolean isButtonTracking() {
        Context context = AppContext.get();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.BUTTON_STATUS), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.button_flag),false);
    }

    public void testStopService() throws Exception {

    }
}