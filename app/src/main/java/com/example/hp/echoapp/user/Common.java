package com.example.hp.echoapp.user;

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
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by HP on 10/23/2017.
 */

public class Common {
    private static ListView mDrawerList;
    private static ArrayList<LeftMenuConst> leftMenuConst;
    private static String[] navMenuList;
    private static LeftMenuAdapter leftMenuAdapter;
    private static TypedArray navMenuIcons;

    SharedPreferences sharedPreferences;

    public static void SlideMenuDesign(final SlidingMenu slidingMenu, final Activity activity, final String clickMenu) {

        //menu list items
        navMenuList = slidingMenu.getResources().getStringArray(R.array.menu_list);
        mDrawerList = (ListView) slidingMenu.findViewById(R.id.lv_leftmenu);

        leftMenuConst=new ArrayList<LeftMenuConst>();
        leftMenuConst.add(new LeftMenuConst(navMenuList[0]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[1]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[2]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[3]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[4]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[5]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[6]));
        leftMenuConst.add(new LeftMenuConst(navMenuList[7]));

        leftMenuAdapter=new LeftMenuAdapter(activity,leftMenuConst);
        mDrawerList.setAdapter(leftMenuAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("userproductactivity")){
                            Intent profile = new Intent(activity, UserProductGridview.class);
                            activity.startActivity(profile);
                            activity.finish();
                        }
                        break;
                    case 1:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("merchants")) {
                            Intent allmerchant = new Intent(activity, GetAllMerchants.class);
                            activity.startActivity(allmerchant);
                            activity.finish();
                        }

                        break;
                    case 2:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("categories")) {
                            Intent allmerchant = new Intent(activity, GetAllCategories.class);
                            activity.startActivity(allmerchant);
                            activity.finish();
                        }

                        break;
                    case 3:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("myorder")) {
                            Intent video = new Intent(activity, MyorderActivity.class);
                            activity.startActivity(video);
                            activity.finish();
                        }

                        break;
                    case 4:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("mycart"))
                        {
                            Intent mycart=new Intent(activity,MyCart.class);
                            activity.startActivity(mycart);
                        }
                        break;
                    case 5:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("newsfeeds"))
                        {
                            Intent news=new Intent(activity,NewsFeedActivity.class);
                            activity.startActivity(news);
                        }
                        break;
                    case 6:
                        slidingMenu.toggle();
                        if(!clickMenu.equals("faq"))
                        {
                            Intent faq=new Intent(activity,FaqActivity.class);
                            activity.startActivity(faq);
                        }
                        break;
                    case 7:
                        slidingMenu.toggle();

                        if(!clickMenu.equals("logout"))
                        {
                            SharedPreferences sharedPreferences = activity.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("firstname", "");
                            editor.putString("lastname","");
                            editor.putString("password", "");
                            editor.putString("email", "");
                            editor.putString("phone", "");
                            editor.putString("id", "");
                            editor.putString("roletype", "");
                            editor.commit();
                            editor.clear();
                            Intent logout = new Intent(activity, TypeOfSignup.class);
                            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(logout);
                        }




                }
            }
        });

    }

}
