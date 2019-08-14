package com.example.udelivery_customer.JSONparser;

public class Operation extends Connection {
    public String URL = "http://192.168.1.6:80/uDelivery/";
    //    String URL = "http://udelivery.000webhostapp.com:80/Courier/server.php";
    //Ganti deng ngana pe IP server ini:: 127.0.0.1 cuma ip loopback jadi kalu mo implementasi di emulator
    String url = "";
    String response = "";

    public String Login(String nim, String password) {
        try {
            url = URL + "Customer/server.php?operation=login&cust_nim=" + nim + "&cust_password=" + password;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getVendors(String latitude, String longitude) {
        try {
            url = URL + "Customer/server.php?operation=get_nearest_vendors&o_latitude=" + latitude + "&o_longitude=" + longitude;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getProducts(String vend_id) {
        try {
            url = URL + "Customer/server.php?operation=get_products&+vend_id=" + vend_id;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getAvailableCourier(String latitude, String longitude) {
        try {
            url = URL + "Customer/server.php?operation=get_available_courier&my_latitude=" + latitude + "&my_longitude=" + longitude;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String placeOrder(String vend_id,String cour_email, String cust_nim, String vend_name, String vend_address, String pickup_lat, String pickup_long, String vend_pos_lat, String vend_pos_long, String or_distance, String or_status) {
        try {
            url = URL + "Customer/server.php?operation=place_order&vend_id="+vend_id+"&cour_email="+ cour_email + "&cust_nim=" + cust_nim + "&vend_name=" + vend_name + "&vend_address=" + vend_address + "&pickup_lat=" + pickup_lat + "&pickup_long=" + pickup_long + "&vend_pos_lat=" + vend_pos_lat + "&vend_pos_long=" + vend_pos_long + "&or_distance=" + or_distance + "&or_status=" + or_status;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
    public String insert_product(String or_id,String product_id,String product_name,String cust_nim, String trans_quantity, String trans_price) {
        try {
            url = URL + "Customer/server.php?operation=insert_products&or_id="+or_id+"&product_id="+product_id+"&product_name="+product_name+"&cust_nim="+cust_nim+"&trans_quantity="+trans_quantity+"&trans_price="+trans_price;
            System.out.println("URL: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
    public String check_order_status(String order_id) {
        try
        {
            url = URL + "Courier/server.php?order_id="+order_id+"&operation=check_order_status";
            System.out.println("URL: " + url);
            response = call(url);
        }
        catch (Exception e)
        {
        }
        return response;
    }

}

