package com.andela.motustracker.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.andela.motustracker.helper.LocationRequestHelper;
import com.andela.motustracker.helper.GoogleClient;
import com.andela.motustracker.helper.NotifyServiceLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

/*
    Use a boolean, mRequestingLocationUpdates, to track whether location updates are currently turned on. In the activity's onResume() method, check whether location updates are currently active, and activate them if not:

@Override
public void onResume() {
    super.onResume();
    if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
        startLocationUpdates();
    }
}
 */
public class LocationHandler implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private static final String TAG = "LocationHandler";

    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    //private String mLastUpdateTime;
    private Context context;
    private NotifyServiceLocation notifyServiceLocation;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates; //save in sharedPreference

    public LocationHandler(Context context, NotifyServiceLocation notifyServiceLocation) {
        this.context = context;
        this.notifyServiceLocation = notifyServiceLocation;
        prepareService();
    }

    public synchronized void prepareService() {
       mGoogleApiClient = GoogleClient.getInstance(context,this,this).getGoogleApiClient();
        LocationRequestHelper locationRequestHelper = new LocationRequestHelper();
        mLocationRequest = locationRequestHelper.createLocationRequest();
        mRequestingLocationUpdates = locationRequestHelper.checkUserLocationSettings();
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

            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        if (mCurrentLocation != null) {
            //callback
        }

        if (mRequestingLocationUpdates) {
            //startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        notifyServiceLocation.getLocationCallBack(mCurrentLocation);
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

}
