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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.adapter.UserProductAdapter;
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
 * Created by HP on 10/17/2017.
 */

public class UserProductGridview extends Activity {
    private GridView gv_products;
    ArrayList<UserProductConstructor> userProductConstructors;
    UserProductAdapter userProductAdapter1;
    ImageView iv_profile,iv_menu;
    SlidingMenu slidingMenu;
    ListView allproductsLV;
    String buy="Buy Now";
    int procount=0;
    String merchant_id,image,product_name,product_id,merchant_name,pro_del;
    int product_quantity;
    double price;

    UserProductAdapter userProductAdapter;
    String BaseUrl="http://media3.co.in/backend/echoapp/services2/products.php?function=getAllproducts";

    public static final int[] productImage = {R.drawable.brinjal, R.drawable.capsicam, R.drawable.cabbage, R.drawable.clustrerbean};
    public static final String[] productTitle = {"Brinjal", "Capsicum", "Cabbage", "Cluster Beans"};
    public static final String[] productPrice = {"$10 per kg", "$10 per kg", "$10 per kg", "$10 per kg"};
    public static final String[] productBuy = {"Buy Now", "Buy Now", "Buy Now", "Buy Now"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productsgridview);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        userProductConstructors=new ArrayList<UserProductConstructor>();

        Common.SlideMenuDesign(slidingMenu, UserProductGridview.this, "userproductactivity");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        gv_products = (GridView) findViewById(R.id.gv_products);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile=new Intent(UserProductGridview.this,MyProfile.class);
                startActivity(profile);
            }
        });

        /*userProductConstructors=new ArrayList<UserProductConstructor>();
        for (int i = 0; i < productImage.length; i++) {
            UserProductConstructor item = new UserProductConstructor(productImage.toString(), productTitle.toString(), productPrice.toString(),buy);
            userProductConstructors.add(item);
        }
        final UserProductAdapter adapter = new UserProductAdapter(UserProductGridview.this,userProductConstructors);
        gv_products.setAdapter(adapter);*/
        new GetAllproducts().execute();

        gv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ArrayList<UserProductConstructor> array = new ArrayList<UserProductConstructor>();
                Intent onitem=new Intent(UserProductGridview.this,ProductDetails.class);
                image=userProductConstructors.get(pos).getProductimage().toString();
                product_id=userProductConstructors.get(pos).getProductid().toString();
                price=userProductConstructors.get(pos).getProductprice();
                product_name=userProductConstructors.get(pos).getProducttitle().toString();
                product_quantity=userProductConstructors.get(pos).getProductQuantity();
                merchant_id=userProductConstructors.get(pos).getMerchantid();
                merchant_name=userProductConstructors.get(pos).getMerchantName();
                pro_del=userProductConstructors.get(pos).getProductdel();

                Log.d("++pro_del",pro_del);

                String upperproname = product_name.substring(0,1).toUpperCase() + product_name.substring(1);

                onitem.putExtra("position",array);

                onitem.putExtra("image",image);
                onitem.putExtra("product_name",upperproname);
                onitem.putExtra("price",price);
                onitem.putExtra("available_stock",product_quantity);
                onitem.putExtra("product_id",product_id);
                onitem.putExtra("merchant_id",merchant_id);
                onitem.putExtra("merchant_name",merchant_name);
                onitem.putExtra("pro_del",pro_del);
                /*Toast toast = Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT);
               toast.show();*/
                Log.d("Data : ",merchant_id+"\n"+image+"\n"+price+"\n"+product_name+"\n"+product_quantity+"\n"+product_id);
                startActivity(onitem);


            }
        });
    }

    private class GetAllproducts extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String productimage,productDel;
        private String producttitle;
        private Double productprice;
        private String productbuy=buy;
        private String merchant_name;
        private String productid;
        private String merchantid;
        private String status;
        private String productqunat;
        private int available_stock;
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
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONObject result = null;
            try {
                result = new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success"))
                {

                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        productimage=data.getString("image").toString();
                        producttitle=data.getString("product_name").toString();
                        productprice=data.getDouble("price");
                        available_stock=data.getInt("avail_stock");
                        merchant_name=data.getString("merchant_name").toString();
                        productid=data.getString("id").toString();
                        merchantid=data.getString("merchant_id").toString();
                        productqunat=data.getString("quantity").toString();
                        productDel=data.getString("delivery_id").toString();
                        status=data.getString("status").toString();

                        String upperproducttitle = producttitle.substring(0,1).toUpperCase() + producttitle.substring(1);

                        SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", upperproducttitle);
                        editor.putString("image",productimage);
                        editor.putString("price", String.valueOf(productprice));
                        editor.putString("total_stock", String.valueOf(available_stock));
                        editor.putString("merchant_name",merchant_name);
                        editor.putString("id",productid);
                        editor.putString("merchant_id",merchantid);

                        editor.commit();


                        Log.d("--productimage",productimage);
                        Log.d("--producttitle",upperproducttitle);
                        Log.d("--productprice",String.valueOf(productprice));
                        Log.d("--total_stock",String.valueOf(available_stock));
                        Log.d("--productid",productid);
                        Log.d("--productDel",productDel);
                        Log.d("--status",status);
                        if(available_stock!=0 && status.equalsIgnoreCase("Active"))
                        {
                            procount++;
                            Log.d("+-procount", String.valueOf(procount));
                            userProductConstructors.add(new UserProductConstructor(merchantid, productid, merchant_name, productimage,
                                    upperproducttitle, productprice, productbuy, available_stock,productDel));
                            final UserProductAdapter adapter = new UserProductAdapter(UserProductGridview.this, userProductConstructors);
                            gv_products.setAdapter(adapter);
                        }



                    }
                    if(procount==0)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserProductGridview.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("No Available Products");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(UserProductGridview.this,UserProductGridview.class);
                                startActivity(in);

                            }
                        }).show();
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



    /* Service integration to place orders
    *
    *  http://162.251.83.200/~wwwm3phpteam/echoapp/services2/orders.php?function=addOrder&uid=44&pid=5&price=400&image=apple.jpg&quantity=20
    * */


}