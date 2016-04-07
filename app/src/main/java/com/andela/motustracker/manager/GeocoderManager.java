package com.andela.motustracker.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import com.andela.motustracker.helper.AppContext;
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
        Intent intent = new Intent(AppContext.get(), FetchAddressIntentService.class);
        resultReceiver = new AddressResultReceiver(new Handler());
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        AppContext.get().startService(intent);
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
                sendBroadcast();
                addDataToSharedPreference();
                //I have the Address and Location in this class...

            }

        }

    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.andela.motustracker.CUSTOM_INTENT");
        intent.putExtra("address", address);
        intent.putExtra("latitude", String.valueOf(location.getLatitude()));
        intent.putExtra("longitude", String.valueOf(location.getLongitude()));
        AppContext.get().sendBroadcast(intent);
    }

    private void addDataToSharedPreference() {
        //date, latitude, logitude, address
        SharedPreferenceManager.getInstance().setDate();
        SharedPreferenceManager.getInstance().setAddress(address);
        SharedPreferenceManager.getInstance().setLatitude(location.getLatitude());
        SharedPreferenceManager.getInstance().setLongitude(location.getLongitude());
    }

    /*public void sendResult() {
        Log.d("waleola","Called SendResult.....");

        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(App.getContext());
        Intent intent = new Intent(App.getContext(), HomeFragment.class);
            intent.putExtra("address", address);
            intent.putExtra("latitude",location.getLatitude());
            intent.putExtra("longitude", location.getLongitude());
        broadcaster.sendBroadcast(intent);

        Log.d("waleola", "Called SendResult.....");

    }*/
}
