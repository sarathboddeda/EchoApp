package com.example.hp.echoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.hp.echoapp.user.UserProductGridview;
import com.example.hp.echoapp.delivery.DelivercenterHome;
import com.example.hp.echoapp.merchant.MerchantProducts;

/**
 * Created by HP on 10/17/2017.
 */

public class SplashScreen extends Activity {
    SharedPreferences sharedPreferences;
   String id,roletype,roletype1,id1,company_name,memail,address,contact_no,year_estd,product_cert,company_reg,merchantpassport,
           buyername,buyeremail,buyeyphone,delverid,delfullname,deladdress,delitown,delistate,delzipcode,delimobile,deliemail,delpassword,
    delicountry,delroletype;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                //Log.d("id",user);
                id = sharedPreferences.getString("id", "").toString();
                roletype = sharedPreferences.getString("roletype", "").toString();
                buyername=sharedPreferences.getString("firstname","").toString();
                buyeremail=sharedPreferences.getString("email","").toString();
                buyeyphone=sharedPreferences.getString("phone","").toString();

                Log.d("roletype buyer",roletype);
                Log.d("buyername",buyername);
                Log.d("buyeremail",buyeremail);
                Log.d("buyeyphone",buyeyphone);

                sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
                id1 = sharedPreferences.getString("id", "").toString();
                roletype1 = sharedPreferences.getString("roletype", "").toString();
                company_name = sharedPreferences.getString("company_name", "").toString();
                memail = sharedPreferences.getString("email", "").toString();
                address = sharedPreferences.getString("address", "").toString();
                contact_no = sharedPreferences.getString("contact_no", "").toString();
                year_estd = sharedPreferences.getString("year_estd", "").toString();
                product_cert = sharedPreferences.getString("product_cert", "").toString();
                company_reg=sharedPreferences.getString("company_reg","").toString();
                merchantpassport=sharedPreferences.getString("passport","").toString();

                Log.d("***id1",id1);
                Log.d("***roletype1",roletype1);
                Log.d("***company_name",company_name);
                Log.d("***memail",memail);
                Log.d("***address",address);
                Log.d("***contact_no",contact_no);
                Log.d("***year_estd",year_estd);
                Log.d("***product_cert",product_cert);


                sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);
                delverid = sharedPreferences.getString("id", "").toString();
                delfullname = sharedPreferences.getString("name", "").toString();
                deladdress = sharedPreferences.getString("address", "").toString();
                delitown = sharedPreferences.getString("city", "").toString();
                delistate = sharedPreferences.getString("state", "").toString();
                delzipcode = sharedPreferences.getString("zipcode", "").toString();
                delimobile = sharedPreferences.getString("phone", "").toString();
                deliemail = sharedPreferences.getString("email", "").toString();
                delpassword = sharedPreferences.getString("password", "").toString();
                delicountry = sharedPreferences.getString("country", "").toString();
                delroletype = sharedPreferences.getString("roletype", "").toString();

                Log.d("***delverid",delverid);
                Log.d("***delfullname",delfullname);
                Log.d("***company_name",company_name);
                Log.d("***deladdress",deladdress);
                Log.d("***delitown",delitown);
                Log.d("***delistate",delistate);
                Log.d("***delzipcode",delzipcode);
                Log.d("***delimobile",delimobile);
                Log.d("***deliemail",deliemail);
                Log.d("***delicountry",delicountry);
                Log.d("***delroletype",delroletype);

                if(roletype.equals("Buyer")) {
                        Intent i = new Intent(SplashScreen.this, UserProductGridview.class);
                        i.putExtra("id", id);
                        startActivity(i);
                        finish();
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);


                    /*Intent i = new Intent(SplashScreen.this, TypeOfSignup.class);
                    startActivity(i);
                    finish();*/
                }
                else if (roletype1.equals("Merchant"))
                {
                    Log.d("roletype1",roletype1);
                    Log.d("roleid1",id1);
                    Intent i = new Intent(SplashScreen.this, MerchantProducts.class);
                        i.putExtra("id", id1);
                        i.putExtra("company_name",company_name);
                        i.putExtra("memail",memail);
                        startActivity(i);
                        finish();
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                }
                else if (delroletype.equals("Delivery"))
                {
                    Intent i = new Intent(SplashScreen.this, DelivercenterHome.class);
                    i.putExtra("id", id1);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                }
                else
                {

                    Intent i = new Intent(SplashScreen.this, TypeOfSignup.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                }
            }
        }, 3000);

    }
}
