package com.example.udelivery_customer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udelivery_customer.Cart;
import com.example.udelivery_customer.Model.Model_Selected_vendor;
import com.example.udelivery_customer.Model.Model_cart;
import com.example.udelivery_customer.Model.Model_product;
import com.example.udelivery_customer.Picasso.Picasso_Vendor;
import com.example.udelivery_customer.Products;
import com.example.udelivery_customer.R;
import com.example.udelivery_customer.Sqlite.DataHelper;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartHolder> {
    DataHelper dhCart;
    private Context mcontext;
    private List<Model_cart> mdata;
    Products pd=new Products();
    Model_Selected_vendor sv=new Model_Selected_vendor();
    public AdapterCart(Context mcontext, List<Model_cart> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public AdapterCart.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater= LayoutInflater.from(mcontext);
        view=inflater.inflate(R.layout.data_cart,parent,false);
        return new AdapterCart.CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCart.CartHolder builder, final int i) {
        builder.et_qty.setText(String.valueOf(mdata.get(i).getQty()));
        builder.et_qty.setEnabled(false);
        builder.pName.setText(mdata.get(i).getProduct_name());
        builder.pPrice.setText(String.valueOf(mdata.get(i).getProduct_price()));
//        builder.et_qty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                int value= Integer.parseInt(String.valueOf(builder.et_qty.getText()))-1;
//                if(value<0){
//                    value=0;
//                }
//                dhCart=new DataHelper(mcontext);
//                dhCart.update_qty(mdata.get(i).getId(),value);
//                refresh(value,value*mdata.get(i).getProduct_price());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        builder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int value=Integer.parseInt(String.valueOf(builder.et_qty.getText()))+1;
                builder.et_qty.setText(String.valueOf(value));

                dhCart=new DataHelper(mcontext);
                dhCart.update_qty(mdata.get(i).getId(),value);
                refresh(value,value*mdata.get(i).getProduct_price());

            }
        });
        builder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=Integer.parseInt(String.valueOf(builder.et_qty.getText()))-1;
                if(builder.et_qty.getText().equals("")&&value<=0){
                    value=1;
                }
                builder.et_qty.setText(String.valueOf(value));

                dhCart=new DataHelper(mcontext);
                dhCart.update_qty(mdata.get(i).getId(),value);
                refresh(value,value*mdata.get(i).getProduct_price());
            }
        });
        builder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "Product "+mdata.get(i).getProduct_name()+"has been deleted from cart",Toast.LENGTH_LONG).show();
                dhCart=new DataHelper(mcontext);
                dhCart.remove_by_id(mdata.get(i).getId());
                refresh(mdata.get(i).getQty(),mdata.get(i).getQty()*mdata.get(i).getProduct_price());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class CartHolder extends RecyclerView.ViewHolder{
        TextView pName,pPrice;
        Button  plus,min,remove;
        EditText et_qty;
        public CartHolder(View itemView) {
            super(itemView);
            pName=(TextView) itemView.findViewById(R.id.tv_cart_prod_name);
            pPrice=(TextView) itemView.findViewById(R.id.tv_cart_price);
            plus=(Button) itemView.findViewById(R.id.btn_cart_increse);
            min=(Button) itemView.findViewById(R.id.btn_cart_prod_reduce);
            remove=(Button) itemView.findViewById(R.id.btn_remove_from_cart);
            et_qty=(EditText)itemView.findViewById(R.id.et_cart_qty);
        }
    }
    void refresh(int qty, int payment){
        if(mcontext instanceof Cart){
            ((Cart)mcontext).load();
        }
    }
}
