package com.andela.motustracker.model;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.location.NotifyServiceLocation;
import com.andela.motustracker.manager.GeocoderManager;
import com.andela.motustracker.manager.SharedPreferenceManager;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 02/04/16.
 */
public class CountDownHandler extends CountDownTimer implements NotifyServiceLocation {
    private long timeSetByUser;
    private SharedPreferenceManager sharedPreferenceManager;

    public CountDownHandler(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        timeSetByUser = millisInFuture;
        sharedPreferenceManager = SharedPreferenceManager.getInstance();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time = timeFormatter(millisUntilFinished);
        updateTextViewTime(time);
    }

    @Override
    public void onFinish() {
        updateTextViewTime("00:00:00");
        Date date = new Date();
        long currentTimeStamp = date.getTime();

        Long timeSpentByUser = currentTimeStamp - timeSetByUser;
        sharedPreferenceManager.setTimeSpent(timeSpentByUser);
        LocationHandler locationHandler = new LocationHandler(AppContext.get(), this);
    }

    @Override
    public void getLocationCallBack(Location location) {
        sharedPreferenceManager.setDate();
        sharedPreferenceManager.setLatitude(location.getLatitude());
        sharedPreferenceManager.setLongitude(location.getLongitude());

        GeocoderManager geocoderManager = new GeocoderManager();
        geocoderManager.startIntentService(location);
    }

    private String timeFormatter(long milliseconds) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
    }

    public void updateTextViewTime(String time) {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.DETECT_TIMER");
        intent.putExtra("time", time);
        AppContext.get().sendBroadcast(intent);
    }

}
