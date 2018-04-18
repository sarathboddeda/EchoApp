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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by HP on 10/18/2017.
 */

public class ProductDetails extends Activity{
    Button bt_detail_addchart,bt_detail_addwhishlist,bt_detail_buy;
    ImageView iv_profile,iv_menu,iv_productdetails_image;
    EditText et_prodquantity;
    SlidingMenu slidingMenu;
    TextView tv_clickedproducttitle,tv_clickedproductstock,tv_clickedproductprice,tv_detailsavailable,tv_pd_additionalifo;
    String clickedproducttitle,clickedproductpricestring,productimagepath,userid,metchantid,prodctid,merchantName,quantstring,
            prodel;
    int numproductquantity=0,numproductqty;
    double clickedproductprice;
    int x=0,detailsavailable;
    TextView tv_prodelte,tv_savelte;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        Common.SlideMenuDesign(slidingMenu, ProductDetails.this, "productdetails");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        tv_clickedproducttitle=(TextView)findViewById(R.id.tv_clickedproducttitle);
        tv_clickedproductstock=(TextView)findViewById(R.id.tv_clickedproductstock);
        tv_prodelte=(TextView)findViewById(R.id.tv_prodelte);
        tv_clickedproductprice=(TextView)findViewById(R.id.tv_clickedproductprice);
        tv_savelte=(TextView)findViewById(R.id.tv_savelte);
        tv_detailsavailable=(TextView)findViewById(R.id.tv_detailsavailable);
        tv_pd_additionalifo=(TextView)findViewById(R.id.tv_pd_additionalifo);
        iv_productdetails_image=(ImageView)findViewById(R.id.iv_productdetails_image);
        et_prodquantity=(EditText)findViewById(R.id.et_prodquantity);

        SharedPreferences sharedPreferences1 = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        userid=sharedPreferences1.getString("id","");
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
        //merchantName=sharedPreferences2.getString("merchant_name","");

        tv_pd_additionalifo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(x==0)
                {
                    LinearLayout ll_additionalcnt = (LinearLayout) findViewById(R.id.ll_additionalcnt);
                    ll_additionalcnt.setVisibility(View.VISIBLE);
                    x++;
                    Log.d("x", String.valueOf(x));
                    
                }
                else
                {
                    LinearLayout ll_additionalcnt = (LinearLayout) findViewById(R.id.ll_additionalcnt);
                    ll_additionalcnt.setVisibility(View.GONE);
                    x--;
                    Log.d("x", String.valueOf(x));
                }

            }
        });
        clickedproducttitle=getIntent().getStringExtra("product_name").toString();
        clickedproductprice=getIntent().getDoubleExtra("price",0);
        detailsavailable=getIntent().getIntExtra("available_stock",0);
        productimagepath=getIntent().getStringExtra("image").toString();
        prodctid=getIntent().getStringExtra("product_id").toString();
        metchantid=getIntent().getStringExtra("merchant_id").toString();
        prodel=getIntent().getStringExtra("pro_del").toString();
        merchantName=getIntent().getStringExtra("merchant_name").toString();

        tv_prodelte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile=new Intent(ProductDetails.this,UserProductGridview.class);
                startActivity(profile);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });

        tv_savelte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productqty=et_prodquantity.getText().toString();
                Log.d("++productqty",productqty);


                if(productqty.equals(""))
                {
                    Validations.MyAlertBox(ProductDetails.this,"Please enter the quantity to want to purchase");
                }
                else {
                    numproductqty = Integer.parseInt(productqty);
                    Log.d("++numproductqty", String.valueOf(numproductqty));
                    if(numproductqty>detailsavailable)
                    {
                        Validations.MyAlertBox(ProductDetails.this,"Please enter the quantity with in the limit");
                    }
                    else {

                        Log.d("Data : ",userid+"\n"+prodctid+"\n"+merchantName+"\n"+clickedproductpricestring+"\n"+productimagepath+
                                "\n"+quantstring+"\n"+metchantid);
                        new ProductDetails.addCart(userid,prodctid,merchantName,clickedproductpricestring,productimagepath,quantstring,metchantid).execute();
                        //startActivity(buy);

                    }
                }


            }
        });


        Log.d("**image",productimagepath);
        Log.d("**product_name",clickedproducttitle);
        Log.d("**price",String.valueOf(clickedproductprice));
        Log.d("**detailsavailable",String.valueOf(detailsavailable));
        Log.d("**product_id", prodctid);
        Log.d("**merchant_id", metchantid);
        Log.d("**merchant_name", merchantName);
        Log.d("**pro_del", prodel);



        String str = clickedproducttitle;
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }
        tv_clickedproducttitle.setText(builder.toString());
        tv_clickedproductprice.setText(String.valueOf(clickedproductprice));
        tv_detailsavailable.setText(String.valueOf(detailsavailable));
        Picasso.with(this).load(productimagepath).into(iv_productdetails_image);
        bt_detail_addchart=(Button)findViewById(R.id.bt_detail_addchart);
        bt_detail_buy=(Button)findViewById(R.id.bt_detail_buy);
        bt_detail_addwhishlist=(Button)findViewById(R.id.bt_detail_addwhishlist);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile=new Intent(ProductDetails.this,MyProfile.class);
                startActivity(profile);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
        bt_detail_addchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String productqty=et_prodquantity.getText().toString();
                Log.d("++productqty",productqty);


                if(productqty.equals(""))
                {
                    Validations.MyAlertBox(ProductDetails.this,"Please enter the quantity to want to purchase");
                }
                else {
                    numproductqty = Integer.parseInt(productqty);
                    Log.d("++numproductqty", String.valueOf(numproductqty));
                    if(numproductqty>detailsavailable)
                    {
                        Validations.MyAlertBox(ProductDetails.this,"Please enter the quantity with in the limit");
                    }
                    else {

                        Log.d("Data : ",userid+"\n"+prodctid+"\n"+merchantName+"\n"+clickedproductpricestring+"\n"+productimagepath+
                                "\n"+quantstring+"\n"+metchantid);
                        new ProductDetails.addCart(userid,prodctid,merchantName,clickedproductpricestring,productimagepath,quantstring,metchantid).execute();
                        //startActivity(buy);

                    }
                }

            }
        });
        bt_detail_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String productqty=et_prodquantity.getText().toString();
                Log.d("++productqty",productqty);
                if(productqty.equals(""))
                {
                    Validations.MyAlertBox(ProductDetails.this,"Please enter the quantity to want to purchase");
                }
                else {
                    numproductqty = Integer.parseInt(productqty);
                    Log.d("++numproductqty", String.valueOf(numproductqty));

                    if(numproductqty>detailsavailable)
                    {
                        Validations.MyAlertBox(ProductDetails.this,"Please enter the quantity with in the limit");
                    }
                    else {

                        Intent buy = new Intent(ProductDetails.this, ShoppingCart.class);

                        buy.putExtra("ordered",numproductqty);
                        buy.putExtra("proid",prodctid);
                        buy.putExtra("product_name",clickedproducttitle);
                        buy.putExtra("price",clickedproductprice);
                        buy.putExtra("image",productimagepath);
                        buy.putExtra("quantity",numproductqty);
                        buy.putExtra("prodel",prodel);
                        buy.putExtra("merchantName",merchantName);

                        Log.d("Data : ",prodctid+"\n"+quantstring+"\n"+clickedproducttitle+"\n"+clickedproductprice+"\n"+productimagepath);
                        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                        startActivity(buy);

                    }
                }


            }
        });

    }


    private class addCart extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String uid, pid,ordedprice, mid,mname, orderedimage,  orderdquantity;
        public addCart(String uid, String pid,String mname, String ordedprice, String orderedimage, String orderdquantity,String mid) {
            this.uid = uid;
            this.pid = pid;
            this.mname=mname;
            this.ordedprice = ordedprice;
            this.orderedimage = orderedimage;
            this.orderdquantity = orderdquantity;
            this.mid=mid;
        }

        @Override
        protected JSONObject doInBackground(Object... strings) {

            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("uid", uid));
            nameValuePairs.add(new BasicNameValuePair("mid",mid));
            nameValuePairs.add(new BasicNameValuePair("mname",mname));
            nameValuePairs.add(new BasicNameValuePair("pid",pid));
            nameValuePairs.add(new BasicNameValuePair("price", ordedprice));
            nameValuePairs.add(new BasicNameValuePair("image", orderedimage));
            nameValuePairs.add(new BasicNameValuePair("quantity", orderdquantity));

            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/orders.php?function=addtoCart",2, nameValuePairs);
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
                        Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_LONG).show();
                        Log.d("==ordedprice", data.getString("total_amount").toString());
                        Log.d("==orderdquantity", data.getString("quantity").toString());
                        Log.d("==_id",data.getString("id").toString());
                        Log.d("==p_id",pid);



                        uid = data.getString("user_id").toString();
                        pid = data.getString("product_id").toString();
                        mid=data.getString("merchant_id").toString();
                        ordedprice = data.getString("total_amount").toString();
                        //orderedimage = data.getString("image").toString();
                        orderdquantity = data.getString("quantity").toString();

                        Log.d("== uid",uid);
                        Log.d("==p_id",pid);
                        Log.d("== mid",mid);
                        Log.d("== ordedprice",ordedprice);
                        Log.d("== orderdquantity",orderdquantity);



                        SharedPreferences sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", uid);
                        editor.putString("product_id", pid);
                        editor.putString("total_amount", ordedprice);
                        //editor.putString("image", orderedimage);
                        editor.putString("quantity", orderdquantity);
                        editor.putString("merchant_id", mid);
                        editor.commit();

                        Validations.MyAlertBox(ProductDetails.this,"Product added to the cart");
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProductDetails.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.addedtocart);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(ProductDetails.this, UserProductGridview.class));
                                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                                dialogInterface.dismiss();
                            }
                        }).show();


                        /*android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProductDetails.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.orderupdated);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(ProductDetails.this, OrderedPlaced.class));
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                                dialogInterface.dismiss();
                            }
                        }).show();*/

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(ProductDetails.this, "Connection Lost");
            }
        }
    }
}
