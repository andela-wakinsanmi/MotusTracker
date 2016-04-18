package com.andela.motustracker.helper;

/**
 * Created by Spykins on 02/04/16.
 */
public enum  GeocoderConstants {
    SUCCESS_RESULT("0"),
    FAILURE_RESULT("1"),
    RECEIVER("com.google.android.gms.location.sample.locationaddress.RECEIVER"),
    RESULT_DATA_KEY("com.google.android.gms.location.sample.locationaddress.RESULT_DATA_KEY"),
    LOCATION_DATA_EXTRA("com.google.android.gms.location.sample.locationaddress.LOCATION_DATA_EXTRA");

    GeocoderConstants(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    private final String realName;
}
