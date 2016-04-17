package com.andela.motustracker.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.test.ActivityInstrumentationTestCase2;

import com.andela.motustracker.R;
import com.andela.motustracker.activity.MainActivity;
import com.andela.motustracker.helper.AppContext;

import org.hamcrest.Matchers;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Spykins on 14/04/16.
 */
public class HomeFragmentTest extends ActivityInstrumentationTestCase2 {
    private Context context = AppContext.get();

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
            onView(withClassName(Matchers.equalTo(DialogPreference.class.getName())));
            //onView(withId(R.id.number_picker)).perform(scrollTo()).check(matches(isClickable()));
        }

    }

    private boolean isButtonTracking() {
        Context context = AppContext.get();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.BUTTON_STATUS), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.button_flag),false);
    }

    @Test
    public void testStopService() throws Exception {
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
            onView(withClassName(Matchers.equalTo(DialogPreference.class.getName())));
            //onView(withId(R.id.number_picker)).perform(scrollTo()).check(matches(isClickable()));
        }
    }

    @Test
    public void testNavigationBar() throws Exception {


        if(isButtonTracking()) {
            onView(withContentDescription(context.getResources().getString(R.string.drawer_open))).perform(click());
            onView(withText("List Places")).check(matches(isDisplayed()));
            onView(withText("Settings")).check(matches(isDisplayed()));
            onView(withContentDescription(context.getResources().getString(R.string.drawer_close))).perform(click());

        } else {
            onView(withContentDescription(context.getResources().getString(R.string.drawer_open))).perform(click());
            onView(withText("List Places")).check(matches(isDisplayed()));
            onView(withText("Settings")).check(matches(isDisplayed()));
            onView(withContentDescription(context.getResources().getString(R.string.drawer_close))).perform(click());
        }
    }
}