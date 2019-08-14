package com.example.udelivery_customer.Sessions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.udelivery_customer.Login;
import com.example.udelivery_customer.MainActivity;

public class User_session extends AppCompatActivity {

    public void setLogin(Context context, String nim, String name, String image){
        SharedPreferences mSettings = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("nim", nim);
        editor.putString("name", name);
        editor.putString("image", image);
        editor.apply();

    }
    public void isLogin(Context context) {

            SharedPreferences session = context.getSharedPreferences("Session", context.MODE_PRIVATE);
            String ses = session.getString("cour_islogin", "false");
            String email = session.getString("cour_email", null);
            if (ses == "true") {
                Intent myintent = new Intent(context, MainActivity.class);
                myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myintent, 0);
            }
            if (ses == null) {
        Intent myintent = new Intent(context, Login.class);
        myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
        myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myintent, 0);
            }
    }
    public void logout(Context context){
        SharedPreferences mSettings = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Session", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref.edit();
        editor1.clear();
        editor1.commit();

        Intent myintent = new Intent(context, Login.class);
        myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
        myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myintent, 0);
    }
}
