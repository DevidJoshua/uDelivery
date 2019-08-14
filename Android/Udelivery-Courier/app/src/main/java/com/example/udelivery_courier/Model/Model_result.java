package com.example.udelivery_courier.Model;

public class Model_result {
    private static String handled_order;
    private static boolean isCanceled;
    private static int result;

    public static int getResult() {
        return result;
    }

    public static void setResult(int result) {
        Model_result.result = result;
    }

    public static boolean isIsCanceled() {
        return isCanceled;
    }

    public static void setIsCanceled(boolean isCanceled) {
        Model_result.isCanceled = isCanceled;
    }

    public static String getHandled_order() {
        return handled_order;
    }

    public static void setHandled_order(String handled_order) {
        Model_result.handled_order = handled_order;
    }
}
