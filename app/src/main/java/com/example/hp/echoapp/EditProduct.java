package com.example.hp.echoapp;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hp.echoapp.merchant.merchantCommon;
import com.example.hp.echoapp.user.UserProductGridview;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 1/5/2018.
 */

public class EditProduct extends Activity {
    EditText et_editproname,et_editproprice,et_editproquanitity,et_editdescpro;
    Button bt_editupload,bt_editproduct;
    ImageView iv_menu;
    SlidingMenu slidingMenu;
    SharedPreferences sharedPreferences;

    String merchant_id,editproid,editprocost,editproquanity,editprodesc,editproImage,product_id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
        merchant_id = sharedPreferences.getString("id", "").toString();
        Log.d("merchant_id",merchant_id);

        merchantCommon.SlideMenuDesign(slidingMenu, EditProduct.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        et_editproname=(EditText)findViewById(R.id.et_editproname);
        et_editproprice=(EditText)findViewById(R.id.et_editproprice);
        et_editproquanitity=(EditText)findViewById(R.id.et_editproquanitity);
        et_editdescpro=(EditText)findViewById(R.id.et_editdescpro);
        bt_editproduct=(Button)findViewById(R.id.bt_editaddproduct);
        editproid=getIntent().getStringExtra("proid").toString();
        new EditProduct.editProduct().execute();

    }

    private class editProduct extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String pid, uid,token;



        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/products.php?function=getProduct&pid="+editproid, 2, nameValuePairs);
            return json;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            //super.onPostExecute(jsonObject);


            try {
                JSONObject result= new JSONObject(jsonObject.toString());
                if(result.getString("status").equals("Success"))
                {
                    JSONArray array=new JSONArray(result.getString("data"));
                    for(int i=0;i<array.length();i++) {
                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        String pro_name, pro_image, pro_price, passporno, address, roletype, contact_no, company_reg, year_estd, product_cert,
                                merchant_id, merchant_passport;
                        //merchant_id = data.getString("_id").toString();
                        pro_name = data.getString("product_name").toString();
                        pro_image = data.getString("image").toString();
                        pro_price = data.getString("price").toString();
                        contact_no = data.getString("contact_no").toString();
                        year_estd = data.getString("year_estd").toString();
                        product_cert = data.getString("product_cert").toString();
                        passporno = data.getString("passport").toString();

                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }
}
