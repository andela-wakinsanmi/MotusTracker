package com.andela.motustracker.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.andela.motustracker.helper.App;
import com.andela.motustracker.model.CountDownHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 31/03/16.
 */
public class CountDownManager  {
    private static CountDownManager countDownManager;
    private CountDownHandler countDownHandler;

    private CountDownManager() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        String valueSet = sharedPreferences.getString("minimumTime", "5");
        long longValue = TimeUnit.MINUTES.toMillis(Long.valueOf(valueSet));
        countDownHandler = new CountDownHandler(longValue,1000);
    }

    public static CountDownManager getInstance() {
        if(countDownManager == null) {
            return new CountDownManager();
        }
        return countDownManager;
    }

    public void start() {
        countDownHandler.start();
    }

    public void cancel() {
        countDownHandler.cancel();
    }


}
