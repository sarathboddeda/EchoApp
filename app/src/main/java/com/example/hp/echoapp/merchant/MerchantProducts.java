package com.example.hp.echoapp.merchant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by HP on 11/15/2017.
 */

public class MerchantProducts extends Activity {
    TextView tv_allavaliable,tv_orderedview,tv_balanceall,tv_merchantname,tv_merchantemail,tv_merchantphone;
    SlidingMenu slidingMenu;
    ImageView iv_menu,iv_profile,iv_merchanteditprofile;
    SharedPreferences sharedPreferences;
    String company_name,merchantid,merchantemail,merchantcontact_no;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_products);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        tv_merchantname=(TextView)findViewById(R.id.tv_merchantname);
        tv_merchantemail=(TextView)findViewById(R.id.tv_merchantemail);
        tv_merchantphone=(TextView)findViewById(R.id.tv_merchantphone);
        iv_merchanteditprofile=(ImageView)findViewById(R.id.iv_merchanteditprofile);

        iv_merchanteditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit=new Intent(MerchantProducts.this,MerchantEditProfile.class);
                startActivity(edit);
            }
        });


        sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
        merchantid = sharedPreferences.getString("id", "").toString();
        company_name = sharedPreferences.getString("company_name", "").toString();
        //merchantid=getIntent().getStringExtra("id").toString();
        //company_name=getIntent().getStringExtra("company_name").toString();
        merchantcontact_no=sharedPreferences.getString("contact_no","").toString();
        merchantemail=sharedPreferences.getString("email","").toString();
        //merchantemail=getIntent().getStringExtra("memail").toString();
        Log.d("**merchant_id",merchantid);

        tv_merchantname.setText(company_name);
        tv_merchantemail.setText(merchantemail);
        tv_merchantphone.setText(merchantcontact_no);


        merchantCommon.SlideMenuDesign(slidingMenu, MerchantProducts.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        tv_allavaliable=(TextView)findViewById(R.id.tv_allavaliable);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        /*iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit=new Intent(MerchantProducts.this,MerchantEditProfile.class);
                startActivity(edit);
            }
        });*/
        tv_orderedview=(TextView)findViewById(R.id.tv_orderedview);
        tv_balanceall=(TextView)findViewById(R.id.tv_balanceall);
        tv_allavaliable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allproducts=new Intent(MerchantProducts.this,TotalProducts.class);
                allproducts.putExtra("merchantid",merchantid);
                startActivity(allproducts);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        tv_orderedview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allordered=new Intent(MerchantProducts.this,OrderedProducts.class);
                startActivity(allordered);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        tv_balanceall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allbalance=new Intent(MerchantProducts.this,BalanceProducts.class);
                startActivity(allbalance);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
    }

}
