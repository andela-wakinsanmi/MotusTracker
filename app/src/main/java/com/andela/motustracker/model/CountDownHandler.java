package com.andela.motustracker.model;

import android.location.Location;
import android.os.CountDownTimer;
import android.util.Log;

import com.andela.motustracker.helper.App;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.andela.motustracker.manager.GeocoderManager;

/**
 * Created by Spykins on 02/04/16.
 */
public class CountDownHandler extends CountDownTimer implements NotifyServiceLocation  {

    public CountDownHandler(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }

    @Override
    public void onFinish() {
        Log.d("waleola", "OnFinish call");
        LocationHandler locationHandler = new LocationHandler(App.getContext(),this);
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

}
