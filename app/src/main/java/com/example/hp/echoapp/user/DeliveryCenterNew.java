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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.echoapp.Validations;
import com.example.hp.echoapp.adapter.DeliveryNewAdapter;
import com.example.hp.echoapp.constructors.DeliverConst;
import com.example.hp.echoapp.constructors.DeliverynewCons;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.merchant.MerchantLogin;
import com.example.hp.echoapp.merchant.MerchantProducts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 12/8/2017.
 */

public class DeliveryCenterNew extends Activity
{
    public List<String> address;
    private ArrayList<String> deladdress;
    String deliveryaddress,del_id;
    int delcount;


    ListView deliveryLV;
    ArrayList<DeliverConst> deliverConsts;
    ArrayList<DeliverynewCons> deliverynewConst;
    DeliveryNewAdapter deliveryNewAdapter;

    SlidingMenu slidingMenu;

    ImageView iv_menu;
    Button bt_deliveredto;
    int orderedquan;
    double orderedprice,shopproductprice;
    String shopproducttitle,productimagepath,prodid,prodel,promername;
    String deliveryFullname,deliveryEmail,deliveryAddress,deliveryState,deliveryCountry,deliveryZipcode,deliverycity,deliveryMobile;


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
        address = new ArrayList<String>();
        Common.SlideMenuDesign(slidingMenu, DeliveryCenterNew.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });

        bt_deliveredto=(Button)findViewById(R.id.bt_deliveredto);

        deliveryLV=(ListView)findViewById(R.id.del_list);
        deladdress = new ArrayList<String>();

        orderedquan=getIntent().getIntExtra("quantit" ,0);
        orderedprice=getIntent().getDoubleExtra("pricepro",0);
        shopproducttitle=getIntent().getStringExtra("product_name").toString();
        prodid=getIntent().getStringExtra("shopproid").toString();

        productimagepath=getIntent().getStringExtra("image").toString();
        prodel=getIntent().getStringExtra("prodel").toString();
        promername=getIntent().getStringExtra("promername").toString();

        Log.d("prodel",prodel);
        Log.d("promername",promername);





        deliverConsts = new ArrayList<DeliverConst>();
        deliverynewConst=new ArrayList<DeliverynewCons>();

        deliveryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                //Toast.makeText(DeliveryCenterNew.this, "==data val"+deliverynewConst.get(i).getAddress(), Toast.LENGTH_SHORT).show();
                deliveryaddress=deliverynewConst.get(i).getAddress();
                del_id=deliverynewConst.get(i).getDelid();
                //Toast.makeText(DeliveryCenterNew.this, "++delid"+del_id, Toast.LENGTH_SHORT).show();


            }
        });

        bt_deliveredto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent checkout = new Intent(DeliveryCenterNew.this, OrderDetails.class);

                    checkout.putExtra("quantit", orderedquan);
                    checkout.putExtra("pricepro", orderedprice);
                    checkout.putExtra("product_name", shopproducttitle);
                    checkout.putExtra("prodid", prodid);
                    checkout.putExtra("image", productimagepath);
                    checkout.putExtra("address", deliveryaddress);
                    checkout.putExtra("del_id", del_id);
                    checkout.putExtra("promername", promername);
                    Log.d("++quantit", String.valueOf(orderedquan));
                    Log.d("++pricepro", String.valueOf(shopproductprice));
                    Log.d("++product_name", shopproducttitle);
                    Log.d("++image", productimagepath);
                    Log.d("++deliveryaddress", deliveryaddress);
                    Log.d("++del_id", del_id);
                    startActivity(checkout);
                    overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                    //Toast.makeText(DeliveryCenterNew.this, "==delivered to " + deliveryaddress, Toast.LENGTH_SHORT).show();


            }
        });

        /*for(int i=0;i<deliveryName.length;i++)
        {

            DeliverynewCons productItem = new DeliverynewCons(deliveryName[i]);
            deliverynewConst.add(productItem);
            deliveryLV=(ListView)findViewById(R.id.del_list);
            final DeliveryNewAdapter adapter=new DeliveryNewAdapter(DeliveryCenterNew.this,deliverynewConst);
            deliveryLV.setAdapter(adapter);
            deliveryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent bal=new Intent(DeliveryCenterNew.this,DeliveryCenterNew.class);
                    startActivity(bal);
                }
            });
        }*/
            /*bt_deliveredto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent checkout=new Intent(DeliveryCenterNew.this,OrderDetails.class);
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




        new DeliveryCenterNew.GetDeliveryCenters().execute();


    }

    private class GetDeliveryCenters extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;

        private JSONObject json;
        String delid,fullname,delemail, address,state,zipcode,country,city, mobile;
        Boolean isSelected=false;


        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/deliverycenters.php?function=getAllcenters",2,nameValuePairs);
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

                        delid=data.getString("id").toString();
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
                    Log.d("--delid",delid);

                    if(delid.equalsIgnoreCase(prodel)) {
                        //String _id,fullname,delemail,password, address,state,zipcode,country,city, mobile;
                        delcount++;
                        String addressfull = fullname.toString() + "\n" + delemail.toString() + "\n" + address.toString() + "\n" +
                                state.toString() + "\n" + zipcode.toString() + "\n" + country.toString() + "\n" + city.toString() + "\n" + mobile.toString();


                        deliverynewConst.add(new DeliverynewCons(addressfull, delid));
                        Log.d("((deladdress", String.valueOf(addressfull));
                        deladdress.add(addressfull);


                        Log.d("((deladdress", String.valueOf(deladdress));
                        //final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenterNew.this,deliverConsts);
                        final DeliveryNewAdapter adapter = new DeliveryNewAdapter(DeliveryCenterNew.this, deliverynewConst);
                        deliveryLV.setAdapter(adapter);

                    }


                    }
                    if(delcount==0)

                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DeliveryCenterNew.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage("This Product is Not Assigned to any delivery center");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(DeliveryCenterNew.this,UserProductGridview.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
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
