package com.example.hp.echoapp.delivery;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HP on 2/16/2018.
 */

public class MerchantProductRating extends Activity {
    Button bt_ratingmerchant;
    SimpleRatingBar ratingBarIDmerchant;
    double rate;
    SharedPreferences sharedPreferences;
    TextView tv_delmerchantproducttitle,tv_delmerchantproductprice,tv_delmerchantquqntity,tv_orderdmerchantname,
            delorderedbuyername,tv_delmerchantquqntitytotat;
    EditText et_descpromerchant;
    ImageView iv_merchantproductdetails_image;
    int merchanttotal;
    String delid, merid,productid,rating,description,orderedprice,orderedproduct_name,orderedmerchant_name,
            orderedbuyer_name,orderedprot_quantity,orderedbuyer_image;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_del_details);
        bt_ratingmerchant=(Button)findViewById(R.id.bt_ratingmerchant);

        sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);


        delid = sharedPreferences.getString("id", "").toString();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        ratingBarIDmerchant=(SimpleRatingBar)findViewById(R.id.ratingBarIDmerchant);
        tv_delmerchantproducttitle=(TextView)findViewById(R.id.tv_delmerchantproducttitle);
        tv_delmerchantproductprice=(TextView)findViewById(R.id.tv_delmerchantproductprice);
        iv_merchantproductdetails_image=(ImageView)findViewById(R.id.iv_merchantproductdetails_image);
        tv_delmerchantquqntity=(TextView)findViewById(R.id.tv_delmerchantquqntity);
        tv_orderdmerchantname=(TextView)findViewById(R.id.tv_orderdmerchantname);
        delorderedbuyername=(TextView)findViewById(R.id.delorderedbuyername);
        tv_delmerchantquqntitytotat=(TextView)findViewById(R.id.tv_delmerchantquqntitytotat);
        et_descpromerchant=(EditText)findViewById(R.id.et_descpromerchant);

        merid=getIntent().getStringExtra("mer_id").toString();
        orderedbuyer_image=getIntent().getStringExtra("pro_image").toString();
        orderedproduct_name=getIntent().getStringExtra("pro_name").toString();
        orderedprice=getIntent().getStringExtra("pro_price").toString();
        orderedprot_quantity=getIntent().getStringExtra("pro_qty").toString();



       /* merchanttotal= Integer.parseInt(orderedprice)* Integer.parseInt(orderedprot_quantity);
        Log.d("**merchanttotal", String.valueOf(merchanttotal));*/
        /*buyerid=getIntent().getStringExtra("orderedbuyer_id").toString();
        productid=getIntent().getStringExtra("orderedproduct_id").toString();
        orderedmerchant_name=getIntent().getStringExtra("orderedmerchant_name").toString();
        orderedbuyer_name=getIntent().getStringExtra("orderedbuyer_name").toString();
        orderedprot_quantity=getIntent().getStringExtra("orderedprot_quantity").toString();
        orderedbuyer_image=getIntent().getStringExtra("orderedImage").toString();
        tv_delmerchantquqntity.setText(orderedprot_quantity);
        tv_orderdmerchantname.setText(orderedmerchant_name);
        delorderedbuyername.setText(orderedbuyer_name);*/

        Picasso.with(this).load(orderedbuyer_image).into(iv_merchantproductdetails_image);
        tv_delmerchantproducttitle.setText(orderedproduct_name);
        tv_delmerchantproductprice.setText(orderedprice);
        tv_delmerchantquqntity.setText(orderedprot_quantity);


        bt_ratingmerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rate=ratingBarIDmerchant.getRating();
                String rating=df2.format(rate);
                Log.d("Rating", String.valueOf(rate));
                description=et_descpromerchant.getText().toString();


                Toast.makeText(MerchantProductRating.this, "Rating = "+rate+" String Rating "+rating, Toast.LENGTH_SHORT).show();
                new MerchantProductRating.merchantRating(delid,merid,rating,description).execute();
            }
        });

    }

    private class merchantRating extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String delid, merchid,productid,rating,description;




        public merchantRating(String delid, String merchid,String rating,String description) {
            this.delid = delid;
            this.merchid = merchid;
            this.productid = productid;
            this.rating = rating;
            this.description=description;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mid", merid));
            nameValuePairs.add(new BasicNameValuePair("did", delid));
            //nameValuePairs.add(new BasicNameValuePair("productid", productid));
            nameValuePairs.add(new BasicNameValuePair("description", description));
            nameValuePairs.add(new BasicNameValuePair("rating", rating));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/ratings.php?function=AddmerchantRatings", 2, nameValuePairs);
            return json;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            //super.onPostExecute(jsonObject);


            try {
                JSONObject result= new JSONObject(jsonObject.toString());
                if(result.getString("status").equals("Success"))
                {

                    /*JSONArray array=new JSONArray(result.getString("data"));
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject data=new JSONObject(array.getJSONObject(i).toString());
                        Log.d("delid", data.getString("delid").toString());
                        Log.d("buyerid",data.getString("buyerid").toString());
                        Log.d("productid", data.getString("productid").toString());
                        Log.d("rating", data.getString("rating").toString());
                        Log.d("phone", data.getString("phone").toString());
                        Log.d("description",data.getString("description").toString());
                        //  Log.d("loginType", data.getString("loginType").toString());

                        String delid, buyerid,productid,rating,description;

                        // _id = data.getString("_id").toString();
                        delid = data.getString("delid").toString();
                        buyerid=data.getString("buyerid").toString();
                        productid = data.getString("productid").toString();
                        rating = data.getString("rating").toString();
                        description = data.getString("description").toString();


                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("delid", delid);
                        editor.putString("buyerid",buyerid);
                        editor.putString("productid", productid);
                        editor.putString("rating", rating);
                        editor.putString("description", description);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserOrderedDeliveryRating.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.userlogin);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(UserOrderedDeliveryRating.this,UserProductGridview.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            }
                        }).show();
                    }*/
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MerchantProductRating.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage(R.string.merchantRating);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent in=new Intent(MerchantProductRating.this,DelivercenterHome.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                        }
                    }).show();
                }
                else
                {
                    Validations.MyAlertBox(MerchantProductRating.this,"Please Enter Valid Credentials");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
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
