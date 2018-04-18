package com.example.hp.echoapp.delivery;

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
import com.example.hp.echoapp.merchant.TotalProducts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by HP on 2/12/2018.
 */

public class DelivercenterHome extends Activity {
    SlidingMenu slidingMenu;
    ImageView iv_menu,iv_profile,iv_deliv_editprofile;
    TextView tv_deliv_name,tv_deliv_email,tv_deliv_phone,tv_all_del_merch,tv_order_buyerview;
    SharedPreferences sharedPreferences;
    String deliveryid,deliveryname,deliveryemail,deliveryphone;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverycenter_home);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);

        DeliveryCommon.SlideMenuDesign(slidingMenu, DelivercenterHome.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });

        tv_deliv_name=(TextView)findViewById(R.id.tv_deliv_name);
        tv_deliv_email=(TextView)findViewById(R.id.tv_deliv_email);
        tv_deliv_phone=(TextView)findViewById(R.id.tv_deliv_phone);


        sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);
        deliveryid = sharedPreferences.getString("id", "").toString();
        deliveryname = sharedPreferences.getString("name", "").toString();
        deliveryphone=sharedPreferences.getString("phone","").toString();
        deliveryemail=sharedPreferences.getString("email","").toString();
        //merchantemail=getIntent().getStringExtra("memail").toString();
        Log.d("**deliveryid",deliveryid);

        tv_deliv_name.setText(deliveryname);
        tv_deliv_email.setText(deliveryemail);
        tv_deliv_phone.setText(deliveryphone);
        iv_deliv_editprofile=(ImageView)findViewById(R.id.iv_deliv_editprofile);
        iv_deliv_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updatedel=new Intent(DelivercenterHome.this,UpdateDeliveryCenter.class);
                startActivity(updatedel);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);

            }
        });


        DeliveryCommon.SlideMenuDesign(slidingMenu, DelivercenterHome.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        tv_all_del_merch=(TextView)findViewById(R.id.tv_all_del_merch);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit=new Intent(DelivercenterHome.this,UpdateDeliveryCenter.class);
                startActivity(edit);
            }
        });
        tv_order_buyerview=(TextView)findViewById(R.id.tv_order_buyerview);

        tv_all_del_merch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent merordrs=new Intent(DelivercenterHome.this,DeliveryCenterMerchantProducts.class);
                merordrs.putExtra("deliveryid",deliveryid);
                startActivity(merordrs);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        tv_order_buyerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allordered=new Intent(DelivercenterHome.this,DeliveryCenterBuyerOrders.class);
                startActivity(allordered);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });

    }
}
