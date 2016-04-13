package com.andela.motustracker.model;

/**
 * Created by Spykins on 10/04/16.
 */
public class DistanceCalculator {
    private double latitude, longitude;

    public DistanceCalculator(double latitude, double longitude) {
        this.latitude = round(latitude, 6);
        this.longitude = round(longitude, 6);
    }

    public double getDistance(double latitude2, double longitude2) {

        latitude2 = round(latitude2, 6);
        longitude2 = round(longitude2, 5);

        final int earthRadius = 6375000;

        Double latDistance = toRad(latitude2 - latitude);
        Double lonDistance = toRad(longitude2 - longitude);
        Double haversineFomular = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(latitude)) * Math.cos(toRad(latitude2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double bearing = 2 * Math.atan2(Math.sqrt(haversineFomular), Math.sqrt(1 - haversineFomular));
        Double distance = earthRadius * bearing;

        return distance;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
