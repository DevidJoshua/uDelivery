package com.example.udelivery_courier.Sessions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.udelivery_courier.Login;
import com.example.udelivery_courier.MainActivity;
import com.example.udelivery_courier.Model.Model_user;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Session_login extends AppCompatActivity {
    Model_user mod=new Model_user();
    public void setLogin(Context context, String cour_email, String name,String image,String vehicle_name,String vehicle_number,String vehicle_type){
        SharedPreferences mSettings = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("name", name);
        editor.putString("cour_email", cour_email);
        editor.putString("cour_islogin","true");
        editor.putString("Image",image);
        editor.putString("vehicle_name",vehicle_name);
        editor.putString("vehicle_number",vehicle_number);
        editor.putString("vehicle_type",vehicle_type);
        editor.apply();
        mod.setEmail(cour_email);
    }
    public void isLogin(Context context) {

//            SharedPreferences session = context.getSharedPreferences("Session", context.MODE_PRIVATE);
//            String ses = session.getString("cour_islogin", null);
//            String email = session.getString("cour_email", null);
//            if (ses == "true") {
//                Intent myintent = new Intent(context, MainActivity.class);
//                myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
//                myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(myintent, 0);
//            }
//            if (ses == null) {
                Intent myintent = new Intent(context, Login.class);
                myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myintent, 0);
//            }
    }
    public void logout(Context context){
        SharedPreferences mSettings = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Session", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref.edit();
        editor1.clear();
        editor1.commit();
    }
}
