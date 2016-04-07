package com.andela.motustracker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.andela.motustracker.helper.App;
import com.andela.motustracker.helper.AppPreferences;
import com.andela.motustracker.helper.GoogleClient;
import com.andela.motustracker.helper.OnHomeButtonClickListener;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements OnHomeButtonClickListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    android.support.v4.app.FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.id_toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_settings_drawer :
                        createAppPreference();
                    break;
                    case R.id.id_home_drawer:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.id_list_drawer :
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new LocationListFragment());
                        fragmentTransaction.commit();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        loadPreference();
        loadHomeFragment();

    }

    private void loadHomeFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new HomeFragment());
        fragmentTransaction.commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //synchronize it here
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.id_settings_actionBar:
                //call settings..
                createAppPreference();
                break;
        }

        return true;
    }

    private void createAppPreference() {
        Intent intent = new Intent(this, AppPreferences.class);
        startActivity(intent);
    }

    private void loadPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String valueSet = sharedPreferences.getString("minimumTime", "");
        Log.e("MOTUS", valueSet);
        //call the timer from here when this happens....
    }

    @Override
    public void startButtonClicked(boolean isButtonOnStart) {
        //start or stop service
        if(isButtonOnStart) {
            //start service

        } else {
            //stop service
        }
    }


    //checking play services
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void displaySettings(Status status) {
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            status.startResolutionForResult(this, 200);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        }
    }



}
