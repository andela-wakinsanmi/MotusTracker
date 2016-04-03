package com.andela.motustracker.model;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.andela.motustracker.helper.App;
import com.andela.motustracker.manager.CountDownManager;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Created by Spykins on 01/04/16.
 */
public class ActivityRecognitionDetector  extends IntentService{

    private static final String TAG ="ActivityRecognition";
    private boolean hasStarted;
    private CountDownManager countDownManager;

    public ActivityRecognitionDetector() {
        super(TAG);
        countDownManager = CountDownManager.getInstance();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity mostProbableActivity = result.getMostProbableActivity();
            int confidence = mostProbableActivity.getConfidence();
            int activityType = mostProbableActivity.getType();
            if(confidence > 80) {
                getFriendlyName(activityType);
            }
       }
    }

    /**
     * When supplied with the integer representation of the activity returns the activity as friendly string
     * @return a friendly string of the
     */
    private void getFriendlyName(int detected_activity_type){
        String activityDetected = null;
        switch (detected_activity_type ) {
            case DetectedActivity.IN_VEHICLE:
                countDownManager.cancel();
                hasStarted = false;
                activityDetected =  "User in Vehicle";
                break;
            case DetectedActivity.ON_BICYCLE:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected =  "User on Bicycle";
                break;
            case DetectedActivity.ON_FOOT:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected =  "User on Foot";
                break;

            case DetectedActivity.RUNNING:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected =  "User is Running";
                break;
            case DetectedActivity.WALKING:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected = "User is walking";
                break;
            case DetectedActivity.STILL:
            case DetectedActivity.TILTING:
                if (!hasStarted) {
                    countDownManager.start();
                    hasStarted = true;
                }
                activityDetected = "User is standing still";
                break;
        }

        sendBroadcast(activityDetected);
    }


    private void sendBroadcast(String activityDetected) {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.DETECTED_ACTIVITY");
        intent.putExtra("ectivityDetected", activityDetected);
        App.getContext().sendBroadcast(intent);
    }
}
