package com.example.udelivery_customer.Model;

public class Model_cart {

    private String vend_id,product_name;
    private int qty,id,total,product_price,product_id;

    public Model_cart(String vend_id, String product_name, int qty, int id, int total, int product_price, int product_id) {
        this.vend_id = vend_id;
        this.product_name = product_name;
        this.qty = qty;
        this.id = id;
        this.total = total;
        this.product_price = product_price;
        this.product_id = product_id;
    }

    public String getVend_id() {
        return vend_id;
    }

    public void setVend_id(String vend_id) {
        this.vend_id = vend_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
