package com.example.udelivery_courier.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.udelivery_courier.Model.Model_history;
import com.example.udelivery_courier.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaper_History extends RecyclerView.Adapter<Adaper_History.HistoryHolder>{
    private ArrayList<String> h_avatar,h_name,h_date_date, h_status;
    public Context mcontext1;
    public ArrayList<Model_history> hlist;

    public Adaper_History(Context mcontext, ArrayList<Model_history> list){
        mcontext1=mcontext;
        hlist=list;
    }
    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_history, parent, false);
        HistoryHolder vh = new HistoryHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        Model_history hist=hlist.get(position);
        TextView name, date, status;
        CircleImageView profile;

        profile=holder.h_cust;
        name=holder.h_name;
        date=holder.h_date;
        status=holder.h_status;

        name.setText(hist.getName());
        date.setText(hist.getDate());
        status.setText(hist.getStatus());

    }

    @Override
    public int getItemCount() {
        return hlist.size();
    }


    public class HistoryHolder extends RecyclerView.ViewHolder{
        TextView h_name, h_date, h_status;
        CircleImageView h_cust;
        public HistoryHolder(View itemView) {
            super(itemView);
            h_name= (TextView) itemView.findViewById(R.id.tv_list_cust);
            h_date= (TextView) itemView.findViewById(R.id.tv_list_date);
            h_status= (TextView) itemView.findViewById(R.id.tv_list_status);
            h_cust= (CircleImageView) itemView.findViewById(R.id.iv_cust_profilePic);
        }
    }
}


