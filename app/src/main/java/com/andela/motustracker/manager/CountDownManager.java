package com.andela.motustracker.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.handler.CountDownHandler;
import com.andela.motustracker.model.LocationData;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 31/03/16.
 */
public class CountDownManager {
    private static CountDownManager countDownManager;
    private CountDownHandler countDownHandler;
    private SharedPreferenceManager sharedPreferenceManager;

    private CountDownManager() {
        sharedPreferenceManager = SharedPreferenceManager.getInstance();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.get());
        String valueSet = sharedPreferences.getString("minimumTime", "5");
        long longValue = TimeUnit.MINUTES.toMillis(Long.valueOf(valueSet));
        countDownHandler = new CountDownHandler(longValue, 1000);
    }

    public static CountDownManager getInstance() {
        if (countDownManager == null) {
            countDownManager = new CountDownManager();
        }
        return countDownManager;
    }

    public void start() {
        countDownHandler.start();
    }

    public void cancel() {
        countDownHandler.updateTextViewTime("00:00:00");
        countDownHandler.cancel();
        if (hasTimeInSharedPreference()) {
            saveInDatabase();
        }
    }

    private boolean hasTimeInSharedPreference() {
        return sharedPreferenceManager.getTimeSpent() != 0;
    }

    private void saveInDatabase() {
        Date date = new Date();
        long currentTimeStamp = date.getTime();
        long previousSavedTime = sharedPreferenceManager.getTimeSpent();
        long timeSpentInLocation = currentTimeStamp - previousSavedTime;
        sharedPreferenceManager.setTimeSpent(timeSpentInLocation);
        DbManager dbManager = new DbManager();
        LocationData locationData = sharedPreferenceManager.getLocationData();
        dbManager.insertIntoDb(locationData);
        sharedPreferenceManager.clearData();
    }

    public void setCountDownManagerNull() {
        countDownManager = null;
    }

}
