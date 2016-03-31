package com.andela.motustracker.helper;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andela.motustracker.model.LocationHandler;

/**
 * Created by Spykins on 30/03/16.
 */
public class MotusService extends Service {

    public MotusService() {
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new MotusServiceThread(startId,getApplicationContext()));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    final class MotusServiceThread implements Runnable,NotifyServiceLocation {
        int service_id;
        Context context;

        public MotusServiceThread(int service_id, Context context) {
            this.context = context;
            this.service_id = service_id;
        }

        @Override
        public void run() {
            //Fetch the Location from service
            //Toast.makeText(context,"Called here",Toast.LENGTH_LONG).show();

            LocationHandler locationHandler = new LocationHandler(context,this);
        }

        @Override
        public void getLocationCallBack(Location location) {
            Log.d("waleola",  location.getLatitude() + "called here");
        }
    }
}



