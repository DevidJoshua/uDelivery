package com.example.udelivery_customer.Model;

public class Model_finding {
    private static int result;
    private static boolean isCanceled;

    public static boolean isIsCanceled() {
        return isCanceled;
    }

    public static void setIsCanceled(boolean isCanceled) {
        Model_finding.isCanceled = isCanceled;
    }

    public static int getResult() {
        return result;
    }

    public static void setResult(int result) {
        Model_finding.result = result;
    }
}
