package com.example.udelivery_courier.Model;

public class Model_user {
    private static String email;
    private static String password;
    private static String status;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Model_user.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Model_user.password = password;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Model_user.status = status;
    }
}
