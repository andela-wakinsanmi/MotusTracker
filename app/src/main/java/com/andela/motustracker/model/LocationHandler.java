package com.andela.motustracker.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.andela.motustracker.helper.GoogleClient;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class LocationHandler implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "LocationHandler";

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private Context context;
    private NotifyServiceLocation notifyServiceLocation;

    public LocationHandler(Context context, NotifyServiceLocation notifyServiceLocation) {
        this.context = context;
        this.notifyServiceLocation = notifyServiceLocation;
        prepareService();
    }

    public synchronized void prepareService() {
       mGoogleApiClient = GoogleClient.getInstance(context,this,this).getGoogleApiClient();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        if (mLastLocation != null) {
            //callback
            notifyServiceLocation.getLocationCallBack(mLastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
}
