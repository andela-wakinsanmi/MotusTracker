package com.andela.motustracker.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.andela.motustracker.helper.App;
import com.andela.motustracker.helper.Constants;
import com.andela.motustracker.service.FetchAddressIntentService;

/**
 * Created by Spykins on 02/04/16.
 */
public class GeocoderManager {

    private AddressResultReceiver resultReceiver;
    private Location location;
    private String address;
    public void startIntentService(Location location) {
        this.location = location;
        Intent intent = new Intent(App.getContext(), FetchAddressIntentService.class);
        resultReceiver = new AddressResultReceiver(new Handler());
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        App.getContext().startService(intent);
    }

    @SuppressLint("ParcelCreator")
    public class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == Constants.SUCCESS_RESULT) {
                 address = resultData.getString(Constants.RESULT_DATA_KEY);
                Log.d("waleola",address);

                // *********I have the Address and the Coordiante here
                //display it in the UI...
            }

        }

    }
}
