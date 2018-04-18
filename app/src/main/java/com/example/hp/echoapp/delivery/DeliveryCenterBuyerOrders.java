package com.example.hp.echoapp.delivery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.adapter.DeliverybuyerOrdersAdapter;
import com.example.hp.echoapp.constructors.DeliveryOrderConst;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.constructors.UserProductConstructor;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 2/12/2018.
 */

public class DeliveryCenterBuyerOrders extends Activity {
    SlidingMenu slidingMenu;
    ListView myorderlistview;
    ArrayList<DeliveryOrderConst> deliveryOrderConsts;
    ImageView iv_menu;
    SharedPreferences sharedPreferences;
    String delid,orderedImage,orderedproduct_id,orderedprice,orderedproduct_name,orderedproduct_quantity,orderedbuyer_id,
            orderedbuyer_name,orderedmerchant_name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userordereddetails);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);

        myorderlistview=(ListView)findViewById(R.id.lv_userorderd);

        DeliveryCommon.SlideMenuDesign(slidingMenu, DeliveryCenterBuyerOrders.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);


        delid = sharedPreferences.getString("id", "").toString();
        Log.d("--delid",delid);
        deliveryOrderConsts = new ArrayList<DeliveryOrderConst>();
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

        new DeliveryCenterBuyerOrders.GetBuyersOrders().execute();

        myorderlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ArrayList<UserProductConstructor> array = new ArrayList<UserProductConstructor>();
                Intent onitem=new Intent(DeliveryCenterBuyerOrders.this,UserOrderedDeliveryRating.class);
                orderedImage=deliveryOrderConsts.get(pos).getProductImage().toString();
                orderedproduct_id=deliveryOrderConsts.get(pos).getProduct_id().toString();
                orderedprice=deliveryOrderConsts.get(pos).getProductWeight();
                orderedproduct_name=deliveryOrderConsts.get(pos).getProductTitle().toString();
                orderedproduct_quantity=deliveryOrderConsts.get(pos).getProductWeight();
                orderedbuyer_id=deliveryOrderConsts.get(pos).getUser_id();
                orderedmerchant_name=deliveryOrderConsts.get(pos).getMerchant_name();
                orderedbuyer_name=deliveryOrderConsts.get(pos).getBuyer_name();

                Log.d("orderedImage",orderedImage);
                Log.d("***orderedproduct_id",orderedproduct_id);
                Log.d("***orderedprice",orderedprice);
                Log.d("***orderedproduct_name",orderedproduct_name);
                Log.d("***orderedprot_quantity",orderedproduct_quantity);
                Log.d("***orderedbuyer_id",orderedbuyer_id);
                Log.d("***orderedmerchant_name",orderedmerchant_name);
                Log.d("***orderedbuyer_name",orderedbuyer_name);

                onitem.putExtra("position",array);

                onitem.putExtra("orderedImage",orderedImage);
                onitem.putExtra("orderedproduct_id",orderedproduct_id);
                onitem.putExtra("orderedprice",orderedprice);
                onitem.putExtra("orderedproduct_name",orderedproduct_name);
                onitem.putExtra("orderedbuyer_id",orderedbuyer_id);
                onitem.putExtra("orderedmerchant_name",orderedmerchant_name);
                onitem.putExtra("orderedbuyer_name",orderedbuyer_name);
                onitem.putExtra("orderedprot_quantity",orderedproduct_quantity);
                /*Toast toast = Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT);
               toast.show();*/
                //Log.d("Data : ",merchant_id+"\n"+image+"\n"+price+"\n"+product_name+"\n"+product_quantity+"\n"+product_id);
                startActivity(onitem);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);

            }
        });



    }

    private class GetBuyersOrders extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String productImage,productTitle,productWeight,productDelivery,product_id,merchant_name,buyer_name,user_id,dcenter_id;
        Boolean isSelected=false;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("did",delid));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/deliverycenters.php?function=GetOrderdetails",2,nameValuePairs);
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
                        productImage=data.getString("product_image").toString();
                        productTitle=data.getString("product_name").toString();
                        productWeight=data.getString("quantity").toString();
                        productDelivery=data.getString("purchase_date").toString();
                        merchant_name=data.getString("merchant_name").toString();
                        buyer_name=data.getString("buyer_name").toString();
                        user_id=data.getString("user_id").toString();
                        dcenter_id=data.getString("dcenter_id").toString();


                        //new MyorderActivity.GetproductData(product_id).execute();
                        Log.d("--productImage",productImage);
                        Log.d("--productTitle",productTitle);
                        Log.d("--productWeight",productWeight);
                        Log.d("--productDelivery",productDelivery);

                        deliveryOrderConsts.add(new DeliveryOrderConst(productImage,productTitle.toString(),productWeight.toString(),
                                productDelivery.toString(),product_id.toString(),merchant_name.toString(),
                                buyer_name.toString(),user_id.toString(),dcenter_id.toString()));
                        //final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenter.this,deliverConsts);
                        final DeliverybuyerOrdersAdapter adapter=new DeliverybuyerOrdersAdapter(DeliveryCenterBuyerOrders.this,deliveryOrderConsts);
                        myorderlistview.setAdapter(adapter);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



}

