package com.example.udelivery_courier;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.udelivery_courier.Sessions.Session_login;
import com.example.udelivery_courier.JSONparser.Operation;
import com.example.udelivery_courier.Model.Model_location;
import com.example.udelivery_courier.Model.Model_user;
import com.example.udelivery_courier.Sessions.Session_login;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    Model_location mod=new Model_location();
    Model_user md=new Model_user();
    Operation op=new Operation();
    SharedPreferences session;
    Session_login check=new Session_login();
    JSONArray arrayOrder,arrayLatitude, arraylongtitude;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        session=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        final String tvemail = session.getString("cour_email", "missing");
        String image = session.getString("Image", "missing");
        String vehicle_number= session.getString("vehicle_number", "000");
        String vehicle_name= session.getString("vehicle_name", "Mobil");
        String name= session.getString("name", "missing");

        TextView user=(TextView)findViewById(R.id.tv_user);
        user.setText(name);
        String iv=op.URL+"Courier/Images/Courier/"+image;
        CircleImageView avatar=(CircleImageView) findViewById(R.id.iv_avatar);
        Picasso.with(getApplicationContext())
                .load(iv)
                .into(avatar);
        TextView vehicle=(TextView)findViewById(R.id.tv_vehicle);
        vehicle.setText(vehicle_name+"("+vehicle_number+")");
        CardView find=(CardView) findViewById(R.id.btn_findcust);
        CardView history=(CardView)findViewById(R.id.btn_history);
        CardView logout=(CardView)findViewById(R.id.btn_logout);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getApplicationContext(), Find.class);
                startActivityForResult(myintent, 0);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    arrayOrder = new JSONArray(op.set_offline(tvemail));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SharedPreferences mSettings = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("cour_email","example@udelivery.com");
                editor.putString("cour_islogin","false");
                editor.apply();
                try {
                    arraylongtitude = new JSONArray(op.setOnOff(md.getEmail(),0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
