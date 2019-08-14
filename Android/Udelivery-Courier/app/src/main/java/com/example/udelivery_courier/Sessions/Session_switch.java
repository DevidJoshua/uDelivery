package com.example.udelivery_courier.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_switch {
    public void set_onof(Context context,String status){
        SharedPreferences pref = context.getSharedPreferences("Switch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1= pref.edit();
        editor1.clear();
        editor1.commit();

        SharedPreferences mSettings = context.getSharedPreferences("Switch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("onof", status);

    }
}
