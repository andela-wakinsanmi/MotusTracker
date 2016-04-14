package com.andela.motustracker.model;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.andela.motustracker.location.LocationRequestHelper;
import com.andela.motustracker.location.NotifyServiceLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationHandler implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "LocationHandler";
    private static Location currentLocation;
    private GoogleApiClient googleApiClient;
    private Context context;
    private NotifyServiceLocation notifyServiceLocation;
    private LocationRequest locationRequest;

    public LocationHandler(Context context, NotifyServiceLocation notifyServiceLocation) {
        this.context = context;
        this.notifyServiceLocation = notifyServiceLocation;
        prepareService();
    }

    public synchronized void prepareService() {
        //GoogleClient.getInstance(context, this, this);
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        LocationRequestHelper locationRequestHelper = new LocationRequestHelper(googleApiClient);
        locationRequest = locationRequestHelper.createLocationRequest();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }

    @Override
    public void onConnected(Bundle arg0) {
        try {
            //LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (currentLocation != null) {
            //callback
            notifyServiceLocation.getLocationCallBack(currentLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

}
