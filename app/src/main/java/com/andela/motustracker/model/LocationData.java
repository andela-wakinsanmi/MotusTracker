package com.andela.motustracker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Spykins on 06/04/16.
 */
public class LocationData implements Parcelable {
    private String address;
    private double longitude;
    private double latitude;
    private String date;
    private Double timeSpent;

    public LocationData(String address, String date, double latitude, double longitude, Long timeSpent) {
        this.address = address;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeSpent = timeSpent.doubleValue();
    }

    protected LocationData(Parcel in) {
        address = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        date = in.readString();
    }

    public static final Creator<LocationData> CREATOR = new Creator<LocationData>() {
        @Override
        public LocationData createFromParcel(Parcel in) {
            return new LocationData(in);
        }

        @Override
        public LocationData[] newArray(int size) {
            return new LocationData[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent.doubleValue();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(date);
    }
}
