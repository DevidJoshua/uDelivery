//package com.example.udelivery_customer;
//
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.gms.location.places.AutocompleteFilter;
//import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.android.gms.maps.model.LatLng;
//
//public class Set_Pickup extends AppCompatActivity {
//    public static final int PICK_UP = 0;
//    public static final int DEST_LOC = 1;
//    private static int REQUEST_CODE = 0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set__pickup);
//        Button pu=(Button)findViewById(R.id.btn_pickup);
//        pu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
//    void placePicker(){
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        try {
//            //menjalankan place picker
//            startActivityForResult(builder.build(this), 1);
//
//            // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format(
//                        "Place: %s \n" +
//                                "Alamat: %s \n" +
//                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
//
//            }
//        }
//                Toast.makeText(this, "Address: "+placeLoc.getAddress().toString()+"\nLatlng: "+placeLoc.getLatLng().latitude+","+placeLoc.getLatLng().longitude+"\nDistrict: ", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//}
