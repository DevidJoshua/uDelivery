package com.example.udelivery_courier.Model;

public class Model_history {
    private static int trans_id;
    private static String Image;
    private static String status;
    private static String name;
    private static String date;

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Model_history.date = date;
    }

    public static int getTrans_id() {
        return trans_id;
    }

    public static void setTrans_id(int trans_id) {
        Model_history.trans_id = trans_id;
    }

    public static String getImage() {
        return Image;
    }

    public static void setImage(String image) {
        Image = image;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Model_history.status = status;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Model_history.name = name;
    }
}
