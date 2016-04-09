package com.andela.motustracker.model;

/**
 * Created by Spykins on 06/04/16.
 */
public class LocationData {
    private String address;
    private double longitude;
    private double latitude;
    private String  date;
    private Double timeSpent;

    public LocationData(String address, String date, double latitude, double longitude, Long timeSpent) {
        this.address = address;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeSpent = timeSpent.doubleValue();

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

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent.doubleValue();
    }

    public void upDateTimeSpent(double newTime) {
        this.timeSpent += newTime;
    }
}
