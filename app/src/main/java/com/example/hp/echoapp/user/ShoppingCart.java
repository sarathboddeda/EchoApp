package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

/**
 * Created by HP on 10/20/2017.
 */

public class ShoppingCart extends Activity {
    Button bt_cart_checkout,bt_continue;
    ImageView iv_profile,iv_menu,iv_shpingcartdetails_image;
    SlidingMenu slidingMenu;
    TextView tv_clickproducttitle,tv_subtotal;
    TextView et_prodcartquantity;
    String shopproducttitle,productimagepath,subTotalstring,shopproid,prodel,promername;
    int productPrice,detailsavailable,getorderedquantity;
    double subTotal,shopproductprice;
    ProductDetails productDetails;
     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.shoppingcart);
         slidingMenu = new SlidingMenu(this);
         slidingMenu.setMode(SlidingMenu.LEFT);
         slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
         slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
         slidingMenu.setFadeDegree(0.20f);
         slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
         slidingMenu.setMenu(R.layout.leftmenu);
         SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();
         shopproducttitle= getIntent().getStringExtra("product_name").toString();
         shopproid= getIntent().getStringExtra("proid").toString();
         shopproductprice= getIntent().getDoubleExtra("price",0);
         detailsavailable=getIntent().getIntExtra("quantity",0);
         productimagepath=getIntent().getStringExtra("image").toString();
         prodel=getIntent().getStringExtra("prodel").toString();
         promername=getIntent().getStringExtra("merchantName").toString();
         getorderedquantity=getIntent().getIntExtra("ordered",0);
         Log.d("**image",productimagepath);
         Log.d("**product_name",shopproducttitle);
         Log.d("**proid",shopproid);
         Log.d("**price", String.valueOf(shopproductprice));
         Log.d("**getorderedquantity", String.valueOf(getorderedquantity));
         Log.d("**total_stock", String.valueOf(detailsavailable));
         Log.d("**prodel", prodel);
         Log.d("**promername", promername);

         productDetails=new ProductDetails();

        /* editor.putString("orderedprice",shopproductprice);
         editor.putString("orderedquantity",getorderedquantity);*/



         Common.SlideMenuDesign(slidingMenu, ShoppingCart.this, "shoppingcart");
         iv_menu = (ImageView) findViewById(R.id.iv_menu);
         iv_menu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 slidingMenu.toggle();
             }
         });
         iv_shpingcartdetails_image=(ImageView)findViewById(R.id.iv_shpingcartdetails_image);
         tv_clickproducttitle=(TextView)findViewById(R.id.tv_clickproducttitle);
         et_prodcartquantity=(TextView) findViewById(R.id.et_prodcartquantity);
         tv_subtotal=(TextView)findViewById(R.id.tv_subtotal);
         et_prodcartquantity.setText(String.valueOf(getorderedquantity));
         tv_clickproducttitle.setText(shopproducttitle);
         subTotal=shopproductprice*getorderedquantity;
         Log.d("**subTotal", String.valueOf(subTotal));
         tv_subtotal.setText(String.valueOf(subTotal));
         Picasso.with(this).load(productimagepath).into(iv_shpingcartdetails_image);

         bt_cart_checkout=(Button)findViewById(R.id.bt_cart_checkout);
         bt_continue=(Button)findViewById(R.id.bt_continue);
         iv_profile=(ImageView)findViewById(R.id.iv_profile);
         iv_profile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent profile=new Intent(ShoppingCart.this,MyProfile.class);
                 startActivity(profile);
                 overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
             }
         });
         bt_cart_checkout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent checkout=new Intent(ShoppingCart.this,DeliveryCenterNew.class);
                 checkout.putExtra("quantit",getorderedquantity);
                 checkout.putExtra("pricepro",subTotal);
                 checkout.putExtra("product_name",shopproducttitle);
                 checkout.putExtra("image",productimagepath);
                 checkout.putExtra("shopproid",shopproid);
                 checkout.putExtra("prodel",prodel);
                 checkout.putExtra("promername",promername);
                 startActivity(checkout);
                 overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
             }
         });
         bt_continue.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent contnue=new Intent(ShoppingCart.this,UserProductGridview.class);
                 startActivity(contnue);
                 overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
             }
         });
     }

    //****** HIDING KEYBOARD *********//

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null
                && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && v instanceof EditText
                && !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop()
                    || y > v.getBottom())
                Validations.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }
}
