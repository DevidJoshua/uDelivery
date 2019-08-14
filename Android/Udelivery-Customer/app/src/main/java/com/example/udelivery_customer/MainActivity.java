package com.example.udelivery_customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.udelivery_customer.JSONparser.Operation;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity {

    SharedPreferences session;
    Operation op=new Operation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        String name = session.getString("name", "missing");
        String image = session.getString("image", "missing");
        String nim = session.getString("nim", "missing");
        System.out.println(image+"-"+name);
        TextView cust_name=(TextView) findViewById(R.id.tv_custname);
        cust_name.setText(name);
        TextView cust_nim=(TextView)findViewById(R.id.tv_custnim);
        cust_nim.setText(nim);
        String iv=op.URL+"Customer/Images/"+image;
        CircleImageView avatar=(CircleImageView) findViewById(R.id.iv_profile);
        Picasso.with(getApplicationContext())
                .load(iv)
                .into(avatar);

        //Shop
        CardView shop=(CardView)findViewById(R.id.btn_shop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Shops.class);
                startActivity(i);
            }
        });
        //Order
        CardView order=(CardView)findViewById(R.id.btn_orders);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), Set_Pickup.class);
//                startActivity(i);
            }
        });
        //Logout
        CardView logout=(CardView)findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Session", MODE_PRIVATE);
                SharedPreferences.Editor editor1= pref.edit();
                editor1.clear();
                editor1.commit();
                Intent i = new Intent(getApplicationContext(), Login.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }

}
