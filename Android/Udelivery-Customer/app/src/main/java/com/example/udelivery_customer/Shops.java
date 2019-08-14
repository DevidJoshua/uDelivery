package com.example.udelivery_customer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.udelivery_customer.Adapter.AdapterVendor;
import com.example.udelivery_customer.JSONparser.Operation;
import com.example.udelivery_customer.Model.Model_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;

public class Shops extends AppCompatActivity {
    RecyclerView recyclerViewVendor;
    AdapterVendor adapterVendor;
    List<Model_vendor> lVendor;
    SimpleLocation mlocation;
    JSONArray ArrayVendor;
    Operation op=new Operation();

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mlocation=new SimpleLocation(this);
        if (!mlocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        final Button back= (Button) findViewById(R.id.btn_back_shop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundColor(Color.parseColor("#777777"));
                finish();
            }
        });

        recyclerViewVendor=(RecyclerView)findViewById(R.id.rv_shops_list);
        recyclerViewVendor.setLayoutManager(new LinearLayoutManager(this));
        lVendor=new ArrayList<>();
        LinearLayout nempty=(LinearLayout) findViewById(R.id.ll_nEmpty);
        LinearLayout empty=(LinearLayout) findViewById(R.id.ll_empty);
        try {
            mlocation.beginUpdates();
            System.out.println("Location: "+mlocation.getLatitude()+","+mlocation.getLongitude());
            String lat=Double.toString(mlocation.getLatitude());
            String lonG=Double.toString(mlocation.getLongitude());
            ArrayVendor = new JSONArray(op.getVendors(lat,lonG));
            System.out.println("Hii");
            if(ArrayVendor.length()>0){
                nempty.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                for (int i = 0; ArrayVendor.length() > i; i++) {
                    JSONObject jsonChildNode = ArrayVendor.getJSONObject(i);
                    final String vend_id= String.valueOf(jsonChildNode.optInt("vend_id"));
                    final String vend_distance= jsonChildNode.optString("distance");
                    final String vend_name = jsonChildNode.optString("vend_name");
                    final String vend_profile_image= jsonChildNode.optString("vend_profile_image");
                    final String vend_phone= jsonChildNode.optString("vend_phone");
                    final String vend_address= jsonChildNode.optString("vend_address");
                    final double vend_long= Double.parseDouble(jsonChildNode.optString("vend_pos_longtitude"));
                    final double vend_lat= Double.parseDouble(jsonChildNode.optString("vend_pos_latitude"));
                    lVendor.add(new Model_vendor(vend_lat,vend_long,vend_id,vend_name,vend_distance,vend_profile_image,vend_address,vend_phone));
                }
            }
            else {
                nempty.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterVendor=new AdapterVendor(this,lVendor);
        recyclerViewVendor.setAdapter(adapterVendor);
        recyclerViewVendor.setLayoutManager(new GridLayoutManager(this,3));
    }
    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        mlocation.beginUpdates();

        // ...
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        mlocation.endUpdates();

        // ...

        super.onPause();
    }

}
