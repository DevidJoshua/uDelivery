package com.example.udelivery_customer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udelivery_customer.Model.Model_Selected_vendor;
import com.example.udelivery_customer.Model.Model_product;
import com.example.udelivery_customer.Picasso.Picasso_Vendor;
import com.example.udelivery_customer.Products;
import com.example.udelivery_customer.R;
import com.example.udelivery_customer.Sqlite.DataHelper;

import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ProductHolder> {
    DataHelper dhCart;
    private Context mcontext;
    private List<Model_product> mdata;
    Products pd=new Products();
    Model_Selected_vendor sv=new Model_Selected_vendor();
    public AdapterProducts(Context mcontext, List<Model_product> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public AdapterProducts.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater= LayoutInflater.from(mcontext);
        view=inflater.inflate(R.layout.data_products,parent,false);
        return new AdapterProducts.ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterProducts.ProductHolder builder, final int i) {
        builder.btn_cart.setEnabled(false);
        final DataHelper dh=new DataHelper(mcontext);
        int total=dh.check_product(mdata.get(i).getProduct_id());
        System.out.println("Vend: "+mdata.get(i).getProduct_id());
        System.out.println("pos: "+i);
        if(total>0){
            builder.rlproduct.setVisibility(View.GONE);
            builder.btn_cart.setVisibility(View.GONE);
            builder.tvadded.setVisibility(View.VISIBLE);
        }
        else{
            builder.rlproduct.setVisibility(View.VISIBLE);
            builder.btn_cart.setVisibility(View.VISIBLE);
            builder.tvadded.setVisibility(View.GONE);
        }
        Picasso_Vendor.download_image(mcontext,mdata.get(i).getProduct_image(),builder.ivProd);
        builder.tvprodname.setText(mdata.get(i).getProduct_name());
        builder.tvprodprice.setText(String.valueOf(mdata.get(i).getProduct_price()));
        builder.btn_de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(builder.et_qty.getText().equals("")){
                    builder.et_qty.setText("0");
                }
                int value= Integer.parseInt(String.valueOf(builder.et_qty.getText()))-1;
                if(value<=0){
                    value=1;
                    builder.btn_cart.setEnabled(false);
                }
                if(value>0){
                    builder.btn_cart.setEnabled(true);
                }
                builder.et_qty.setText(String.valueOf(value));
            }
        });
        builder.btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(builder.et_qty.getText().equals("")){
                    builder.et_qty.setText("0");
                }
                int value= Integer.parseInt(String.valueOf(builder.et_qty.getText()))+1;
                if(value<0){
                    value=0;
                    builder.btn_cart.setEnabled(false);
                }
                if(value>0){
                    builder.btn_cart.setEnabled(true);
                }
                builder.et_qty.setText(String.valueOf(value));

            }
        });
        builder.btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty= Integer.parseInt(String.valueOf(builder.et_qty.getText()));

                if(builder.et_qty.getText().equals("")){
                    builder.et_qty.setText("0");
                }
                dhCart=new DataHelper(mcontext);
                dhCart.add_to_cart(mdata.get(i).getVend_id(),mdata.get(i).getProduct_id(),mdata.get(i).getProduct_name(),mdata.get(i).getProduct_price(),qty);
                refresh(dhCart.get_sum_qty(sv.getVend_id()),dhCart.get_sum_payment(sv.getVend_id()));
                Toast.makeText(mcontext,"Product "+mdata.get(i).getProduct_name()+" \nQuantity: "+(builder.et_qty.getText()).toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{
        TextView tvadded,tvprodname, tvprodprice;
        ImageView ivProd;
        EditText et_qty;
        Button btn_in,btn_de;
        ImageButton btn_cart;
        RelativeLayout rlproduct;
        public ProductHolder(View itemView) {
            super(itemView);
            tvadded=(TextView) itemView.findViewById(R.id.tv_added);
            tvprodname=(TextView) itemView.findViewById(R.id.tv_prod_name);
            tvprodprice=(TextView) itemView.findViewById(R.id.tv_price);
            ivProd=(ImageView) itemView.findViewById(R.id.iv_product);
            btn_cart=(ImageButton) itemView.findViewById(R.id.btn_add_tocart);
            btn_in=(Button) itemView.findViewById(R.id.btn_prod_increse);
            btn_de=(Button) itemView.findViewById(R.id.btn_prod_reduce);
            et_qty=(EditText) itemView.findViewById(R.id.et_qty);
            rlproduct=(RelativeLayout) itemView.findViewById(R.id.rl_prod);
        }
    }
    void refresh(int qty, int payment){
        TextView items=(TextView) ((Activity)mcontext).findViewById(R.id.tv_tot_item);
        items.setText("Total:"+String.valueOf(qty)+" Items");
        TextView pay=(TextView) ((Activity)mcontext).findViewById(R.id.tv_tot_payment);
        pay.setText(String.valueOf(payment));
        if(mcontext instanceof Products){
            ((Products)mcontext).reload();
        }
    }
}
