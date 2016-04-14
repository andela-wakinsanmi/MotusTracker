package com.andela.motustracker.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by Spykins on 14/04/16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

   /* public MainActivityTest() {
        super(MainActivity.class);
    }*/

    public void testOnCreate() throws Exception {

    }

    public void testOnPostCreate() throws Exception {


    }

    public void testOnCreateOptionsMenu() throws Exception {

    }

    public void testOnOptionsItemSelected() throws Exception {

    }

    public void testStartButtonClicked() throws Exception {

    }

    public void testOnResume() throws Exception {

    }
}