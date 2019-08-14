package com.example.udelivery_courier.JSONparser;


public class Operation extends Connection {
    public String URL = "http://192.168.1.6:80/uDelivery/";
//    String URL = "http://udelivery.000webhostapp.com:80/Courier/server.php";
    //Ganti deng ngana pe IP server ini:: 127.0.0.1 cuma ip loopback jadi kalu mo implementasi di emulator
    String url = "";
    String response = "";

    public String Login(String email, String password) {
        try
        {
            url = URL + "Courier/server.php?operation=login&cour_email="+email+"&cour_password="+password;
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String Update_location(String email, String latitude, String longtitude) {
        try
        {
            url = URL + "Courier/server.php?operation=update_location&email="+email+"&latitude="+latitude+"&longtitude="+longtitude;
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String store_location(String email) {
        try
        {
            url = URL + "Courier/server.php?operation=get_longtitude&cour_email="+email;
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String get_all_trans_data(String email) {
        try
        {
            url = URL + "Courier/server.php?cour_email="+email+"&operation=get_trans_data";
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String setOnOff(String email, int status) {
        try
        {
            url = URL + "Courier/server.php?cour_email="+email+"&operation=set_onof&status="+status;
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String get_order_data(String order_id) {
        try
        {
            url = URL + "Courier/server.php?order_id="+order_id+"&operation=get_order_data";
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String cancel_order(String order_id,String cour_email) {
        try
        {
            url = URL + "Courier/server.php?order_id="+order_id+"&cour_email="+cour_email+"&operation=cancel_order";
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }
    public String set_offline(String cour_email) {
        try {
            url = URL + "Courier/server.php?operation=set_offline&cour_email="+cour_email;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
    public String confirm_order(String order_id,String cour_email) {
        try
        {
            url = URL + "Courier/server.php?order_id="+order_id+"&cour_email="+cour_email+"&operation=confirm_order";
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }

}

