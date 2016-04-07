package com.andela.motustracker.model;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

/**
 * Created by Spykins on 01/04/16.
 */
public class ActivityRecognitionScan implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "ActivityRecognition";
    private static GoogleApiClient googleApiClient;
    private Context context;


    public ActivityRecognitionScan(Context context) {
        this.context = context;
        if (context == null) {
            Log.e("null context", "true");
        }
    }

    public void startActivityRecognitionScan() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
        googleApiClient.connect();
    }

    public void stopActivityRecognitionScan() {
        try {
            googleApiClient.disconnect();
        } catch (IllegalStateException e) {
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent(context, ActivityRecognitionDetector.class);
        PendingIntent callbackIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 0, callbackIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");
    }

}
