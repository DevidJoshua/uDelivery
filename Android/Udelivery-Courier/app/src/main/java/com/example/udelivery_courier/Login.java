package com.example.udelivery_courier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udelivery_courier.Sessions.Session_login;
import com.example.udelivery_courier.Model.Model_user;
import com.example.udelivery_courier.JSONparser.Operation;
import com.example.udelivery_courier.JSONparser.Operation;
import com.example.udelivery_courier.Model.Model_user;
import com.example.udelivery_courier.Sessions.Session_login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity{
    //    EditText email, pass;
    Button  enter;
    Model_user M_user=new Model_user();
    Operation op=new Operation();
    JSONArray arrayUser;
    Session_login login=new Session_login();
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
        enter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail= email.getText().toString();
                String password= pass.getText().toString();
                login(lin,e_mail,password);
            }
        });

    }
    void login(View view,String email, String upass){
        try {
            arrayUser= new JSONArray(op.setOnOff(M_user.getEmail(),1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            arrayUser = new JSONArray(op.Login(email,upass));
            for (int i = 0; arrayUser.length() > i; i++) {
                JSONObject jsonChildNode = arrayUser.getJSONObject(i);
                final String name= jsonChildNode.optString("name");
                final String status = jsonChildNode.optString("status");
                final String image= jsonChildNode.optString("image");
                final String vehicle_name= jsonChildNode.optString("vehicle_name");
                final String vehicle_number= jsonChildNode.optString("vehicle_number");
                final String vehicle_type= jsonChildNode.optString("type");
                System.out.println("Status "+status);
                int stat=Integer.parseInt(status);
                if (stat == 1) {
                    login.setLogin(getApplicationContext(),email,name,image,vehicle_name,vehicle_number,vehicle_type);
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

