package com.example.hp.echoapp.user;

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
 * Created by HP on 2/8/2018.
 */

public class TypesOfProducts extends Activity {
    TextView tv_allalpros,tv_catprosall,tv_merallpros,tv_buyertypename,tv_buyertypeemail,tv_buyertypephone;
    SlidingMenu slidingMenu;
    ImageView iv_menu,iv_profile;
    SharedPreferences sharedPreferences;
    String buyername,buyerid,buyeremail,buyeyphone,id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.types_products_buyers);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        tv_buyertypename=(TextView)findViewById(R.id.tv_buyertypename);
        tv_buyertypeemail=(TextView)findViewById(R.id.tv_buyertypeemail);
        tv_buyertypephone=(TextView)findViewById(R.id.tv_buyertypephone);


        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        //Log.d("id",user);
        id = sharedPreferences.getString("id", "").toString();
        buyername=sharedPreferences.getString("firstname","").toString();
        buyeremail=sharedPreferences.getString("email","").toString();
        buyeyphone=sharedPreferences.getString("phone","").toString();


        Log.d("**buyerid",buyerid);
        Log.d("**buyer_cntno",buyername);
        Log.d("**buyeremail",buyeremail);
        Log.d("**buyeyphone",buyeyphone);

        tv_buyertypename.setText(buyername);
        tv_buyertypeemail.setText(buyeremail);
        tv_buyertypephone.setText(buyeyphone);


        Common.SlideMenuDesign(slidingMenu, TypesOfProducts.this, "typesofproducts");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        tv_allalpros=(TextView)findViewById(R.id.tv_allalpros);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit=new Intent(TypesOfProducts.this,EditProfile.class);
                startActivity(edit);
            }
        });
        tv_merallpros=(TextView)findViewById(R.id.tv_merallpros);
        tv_catprosall=(TextView)findViewById(R.id.tv_catprosall);
        tv_allalpros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allproducts=new Intent(TypesOfProducts.this,UserProductGridview.class);
                allproducts.putExtra("buyerid",buyerid);
                startActivity(allproducts);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        tv_merallpros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allordered=new Intent(TypesOfProducts.this,GetMerchantProducts.class);
                startActivity(allordered);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        tv_catprosall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allbalance=new Intent(TypesOfProducts.this,CategoryProducts.class);
                startActivity(allbalance);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
    }
}
