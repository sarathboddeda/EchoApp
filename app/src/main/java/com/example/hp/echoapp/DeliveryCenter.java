package com.example.hp.echoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.adapter.DeliveryAdapter;
import com.example.hp.echoapp.constructors.DeliverConst;
import com.example.hp.echoapp.user.Common;
import com.example.hp.echoapp.user.OrderDetails;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 12/8/2017.
 */

public class DeliveryCenter extends Activity
{
    SlidingMenu slidingMenu;


    public static final String[] deliveryName={"Media3 Internation PVT LTD", "Media3 Internation PVT LTD","Media3 Internation PVT LTD","Media3 Internation PVT LTD"};
    public static final String[] deliveryAddress={"Anr Plaza,Gurudwar", "Gvk Plaza,Gurudwar","Green Park,Gurudwar","Gvk Plaza,Gurudwar"};
    public static final String[] delverpin={"530043", "530037","530051","530013"};


    ListView deliveryLV;
    ArrayList<DeliverConst> deliverConsts;
    ImageView iv_menu;
    Button bt_deliveredto;
    String orderedquan,orderedprice,getorderedquantity,subTotalstring,shopproducttitle,shopproductprice,productimagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_center);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        //bt_deliveredto=(Button)findViewById(R.id.bt_deliveredto);

        deliveryLV=(ListView)findViewById(R.id.del_list);


        orderedquan=getIntent().getStringExtra("quantit").toString();
        orderedprice=getIntent().getStringExtra("pricepro").toString();
        shopproducttitle=getIntent().getStringExtra("product_name").toString();
        shopproductprice=getIntent().getStringExtra("price").toString();
        productimagepath=getIntent().getStringExtra("image").toString();

        Common.SlideMenuDesign(slidingMenu, DeliveryCenter.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        deliverConsts = new ArrayList<DeliverConst>();

        deliveryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Intent checkout=new Intent(DeliveryCenter.this,OrderDetails.class);
                checkout.putExtra("fullname",deliverConsts.get(i).getFullname().toString());
                checkout.putExtra("delemail",deliverConsts.get(i).getDelemail().toString());
                checkout.putExtra("deladdress",deliverConsts.get(i).getAddress().toString());
                checkout.putExtra("delstate",deliverConsts.get(i).getState().toString());
                checkout.putExtra("delcountry",deliverConsts.get(i).getCountry().toString());
                checkout.putExtra("delzipcode",deliverConsts.get(i).getZipcode().toString());
                checkout.putExtra("delcity",deliverConsts.get(i).getCity().toString());
                checkout.putExtra("delphone",deliverConsts.get(i).getMobile().toString());
                checkout.putExtra("quantit",orderedquan);
                checkout.putExtra("pricepro",subTotalstring);
                checkout.putExtra("product_name",shopproducttitle);
                checkout.putExtra("price",shopproductprice);
                checkout.putExtra("image",productimagepath);
                Log.d("++quantit",orderedquan);
                Log.d("++pricepro",shopproductprice);
                Log.d("++product_name",shopproducttitle);
                Log.d("++price",shopproductprice);
                Log.d("++image",productimagepath);
                startActivity(checkout);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);

            }
        });

        /*for(int i=0;i<deliveryName.length;i++)
        {

            DeliverConst productItem = new DeliverConst(deliveryName[i],deliveryAddress[i],delverpin[i]);
            deliverConsts.add(productItem);
            deliveryLV=(ListView)findViewById(R.id.del_list);
            final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenter.this,deliverConsts);
            deliveryLV.setAdapter(adapter);
            deliveryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   *//* Intent bal=new Intent(DeliveryCenter.this,OrderDetails.class);
                    startActivity(bal);*//*
                }
            });
        }*/
            /*bt_deliveredto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent checkout=new Intent(DeliveryCenter.this,OrderDetails.class);
                   *//*bal.putExtra("delorderquan",orderedquan);
                    bal.putExtra("delorderdprice",orderedprice);
                    startActivity(bal);*//*

                    checkout.putExtra("quantit",orderedquan);
                    checkout.putExtra("pricepro",subTotalstring);
                    checkout.putExtra("product_name",shopproducttitle);
                    checkout.putExtra("price",shopproductprice);
                    checkout.putExtra("image",productimagepath);
                    Log.d("++quantit",orderedquan);
                    Log.d("++pricepro",shopproductprice);
                    Log.d("++product_name",shopproducttitle);
                    Log.d("++price",shopproductprice);
                    Log.d("++image",productimagepath);
                    startActivity(checkout);
                }
            });*/

        new DeliveryCenter.GetDeliveryCenters().execute();


    }

    private class GetDeliveryCenters extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String _id,fullname,delemail, address,state,zipcode,country,city, mobile;
        Boolean isSelected=false;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json=JSONParser.makeServiceCall("http://162.251.83.200/~wwwm3phpteam/echoapp/services2/deliverycenters.php?function=getAllcenters",2,nameValuePairs);
            Log.d("--json", String.valueOf(json));
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONObject result = null;
            try {
                result = new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success")) {

                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        fullname=data.getString("name").toString();
                        delemail=data.getString("email").toString();
                        address=data.getString("address").toString();
                        state=data.getString("state").toString();
                        zipcode=data.getString("zipcode").toString();
                        country=data.getString("country").toString();
                        city=data.getString("city").toString();
                        mobile=data.getString("phone").toString();

                    SharedPreferences sharedPreferences = getSharedPreferences("deliveryDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", fullname);
                    editor.putString("email",delemail);
                    editor.putString("address",address);
                    editor.putString("state",state);
                    editor.putString("zipcode",zipcode);
                    editor.putString("country",country);
                    editor.putString("city",city);
                    editor.putString("phone",mobile);
                     editor.commit();



                        Log.d("--fullname",fullname);
                        Log.d("--delemail",delemail);
                        Log.d("--address",address);
                        Log.d("--state",state);
                        Log.d("--zipcode",zipcode);
                        Log.d("--country",country);
                        Log.d("--city",city);
                        Log.d("--mobile",mobile);

                        //String _id,fullname,delemail,password, address,state,zipcode,country,city, mobile;

                        deliverConsts.add(new DeliverConst(fullname.toString(),delemail.toString(),address.toString(),
                                state.toString(),zipcode.toString(),country.toString(), city.toString(),mobile.toString()));
                        //final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenter.this,deliverConsts);
                        final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenter.this,deliverConsts);
                        deliveryLV.setAdapter(adapter);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*catch (Exception e1){
                Validations.MyAlertBox(TotalProducts.this,getResources().getString(R.string.connectionLost));
            }*/
        }
    }


}
