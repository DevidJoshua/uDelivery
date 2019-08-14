package com.example.udelivery_customer.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_udelivery.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table tb_cart(id integer primary key autoincrement,vend_id text, product_id int,product_name text, product_price integer ,qty integer, total integer);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
    public void  add_to_cart(String vend_id,int product_id,String product_name,int product_price,int qty){
        SQLiteDatabase db = this.getReadableDatabase();
        String        sql = "INSERT INTO tb_cart VALUES (null,'"+vend_id+"','"+product_id+"','"+product_name+"',"+product_price+","+qty+","+product_price*qty+");";
        db.execSQL(sql);
    }
   public void  calculate(String vend_id,int qty, int price, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String        sql = "UPDATE tb_cart SET qty="+qty+",total="+price*qty+" WHERE id="+id+" AND vend_id="+vend_id+";";
        db.execSQL(sql);
    }
    public void remove_by_id(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String        sql = "DELETE FROM tb_cart WHERE id="+id+";";
        db.execSQL(sql);
    }
    public void clear_cart(String vend_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String        sql = "DELETE FROM tb_cart WHERE vend_id="+vend_id+";";
        db.execSQL(sql);
    }
    public int check_product(int id_product){
        Cursor cursor=getReadableDatabase().rawQuery("SELECT *FROM tb_cart WHERE product_id="+id_product,null);
        return cursor.getCount();
    }
    public int get_sum_payment(String vend_id) {
        int tot=0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT sum(qty*product_price) as Tot FROM tb_cart WHERE vend_id="+vend_id, null);
        if (cursor.moveToFirst()) {
            tot=cursor.getInt(cursor.getColumnIndex("Tot"));
        }
        return  tot;
    }
    public int get_sum_qty(String vend_id){
        int tot=0;
        Cursor cursor=getReadableDatabase().rawQuery("SELECT sum(qty) as Tot FROM tb_cart WHERE vend_id="+vend_id,null);
        if(cursor.moveToFirst()) {
            tot = cursor.getInt(cursor.getColumnIndex("Tot"));
        }
        return tot;
    }
    public void update_qty(int id, int qty){
        SQLiteDatabase db = this.getReadableDatabase();
        String        sql = "UPDATE tb_cart SET qty="+qty+",total="+qty+"*product_price WHERE id="+id+";";
        db.execSQL(sql);
    }
    public int cout_tot_row(String vend_id){
        int tot=0;
        Cursor cursor=getReadableDatabase().rawQuery("SELECT *FROM tb_cart WHERE vend_id="+vend_id,null);
        if(cursor.moveToFirst()) {
            tot = cursor.getCount();
        }
        return tot;
    }
}
