package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.adapter.MyOrderListviewAdapter;
import com.example.hp.echoapp.constructors.MyOrderConst;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 10/23/2017.
 */

public class MyorderActivity extends Activity{

    SlidingMenu slidingMenu;
    String productImage;
    public static final Integer[] productImages={R.drawable.brinjal,
            R.drawable.clustrerbean,R.drawable.cabbage,R.drawable.capsicam};
    public static final String[] productTitle={"Brinjal",
            "Clusterbean","Cabbage","Capsicum"};
    public static final String[] productWeight={"50 kilos Bag",
            "50 kilos Bag","50 kilos Bag","50 kilos Bag"};
    public static final String[] productDelivery={"We will deliver On 18th dec",
            "We will deliver On 18th dec","We will deliver On 18th dec","We will deliver On 18th dec"};


    ListView myorderlistview;
    ArrayList<MyOrderConst> myOrderConsts;
    ImageView iv_menu;
    SharedPreferences sharedPreferences;
    String userid;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder_listview);
        slidingMenu = new SlidingMenu(this);

        myorderlistview=(ListView)findViewById(R.id.lv_myorders);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);

        Common.SlideMenuDesign(slidingMenu, MyorderActivity.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);


        userid = sharedPreferences.getString("id", "").toString();
        Log.d("--userid",userid);
        myOrderConsts = new ArrayList<MyOrderConst>();
        /*for(int i=0;i<productImages.length;i++)
        {
            MyOrderConst productItem = new MyOrderConst(productImages[i],productTitle[i],productWeight[i],productDelivery[i]);
            myOrderConsts.add(productItem);
            myorderlistview=(ListView)findViewById(R.id.lv_myorders);
            final MyOrderListviewAdapter adapter=new MyOrderListviewAdapter(this,R.layout.myorder_singlelist,myOrderConsts);
            myorderlistview.setAdapter(adapter);
            myorderlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent orderclick=new Intent(MyorderActivity.this,OrderDetails.class);
                    startActivity(orderclick);
                }
            });
        }*/

       new MyorderActivity.GetBuyersOrders().execute();

    }

    private class GetBuyersOrders extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String productImage,merchantname,productWeight,productDelivery;
        Boolean isSelected=false;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/orders.php?function=getBuyerorders&uid="+userid,2,nameValuePairs);
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

                        productImage=data.getString("product_image").toString();
                        merchantname=data.getString("merchant_name").toString();
                        productWeight=data.getString("quantity").toString();
                        productDelivery=data.getString("product_name").toString();


                       //new MyorderActivity.GetproductData(product_id).execute();
                        Log.d("--productImage",productImage);
                        Log.d("--productTitle",merchantname);
                        Log.d("--productWeight",productWeight);
                        Log.d("--productDelivery",productDelivery);

                        myOrderConsts.add(new MyOrderConst(productImage,merchantname.toString(),productWeight.toString(),
                                productDelivery.toString()));
                       //final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenter.this,deliverConsts);
                       final MyOrderListviewAdapter adapter=new MyOrderListviewAdapter(MyorderActivity.this,myOrderConsts);
                        myorderlistview.setAdapter(adapter);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

   /* private class GetproductData extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String product_id;

        public GetproductData(String product_id) {
            this.product_id = product_id;

        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json=JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/products.php?function=getProduct&pid="+product_id,2,nameValuePairs);
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


                        product_id=data.getString("product_id").toString();

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/

}
