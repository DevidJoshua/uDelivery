package com.example.udelivery_customer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.udelivery_customer.JSONparser.Operation;
import com.example.udelivery_customer.Model.Model_Selected_vendor;
import com.example.udelivery_customer.Model.Model_vendor;
import com.example.udelivery_customer.Picasso.Picasso_Vendor;
import com.example.udelivery_customer.Products;
import com.example.udelivery_customer.R;

import java.util.List;

public class AdapterVendor extends RecyclerView.Adapter<AdapterVendor.VendorHolder> {
    Operation op=new Operation();
    private Context mcontext;
    private List<Model_vendor> mdata;

    Model_Selected_vendor vend=new Model_Selected_vendor();
    public AdapterVendor(Context mcontext, List<Model_vendor> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public VendorHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater= LayoutInflater.from(mcontext);
        view=inflater.inflate(R.layout.data_shops,parent,false);
        return new VendorHolder(view);
    }

    @Override
    public void onBindViewHolder(final VendorHolder builder, final int i) {

        builder.tvDistance.setText(mdata.get(i).getVend_distance()+" m");
        builder.tvName.setText(mdata.get(i).getVend_name());
        Picasso_Vendor.download_image(mcontext,op.URL+"Vendor/Images/Profile"+mdata.get(i).getVend_image(),builder.imageView);
        builder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vend.setVend_address(mdata.get(i).getVend_address());
                vend.setVend_id(mdata.get(i).getVend_id());
                vend.setVend_image(mdata.get(i).getVend_image());
                vend.setVend_phone(mdata.get(i).getVend_phone());
                vend.setVend_name(mdata.get(i).getVend_name());
                vend.setVend_latitude(mdata.get(i).getVend_latitude());
                vend.setVend_longitude(mdata.get(i).getVend_longitude());
                vend.setVend_distance(mdata.get(i).getVend_distance());
                Intent i=new Intent(mcontext, Products.class);
                mcontext.startActivity(i);
            }
        });
        builder.shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vend.setVend_address(mdata.get(i).getVend_address());
                vend.setVend_id(mdata.get(i).getVend_id());
                vend.setVend_image(mdata.get(i).getVend_image());
                vend.setVend_phone(mdata.get(i).getVend_phone());
                vend.setVend_name(mdata.get(i).getVend_name());
                vend.setVend_latitude(mdata.get(i).getVend_latitude());
                vend.setVend_longitude(mdata.get(i).getVend_longitude());
                vend.setVend_distance(mdata.get(i).getVend_distance());
                Intent i=new Intent(mcontext, Products.class);
                mcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class VendorHolder extends RecyclerView.ViewHolder{
        CardView shop;
        TextView tvName, tvDistance;
        ImageView imageView;
        Button chart;
        public VendorHolder(View itemView) {
            super(itemView);
            tvName=(TextView) itemView.findViewById(R.id.tv_shop_name);
            tvDistance=(TextView) itemView.findViewById(R.id.tv_distance);
            imageView=(ImageView) itemView.findViewById(R.id.iv_shop);
            chart=(Button) itemView.findViewById(R.id.btn_chart);
            shop=(CardView)itemView.findViewById(R.id.cv_shop);
        }
    }
}
