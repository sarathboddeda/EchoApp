package com.example.hp.echoapp.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.adapter.UserProductAdapter;
import com.example.hp.echoapp.merchant.MerchantProducts;
import com.example.hp.echoapp.constructors.UserProductConstructor;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 1/10/2018.
 */

public class GetMerchantProducts extends Activity {
    private GridView mergv_products;
    ArrayList<UserProductConstructor> userProductConstructors;
    UserProductAdapter userProductAdapter1;
    ImageView iv_profile,iv_menu;
    SlidingMenu slidingMenu;
    ListView allproductsLV;
    String buy="Buy Now";
    int procount=0;
    String merchant_id,image,product_name,merchantname,product_id,promerid,pro_del,merchant_name;
    int product_quantity;
    double price;

    UserProductAdapter userProductAdapter;
    private ProgressDialog progressDialog;
    String BaseUrl="http://media3.co.in/backend/echoapp/services2/products.php?function=getAllproducts";

    public static final int[] productImage = {R.drawable.brinjal, R.drawable.capsicam, R.drawable.cabbage, R.drawable.clustrerbean};
    public static final String[] productTitle = {"Brinjal", "Capsicum", "Cabbage", "Cluster Beans"};
    public static final String[] productPrice = {"$10 per kg", "$10 per kg", "$10 per kg", "$10 per kg"};
    public static final String[] productBuy = {"Buy Now", "Buy Now", "Buy Now", "Buy Now"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_product);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        userProductConstructors=new ArrayList<UserProductConstructor>();
        promerid=getIntent().getStringExtra("merid").toString();
        Log.d("--promerid",promerid);
        Common.SlideMenuDesign(slidingMenu, GetMerchantProducts.this, "merchantproductactivity");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        mergv_products = (GridView) findViewById(R.id.mergv_products);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile=new Intent(GetMerchantProducts.this,MyProfile.class);
                startActivity(profile);
            }
        });


        new GetMerchantProducts.GetMerchantproducts().execute();

        mergv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ArrayList<UserProductConstructor> array = new ArrayList<UserProductConstructor>();
                Intent onitem=new Intent(GetMerchantProducts.this,ProductDetails.class);
                image=userProductConstructors.get(pos).getProductimage().toString();
                price=userProductConstructors.get(pos).getProductprice();
                product_name=userProductConstructors.get(pos).getProducttitle().toString();
                product_quantity=userProductConstructors.get(pos).getProductQuantity();
                product_id=userProductConstructors.get(pos).getProductid().toString();
                merchant_id=userProductConstructors.get(pos).getMerchantid();
                pro_del=userProductConstructors.get(pos).getProductdel();
                merchant_name=userProductConstructors.get(pos).getMerchantName();
                onitem.putExtra("position",array);
                onitem.putExtra("image",userProductConstructors.get(pos).getProductimage().toString());
                onitem.putExtra("product_name",userProductConstructors.get(pos).getProducttitle().toString());
                onitem.putExtra("price",userProductConstructors.get(pos).getProductprice());
                onitem.putExtra("total_stock",userProductConstructors.get(pos).getProductQuantity());
                onitem.putExtra("product_id",userProductConstructors.get(pos).getProductid().toString());
                onitem.putExtra("merchant_id",userProductConstructors.get(pos).getMerchantid().toString());
                onitem.putExtra("pro_del",pro_del);
                onitem.putExtra("merchant_name",merchant_name);

                /*Toast toast = Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT);
               toast.show();*/
                Log.d("Data : ",merchant_id+"\n"+image+"\n"+price+"\n"+product_name+"\n"+product_quantity+"\n"+product_id+"\n"+pro_del);
                startActivity(onitem);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);

            }
        });
    }

    private class GetMerchantproducts extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String productimage,producttitle,productbuy=buy,merchant_name,productid,merchantid,proddel,status,
                userid,productqunat,productcat;
        int productprice,available_stock;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall(BaseUrl,2,nameValuePairs);
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(GetMerchantProducts.this);
            progressDialog.setMessage("Loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
            JSONObject result = null;
            try {
                result = new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success")) {

                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        productimage=data.getString("image").toString();
                        producttitle=data.getString("product_name").toString();
                        productprice=data.getInt("price");
                        available_stock=data.getInt("avail_stock");
                        merchant_name=data.getString("merchant_name").toString();
                        productid=data.getString("id").toString();
                        merchantid=data.getString("merchant_id").toString();
                        productqunat=data.getString("quantity").toString();
                        productcat=data.getString("category").toString();
                        proddel=data.getString("delivery_id").toString();
                        status=data.getString("status").toString();




                       /* SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", producttitle);
                        editor.putString("image",productimage);
                        editor.putString("price",String.valueOf(productprice));
                        editor.putInt("available_stock",available_stock);
                        editor.putString("merchant_name",merchant_name);
                        editor.putString("id",productid);
                        editor.putString("merchant_id",merchantid);
                        editor.putString("category",productcat);

                        editor.commit();*/


                        Log.d("--productimage",productimage);
                        Log.d("--producttitle",producttitle);
                        Log.d("--productprice",String.valueOf(productprice));
                        Log.d("--avail_stock",String.valueOf(available_stock));
                        Log.d("--productid",productid);
                        Log.d("--category",productcat);
                        Log.d("--merchant_name",merchant_name);
                        Log.d("--merchant_name",merchant_name);
                        Log.d("--proddel",proddel);
                        Log.d("--merchantid",merchantid);


                        //Log.d("--delivered",delivered);
                        //int productimage, String producttitle, String productprice, String productbuy

                        if(promerid.equalsIgnoreCase(merchantid)) {


                            if(available_stock!=0 && status.equalsIgnoreCase("Active")) {

                                procount++;

                                userProductConstructors.add(new UserProductConstructor(merchantid.toString(), productid.toString(), merchant_name.toString(), productimage.toString(),
                                        producttitle.toString(),
                                        productprice,
                                        productbuy.toString(), available_stock, proddel));

                                final UserProductAdapter adapter = new UserProductAdapter(GetMerchantProducts.this, userProductConstructors);
                                mergv_products.setAdapter(adapter);
                            }



                        }


                    }
                    if(procount==0)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(GetMerchantProducts.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("No Available Products For this Merchant");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(GetMerchantProducts.this,UserProductGridview.class);
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
