package com.example.udelivery_courier;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
//import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udelivery_courier.Model.Model_result;
import com.example.udelivery_courier.Model.Model_switch;
import com.example.udelivery_courier.Sessions.Session_switch;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.example.udelivery_courier.JSONparser.Operation;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permission;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import de.hdodenhof.circleimageview.CircleImageView;
import im.delight.android.location.SimpleLocation;

public class Find extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    Model_result res=new Model_result();
    SharedPreferences session;
    JSONArray arrayLocation,arrayOrder;
    Model_switch onof=new Model_switch();
    Operation op=new Operation();
//    private AtomicBoolean running=new AtomicBoolean(false);
    public  SimpleLocation simpleLocationlocation;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        LinearLayout back=(LinearLayout)findViewById(R.id.btn_backf);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myintent, 0);
                finish();
            }
        });
        simpleLocationlocation = new SimpleLocation(this);

        if (!simpleLocationlocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }
        System.out.println("Location: "+simpleLocationlocation.getLatitude()+","+simpleLocationlocation.getLongitude());
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final Switch location=(Switch)findViewById(R.id.sw_location);
        location.setEnabled(false);
        request_permission();
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        final TextView stat=(TextView)findViewById(R.id.tv_stat);
        final TextView finding=(TextView)findViewById(R.id.tv_finding);
        final Switch location=(Switch)findViewById(R.id.sw_location);
        session=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        final String tvemail = session.getString("cour_email", "missing");

        session=getApplicationContext().getSharedPreferences("Switch", Context.MODE_PRIVATE);
        final String sSwitch = session.getString("onof", "off");
        if(sSwitch.equals("on")){
            location.setChecked(true);
        }
        else{
            location.setChecked(false);
        }
        location.setChecked(false);
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String latitude = String.format("%.10f",simpleLocationlocation.getLatitude());
                String longtitude = String.format("%.10f",simpleLocationlocation.getLongitude());
                Session_switch sw=new Session_switch();
                if(location.isChecked()){
                    onof.setIsActive(1);
                    simpleLocationlocation.beginUpdates();
                    sw.set_onof(getApplicationContext(),"on");
                    setOnOff(1,tvemail,latitude,longtitude);
                    session=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
                    String email = session.getString("cour_email", "missing");
                    LatLng mylocation = new LatLng(simpleLocationlocation.getLatitude(),simpleLocationlocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,17),4000,null);
                    mMap.addMarker(new MarkerOptions().position(mylocation).title("You")).setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_me));
                    stat.setTextColor(Color.GREEN);
                    stat.setText("LOCATION ON");
                    finding.setText("Finding...");
                }
                else {
                    try {
                        arrayOrder = new JSONArray(op.set_offline(tvemail));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    onof.setIsActive(0);
                    simpleLocationlocation.endUpdates();
                    sw.set_onof(getApplicationContext(),"off");
                    setOnOff(0,tvemail,latitude,longtitude);
                    Intent i =new Intent(getApplicationContext(),LocationServices.class);
                    LatLng mylocation = new LatLng(simpleLocationlocation.getLatitude(),simpleLocationlocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,5),4000,null);
                    mMap.addMarker(new MarkerOptions().position(mylocation).title("You")).setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_me));
                    stat.setTextColor(Color.RED);
                    stat.setText("LOCATION OFF");
                    finding.setText("Find Your Customer");
                }
            }
        });
    }
    void setOnOff(final int status, String uemail, final String latitude, final String longitude)
    {
    final String email=uemail;
        final Handler handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1==1){
                    showResultDialog(res.getHandled_order());
                }
            }
        };

    class runThread extends Thread{
                public void run(){
                    try {
                        while (res.getResult()!=1){
                                System.out.println("Updating Location"+": " + latitude + "," + longitude);
                                    arrayLocation = new JSONArray(op.Update_location(email, latitude,longitude));
                                    JSONObject jsonChildNode = arrayLocation.getJSONObject(0);
                                    res.setResult(Integer.parseInt(jsonChildNode.getString("result")));
                                    System.out.println("Result:"+res.getResult());
                            Thread.sleep(10000);
                        }
                        if(res.getResult()==1){
                            try {
                                arrayLocation = new JSONArray(op.Update_location(email, latitude,longitude));
                                JSONObject jsonChildNode = arrayLocation.getJSONObject(0);
                                res.setHandled_order(jsonChildNode.getString("cour_order_handle"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Message msg=new Message();
                            msg.arg1=1;
                            handler.sendMessage(msg);
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        }
        switch (status){
            case 1:
//                running.set(true);
                runThread t=new runThread();
                t.start();
                break;
            case 0:
//                running.set(false);
//                runThread t2=new runThread();
//                t2.stop();
                break;
        }

    }
    void showResultDialog(String o_id){
        try {
            session=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
            final String tvemail = session.getString("cour_email", "missing");

            arrayLocation = new JSONArray(op.get_order_data(res.getHandled_order()));
            for (int i = 0; i < arrayLocation.length(); i++) {
                JSONObject jsonChildNode = arrayLocation.getJSONObject(0);
                final String cust_image=jsonChildNode.optString("cust_Image");
                final String or_id=jsonChildNode.optString("or_id");
                final String cour_email=jsonChildNode.optString("cour_email");
                final String cust_nim=jsonChildNode.optString("cust_nim");
                final String vend_id=jsonChildNode.optString("vend_id");
                final String vend_name=jsonChildNode.optString("vend_name");
                final String vend_address=jsonChildNode.optString("vend_address");
                final String qty=jsonChildNode.optString("payment");
                final String cust_name=jsonChildNode.optString("cust_name");
                final String payment=jsonChildNode.optString("qty");
                final double pickup_lat=Double.parseDouble(jsonChildNode.optString("pickup_lat"));
                final double pickup_long=Double.parseDouble(jsonChildNode.optString("pickup_long"));
                final String or_distance=jsonChildNode.optString("or_distance");
                final String phone_cust=jsonChildNode.optString("Cust_phone");
                final String phone_vend=jsonChildNode.optString("Vend_phone");

                final Dialog dialog = new Dialog(Find.this);
                dialog.setTitle("New Order Found");

                dialog.setContentView(R.layout.dialog_result);
                TextView c_phone=(TextView) dialog.findViewById(R.id.tv_phone_cust);
                c_phone.setText("Customer phone: "+phone_cust);
                TextView v_phone=(TextView) dialog.findViewById(R.id.tv_phone_vend);
                v_phone.setText("Customer phone: "+phone_vend);
                CircleImageView avatar = (CircleImageView) dialog.findViewById(R.id.iv_cust_profile);
                String iv = op.URL + "Customer/Images/"+cust_image;
                Picasso.with(getApplicationContext())
                        .load(iv)
                        .into(avatar);
                TextView v_name = (TextView) dialog.findViewById(R.id.tv_d_vendor);
                v_name.setText(vend_name);
                TextView name = (TextView) dialog.findViewById(R.id.tv_cust_name);
                name.setText(cust_name);
                TextView tvpayment = (TextView) dialog.findViewById(R.id.tv_payment);
                tvpayment.setText("Payment:"+String.valueOf(payment));
                TextView tvqty = (TextView) dialog.findViewById(R.id.tv_qty);
                tvqty.setText("Total items:"+qty);
                TextView total = (TextView) dialog.findViewById(R.id.tv_delivery_cost_total);
                total.setText("Total Payment: Rp."+String.valueOf(Integer.parseInt(payment)+10000));
                Button confirm = (Button) dialog.findViewById(R.id.btn_confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Toast.makeText(getApplicationContext(),"Delivering",Toast.LENGTH_LONG).show();
                            arrayOrder = new JSONArray(op.confirm_order(or_id,tvemail));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button close = (Button) dialog.findViewById(R.id.btn_cancel);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            arrayOrder = new JSONArray(op.cancel_order(or_id,tvemail));

                            Intent i=new Intent(getApplicationContext(),Find.class);
                            startActivity(i);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        simpleLocationlocation.beginUpdates();
    }

    @Override
    protected void onPause() {
        simpleLocationlocation.endUpdates();
        super.onPause();
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_me);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    void  request_permission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        final Switch location=(Switch)findViewById(R.id.sw_location);
                        location.setEnabled(true);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
