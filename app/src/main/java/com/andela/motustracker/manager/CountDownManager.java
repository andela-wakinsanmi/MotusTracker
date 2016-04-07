package com.andela.motustracker.manager;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.model.CountDownHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 31/03/16.
 */
public class CountDownManager  {
    private static CountDownManager countDownManager;
    private CountDownTimer countDownHandler;
    //private boolean isCountingDown = true;

    private CountDownManager() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.get());
        String valueSet = sharedPreferences.getString("minimumTime", "5");
        long longValue = TimeUnit.MINUTES.toMillis(Long.valueOf(valueSet));
        countDownHandler = new CountDownHandler(longValue,1000);
    }

    public static CountDownManager getInstance() {
        if(countDownManager == null) {
            countDownManager = new CountDownManager();
        }
        return countDownManager;
    }

    public void start() {
        //Check if it has finished oreviously...

        //if(!hasTimeInSharedPreference()) {
            countDownHandler.cancel();
            Log.d("waleola", "called start..in CountDownManager");
                countDownHandler.start();
        //}
    }

    public void cancel() {
        //if i cancel, i want to check if i finished and save anything previously...
        //I want to save the data into database... if I have.......
        //isCountingDown = true;
        countDownHandler.cancel();
        Log.d("waleola", "called cancel..in CountDownManager");

        Long currentTimeStamp = System.currentTimeMillis()/1000;
        if(hasTimeInSharedPreference()) {
            long previousSavedTime = SharedPreferenceManager.getInstance().getTimeSpent();
            long timeSpentInLocation = currentTimeStamp - previousSavedTime;
            SharedPreferenceManager.getInstance().setTimeSpent(timeSpentInLocation);
            Log.d("waleola", "called cancel..in the IFFF** CountDownManager.... time = " + timeSpentInLocation);

            ////save data to database...... and reset all data
            DbManager dbManager = new DbManager();
            dbManager.insertIntoDb(SharedPreferenceManager.getInstance().getLocationData());
            SharedPreferenceManager.getInstance().setTimeSpent(0);
        }
    }

    private boolean hasTimeInSharedPreference() {
        return SharedPreferenceManager.getInstance().getTimeSpent() != 0;
    }


}
