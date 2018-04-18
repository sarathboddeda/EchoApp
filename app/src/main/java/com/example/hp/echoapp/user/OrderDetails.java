package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 10/23/2017.
 */

public class OrderDetails extends Activity {
    SlidingMenu slidingMenu;
    ImageView iv_menu,iv_myorderdetailsimage;
    Button bt_placeordered;
    TextView tv_myorderdtl_title,tv_myorder_quant,tv_merchant,tv_orderdetails_price,tv_orderdelname,tv_orderdelemail,
    tv_orderdelphone,tv_orderdeldoorno,tv_orderdelcity,tv_orderdelstate,tv_orderdelpinno;
    String orderedtitle,orderedImagePath,orderedMerchant,orderedQuantityString,userid,prodctid,metchantid,buyer_name;
    String delfullname,delemail,deladdress,delcity,delstate,delmobile,delzipcode,fulladdress,delid,orderedPriceString;
    ShoppingCart shoppingCart;
    int orderedQuantity;
    double orderedPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        bt_placeordered=(Button)findViewById(R.id.bt_placeordered);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        shoppingCart=new ShoppingCart();
        tv_myorderdtl_title=(TextView)findViewById(R.id.tv_myorderdtl_title);
        iv_myorderdetailsimage=(ImageView)findViewById(R.id.iv_myorderdetailsimage);
        tv_myorder_quant=(TextView)findViewById(R.id.tv_myorder_quant);
        tv_merchant=(TextView)findViewById(R.id.tv_merchant);
        tv_orderdetails_price=(TextView)findViewById(R.id.tv_orderdetails_price);
        tv_orderdelname=(TextView)findViewById(R.id.tv_orderdelname);
        tv_orderdelemail=(TextView)findViewById(R.id.tv_orderdelemail);
        tv_orderdelphone=(TextView)findViewById(R.id.tv_orderdelphone);
        tv_orderdeldoorno=(TextView)findViewById(R.id.tv_orderdeldoorno);
        tv_orderdelcity=(TextView)findViewById(R.id.tv_orderdelcity);
        tv_orderdelstate=(TextView)findViewById(R.id.tv_orderdelstate);
        tv_orderdelpinno=(TextView)findViewById(R.id.tv_orderdelpinno);
        SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);

        orderedtitle=getIntent().getStringExtra("product_name").toString();
        orderedMerchant=getIntent().getStringExtra("promername").toString();
        //orderedMerchant=sharedPreferences.getString("merchant_name","");

        //prodctid=sharedPreferences.getString("id","");
        orderedImagePath=getIntent().getStringExtra("image").toString();
        prodctid=getIntent().getStringExtra("prodid").toString();
        orderedPrice=getIntent().getDoubleExtra("pricepro",0);
        orderedPriceString= String.valueOf(orderedPrice);
        Log.d("--orderedPriceString",orderedPriceString);
        orderedQuantity=getIntent().getIntExtra("quantit",0);
        orderedQuantityString=String.valueOf(orderedQuantity);
        fulladdress=getIntent().getStringExtra("address").toString();
        delid=getIntent().getStringExtra("del_id").toString();
        String [] arrOfStr = fulladdress.split("\n", 6);
        delfullname=arrOfStr[0];
        delemail=arrOfStr[1];
        deladdress=arrOfStr[2];
        delstate=arrOfStr[3];
        delmobile=arrOfStr[4];
        delzipcode=arrOfStr[5];

        Picasso.with(this).load(orderedImagePath).into(iv_myorderdetailsimage);
        tv_myorderdtl_title.setText(orderedtitle);
        tv_myorder_quant.setText(orderedQuantityString);
        tv_merchant.setText(orderedMerchant);
        tv_orderdetails_price.setText(orderedPriceString);
        tv_orderdelname.setText(delfullname);
        tv_orderdelemail.setText(delemail);
        tv_orderdelcity.setText(delcity);
        tv_orderdeldoorno.setText(deladdress);
        tv_orderdelphone.setText(delmobile);
        tv_orderdelpinno.setText(delzipcode);


        SharedPreferences sharedPreferences1 = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        userid=sharedPreferences1.getString("id","");
        buyer_name=sharedPreferences1.getString("firstname","");

        SharedPreferences sharedPreferences2 = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
        metchantid=sharedPreferences2.getString("merchant_id","");



        Common.SlideMenuDesign(slidingMenu, OrderDetails.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        bt_placeordered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent placeorder=new Intent(OrderDetails.this,OrderedPlaced.class);
                startActivity(placeorder);*/

                if (Validations.isConnectedToInternet(OrderDetails.this))
                {
                    Log.d("__userid",userid);
                    Log.d("__prodctid",prodctid);
                    Log.d("__orderedPrice", String.valueOf(orderedPrice));
                    Log.d("__orderedImagePath",orderedImagePath);
                    Log.d("__orderedQuantity",String.valueOf(orderedQuantity));
                    Log.d("__metchantid",metchantid);
                    Log.d("__orderedMerchant",orderedMerchant);

                    //String uid, String pid, String ordedprice, String orderedimage, String orderdquantity
                    new OrderDetails.ordered(userid,buyer_name,prodctid,orderedMerchant,delid,orderedPriceString,orderedImagePath,orderedQuantityString,metchantid).execute();
                }
                else
                {
                    Validations.MyAlertBox(OrderDetails.this, "INTERNET CONNECTION FAILED");
                }
            }
        });

    }

    private class ordered extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String uid, pid, mid, orderedimage,ordedprice,  orderdquantity,merchant_name,buyer_name,delid;
        public ordered(String uid,String buyer_name, String pid,String merchant_name,String delid,String ordedprice, String orderedimage, String orderdquantity,String mid) {
            this.uid = uid;
            this.buyer_name = buyer_name;
            this.pid = pid;
            this.merchant_name=merchant_name;
            this.delid=delid;
            this.ordedprice = ordedprice;
            this.orderedimage = orderedimage;
            this.orderdquantity = orderdquantity;
            this.mid=mid;

        }



        @Override
        protected JSONObject doInBackground(Object... strings) {

            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("uid", uid));
            nameValuePairs.add(new BasicNameValuePair("bname", buyer_name));
            nameValuePairs.add(new BasicNameValuePair("mid",mid));
            nameValuePairs.add(new BasicNameValuePair("mname",merchant_name));
            nameValuePairs.add(new BasicNameValuePair("did",delid));
            nameValuePairs.add(new BasicNameValuePair("pid",pid));
            nameValuePairs.add(new BasicNameValuePair("price", ordedprice));
            nameValuePairs.add(new BasicNameValuePair("image", orderedimage));
            nameValuePairs.add(new BasicNameValuePair("quantity", orderdquantity));


            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/orders.php?function=addOrder",2, nameValuePairs);
            return json;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            // super.onPostExecute(jsonObject);
            try {
                JSONObject result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equals("Success")) {
                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject data=new JSONObject(array.getJSONObject(i).toString());
                        //Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_LONG).show();
                        Log.d("==data",data.toString());
                        //Log.d("uid", data.getString("id").toString());
                        //Log.d("==pid", data.getString("product_id").toString());
                        Log.d("==ordedprice", data.getString("total_amount").toString());
                        //Log.d("==orderedimage", data.getString("image").toString());
                        Log.d("==orderdquantity", data.getString("quantity").toString());
                        Log.d("==_id",data.getString("id").toString());



                        uid = data.getString("user_id").toString();
                        pid = data.getString("product_id").toString();
                        mid=data.getString("merchant_id").toString();
                        ordedprice = data.getString("total_amount").toString();
                        //orderedimage = data.getString("image").toString();
                        orderdquantity = data.getString("quantity").toString();



                        SharedPreferences sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", uid);
                        editor.putString("product_id", pid);
                        editor.putString("total_amount", ordedprice);
                        //editor.putString("image", orderedimage);
                        editor.putString("quantity", orderdquantity);
                        editor.putString("merchant_id", mid);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.orderupdated);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(OrderDetails.this, OrderedPlaced.class));
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                                dialogInterface.dismiss();
                            }
                        }).show();

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(OrderDetails.this, "Connection Lost");
            }
        }
    }
}
