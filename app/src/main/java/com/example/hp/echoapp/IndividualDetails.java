package com.example.hp.echoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hp.echoapp.adapter.IndividualAdapter;
import com.example.hp.echoapp.merchant.merchantCommon;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by HP on 11/17/2017.
 */

public class IndividualDetails extends Activity {
    SlidingMenu slidingMenu;

    public static final String[] individualTitle={"Andrew", "David","Chrisis","Vinter"};
    public static final String[] individualDate={"2017-11-10 12:34:56", "2017-11-10 12:34:56","2017-11-10 12:34:56","2017-11-10 12:34:56"};
    public static final String[] individualQty={"300", "400","200","600"};


    ListView individualproLV;
    ArrayList<IndividualConstructor> individualConstructors;
    ImageView iv_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_details);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);

        merchantCommon.SlideMenuDesign(slidingMenu, IndividualDetails.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        individualConstructors = new ArrayList<IndividualConstructor>();
        for(int i=0;i<individualQty.length;i++)
        {

            IndividualConstructor productItem = new IndividualConstructor(individualTitle[i],individualDate[i],individualQty[i]);
            individualConstructors.add(productItem);
            individualproLV=(ListView)findViewById(R.id.lv_indivordered);
            final IndividualAdapter adapter=new IndividualAdapter(IndividualDetails.this,individualConstructors);
            individualproLV.setAdapter(adapter);
        }
    }
}
