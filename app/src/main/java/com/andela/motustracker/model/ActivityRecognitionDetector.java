package com.andela.motustracker.model;

import android.app.IntentService;
import android.content.Intent;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.manager.CountDownManager;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Created by Spykins on 01/04/16.
 */
public class ActivityRecognitionDetector extends IntentService {

    private static final String TAG = "ActivityRecognition";
    private static boolean hasStarted = false;
    private CountDownManager countDownManager = CountDownManager.getInstance();

    public ActivityRecognitionDetector() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        DetectedActivity mostProbableActivity = result.getMostProbableActivity();
        int confidence = mostProbableActivity.getConfidence();
        int activityType = mostProbableActivity.getType();
        if (confidence > 30) {
            activityType(activityType);
        }
    }

    private void activityType(int detected_activity_type) {
        String activityDetected = null;

        switch (detected_activity_type) {
            case DetectedActivity.IN_VEHICLE:
                countDownManager.cancel();
                hasStarted = false;
                activityDetected = "User in Vehicle";
                break;
            case DetectedActivity.ON_BICYCLE:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected = "User on Bicycle";
                break;
            case DetectedActivity.ON_FOOT:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected = "User on Foot";
                break;
            case DetectedActivity.RUNNING:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected = "User is Running";
                break;
            case DetectedActivity.WALKING:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected = "User is walking";
                break;
            case DetectedActivity.UNKNOWN:
                hasStarted = false;
                countDownManager.cancel();
                activityDetected = "unknown";
                break;
            case DetectedActivity.STILL:
            case DetectedActivity.TILTING:
                if (!hasStarted) {
                    countDownManager.start();
                    hasStarted = true;
                }
                activityDetected = "User is standing still";
        }
        sendBroadcast(activityDetected);
    }

    private void sendBroadcast(String activityDetected) {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.DETECTED_ACTIVITY");
        intent.putExtra("activityDetected", "Activity\\n" + activityDetected);
        AppContext.get().sendBroadcast(intent);
    }

    public static void resetFlag() {
        hasStarted = false;
    }
}
