package com.example.udelivery_courier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.udelivery_courier.Adapter.Adaper_History;
import com.example.udelivery_courier.JSONparser.Operation;
import com.example.udelivery_courier.Model.Model_history;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class History extends AppCompatActivity {
JSONArray arrayHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rv_history);
        Operation op=new Operation();
        ArrayList hlist=new ArrayList<>();
        try {
//            arrayHistory=new JSONArray(op.Login());
            for (int i = 0; arrayHistory.length() > i; i++) {
                JSONObject jsonChildNode = arrayHistory.getJSONObject(i);
                String name=jsonChildNode.optString("t_name");
                String status=jsonChildNode.optString("c_name");
                String date=jsonChildNode.optString("t_picture");
                String image=jsonChildNode.optString("status");
                String c_room=jsonChildNode.optString("c_room");
//                hlist.add(new Model_history(tname, cname, astatus,c_room));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayoutManager lmanager=new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager=lmanager;
        recyclerView.setLayoutManager(layoutManager);

        Adaper_History hadapter=new Adaper_History(this,hlist);
        recyclerView.setAdapter(hadapter);
    }
}
