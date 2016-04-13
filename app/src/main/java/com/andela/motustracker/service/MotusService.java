package com.andela.motustracker.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.andela.motustracker.manager.CountDownManager;
import com.andela.motustracker.manager.SharedPreferenceManager;
import com.andela.motustracker.model.ActivityRecognitionDetector;
import com.andela.motustracker.model.ActivityRecognitionScan;
import com.andela.motustracker.model.DistanceCalculator;
import com.andela.motustracker.model.LocationHandler;
import com.andela.motustracker.model.MockData;

/**
 * Created by Spykins on 30/03/16.
 */
public class MotusService extends Service{
    private ActivityRecognitionScan activityRecognitionScan;

    public MotusService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        activityRecognitionScan = new ActivityRecognitionScan(AppContext.get());
        activityRecognitionScan.startActivityRecognitionScan();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityRecognitionScan.stopActivityRecognitionScan();
        CountDownManager countDownManager = CountDownManager.getInstance();
        countDownManager.cancel();
        countDownManager.setCountDownManagerNull();
        ActivityRecognitionDetector.resetFlag();
        clearScreen();
        clearPreferenceData();
    }

    private void clearScreen() {

    }

    private void clearPreferenceData() {
        SharedPreferenceManager.getInstance().clearData();
    }

}



