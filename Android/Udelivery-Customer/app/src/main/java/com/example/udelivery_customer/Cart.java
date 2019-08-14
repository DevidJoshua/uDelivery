package com.example.udelivery_customer;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.udelivery_customer.Adapter.AdapterCart;
import com.example.udelivery_customer.Adapter.AdapterProducts;
import com.example.udelivery_customer.Model.Model_Selected_vendor;
import com.example.udelivery_customer.Model.Model_cart;
import com.example.udelivery_customer.Model.Model_product;
import com.example.udelivery_customer.Sqlite.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    DataHelper dhcart;
    Model_Selected_vendor vendor=new Model_Selected_vendor();
    RecyclerView cart;
    List<Model_cart> lCart;
    AdapterCart adapterCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        final Button back=(Button)findViewById(R.id.btn_back_cart);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundColor(Color.parseColor("#777777"));
                Intent myintent = new Intent(getApplicationContext(), Products.class);
                myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myintent, 0);
                finish();
            }
        });
        final LinearLayout ll=(LinearLayout)findViewById(R.id.btn_p_order);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setBackgroundColor(Color.parseColor("#777777"));
                Intent i=new Intent(getApplicationContext(),Make_order.class);
                startActivity(i);
            }
        });
        load();
    }
    public void load()
    {
        dhcart=new DataHelper(this);
        TextView payment=(TextView) findViewById(R.id.tv_totalPayment);
        payment.setText(String.valueOf("Payment: Rp."+dhcart.get_sum_payment(vendor.getVend_id())));
        System.out.println("Payment: "+dhcart.get_sum_payment(vendor.getVend_id()));

        TextView quantity=(TextView) findViewById(R.id.tv_totalItems);
        quantity.setText(String.valueOf("Total Items:"+dhcart.get_sum_qty(vendor.getVend_id())));
        System.out.println(dhcart.get_sum_qty(vendor.getVend_id()));
        final LinearLayout ll=(LinearLayout)findViewById(R.id.btn_p_order);
        if(dhcart.get_sum_qty(vendor.getVend_id())<=0){
            ll.setEnabled(false);
        }
        else {
            ll.setEnabled(true);
        }


        LinearLayout Empty=(LinearLayout)findViewById(R.id.ll_empty_cart);
        LinearLayout noEmpty=(LinearLayout)findViewById(R.id.ll_cart);

        SQLiteDatabase db=dhcart.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT *FROM tb_cart where vend_id="+vendor.getVend_id(),null);
        dhcart=new DataHelper(this);
        int tot=dhcart.cout_tot_row(vendor.getVend_id());
        System.out.println("Total items: "+tot);
        if(tot==0){
            Empty.setVisibility(View.VISIBLE);
            noEmpty.setVisibility(View.GONE);
        }
        else{
                Empty.setVisibility(View.GONE);
                noEmpty.setVisibility(View.VISIBLE);
                cart=(RecyclerView)findViewById(R.id.rv_cart);
                cart.setLayoutManager(new LinearLayoutManager(this));
                lCart=new ArrayList<>();
                cursor.moveToFirst();
                do{
                    int id=cursor.getInt(0);
                    String vend_id=cursor.getString(1);
                    int product_id=cursor.getInt(2);
                    String product_name=cursor.getString(3);
                    int product_price=cursor.getInt(4);
                    int qty=cursor.getInt(5);
                    int total=cursor.getInt(6);
                    System.out.println("id cart: "+id);
                    lCart.add(new Model_cart(vend_id,product_name,qty,id,total,product_price,product_id));
                }while(cursor.moveToNext());
                adapterCart=new AdapterCart(this,lCart);
                cart.setAdapter(adapterCart);
            }
    }
}
