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
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.adapter.MyOrderListviewAdapter;
import com.example.hp.echoapp.constructors.MyCartContructor;
import com.example.hp.echoapp.adapter.MyCartAdapter;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.constructors.MyOrderConst;
import com.example.hp.echoapp.merchant.OrderedProducts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 11/16/2017.
 */

public class MyCart extends Activity {
    SlidingMenu slidingMenu;
    SharedPreferences sharedPreferences;
    public  static String userid;
    int procount;

    /*public static final Integer[] myCartImage={R.drawable.brinjal, R.drawable.clustrerbean,R.drawable.cabbage,
            R.drawable.capsicam};
    public static final String[] myCartTitle={"Brinjal", "Clusterbean","Cabbage","Capsicum"};
    public static final String[] myCartWeight={"50 kilos Bag", "50 kilos Bag","50 kilos Bag","50 kilos Bag"};
    public static final String[] myCartcost={"300$", "400$","200$","600$"};
    public static final String[] myCartMerchant={"SunShine Mall","SunShine Mall","SunShine Mall","SunShine Mall"};
    public static final String[] myCartDelwith={"Delivery in 2 days:Sunday:20$","Delivery in 2 days:Sunday:20$","Delivery in 2 days:Sunday:20$",
            "Delivery in 2 days:Sunday:20$"};
    public static final String[] myCartDelwithout={"Delivery in 1 days:Saturday:20$","Delivery in 1 days:Saturday:20$","Delivery in 2 days:Sunday:20$",
            "Delivery in 2 days:Sunday:20$"};*/

    ListView myCartLV;
    ImageView iv_menu;
    //ArrayList<MyOrderConst> myOrderConsts;
    ArrayList<MyCartContructor> myCartContructors;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart_screen);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);


        userid = sharedPreferences.getString("id", "").toString();
        Log.d("--userid",userid);

        Common.SlideMenuDesign(slidingMenu, MyCart.this, "mycart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        myCartLV=(ListView)findViewById(R.id.lv_mycart);
        myCartContructors = new ArrayList<MyCartContructor>();
        /*for(int i=0;i<myCartImage.length;i++)
        {

            MyCartContructor productItem = new MyCartContructor(myCartImage[i],myCartTitle[i],myCartWeight[i],
                    myCartcost[i],myCartMerchant[i],myCartDelwith[i],myCartDelwithout[i]);
            myCartContructors.add(productItem);
            myCartLV=(ListView)findViewById(R.id.lv_mycart);
            final MyCartAdapter adapter=new MyCartAdapter(MyCart.this,myCartContructors);
            myCartLV.setAdapter(adapter);
        }*/
        new MyCart.GetBuyersCart().execute();

    }

    private class GetBuyersCart extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String productid;
        Boolean isSelected=false;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/orders.php?function=BuyerCart&uid="+userid,2,nameValuePairs);
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
            try
            {
                result = new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success"))
                {
                    JSONArray array = new JSONArray(result.getString("data"));
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());
                        productid=data.getString("product_id").toString();
                        new MyCart.GetProductData(productid).execute();
                    }
                }
                else if(result.getString("status").toString().equals("Failed"))
                {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyCart.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage("No Products in the Cart to Display");
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent in=new Intent(MyCart.this,UserProductGridview.class);
                            startActivity(in);

                        }
                    }).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }




    }

    public class GetProductData extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String productTitle,productWeight,productDelivery,productid,productCost,productImage,productMerchant,productcost,proid;
        Boolean isSelected=false;

        public GetProductData(String productid) {
            this.productid = productid;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/products.php?function=getProduct&pid="+productid,2,nameValuePairs);
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

            try {

                JSONObject result= new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success"))
                {

                    JSONArray array=new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        productTitle=data.getString("product_name").toString();
                        productWeight=data.getString("avail_stock").toString();
                        productcost=data.getString("price").toString();
                        productMerchant=data.getString("merchant_name").toString();
                        productImage=data.getString("image").toString();
                        proid=data.getString("id").toString();


                        //new MyorderActivity.GetproductData(product_id).execute();
                        Log.d("--productImage",productImage);
                        Log.d("--productTitle",productTitle);
                        Log.d("--productWeight",productWeight);
                        Log.d("--productMerchant",productMerchant);
                        Log.d("--productcost",productcost);
                        Log.d("--proid",proid);

                        procount++;
                        myCartContructors.add(new MyCartContructor(productImage,
                                productTitle,
                                productWeight,
                                productCost,
                                productMerchant,proid));
                        final MyCartAdapter adapter=new MyCartAdapter(MyCart.this,myCartContructors);
                        myCartLV.setAdapter(adapter);
                    }
                    if(procount==0)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyCart.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("No Available Products For this Merchant");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(MyCart.this,UserProductGridview.class);
                                startActivity(in);

                            }
                        }).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
