package com.example.hp.echoapp.user;

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
import android.widget.TextView;

import com.example.hp.echoapp.adapter.AllCatAdapter;
import com.example.hp.echoapp.constructors.AllcategoryConst;
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

public class GetAllCategories extends Activity {
    TextView tv_cat_name;
    SlidingMenu slidingMenu;
    ImageView iv_menu;
    ListView ll_allcategories;
    ArrayList<AllcategoryConst> allcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_categories);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        Common.SlideMenuDesign(slidingMenu, GetAllCategories.this, "shoppingcart");
        allcategory=new ArrayList<AllcategoryConst>();
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        findViewById();

        new GetAllCategories.GetallCategories().execute();
        ll_allcategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent category=new Intent(GetAllCategories.this,CategoryProducts.class);
             category.putExtra("category",allcategory.get(i).getCatid());
                startActivity(category);
            }
        });
    }
    private  void findViewById()
    {
        tv_cat_name=(TextView)findViewById(R.id.tv_cat_name);
        ll_allcategories=(ListView)findViewById(R.id.ll_allcategory);
    }

    private class GetallCategories extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String _id,categoryName,merchantNumber;


        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/category.php?function=Getallcategories",2,nameValuePairs);
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

                        categoryName=data.getString("category_name").toString();
                        _id=data.getString("id").toString();

                        SharedPreferences sharedPreferences = getSharedPreferences("allmerchantDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("category_name", categoryName);
                        editor.putString("id",_id);
                        editor.commit();



                        Log.d("--categoryName",categoryName);
                        Log.d("--id",_id);


                        //String _id,fullname,delemail,password, address,state,zipcode,country,city, mobile;

                       /* allMerchantConsts.add(new AllMerchantConst(_id.toString(),merchantName.toString(),merchantNumber.toString());
                        //final DeliveryAdapter adapter=new DeliveryAdapter(DeliveryCenter.this,deliverConsts);
                        final GetallMerchants adapter=new GetAllMerchants(GetAllMerchants.this,allMerchantConsts);
                        ll_allmerchants.setAdapter(adapter);*/

                        allcategory.add(new AllcategoryConst(
                                _id.toString(),
                                categoryName.toString()));
                        final AllCatAdapter adapter=new AllCatAdapter(GetAllCategories.this,allcategory);
                        ll_allcategories.setAdapter(adapter);

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
