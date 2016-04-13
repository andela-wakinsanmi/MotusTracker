package com.andela.motustracker.preference;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.NumberPicker;

import com.andela.motustracker.R;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.manager.SharedPreferenceManager;
import com.andela.motustracker.preference.NumberPickerPreference;

public class AppPreferences extends AppCompatActivity {
    private Toolbar toolbar;
    private static SharedPreferences sharedPreferences = PreferenceManager.
            getDefaultSharedPreferences(AppContext.get());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_preferences);

        toolbar = (Toolbar) findViewById(R.id.id_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_preferences);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SettingsFragment settingsFragment = new SettingsFragment();

        fragmentTransaction.add(R.id.id_fragment_inflate, settingsFragment, "SETTINGS_FRAGMENT");
        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        private String savedValue;
        private String sharedPreferenceKey = "minimumTime";
        private static final String NUMBER_PICKER_DEFAULT = "5";


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);

            NumberPickerPreference numberPickerPreference = (NumberPickerPreference) findPreference(sharedPreferenceKey);
            numberPickerPreference.setSummary(loadString() + " Minutes");
        }

        public String loadString() {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.get());
            savedValue = sharedPreferences.getString(sharedPreferenceKey, NUMBER_PICKER_DEFAULT);
            return savedValue;
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        }

        @Override
        public void onPause() {
            super.onPause();
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
