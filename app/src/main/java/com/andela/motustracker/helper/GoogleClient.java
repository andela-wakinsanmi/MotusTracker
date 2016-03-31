package com.andela.motustracker.helper;

import android.content.Context;
import android.media.browse.MediaBrowser;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Spykins on 31/03/16.
 */
public class GoogleClient {

    private GoogleApiClient mGoogleApiClient ;
    private static GoogleClient googleClient;
    private Context context;
    private GoogleApiClient.ConnectionCallbacks listener;
    private GoogleApiClient.OnConnectionFailedListener secondListener;

    private GoogleClient(Context context, GoogleApiClient.ConnectionCallbacks listener,
                         GoogleApiClient.OnConnectionFailedListener secondListener) {
        this(context);
        this.secondListener = secondListener;
        this.listener = listener;
    }
    private GoogleClient(Context context) {
        this.context = context;
    }

    public static GoogleClient getInstance(Context context, GoogleApiClient.ConnectionCallbacks listener,
                                           GoogleApiClient.OnConnectionFailedListener secondListener) {
       return (googleClient == null) ? new GoogleClient(context,listener,secondListener) : googleClient;
    }

    public GoogleApiClient getGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(listener)
                    .addOnConnectionFailedListener(secondListener)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        return mGoogleApiClient;
    }
}
