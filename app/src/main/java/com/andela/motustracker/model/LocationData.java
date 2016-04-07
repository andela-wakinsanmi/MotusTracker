package com.andela.motustracker.model;

/**
 * Created by Spykins on 06/04/16.
 */
public class LocationData {
    private String address;
    private double longitude;
    private double latitude;
    private String  date;
    private double timeSpent;

    public LocationData(String address, String date, double latitude, double longitude, long timeSpent) {
        this.address = address;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeSpent = Double.doubleToLongBits(timeSpent);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    /*
    COLUMN_ADDRESS ("address"),
    COLUMN_LONGITUDE("longitude"),
    COLUMN_LATITUDE("latitude"),
    COLUMN_TIMESPENT("duration"),
    COLUMN_DATE("dateCreated");
     */

}
