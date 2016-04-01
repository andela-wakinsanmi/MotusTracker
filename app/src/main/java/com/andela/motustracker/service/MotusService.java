package com.andela.motustracker.service;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andela.motustracker.helper.NotifyActivityRecognition;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.andela.motustracker.model.ActivityRecognitionDetector;
import com.andela.motustracker.model.ActivityRecognitionScan;
import com.andela.motustracker.model.LocationHandler;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Created by Spykins on 30/03/16.
 */
public class MotusService extends Service {


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
        Thread thread = new Thread(new MotusServiceThread(startId,getApplicationContext(),intent));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    final class MotusServiceThread implements Runnable,NotifyServiceLocation, NotifyActivityRecognition {
        int service_id;
        Context context;
        private ActivityRecognitionDetector activityRecognitionDetector;
        private ActivityRecognitionResult result;
        private DetectedActivity mostProbableActivity;
        Intent intent;

        public MotusServiceThread(int service_id, Context context, Intent intent) {
            this.context = context;
            this.service_id = service_id;
            this.intent = intent;

            result = ActivityRecognitionResult.extractResult(intent);

            if (result != null) {

                mostProbableActivity = result.getMostProbableActivity();
                int confidence = mostProbableActivity.getConfidence();
                int activityType = mostProbableActivity.getType();
            }
        }

        @Override
        public void run() {
            LocationHandler locationHandler = new LocationHandler(context,this);
            ActivityRecognitionScan activityRecognitionScan = new ActivityRecognitionScan(context);
            activityRecognitionScan.startActivityRecognitionScan();
        }

        @Override
        public void getLocationCallBack(Location location) {
            Log.d("waleola",  location.getLatitude() + "called here");
            //check the Activity Recognition
        }

        @Override
        public void getActivityRecognitionCallBack(Bundle bundle) {
            Log.d("waleola", "Activity Recognition has changed..");
        }
    }
}



