package com.example.udelivery_customer.Model;

public class Model_Selected_vendor {
    private static  String vend_id;
    private static  String vend_name;
    private  static String vend_distance;
    private static  String vend_image;
    private static  String vend_address;
    private static  String vend_phone;
    private static  double vend_latitude;
    private static  double vend_longitude;

    public static double getVend_latitude() {
        return vend_latitude;
    }

    public static void setVend_latitude(double vend_latitude) {
        Model_Selected_vendor.vend_latitude = vend_latitude;
    }

    public static double getVend_longitude() {
        return vend_longitude;
    }

    public static void setVend_longitude(double vend_longitude) {
        Model_Selected_vendor.vend_longitude = vend_longitude;
    }

    public static String getVend_id() {
        return vend_id;
    }

    public static void setVend_id(String vend_id) {
        Model_Selected_vendor.vend_id = vend_id;
    }

    public static String getVend_name() {
        return vend_name;
    }

    public static void setVend_name(String vend_name) {
        Model_Selected_vendor.vend_name = vend_name;
    }

    public static String getVend_distance() {
        return vend_distance;
    }

    public static void setVend_distance(String vend_distance) {
        Model_Selected_vendor.vend_distance = vend_distance;
    }

    public static String getVend_image() {
        return vend_image;
    }

    public static void setVend_image(String vend_image) {
        Model_Selected_vendor.vend_image = vend_image;
    }

    public static String getVend_address() {
        return vend_address;
    }

    public static void setVend_address(String vend_address) {
        Model_Selected_vendor.vend_address = vend_address;
    }

    public static String getVend_phone() {
        return vend_phone;
    }

    public static void setVend_phone(String vend_phone) {
        Model_Selected_vendor.vend_phone = vend_phone;
    }
}

