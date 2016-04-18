package com.andela.motustracker.fragment;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.andela.motustracker.R;
import com.andela.motustracker.activity.MainActivity;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.model.MockData;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Spykins on 14/04/16.
 */
public class LocationListFragmentTest extends ActivityInstrumentationTestCase2 {
    private Context context = AppContext.get();

    public LocationListFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Test
    public void testOnCreateView() throws Exception {

        onView(withContentDescription(context.getResources().getString(R.string.drawer_open))).perform(click());
        onView(withText("List Places")).check(matches(isDisplayed()));
        onView(withText("Settings")).check(matches(isDisplayed()));
        onView(withText("List Places")).perform(click());
        MockData mockData = new MockData();
        mockData.addDataToDatabase();
        onView(withContentDescription(context.getResources().getString(R.string.drawer_open))).perform(click());
        onView(withText("Home")).perform(click());
        onView(withContentDescription(context.getResources().getString(R.string.drawer_open))).perform(click());
        onView(withText("List Places")).check(matches(isDisplayed()));
        onView(withText("List Places")).perform(click());
        onView(withText("List By Date")).perform(click());
        onView(withText("List By Location")).perform(click());
        onView(withText("List By Location")).perform(click());
        onView(withText("List By Date")).perform(click());

    }
}