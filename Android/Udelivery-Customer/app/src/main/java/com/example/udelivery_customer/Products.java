package com.example.udelivery_customer;

import android.app.Dialog;
import android.content.Intent;
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

import com.example.udelivery_customer.Adapter.AdapterProducts;
import com.example.udelivery_customer.Adapter.AdapterVendor;
import com.example.udelivery_customer.JSONparser.Operation;
import com.example.udelivery_customer.Model.Model_Selected_vendor;
import com.example.udelivery_customer.Model.Model_product;
import com.example.udelivery_customer.Model.Model_vendor;
import com.example.udelivery_customer.Sqlite.DataHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.delight.android.location.SimpleLocation;

public class Products extends AppCompatActivity {
    RecyclerView recyclerViewProduct;
    DataHelper dbCart=new DataHelper(this);;
    AdapterProducts adapterProduct;
    List<Model_product> lProduct;
    JSONArray ArrayProduct;
    Model_Selected_vendor vendor=new Model_Selected_vendor();
    Operation op=new Operation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Button clear=(Button)findViewById(R.id.btn_clear_cart);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCart.clear_cart(vendor.getVend_id());
                reload();
            }
        });
       final Button back=(Button)findViewById(R.id.btn_back_prod);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundColor(Color.parseColor("#777777"));
                finish();
            }
        });
        String iv=op.URL+"Vendor/Images/profile/"+vendor.getVend_image();
        CircleImageView profile=(CircleImageView)findViewById(R.id.iv_p_shop);Picasso.with(getApplicationContext())
                .load(iv)
                .into(profile);
        reload();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reload();
    }
    public void reload(){

        TextView name=(TextView)findViewById(R.id.tv_nameShop);
        name.setText(vendor.getVend_name());

        TextView phone=(TextView)findViewById(R.id.tv_phoneShop);
        phone.setText("Phone: "+vendor.getVend_phone());


        TextView qty=(TextView)findViewById(R.id.tv_tot_item);
        qty.setText(String.valueOf(dbCart.get_sum_qty(vendor.getVend_id()))+" Items");

        TextView payment=(TextView)findViewById(R.id.tv_tot_payment);
        payment.setText(String.valueOf("Payment: Rp."+dbCart.get_sum_payment(vendor.getVend_id())+",-"));

        Button cart=(Button)findViewById(R.id.btn_show_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Cart.class);
                startActivity(i);
            }
        });
        recyclerViewProduct=(RecyclerView)findViewById(R.id.rv_products);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));
        lProduct=new ArrayList<>();
        LinearLayout empty=(LinearLayout) findViewById(R.id.ll_empty2);
        try {
            ArrayProduct = new JSONArray(op.getProducts(vendor.getVend_id()));
            if(ArrayProduct.length()>0){
                empty.setVisibility(View.GONE);
                for (int i = 0; ArrayProduct.length() > i; i++) {
                    JSONObject jsonChildNode = ArrayProduct.getJSONObject(i);
                    final int product_id= Integer.parseInt(String.valueOf(jsonChildNode.optInt("product_id")));
                    final int product_price= Integer.parseInt(String.valueOf(jsonChildNode.optString("product_price")));
                    final String product_image= jsonChildNode.optString("product_image");
                    final String product_name= jsonChildNode.optString("product_name");
                    final String product_caption= jsonChildNode.optString("product_caption");
                    final String vend_id= jsonChildNode.optString("vend_id");
                    lProduct.add(new Model_product(vend_id,product_id,product_price,product_image,product_name,product_caption));
                }
            }
            else {
                empty.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterProduct=new AdapterProducts(this,lProduct);
        recyclerViewProduct.setAdapter(adapterProduct);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this,3));
    }
}

