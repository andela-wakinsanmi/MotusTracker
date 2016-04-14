package com.andela.motustracker.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
    private PendingIntent callbackIntent;

    public ActivityRecognitionScan(Context context) {
        this.context = context;
        makeConnection();
        if (context == null) {
        }
    }

    private void makeConnection() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
    }

    public void startActivityRecognitionScan() {
        googleApiClient.connect();
    }

    public void stopActivityRecognitionScan() {
        try {
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(googleApiClient, callbackIntent);
        } catch (IllegalStateException e) {
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent(context, ActivityRecognitionDetector.class);
        callbackIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 0, callbackIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

}
