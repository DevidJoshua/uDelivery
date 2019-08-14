package com.example.udelivery_courier.Model;

public class Model_switch {
    private static int isActive;
    private static String longtitude;
    private static String latitude;

    public static String getLongtitude() {
        return longtitude;
    }

    public static void setLongtitude(String longtitude) {
        Model_switch.longtitude = longtitude;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        Model_switch.latitude = latitude;
    }

    public static int getIsActive() {
        return isActive;
    }

    public static void setIsActive(int isActive) {
        Model_switch.isActive = isActive;
    }
}
