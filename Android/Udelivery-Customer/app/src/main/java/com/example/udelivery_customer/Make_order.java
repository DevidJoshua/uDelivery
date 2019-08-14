package com.example.udelivery_customer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udelivery_customer.JSONparser.Operation;
import com.example.udelivery_customer.Model.Model_Selected_vendor;
import com.example.udelivery_customer.Model.Model_cart;
import com.example.udelivery_customer.Model.Model_finding;
import com.example.udelivery_customer.Model.Model_pickup;
import com.example.udelivery_customer.Model.Model_status_order;
import com.example.udelivery_customer.Sessions.User_session;
import com.example.udelivery_customer.Sqlite.DataHelper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import im.delight.android.location.SimpleLocation;

public class Make_order extends FragmentActivity implements OnMapReadyCallback {
    Model_status_order stat=new Model_status_order();
    SharedPreferences us;
    Model_finding found=new Model_finding();
    Operation op=new Operation();
    DataHelper dhcart=new DataHelper(this);
    SimpleLocation mlocation;
    Model_Selected_vendor vendor=new Model_Selected_vendor();
    JSONArray arrayStatus,arrayOrder,arrayFind;

    Model_pickup pickup=new Model_pickup();
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        overlay(0);
        found.setIsCanceled(false);
        TextView cost=(TextView)findViewById(R.id.tv_order_cost);
        cost.setText("Rp."+String.valueOf(dhcart.get_sum_payment(vendor.getVend_id())+10000));
        ImageView iv=(ImageView)findViewById(R.id.iv_marker_pick_up);
        iv.setVisibility(View.GONE);
        final ImageView back=(ImageView) findViewById(R.id.btn_back_order);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.cercle2));
                Intent myintent = new Intent(getApplicationContext(), Shops.class);
                myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myintent, 0);
                finish();
            }
        });

        final Button set_order=(Button)findViewById(R.id.btn_set_order);
        set_order.setEnabled(false);
        set_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_order.setBackgroundColor(Color.parseColor("#CAD56F"));
                overlay(1);
                order(String.valueOf(set_order.getText()));
            }
        });
        mlocation=new SimpleLocation(this);
        if (!mlocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        final LinearLayout btn_pick_up=(LinearLayout)findViewById(R.id.btn_add_pickup);
        btn_pick_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iw=(ImageView)findViewById(R.id.iv_marker_pick_up);
                iw.setVisibility(View.VISIBLE);
                TextView pu=(TextView)findViewById(R.id.tv_p_up);
                pu.setText("Set location");
                Button set_order=(Button)findViewById(R.id.btn_set_order);
                set_order.setEnabled(true);
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
//                        Toast.makeText(getApplicationContext(),"Lat"+cameraPosition.target.latitude+", Long"+cameraPosition.target.longitude,Toast.LENGTH_SHORT).show();
                        pickup.setLatitude(cameraPosition.target.latitude);
                        pickup.setLongitude(cameraPosition.target.longitude);
                    }
                });


//                mlocation.beginUpdates();
//                double lat=mlocation.getLatitude();
//                double lonG=mlocation.getLongitude();
//                LatLng latLng=new LatLng(lat,lonG);
//                btn_pick_up.setBackgroundColor(Color.parseColor("#777777"));
//                mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_add_location_black_24dp))
//                .draggable(true));

//                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
//                Intent intent;
//                try {
//                    startActivityForResult(builder.build(Make_order.this),1);
//                }catch (GooglePlayServicesRepairableException e){
//                    e.printStackTrace();
//                }catch (GooglePlayServicesNotAvailableException e){
//                    e.printStackTrace();
//                }
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==1){
//            if(requestCode==RESULT_OK){
//                Place placeLoc=PlacePicker.getPlace(data,this);
//                Toast.makeText(this, "Address: "+placeLoc.getAddress().toString()+"\nLatlng: "+placeLoc.getLatLng().latitude+","+placeLoc.getLatLng().longitude+"\nDistrict: ", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mylocation = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,17),4000,null);
        mMap.addMarker(new MarkerOptions().position(mylocation).title("You")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_black_24dp));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mlocation.beginUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mlocation.endUpdates();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.bulat);
        background.setBounds(40, 20, background.getIntrinsicWidth()+40, background.getIntrinsicHeight()+20);
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    void overlay(int status){
        switch (status){
            case 0:
                ProgressBar pb=(ProgressBar)findViewById(R.id.pg_finding);
                pb.setVisibility(View.GONE);
                TextView pg=(TextView)findViewById(R.id.tv_finding_courier);
                pg.setVisibility(View.GONE);
                break;
            case 1:
                ProgressBar pb2=(ProgressBar)findViewById(R.id.pg_finding);
                pb2.setVisibility(View.VISIBLE);
                TextView pg2=(TextView)findViewById(R.id.tv_finding_courier);
                pg2.setVisibility(View.VISIBLE);
                break;
                default:
                    break;
        }
    }
    void order(final String option)
    {
        mlocation.beginUpdates();
        final  Handler handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1==1){
                    try
                    {
                        final String lat= String.valueOf(mlocation.getLatitude());
                        final String lonG=String.valueOf(mlocation.getLongitude());
                        arrayFind= new JSONArray(op.getAvailableCourier(lat,lonG));
                        find_courier(arrayFind.length(),lat,lonG);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                if(msg.arg1==2){
                    overlay(0);
                    RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_overlay);
                    rl.setBackgroundColor(Color.parseColor("#00000000"));
                }
            }
        };

        class runThread extends Thread{
         public Handler order;
            @Override
            public void run() {
                try {
                    while (found.getResult()==0||found.isIsCanceled()==true){
                        final String lat= String.valueOf(mlocation.getLatitude());
                        final String lonG=String.valueOf(mlocation.getLongitude());
                        arrayFind= new JSONArray(op.getAvailableCourier(lat,lonG));
                            JSONObject jsonChildNode = arrayFind.getJSONObject(0);
                            int result=Integer.parseInt(jsonChildNode.optString("result"));
                            found.setResult(result);
                            System.out.println("Result "+result);
                        Thread.sleep(100);
                    }
                    if (found.isIsCanceled()==true){
                        Message msg=new Message();
                        msg.arg1=2;
                        handler.sendMessage(msg);
                    }
                    else {
                        Message msg=new Message();
                        msg.arg1=1;
                        handler.sendMessage(msg);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


        switch (option){
            case "Cancel":
                overlay(0);
//                runThread run2=new runThread();
//                run2.Intr();
                found.setIsCanceled(true);
                final Button set_order=(Button)findViewById(R.id.btn_set_order);
                RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_overlay);
                rl.setBackgroundColor(Color.parseColor("#00000000"));
                set_order.setText("Order");
                set_order.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                break;
            case "Order":
                found.setIsCanceled(false);
                overlay(1);
                runThread run=new runThread();
                run.start();
                final Button set_order2=(Button)findViewById(R.id.btn_set_order);
                set_order2.setText("Cancel");
                RelativeLayout rl2=(RelativeLayout)findViewById(R.id.rl_overlay);
                rl2.setBackgroundColor(Color.parseColor("#83000000"));
                set_order2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }
    void check_order_status(final String order_id, String option)
    {
        final  Handler handler2= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1==1){
                    show_dialog_result("Canceled","Your Order has been canceled");
                }
                if(msg.arg1==2){
                    show_dialog_result("Finish","Your Order has been arrived. Thanks for ordering");
                }
            }
        };
        class runThread2 extends Thread{
            @Override
            public void run() {
                try {
                   while (stat.equals("Inprogress")||stat.equals("Uncheck")){
                        arrayStatus= new JSONArray(op.check_order_status(order_id));
                        JSONObject jsonChildNode = arrayFind.getJSONObject(0);
                        String result=jsonChildNode.optString("result");
                        stat.setOrder_status(result);
                   }
                    Thread.sleep(100);
                    if (stat.equals("Finish")){
                        Message msg=new Message();
                        msg.arg1=2;
                        handler2.sendMessage(msg);
                    }
                    else if(stat.equals("Canceled by Courier")||stat.equals("Canceled by Customer")){
                        Message msg=new Message();
                        msg.arg1=1;
                        handler2.sendMessage(msg);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        switch (option){
            case "start":
                System.out.println("Start checking");
                runThread2 run=new runThread2();
                run.start();
                break;
            }
    }
    void show_dialog_result(final String option, String status){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Your Packages");
        alertDialogBuilder
                .setMessage(status)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                                 if(option.equals("Canceled")){
                                     Intent myintent = new Intent(getApplicationContext(), Make_order.class);
                                     startActivityForResult(myintent, 0);
                                 }
                                 else {
                                     Toast.makeText(getApplicationContext(),"Thanks for order",Toast.LENGTH_LONG).show();
                                     Intent myintent = new Intent(getApplicationContext(), Shops.class);
                                     startActivityForResult(myintent, 0);
                                 }
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }
    void find_courier(int find, String lat, String lonG)
    {
        try{

            //Origin-destination
            final String vend_pos_lat=String.valueOf(vendor.getVend_latitude());
            final String vend_pos_long=String.valueOf(vendor.getVend_longitude());
            final String pickup_lat=String.valueOf(pickup.getLatitude());
            final String pickup_long=String.valueOf(pickup.getLongitude());

            //etc
            final String or_distance=vendor.getVend_distance();


            final String vend_name_set=vendor.getVend_name();
            final String str1=new String(vend_name_set);
            final String vend_name=str1.replace(" ","%20");

            final String vend_id=vendor.getVend_id();
            final String vend_address_set=vendor.getVend_name();
            final String str2=new String(vend_address_set);
            final String vend_address=str2.replace(" ","%20");

            us=getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
            final String cust_nim = us.getString("nim", "missing");
            if(find>0){
            arrayFind= new JSONArray(op.getAvailableCourier(lat,lonG));
                for(int i=0;i<arrayFind.length();i++)
                    {
                        found.setIsCanceled(false);
                        JSONObject jsonChildNode = arrayFind.getJSONObject(i);
                        final String cour_email=jsonChildNode.optString("cour_email");
                        final String cour_image=jsonChildNode.optString("cour_image");
                        final String cour_name=jsonChildNode.optString("cour_name");
                        final String cour_pos_latitude=jsonChildNode.optString("cour_pos_latitude");
                        final String cour_pos_longtitude=jsonChildNode.optString("cour_pos_longtitude");
                        final String cour_vehicle_type=jsonChildNode.optString("cour_vehicle_type");
                        final String cour_vehicle_number=jsonChildNode.optString("cour_vehicle_number");
                        final String cour_phone=jsonChildNode.optString("cour_phone");
                        final String cour_vehicle_name=jsonChildNode.optString("cour_vehicle_name");
                        overlay(0);
                        ImageView iw=(ImageView)findViewById(R.id.iv_marker_pick_up);
                        iw.setVisibility(View.GONE);
                        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_overlay);
                        rl.setBackgroundColor(Color.parseColor("#00000000"));

                        LinearLayout ribbon=(LinearLayout)findViewById(R.id.ll_ribbon);
                        ribbon.setVisibility(View.GONE);

                        final Dialog dialog = new Dialog(Make_order.this);
                        dialog.setTitle("Your Courier Has been found");
                        dialog.setContentView(R.layout.dialog_found_courier);
                        CircleImageView avatar=(CircleImageView) dialog.findViewById(R.id.iv_cour_profile);
                        String iv=op.URL+"Courier/Images/Courier/"+cour_image;
                        Picasso.with(getApplicationContext())
                                .load(iv)
                                .into(avatar);
                        TextView name=(TextView)dialog.findViewById(R.id.tv_cour_name);
                        name.setText(cour_name);
                        TextView vehicle=(TextView)dialog.findViewById(R.id.tv_cour_vehicle);
                        vehicle.setText(cour_vehicle_name+"("+cour_vehicle_number+")");
                        TextView phone=(TextView)dialog.findViewById(R.id.tv_phone_cour);
                        phone.setText(cour_phone);

                        Button close=(Button)dialog.findViewById(R.id.btn_close_cour);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    dhcart=new DataHelper(Make_order.this);
                                    SQLiteDatabase db=dhcart.getReadableDatabase();
                                    Cursor cursor= db.rawQuery("SELECT *FROM tb_cart where vend_id="+vendor.getVend_id(),null);
                                    dhcart=new DataHelper(Make_order.this);
                                    cursor.moveToFirst();
                                    do{
                                        String vend=cursor.getString(1);
                                        String vend_id=vend.replace(" ","%20");
                                        String product_id=String.valueOf(cursor.getInt(2));
                                        String product=cursor.getString(3);
                                        String product_name=product.replace(" ","%20");
                                        String qty=String.valueOf(cursor.getInt(5));
                                        String total=String.valueOf(cursor.getInt(6));
                                        arrayFind= new JSONArray(op.insert_product(vend_id,product_id,product_name,cust_nim, qty, total));
                                    }while(cursor.moveToNext());
                                    RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_overlay);
                                    rl.setBackgroundColor(Color.parseColor("#00000000"));
                                    double lat=Double.parseDouble(String.valueOf(cour_pos_latitude));
                                    double longi=Double.parseDouble(String.valueOf(cour_pos_longtitude));
                                    dialog.dismiss();
                                    arrayOrder= new JSONArray(op.placeOrder(vend_id,cour_email,cust_nim, vend_name, vend_address, pickup_lat, pickup_long,vend_pos_lat,vend_pos_long, or_distance,"Inprogress"));
                                    JSONObject jsonChildNode = arrayFind.getJSONObject(0);
                                    final String o_id=jsonChildNode.optString("order_id");
                                    System.out.println("Location:"+lat+","+longi);
                                    overlay(1);
                                    check_order_status(o_id,"start");
                                    TextView pg2=(TextView)findViewById(R.id.tv_finding_courier);
                                    pg2.setText("Delivering your packages...");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialog.show();
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_overlay);
        rl.setBackgroundColor(Color.parseColor("#00000000"));
    }
}
