package com.example.udelivery_customer.Model;

public class Model_product {
     private int product_id;
    private int product_price;
    private String product_image;
    private String product_name;
    private String product_caption;
    private String vend_id;

    public Model_product(String vend_id,int product_id, int product_price, String product_image, String product_name, String product_caption) {
        this.vend_id = vend_id;
        this.product_id = product_id;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_caption = product_caption;
    }

    public String getVend_id() {
        return vend_id;
    }

    public void setVend_id(String vend_id) {
        this.vend_id = vend_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_caption() {
        return product_caption;
    }

    public void setProduct_caption(String product_caption) {
        this.product_caption = product_caption;
    }
}
