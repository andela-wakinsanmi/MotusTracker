package com.andela.motustracker.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.andela.motustracker.R;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.model.LocationData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Spykins on 06/04/16.
 */
public class SharedPreferenceManager {
    private Context context;
    private static SharedPreferenceManager sharedPreferenceManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferenceManager(){
        context = AppContext.get();
        sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.details_for_db), Context.MODE_PRIVATE);;

    }

    public static SharedPreferenceManager getInstance() {
        if(sharedPreferenceManager == null) {
            sharedPreferenceManager = new SharedPreferenceManager();
            return sharedPreferenceManager;
        }
        return sharedPreferenceManager;
    }

    public void setTimeSpent(long timeSpent) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.time), timeSpent);
        editor.apply();

    }

    public long getTimeSpent() {
        long time = sharedPreferences.getLong(context.getString(R.string.time), 0);
        Log.d("waleola", "called SharedPreferenceManager.. time .... = " + time);
        return time;
    }

    public void setDate() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.date), new Date().getTime());
        editor.apply();

    }

    public String getDate() {

        Date date = new Date (sharedPreferences.getLong(context.getString(R.string.date), 0));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public void setAddress(String address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.address), address);
        editor.apply();

    }

    public String getAddress() {
        return sharedPreferences.getString(context.getString(R.string.address), "");
    }

    public void  setLatitude(double latitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.latitude), Double.doubleToLongBits(latitude));
        editor.apply();
    }

    public double getLatitude() {
        return Double.longBitsToDouble(sharedPreferences.getLong(context.getString(R.string.latitude), 0));
    }

    public void  setLongitude(double longitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.longitude), Double.doubleToLongBits(longitude));
        editor.apply();
    }

    public double getLongitude() {
        return Double.longBitsToDouble(sharedPreferences.getLong(context.getString(R.string.longitude), 0));
    }

    public LocationData getLocationData() {
        return new LocationData(getAddress(),getDate(),getLatitude(), getLongitude(),
                getTimeSpent());
    }


}
