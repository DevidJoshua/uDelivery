package com.example.udelivery_courier.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import com.example.udelivery_courier.JSONparser.Operation;

import org.json.JSONArray;
import org.json.JSONException;

public class GPS_Service extends Service implements LocationListener{
    private  Bundle data=new Bundle();
    private final Context context;
    boolean isGPSEnabled= false;
    boolean isNetworkEnabled= false;
    SharedPreferences sharedPreferences;
    Operation op=new Operation();
    JSONArray arrayLocation;
    String email;
    Location location,getloc;
    protected LocationManager locationManager;

    public GPS_Service(Context context)
    {
        super();
        this.context = context;
    }

    public Location getLocation(){
        try {
            locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled=locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            isNetworkEnabled=locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                if(isGPSEnabled){
                    if(location==null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,this);
                        if(locationManager!=null){
                            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
                //kalu lokasi nda dapat dari GPS dia m ambe dari jaringan
                if(location==null){
                    if(isNetworkEnabled){
                        if(location==null){
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,10,this);
                            if(locationManager!=null){
                                location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return location;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
//        location=getLocation();
//        sharedPreferences=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
//        String email = sharedPreferences.getString("cour_email", "missing");
//        try {
//
//            arrayLocation = new JSONArray(op.Update_location(email,location.getLongitude(),location.getLongitude()));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
