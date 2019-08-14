package com.example.udelivery_customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
//    Session_login checker=new Session_login();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                checker.isLogin(SplashScreen.this);
                SharedPreferences session = SplashScreen.this.getSharedPreferences("Session", SplashScreen.this.MODE_PRIVATE);
                String ses = session.getString("nim", null);
                if (ses == null) {
                    Intent myintent = new Intent(SplashScreen.this, Login.class);
                    myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                    myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(myintent, 0);
                }
                else{
                    Intent myintent = new Intent(SplashScreen.this, MainActivity.class);
                    myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                    myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(myintent, 0);

                }
            }
        },3000);
    }
}
