package com.andela.motustracker.model;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
        switch (detected_activity_type ) {
            case DetectedActivity.IN_VEHICLE:
                countDownManager.cancel();
                hasStarted = false;
                Log.d("waleola", "IN_VEHICLE") ;
                break;
            case DetectedActivity.ON_BICYCLE:
                hasStarted = false;
                countDownManager.cancel();
                Log.d("waleola", "ON_BICYCLE") ;
                break;
            case DetectedActivity.ON_FOOT:
                hasStarted = false;
                countDownManager.cancel();
                Log.d("waleola", "ON_FOOT") ;
                break;

            case DetectedActivity.RUNNING:
                hasStarted = false;
                countDownManager.cancel();
                Log.d("waleola", "RUNNING") ;
                break;
            case DetectedActivity.WALKING:
                hasStarted = false;
                countDownManager.cancel();
                Log.d("waleola", "WALKING") ;
                break;
            case DetectedActivity.STILL:
            case DetectedActivity.TILTING:
                if (!hasStarted) {
                    countDownManager.start();
                    hasStarted = true;
                }
                Log.d("waleola", "STILL and TILTING") ;
                break;


        }
    }
}
