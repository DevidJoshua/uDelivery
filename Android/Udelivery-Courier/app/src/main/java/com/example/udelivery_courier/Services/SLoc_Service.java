package com.example.udelivery_courier.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;

import com.example.udelivery_courier.JSONparser.Operation;

import org.json.JSONArray;
import org.json.JSONException;

public class SLoc_Service extends Service {
    String email;
    Context context;
    SharedPreferences sharedPreferences;
    Operation op=new Operation();
    JSONArray arrayLocation;
    Location location;
    GPS_Service g=new GPS_Service(getApplicationContext());
    public SLoc_Service(Context context) {
        this.context = context;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        email=intent.getStringExtra("email");
//        System.out.println("Email: "+email);
//        location=g.getLocation();
//        sharedPreferences=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
//        String email = sharedPreferences.getString("cour_email", "missing");
//        try {
//
//            arrayLocation = new JSONArray(op.Update_location(email,location.getLongitude(),location.getLongitude()));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return  START_NOT_STICKY;
//    }
}
