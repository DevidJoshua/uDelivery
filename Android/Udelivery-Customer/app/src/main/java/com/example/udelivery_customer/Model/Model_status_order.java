package com.example.udelivery_customer.Model;

public class Model_status_order {
    private static String order_status;

    public static String getOrder_status() {
        return order_status;
    }

    public static void setOrder_status(String order_status) {
        Model_status_order.order_status = order_status;
    }
}
