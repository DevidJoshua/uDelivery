package com.example.udelivery_customer.Model;

public class Model_vendor{
    private String vend_id;
    private String vend_name;
    private String vend_distance;
    private String vend_image;
    private String vend_address;
    private String vend_phone;
    private double vend_latitude;
    private double vend_longitude;

    public double getVend_latitude() {
        return vend_latitude;
    }

    public void setVend_latitude(double vend_latitude) {
        this.vend_latitude = vend_latitude;
    }

    public double getVend_longitude() {
        return vend_longitude;
    }

    public void setVend_longitude(double vend_longitude) {
        this.vend_longitude = vend_longitude;
    }

    public Model_vendor(double Vend_latitude, double Vend_longitude, String Vend_id, String Vend_name, String Vend_distance, String Vend_image, String Vend_address, String Vend_phone) {
        vend_longitude=Vend_longitude;
        vend_latitude=Vend_latitude;
        vend_id=Vend_id;
        vend_name=Vend_name;
        vend_distance=Vend_distance;
        vend_image=Vend_image;
        vend_phone=Vend_phone;
        vend_address=Vend_address;
    }

    public String getVend_id() {
        return vend_id;
    }

    public void setVend_id(String vend_id) {
        this.vend_id = vend_id;
    }

    public String getVend_name() {
        return vend_name;
    }

    public void setVend_name(String vend_name) {
        this.vend_name = vend_name;
    }

    public String getVend_distance() {
        return vend_distance;
    }

    public void setVend_distance(String vend_distance) {
        this.vend_distance = vend_distance;
    }

    public String getVend_image() {
        return vend_image;
    }

    public void setVend_image(String vend_image) {
        this.vend_image = vend_image;
    }

    public String getVend_address() {
        return vend_address;
    }

    public void setVend_address(String vend_address) {
        this.vend_address = vend_address;
    }

    public String getVend_phone() {
        return vend_phone;
    }

    public void setVend_phone(String vend_phone) {
        this.vend_phone = vend_phone;
    }
}
