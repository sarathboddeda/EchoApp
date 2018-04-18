package com.example.hp.echoapp.merchant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hp.echoapp.LeftMenuAdapter;
import com.example.hp.echoapp.LeftMenuConst;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.TypeOfSignup;
import com.example.hp.echoapp.merchant.BalanceProducts;
import com.example.hp.echoapp.merchant.MerchantProducts;
import com.example.hp.echoapp.merchant.OrderedProducts;
import com.example.hp.echoapp.merchant.TotalProducts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by HP on 11/16/2017.
 */

public class merchantCommon {
    private static ListView mDrawerList;
    private static ArrayList<LeftMenuConst> leftMenuConst;
    private static String[] navMenuList;
    private static LeftMenuAdapter leftMenuAdapter;
    private static TypedArray navMenuIcons;
    SharedPreferences sharedPreferences;

    public static void SlideMenuDesign(final SlidingMenu slidingMenu, final Activity activity, final String clickMenu) {

        //menu list items
        navMenuList = slidingMenu.getResources().getStringArray(R.array.merchant_list);
        mDrawerList = (ListView) slidingMenu.findViewById(R.id.lv_leftmenu);

        leftMenuConst=new ArrayList<LeftMenuConst>();
        leftMenuConst.add(new LeftMenuConst(navMenuList[0]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[1]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[2]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[3]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[4]));
        leftMenuAdapter=new LeftMenuAdapter(activity,leftMenuConst);
        mDrawerList.setAdapter(leftMenuAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:

                        slidingMenu.toggle();
                        if(!clickMenu.equals("merchantproducts")){
                            Intent profile = new Intent(activity, MerchantProducts.class);
                            activity.startActivity(profile);
                            activity.finish();

                        }
                        break;
                    case 1:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("availableproducts")) {
                            Intent video = new Intent(activity, TotalProducts.class);
                            activity.startActivity(video);
                            activity.finish();
                        }

                        break;
                    case 2:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("orderedproducts"))
                        {
                            Intent mycart=new Intent(activity,OrderedProducts.class);
                            activity.startActivity(mycart);
                        }

                        break;
                    case 3:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("balanceproducts"))
                        {
                            Intent bal=new Intent(activity,BalanceProducts.class);
                            activity.startActivity(bal);
                        }
                        break;
                    case 4:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("logout"))
                        {
                            SharedPreferences sharedPreferences = activity.getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("company_name", "");
                            editor.putString("password","");
                            editor.putString("email", "");
                            editor.putString("address", "");
                            editor.putString("contact_no","");
                            editor.putString("year_estd","");
                            editor.putString("product_cert","");
                            editor.putString("id", "");
                            editor.putString("roletype", "");
                            editor.commit();
                            editor.clear();
                            Intent logout=new Intent (activity,TypeOfSignup.class);
                            activity.startActivity(logout);
                        }


                }
            }
        });

    }
}
