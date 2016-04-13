package com.andela.motustracker.helper;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by Spykins on 31/03/16.
 */
public class LocationRequestHelper {
    private PendingResult<LocationSettingsResult> result;
    private boolean requestingLocationUpdates;
    private GoogleApiClient googleApiClient;

    public LocationRequestHelper(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        requestingLocationUpdates = true;
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.d("waleola", "Called checkUserLocationSettings*** LocationSettingsStatusCodes.RESOLUTION_REQUIRED: ..");

                        Intent intent = new Intent();
                        intent.setAction("com.andela.motustracker.LOCATION_SETTINGS");
                        intent.putExtra("status", status);
                        AppContext.get().sendBroadcast(intent);
                        requestingLocationUpdates = false;
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        requestingLocationUpdates = false;
                        break;
                }
            }
        });
        return mLocationRequest;
    }

}
