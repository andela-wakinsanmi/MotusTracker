package com.andela.motustracker.model;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Created by Spykins on 01/04/16.
 */
public class ActivityRecognitionDetector  extends IntentService{

    private static final String TAG ="ActivityRecognition";

    public ActivityRecognitionDetector() {
        super(TAG);
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
    private static void getFriendlyName(int detected_activity_type){
        switch (detected_activity_type ) {
            case DetectedActivity.IN_VEHICLE:
                Log.d("waleola", "IN_VEHICLE") ;
                break;
            case DetectedActivity.ON_BICYCLE:
                Log.d("waleola", "ON_BICYCLE") ;
                break;
            case DetectedActivity.ON_FOOT:
                Log.d("waleola", "ON_FOOT") ;
                break;

            case DetectedActivity.RUNNING:
                Log.d("waleola", "RUNNING") ;
                break;

            case DetectedActivity.STILL:
            case DetectedActivity.TILTING:
                Log.d("waleola", "STILL and TILTING") ;
                break;
            case DetectedActivity.UNKNOWN:
                Log.d("waleola", "UNKNOWN");
                break;
            case DetectedActivity.WALKING:
                Log.d("waleola", "WALKING") ;
                break;
            default:
                Log.d("waleola", "default") ;
        }
    }
}
