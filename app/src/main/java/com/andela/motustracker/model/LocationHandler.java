package com.andela.motustracker.model;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
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

public class LocationHandler implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private static final String TAG = "LocationHandler";
    private Location currentLocation;
    private GoogleApiClient googleApiClient;
    private Context context;
    private NotifyServiceLocation notifyServiceLocation;
    private LocationRequest locationRequest;
    private boolean requestingLocationUpdates; //save in sharedPreference

    public LocationHandler(Context context, NotifyServiceLocation notifyServiceLocation) {
        this.context = context;
        this.notifyServiceLocation = notifyServiceLocation;
        prepareService();
    }

    public synchronized void prepareService() {
        googleApiClient = GoogleClient.getInstance(context, this, this).getGoogleApiClient();
        LocationRequestHelper locationRequestHelper = new LocationRequestHelper();
        locationRequest = locationRequestHelper.createLocationRequest();
        requestingLocationUpdates = locationRequestHelper.checkUserLocationSettings();
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
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (currentLocation != null) {
            //callback
        }

        if (requestingLocationUpdates) {
            //startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        notifyServiceLocation.getLocationCallBack(currentLocation);
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

}