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
 * Created by HP on 1/10/2018.
 */

public class CategoryProducts extends Activity {
    private GridView gv_catproducts;
    ArrayList<UserProductConstructor> userProductConstructors;
    UserProductAdapter userProductAdapter1;
    ImageView iv_profile,iv_menu;
    SlidingMenu slidingMenu;
    ListView allproductsLV;
    int procount=0;
    String buy="Buy Now";
    String merchant_id,image,product_name,procategoryname,product_id,pro_del,merchant_name;
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
        setContentView(R.layout.category_products);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        userProductConstructors=new ArrayList<UserProductConstructor>();
        procategoryname=getIntent().getStringExtra("category").toString();
        Log.d("--procategoryname",procategoryname);
        Common.SlideMenuDesign(slidingMenu, CategoryProducts.this, "categoryproductactivity");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        gv_catproducts = (GridView) findViewById(R.id.catgv_products);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile=new Intent(CategoryProducts.this,MyProfile.class);
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
        new CategoryProducts.GetAllproducts().execute();

        gv_catproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ArrayList<UserProductConstructor> array = new ArrayList<UserProductConstructor>();
                Intent onitem=new Intent(CategoryProducts.this,ProductDetails.class);
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
                Log.d("Data : ",merchant_id+"\n"+image+"\n"+price+"\n"+product_name+"\n"+product_quantity+"\n"+product_id);
                startActivity(onitem);


            }
        });
    }

    private class GetAllproducts extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String productimage,producttitle,productbuy=buy,merchant_name,productid,merchantid,proddel,status,
                userid,productqunat,productcat;
        int Availabe_stock,productprice;

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

                if (result.getString("status").toString().equals("Success")) {

                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        productimage=data.getString("image").toString();
                        producttitle=data.getString("product_name").toString();
                        productprice=data.getInt("price");
                        Availabe_stock=data.getInt("total_stock");
                        merchant_name=data.getString("merchant_name").toString();
                        productid=data.getString("id").toString();
                        merchantid=data.getString("merchant_id").toString();
                        productqunat=data.getString("quantity").toString();
                        productcat=data.getString("category").toString();
                        proddel=data.getString("delivery_id").toString();
                        status=data.getString("status").toString();





                        SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", producttitle);
                        editor.putString("image",productimage);
                        editor.putInt("price",productprice);
                        editor.putInt("Availabe_stock",Availabe_stock);
                        editor.putString("merchant_name",merchant_name);
                        editor.putString("id",productid);
                        editor.putString("merchant_id",merchantid);
                        editor.putString("category",productcat);
                        editor.putString("category",productcat);

                        editor.commit();


                        Log.d("--productimage",productimage);
                        Log.d("--producttitle",producttitle);
                        Log.d("--productprice",String.valueOf(productprice));
                        Log.d("--Availabe_stock", String.valueOf(Availabe_stock));
                        Log.d("--productid",productid);
                        Log.d("--category",productcat);
                        Log.d("--productqunat",productqunat);
                        Log.d("--proddel",proddel);



                        //Log.d("--delivered",delivered);

                        //int productimage, String producttitle, String productprice, String productbuy

                        if(productcat.equalsIgnoreCase(procategoryname)) {


                            if(Availabe_stock!=0 || status.equalsIgnoreCase("Active")) {

                                procount++;

                                userProductConstructors.add(new UserProductConstructor(merchantid.toString(), productid.toString(), merchant_name.toString(), productimage.toString(),
                                        producttitle.toString(),
                                        productprice,
                                        productbuy.toString(), Availabe_stock, proddel
                                ));

                                final UserProductAdapter adapter = new UserProductAdapter(CategoryProducts.this, userProductConstructors);
                                gv_catproducts.setAdapter(adapter);
                            }

                        }


                    }
                    if(procount==0)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CategoryProducts.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("No Products Available For this Category");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(CategoryProducts.this,UserProductGridview.class);
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
