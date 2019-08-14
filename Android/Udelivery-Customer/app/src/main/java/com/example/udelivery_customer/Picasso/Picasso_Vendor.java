package com.example.udelivery_customer.Picasso;

import android.content.Context;
import android.widget.ImageView;

import com.example.udelivery_customer.R;
import com.squareup.picasso.Picasso;

public class Picasso_Vendor {
    public  static void download_image(Context context, String url, ImageView img){
        if(url!=null&&url.length()>0)
        {
            Picasso.with(context).load(url).placeholder(R.drawable.ic_store_mall_directory_black_24dp).resize(50,50).into(img);
        }
        else{
            Picasso.with(context).load(R.drawable.ic_store_mall_directory_black_24dp).centerCrop().resize(50,50).into(img);
        }

    }
}
