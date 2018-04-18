package com.example.hp.echoapp.merchant;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.example.hp.echoapp.adapter.AvailableAdapter;
import com.example.hp.echoapp.constructors.AvailableConst;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 11/15/2017.
 */

public class TotalProducts extends Activity {
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
    ArrayList<AvailableConst> availableConsts;
    ImageView iv_menu;
    AvailableAdapter availableAdapter;
    Button iv_addproduct;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_products);
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

        availableproductsLV=(ListView)findViewById(R.id.lv_available);
        merchantCommon.SlideMenuDesign(slidingMenu, TotalProducts.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_addproduct=(Button) findViewById(R.id.iv_addproduct);
        iv_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add=new Intent(TotalProducts.this,AddProductsNew.class);
                startActivity(add);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        availableConsts = new ArrayList<AvailableConst>();

       new TotalProducts.GetAvailableproducts().execute();
    }

    private class GetAvailableproducts extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String product_name,product_image,product_price,delivered,time,buyer_name,
                quantity,total_stock,sold_stock,avail_stock,description,image,product_id,pro_status;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/products.php?function=getmyproducts&mid="+merchant_id,1,nameValuePairs);
            Log.d("--json", String.valueOf(json));
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TotalProducts.this);
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

                JSONArray array = new JSONArray(result.getString("msg"));

                for (int i = 0; i < array.length(); i++) {

                    JSONObject data = new JSONObject(array.getJSONObject(i).toString());
                    product_name=data.getString("product_name").toString();
                    product_image=data.getString("image").toString();
                    product_price=data.getString("price").toString();
                    quantity=data.getString("quantity").toString();
                    //image=data.getString("image").toString();
                    total_stock=data.getString("total_stock").toString();
                    sold_stock=data.getString("sold_stock").toString();
                    avail_stock=data.getString("avail_stock").toString();
                    description=data.getString("description").toString();
                    time=data.getString("date_added").toString();
                    product_id=data.getString("id").toString();
                    merchant_id=data.getString("merchant_id").toString();
                    pro_status=data.getString("status").toString();
                    //buyer_name=data.getString("buyer_name").toString();

                    /*SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("product_name", product_name);
                    editor.putString("image",product_image);
                    editor.putString("price",product_price);
                    editor.putString("quantity",quantity);
                    editor.putString("total_stock",total_stock);
                    editor.putString("sold_stock",sold_stock);
                    editor.putString("avail_stock",avail_stock);
                    editor.putString("description",description);
                    editor.putString("date_added",time);
                    editor.commit();*/

                    procount++;


                    Log.d("**product_image",product_image);
                    Log.d("--product_name",product_name);
                    Log.d("--quantity",quantity);
                    Log.d("--product_price",product_price);
                    Log.d("--total_stock",total_stock);
                    Log.d("--sold_stock",sold_stock);
                    Log.d("--avail_stock",avail_stock);

                    availableConsts.add(new AvailableConst(product_image.toString(),product_name.toString(),quantity.toString(),
                            product_price.toString(),time.toString(),total_stock.toString(),
                            sold_stock.toString(),total_stock.toString(),description.toString(),merchant_id.toString(),product_id.toString(),avail_stock.toString(),pro_status,
                            buyer_name));
                    final AvailableAdapter adapter=new AvailableAdapter(TotalProducts.this,availableConsts);
                    availableproductsLV.setAdapter(adapter);

                }

                    if(procount==0)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TotalProducts.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("No Products Ordered Yet");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(TotalProducts.this,MerchantProducts.class);
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
