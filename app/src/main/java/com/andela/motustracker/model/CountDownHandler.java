package com.andela.motustracker.model;

import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.util.Log;

import com.andela.motustracker.helper.App;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.andela.motustracker.manager.GeocoderManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 02/04/16.
 */
public class CountDownHandler extends CountDownTimer implements NotifyServiceLocation  {

    public CountDownHandler(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time = timeFormatter(millisUntilFinished);
        updateTextViewTime(time);
        //setText(time);
    }

    @Override
    public void onFinish() {
        LocationHandler locationHandler = new LocationHandler(AppContext.get(),this);
    }

    @Override
    public void getLocationCallBack(Location location) {
        Log.d("waleola", location.getLatitude() + " is the Latitude returned");
        GeocoderManager geocoderManager = new GeocoderManager();
        geocoderManager.startIntentService(location);

        //start counting how Long the user spent... Add the number in the App Manifest....
        //Save it in database......... ones.... onTick

        //get the Location....
        //get the Address
        //Display it on The UI...
        //Start Counting the Time the user stays in the Location....
    }

    private String timeFormatter(long milliseconds) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void updateTextViewTime(String time) {
        //com.andela.motustracker.DETECT_TIMER
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.DETECT_TIMER");
        intent.putExtra("time", time);
        AppContext.get().sendBroadcast(intent);
    }



}
