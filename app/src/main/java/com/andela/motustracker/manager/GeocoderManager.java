package com.andela.motustracker.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.helper.GeocoderConstants;
import com.andela.motustracker.service.FetchAddressService;

/**
 * Created by Spykins on 02/04/16.
 */
public class GeocoderManager {

    private AddressResultReceiver resultReceiver;
    private Location location;
    private String address;

    public void startIntentService(Location location) {
        this.location = location;
        Intent intent = new Intent(AppContext.get(), FetchAddressService.class);
        resultReceiver = new AddressResultReceiver(new Handler());
        intent.putExtra(GeocoderConstants.RECEIVER.getRealName(), resultReceiver);
        intent.putExtra(GeocoderConstants.LOCATION_DATA_EXTRA.getRealName(), location);
        AppContext.get().startService(intent);
    }

    @SuppressLint("ParcelCreator")
    public class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == Integer.parseInt(GeocoderConstants.SUCCESS_RESULT.getRealName())) {
                address = resultData.getString(GeocoderConstants.RESULT_DATA_KEY.getRealName());
                sendBroadcast();
                addDataToSharedPreference();
            }
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.CUSTOM_INTENT");
        intent.putExtra("address", address);
        AppContext.get().sendBroadcast(intent);
    }

    private void addDataToSharedPreference() {
        SharedPreferenceManager.getInstance().setAddress(address);
    }

}
