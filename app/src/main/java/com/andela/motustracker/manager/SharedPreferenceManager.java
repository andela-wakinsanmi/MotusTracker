package com.andela.motustracker.manager;

import android.content.Context;
import android.content.SharedPreferences;

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

    private SharedPreferenceManager() {
        context = AppContext.get();
        sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.details_for_db), Context.MODE_PRIVATE);
        ;
    }

    public static SharedPreferenceManager getInstance() {
        if (sharedPreferenceManager == null) {
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
        return sharedPreferences.getLong(context.getString(R.string.time), 0);
    }

    public void setDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateFormat = simpleDateFormat.format(date);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.date), dateFormat.split(" ")[0]);
        editor.apply();
    }

    public String getDate() {
        return sharedPreferences.getString(context.getString(R.string.date), " ");
    }

    public void setAddress(String address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.address), address);
        editor.apply();

    }

    public String getAddress() {
        return sharedPreferences.getString(context.getString(R.string.address), "");
    }

    public void setLatitude(double latitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.latitude), Double.doubleToLongBits(latitude));
        editor.apply();
    }

    public double getLatitude() {
        return Double.longBitsToDouble(sharedPreferences.getLong(context.getString(R.string.latitude), 0));
    }

    public void setLongitude(double longitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.longitude), Double.doubleToLongBits(longitude));
        editor.apply();
    }

    public double getLongitude() {
        return Double.longBitsToDouble(sharedPreferences.getLong(context.getString(R.string.longitude), 0));
    }

    public LocationData getLocationData() {
        if(getAddress().equals("") || getAddress() == null) {
            setAddress(AppContext.get().getString(R.string.location_unknown));
        }
        return new LocationData(getAddress(), getDate(), getLatitude(), getLongitude(),
                getTimeSpent());
    }

    public void clearData() {
        setLatitude(0);
        setLongitude(0);
        setTimeSpent(0);
        setAddress("");
    }

}
