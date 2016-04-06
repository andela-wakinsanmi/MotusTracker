package com.andela.motustracker.model;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.util.Log;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.andela.motustracker.manager.GeocoderManager;
import com.andela.motustracker.manager.SharedPreferenceManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 02/04/16.
 */
public class CountDownHandler extends CountDownTimer implements NotifyServiceLocation  {
    Context context = AppContext.get();
    long timeSetByUser;

    public CountDownHandler(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        timeSetByUser = millisInFuture;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time = timeFormatter(millisUntilFinished);
        Log.d("waleola", "called onTick");
        updateTextViewTime(time);
    }

    @Override
    public void onFinish() {
        Log.d("waleola", "called onFinish");
        Long currentTimeStamp = System.currentTimeMillis()/1000;
        Long timeSpentByUser = currentTimeStamp - timeSetByUser;
        SharedPreferenceManager.getInstance().setTimeSpent(timeSpentByUser);
        LocationHandler locationHandler = new LocationHandler(AppContext.get(),this);
    }

    @Override
    public void getLocationCallBack(Location location) {
        GeocoderManager geocoderManager = new GeocoderManager();
        geocoderManager.startIntentService(location);
    }

    private String timeFormatter(long milliseconds) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void updateTextViewTime(String time) {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.DETECT_TIMER");
        intent.putExtra("time", time);
        AppContext.get().sendBroadcast(intent);
    }


}
