package com.andela.motustracker.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import com.andela.motustracker.R;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.helper.GeocoderConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Spykins on 02/04/16.
 */
public class FetchAddressService extends IntentService {
    private static final String TAG = "FetchAddress";
    private Location location;
    protected ResultReceiver resultReceiver;

    public FetchAddressService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";

        location = intent.getParcelableExtra(
                GeocoderConstants.LOCATION_DATA_EXTRA.getRealName());
        resultReceiver = intent.getParcelableExtra(GeocoderConstants.RECEIVER.getRealName());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            errorMessage = getString(R.string.service_not_available);
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = getString(R.string.invalid_lat_long_used);
        }

        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
            }
            sendBroadcast(errorMessage);
            deliverResultToReceiver(Integer.parseInt(GeocoderConstants.
                    FAILURE_RESULT.getRealName()), errorMessage);

        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }

            deliverResultToReceiver(Integer.parseInt(GeocoderConstants.SUCCESS_RESULT.getRealName()),
                    TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(GeocoderConstants.RESULT_DATA_KEY.getRealName(), message);
        resultReceiver.send(resultCode, bundle);
    }

    private void sendBroadcast(String error) {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.CUSTOM_INTENT");
        intent.putExtra("address", error + "\n check your internet connection");
        intent.putExtra("latitude", String.valueOf(location.getLatitude()));
        intent.putExtra("longitude", String.valueOf(location.getLongitude()));
        AppContext.get().sendBroadcast(intent);
    }
}
