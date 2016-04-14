package com.andela.motustracker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.andela.motustracker.R;
import com.andela.motustracker.fragment.HomeFragment;
import com.andela.motustracker.fragment.LocationListFragment;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.preference.AppPreferences;
import com.andela.motustracker.helper.OnHomeButtonClickListener;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private static Activity activity;

    android.support.v4.app.FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
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
                        if(!isButtonTracking()) {
                            createAppPreference();
                        } else {
                            displayErrorDialog();
                        }
                    break;
                    case R.id.id_home_drawer:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                        if(getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Home");
                        }
                        fragmentTransaction.commit();
                        break;
                    case R.id.id_list_drawer :
                        if(getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("List");
                        }
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
                if(!isButtonTracking()) {
                    createAppPreference();
                } else {
                    displayErrorDialog();
                }

                break;
        }

        return true;
    }

    private void createAppPreference() {
        Intent intent = new Intent(this, AppPreferences.class);
        startActivityForResult(intent,20);
    }

    private void loadPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String valueSet = sharedPreferences.getString("minimumTime", "");
        //call the timer from here when this happens....
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static class ReceiveLocationSettings extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Status status = intent.getParcelableExtra("status");
            try {
                status.startResolutionForResult(MainActivity.activity, 200);
            } catch (IntentSender.SendIntentException e) {
                // Ignore the error.
            }

        }
    }

    private boolean isButtonTracking() {
        SharedPreferences sharedPreferences = AppContext.get().getSharedPreferences(getString
                (R.string.BUTTON_STATUS), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(getString(R.string.button_flag),false);
    }

    private void displayErrorDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("OOps!!!");
        alertDialog.setMessage("You cannot change App settings while Tracking");
        //alertDialog.setIcon(R.drawable.);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }


}
