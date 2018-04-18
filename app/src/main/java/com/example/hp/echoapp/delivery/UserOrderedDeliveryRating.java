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
 * Created by HP on 2/14/2018.
 */

public class UserOrderedDeliveryRating extends Activity {

    Button bt_rating;
    SimpleRatingBar ratingBarID;
    double rate;
    SharedPreferences sharedPreferences;
    TextView tv_delorderedproducttitle,tv_delorderdproductprice,tv_delorderdquqntity,tv_orderdmerchantname,
            delorderedbuyername;
    EditText et_descproordered;
    ImageView iv_orderedproductdetails_image;
    String delid, buyerid,productid,rating,description,orderedprice,orderedproduct_name,orderedmerchant_name,
            orderedbuyer_name,orderedprot_quantity,orderedbuyer_image;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ordered_del);
        bt_rating=(Button)findViewById(R.id.bt_rating);

        sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);


        delid = sharedPreferences.getString("id", "").toString();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        ratingBarID=(SimpleRatingBar)findViewById(R.id.ratingBarID);
        tv_delorderedproducttitle=(TextView)findViewById(R.id.tv_delorderedproducttitle);
        tv_delorderdproductprice=(TextView)findViewById(R.id.tv_delorderdproductprice);
        iv_orderedproductdetails_image=(ImageView)findViewById(R.id.iv_orderedproductdetails_image);
        tv_delorderdquqntity=(TextView)findViewById(R.id.tv_delorderdquqntity);
        tv_orderdmerchantname=(TextView)findViewById(R.id.tv_orderdmerchantname);
        delorderedbuyername=(TextView)findViewById(R.id.delorderedbuyername);
        et_descproordered=(EditText)findViewById(R.id.et_descproordered);

        orderedproduct_name=getIntent().getStringExtra("orderedproduct_name").toString();
        buyerid=getIntent().getStringExtra("orderedbuyer_id").toString();
        productid=getIntent().getStringExtra("orderedproduct_id").toString();
        orderedprice=getIntent().getStringExtra("orderedprice").toString();
        orderedmerchant_name=getIntent().getStringExtra("orderedmerchant_name").toString();
        orderedbuyer_name=getIntent().getStringExtra("orderedbuyer_name").toString();
        orderedprot_quantity=getIntent().getStringExtra("orderedprot_quantity").toString();
        orderedbuyer_image=getIntent().getStringExtra("orderedImage").toString();



        tv_delorderedproducttitle.setText(orderedproduct_name);
        tv_delorderdproductprice.setText(orderedprice);
        tv_delorderdquqntity.setText(orderedprot_quantity);
        tv_orderdmerchantname.setText(orderedmerchant_name);
        delorderedbuyername.setText(orderedbuyer_name);

        Picasso.with(this).load(orderedbuyer_image).into(iv_orderedproductdetails_image);


        bt_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rate=ratingBarID.getRating();
                String rating=df2.format(rate);
                Log.d("Rating", String.valueOf(rate));
                description=et_descproordered.getText().toString();


                Toast.makeText(UserOrderedDeliveryRating.this, "Rating = "+rate+" String Rating "+rating, Toast.LENGTH_SHORT).show();
                new UserOrderedDeliveryRating.buyerRating(delid,buyerid,rating,description).execute();
            }
        });

    }

    private class buyerRating extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String delid, buyerid,productid,rating,description;




        public buyerRating(String delid, String buyerid,String rating,String description) {
            this.delid = delid;
            this.buyerid = buyerid;
            this.productid = productid;
            this.rating = rating;
            this.description=description;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("bid", buyerid));
            nameValuePairs.add(new BasicNameValuePair("did", delid));
            //nameValuePairs.add(new BasicNameValuePair("productid", productid));
            nameValuePairs.add(new BasicNameValuePair("description", description));
            nameValuePairs.add(new BasicNameValuePair("rating", rating));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/ratings.php?function=AddbuyerRatings", 2, nameValuePairs);
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
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserOrderedDeliveryRating.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage(R.string.buyerrating);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent in=new Intent(UserOrderedDeliveryRating.this,DelivercenterHome.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                        }
                    }).show();
                }
                else
                {
                    Validations.MyAlertBox(UserOrderedDeliveryRating.this,"Please Enter Valid Credentials");
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
