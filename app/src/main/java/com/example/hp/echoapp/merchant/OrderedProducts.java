package com.example.hp.echoapp.merchant;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.echoapp.adapter.OrderedAdapter;
import com.example.hp.echoapp.constructors.AvailableConst;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.constructors.OrderedConst;
import com.example.hp.echoapp.user.CategoryProducts;
import com.example.hp.echoapp.user.UserProductGridview;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 11/16/2017.
 */

public class OrderedProducts extends Activity {
    SlidingMenu slidingMenu;
    SharedPreferences sharedPreferences;
    String merchant_id;
    int procount;


    public static final Integer[] availableImage={R.drawable.brinjal, R.drawable.clustrerbean,R.drawable.cabbage,
            R.drawable.capsicam};
    public static final String[] availableTitle={"Brinjal", "Clusterbean","Cabbage","Capsicum"};
    public static final String[] availablekilos={"50 kilos Bag", "50 kilos Bag","50 kilos Bag","50 kilos Bag"};
    public static final String[] availablecost={"300$", "400$","200$","600$"};
    public static final String[] availabledelvery={"SunShine Mall","SunShine Mall","SunShine Mall","SunShine Mall"};
    public static final String[] availableeta={"2017-01-25 08:00","2017-01-25 08:00","2017-01-25 08:00","2017-01-25 08:00"};
    public static final String[] availablepro={"50 kilos Bag","70 kilos Bag","80 kilos Bag","50 kilos Bag"};
    public static final String[] availableorder={"50 kilos Bag","30 kilos Bag","20 kilos Bag","50 kilos Bag"};
    public static final String[] availablebal={"50 kilos Bag","50 kilos Bag","50 kilos Bag","50 kilos Bag"};
    ListView availableproductsLV;
    ArrayList<OrderedConst> orderedConsts;
    ImageView iv_menu;
    OrderedAdapter orderedAdapter;
    Button iv_addproduct;
    TextView tv_avail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordered_products);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);


        merchant_id = sharedPreferences.getString("id", "").toString();
        //merchant_id=getIntent().getStringExtra("merchantid").toString();

        availableproductsLV=(ListView)findViewById(R.id.lv_ordered);
        merchantCommon.SlideMenuDesign(slidingMenu, OrderedProducts.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        /*iv_addproduct=(Button) findViewById(R.id.iv_addproduct);
        iv_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add=new Intent(OrderedProducts.this,AddProductsNew.class);
                startActivity(add);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });*/
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        orderedConsts = new ArrayList<OrderedConst>();

        new OrderedProducts.GetOrderedproducts().execute();
    }

    private class GetOrderedproducts extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String product_name,product_image,product_price,delivered,time,pro_status,buyer_name,
                quantity,total_stock,sold_stock,avail_stock,description,image,product_id;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/orders.php?function=getMerchantorders",2,nameValuePairs);
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


           // JSONObject result = null;
            try {
                JSONObject result = new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success")) {

                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        product_name=data.getString("product_name").toString();
                        String producttitle = product_name.substring(0,1).toUpperCase() + product_name.substring(1);
                        product_image=data.getString("product_image").toString();
                        product_price=data.getString("total_amount").toString();
                        quantity=data.getString("quantity").toString();
                        buyer_name=data.getString("buyer_name").toString();


                        Log.d("**buyerNmae",buyer_name);


                            procount++;

                        orderedConsts.add(new OrderedConst(product_image,producttitle, quantity,product_price,buyer_name));
                            final OrderedAdapter adapter = new OrderedAdapter(OrderedProducts.this, orderedConsts);
                            availableproductsLV.setAdapter(adapter);


                    }


                    if(procount==0)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderedProducts.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("No Products Ordered Yet");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(OrderedProducts.this,MerchantProducts.class);
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
}
