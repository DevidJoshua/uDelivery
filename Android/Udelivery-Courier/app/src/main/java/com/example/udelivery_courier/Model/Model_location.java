package com.example.udelivery_courier.Model;
public class Model_location {
    private  static double latitude;
    private  static double longtitu;

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Model_location.latitude = latitude;
    }

    public static double getLongtitu() {
        return longtitu;
    }

    public static void setLongtitu(double longtitu) {
        Model_location.longtitu = longtitu;
    }
}
