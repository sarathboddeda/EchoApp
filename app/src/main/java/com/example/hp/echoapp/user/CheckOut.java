package com.example.hp.echoapp.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hp.echoapp.R;

/**
 * Created by HP on 10/20/2017.
 */

public class CheckOut extends Activity {
    Button bt_deliver,bt_proceed;
    ImageView iv_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_signup);
        bt_deliver=(Button)findViewById(R.id.bt_deliver);
        //bt_proceed=(Button)findViewById(R.id.bt_proceed);
        iv_profile=(ImageView)findViewById(R.id.iv_profile);
        /*iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile=new Intent(CheckOut.this,MyProfile.class);
                startActivity(profile);
            }
        });*/
        bt_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bt_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
