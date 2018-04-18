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
import com.squareup.picasso.Picasso;

/**
 * Created by HP on 10/20/2017.
 */

public class MyProfile extends Activity {
    ImageView iv_editprofile,iv_menu,iv_profileimage;
    TextView tv_allorders,tv_buyertypename,tv_buyertypeemail,tv_buyertypephone;
    SlidingMenu slidingMenu;
    SharedPreferences sharedPreferences;
    String buyername,buyerid,buyeremail,buyeyphone,buyerImge,id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);


        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        //Log.d("id",user);
        buyerid = sharedPreferences.getString("id", "").toString();
        buyername=sharedPreferences.getString("firstname","").toString();
        buyeremail=sharedPreferences.getString("email","").toString();
        buyeyphone=sharedPreferences.getString("phone","").toString();
        buyeyphone=sharedPreferences.getString("phone","").toString();
       // buyerImge=sharedPreferences.getString("proimage","").toString();

        tv_buyertypename=(TextView)findViewById(R.id.tv_profilename);
        tv_buyertypeemail=(TextView)findViewById(R.id.tv_profileemail);
        tv_buyertypephone=(TextView)findViewById(R.id.tv_profilephone);


        Log.d("**buyerid",buyerid);
        Log.d("**buyer_cntno",buyername);
        Log.d("**buyeremail",buyeremail);
        Log.d("**buyeyphone",buyeyphone);

        tv_buyertypename.setText(buyername);
        tv_buyertypeemail.setText(buyeremail);
        tv_buyertypephone.setText(buyeyphone);

        Common.SlideMenuDesign(slidingMenu, MyProfile.this, "myprofile");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        iv_editprofile=(ImageView)findViewById(R.id.iv_editprofile);
        //iv_profileimage=(ImageView)findViewById(R.id.iv_profileimage);
        //Picasso.with(this).load(buyerImge).into(iv_profileimage);
        tv_allorders=(TextView)findViewById(R.id.tv_allorders);
        iv_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editprofile= new Intent(MyProfile.this,EditProfile.class);
                startActivity(editprofile);
            }
        });
        tv_allorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderlist=new Intent(MyProfile.this,MyorderActivity.class);
                startActivity(orderlist);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
    }
}
