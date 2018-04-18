package com.example.hp.echoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hp.echoapp.user.LoginScreen;
import com.example.hp.echoapp.delivery.DeliveryLogin;
import com.example.hp.echoapp.merchant.MerchantLogin;

/**
 * Created by HP on 10/17/2017.
 */

public class TypeOfSignup extends Activity {


    String tokenId;
    Button bt_signupuser,bt_signupmerchant,bt_deliversignupuser;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_type);




        bt_signupuser = (Button) findViewById(R.id.bt_signupuser);
        bt_signupmerchant=(Button) findViewById(R.id.bt_signupmerchant);
        bt_deliversignupuser=(Button)findViewById(R.id.bt_deliversignupuser);

        bt_signupuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userlogin=new Intent(TypeOfSignup.this,LoginScreen.class);
                startActivity(userlogin);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
        bt_signupmerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent merchantlogin=new Intent(TypeOfSignup.this,MerchantLogin.class);
                startActivity(merchantlogin);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
        bt_deliversignupuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent delsignup=new Intent(TypeOfSignup.this,DeliveryLogin.class);
                startActivity(delsignup);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
    }
}
