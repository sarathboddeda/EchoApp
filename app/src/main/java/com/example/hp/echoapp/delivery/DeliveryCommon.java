package com.example.hp.echoapp.delivery;

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
import com.example.hp.echoapp.merchant.MerchantProducts;
import com.example.hp.echoapp.merchant.TotalProducts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by HP on 12/20/2017.
 */

public class DeliveryCommon {
    private static ListView mDrawerList;
    private static ArrayList<LeftMenuConst> leftMenuConst;
    private static String[] navMenuList;
    private static LeftMenuAdapter leftMenuAdapter;
    private static TypedArray navMenuIcons;

    SharedPreferences sharedPreferences;
    String _id,fullname,delemail,password,address,mobile,state,city,country,zipcode;

    public static void SlideMenuDesign(final SlidingMenu slidingMenu, final Activity activity, final String clickMenu) {

        //menu list items
        navMenuList = slidingMenu.getResources().getStringArray(R.array.delivery_list);
        mDrawerList = (ListView) slidingMenu.findViewById(R.id.lv_leftmenu);

        leftMenuConst=new ArrayList<LeftMenuConst>();
        leftMenuConst.add(new LeftMenuConst(navMenuList[0]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[1]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[2]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[3]));
        leftMenuAdapter=new LeftMenuAdapter(activity,leftMenuConst);
        mDrawerList.setAdapter(leftMenuAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:

                        slidingMenu.toggle();
                        if(!clickMenu.equals("MerchantProducts")){
                            Intent profile = new Intent(activity, DelivercenterHome.class);
                            activity.startActivity(profile);
                            activity.finish();

                        }
                        break;
                    case 1:

                        slidingMenu.toggle();
                        if(!clickMenu.equals("MerchantProducts")){
                            Intent profile = new Intent(activity, DeliveryCenterMerchantProducts.class);
                            activity.startActivity(profile);
                            activity.finish();

                        }
                        break;
                    case 2:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("buyerOrders")) {
                            Intent video = new Intent(activity, DeliveryCenterBuyerOrders.class);
                            activity.startActivity(video);
                            activity.finish();
                        }

                        break;
                    case 3:

                        slidingMenu.toggle();
                        if(!clickMenu.equals("logout")){
                            SharedPreferences sharedPreferences = activity.getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", "");
                            editor.putString("name", "");
                            editor.putString("email", "");
                            editor.putString("password", "");
                            editor.putString("phone", "");
                            editor.putString("address", "");
                            editor.putString("state", "");
                            editor.putString("city", "");
                            editor.putString("country", "");
                            editor.putString("zipcode", "");
                            editor.putString("roletype", "");
                            editor.commit();

                            Intent profile = new Intent(activity, TypeOfSignup.class);

                            activity.startActivity(profile);
                            activity.finish();

                        }
                        break;
                    /*case 1:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("myorder")) {
                            Intent video = new Intent(activity, MyorderActivity.class);
                            activity.startActivity(video);
                            activity.finish();
                        }

                        break;
                    case 2:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("mycart"))
                        {
                            Intent mycart=new Intent(activity,MyCart.class);
                            activity.startActivity(mycart);
                        }
                        break;
                    case 3:
                        slidingMenu.toggle();

                        if(!clickMenu.equals("logout"))
                        {
                            *//*SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id","");
                            editor.putString("firstname", "");
                            editor.putString("lastname","");
                            editor.putString("password", "");
                            editor.putString("email", "");
                            editor.putString("mobile","");
                            editor.commit();*//*

                            Intent logout = new Intent(activity, TypeOfSignup.class);
                            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(logout);
                        }
*/



                }
            }
        });

    }

}
