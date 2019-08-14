package com.example.udelivery_customer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.udelivery_customer.JSONparser.Operation;
import com.example.udelivery_customer.Sessions.User_session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button  enter;
    Operation op=new Operation();
    JSONArray arrayUser;
    User_session ses=new User_session();
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_login);
        enter=(Button) findViewById(R.id.btn_login);
        final EditText email=(EditText) findViewById(R.id.et_email);
        final EditText pass=(EditText) findViewById(R.id.et_password);
        final LinearLayout lin=(LinearLayout)findViewById(R.id.ll_main);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail= email.getText().toString();
                String password= pass.getText().toString();
                login(lin,e_mail,password);
            }
        });
    }
    void login(View view,String nim, String upass){
        try {
            arrayUser = new JSONArray(op.Login(nim,upass));
            for (int i = 0; arrayUser.length() > i; i++) {
                JSONObject jsonChildNode = arrayUser.getJSONObject(i);
                final String cust_name= jsonChildNode.optString("name");
                final String cust_status = jsonChildNode.optString("status");
                final String cust_image= jsonChildNode.optString("image");
                int stat=Integer.parseInt(cust_status);
                if (stat == 1) {
                    ses.setLogin(getApplicationContext(),nim,cust_name,cust_image);
                    System.out.println("Profile "+cust_name+","+cust_image);
                    Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
                    myintent.addFlags(myintent.FLAG_ACTIVITY_CLEAR_TOP);
                    myintent.setFlags(myintent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(myintent, 0);
                    finish();
                } else if (stat == 0){
                    Snackbar.make(view,"Wrong password or username",Snackbar.LENGTH_LONG).show();
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
            Snackbar.make(view,"Device offline. Make sure you're online!!",Snackbar.LENGTH_LONG).show();
        }
    }
}
