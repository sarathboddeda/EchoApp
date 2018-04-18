package com.example.hp.echoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by HP on 10/20/2017.
 */

public class Header extends Activity {
    ImageView iv_profile;
    String roletype,roletype1;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInsatnceState)
    {
        super.onCreate(savedInsatnceState);
        setContentView(R.layout.headerinner);

        /*iv_profile=(ImageView)findViewById(R.id.iv_profile);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        //Log.d("id",user);

        roletype = sharedPreferences.getString("roletype", "").toString();


        Log.d("roletype buyer",roletype);



        sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);

        roletype1 = sharedPreferences.getString("roletype", "").toString();


        Log.d("roletype1 buyer",roletype1);
        if (roletype == "Buyer")
        {
            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile=new Intent(Header.this,EditProfile.class);
                    startActivity(profile);
                }
            });
        }
        if (roletype1=="Merchant") {
            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(Header.this, MyProfile.class);
                    startActivity(profile);
                }
            });
        }*/
    }
}
