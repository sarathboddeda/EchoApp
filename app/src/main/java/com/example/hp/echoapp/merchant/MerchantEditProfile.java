package com.example.hp.echoapp.merchant;

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

import com.example.hp.echoapp.user.Common;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 11/17/2017.
 */

public class MerchantEditProfile extends Activity {
    EditText et_editmerchantcmpnyname,et_editmerchantcmpnyregis,
            et_editmerchantpersonaladdress,et_editmerchantpersonalcnmt,
            et_editmerchantemail,et_editmerchantpassword,et_editmerchantrepassword
            ,et_editmerchantyearestablished,et_editmerchantproductcertification,et_editmerchantpassport;
    Button bt_editmerchantsignup;
    ImageView iv_menu;
    SlidingMenu slidingMenu;
    String merchnatname,merchantregistration,merchantaddress,merchnatcontact,merchantpasword,merchantnewpassword,
    merchantyearesta,merchantcertification,merchantpassport,merchant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_editprofile);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        merchantCommon.SlideMenuDesign(slidingMenu, MerchantEditProfile.this, "merchanteditprofile");
       findViewById();
        SharedPreferences sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
        merchnatname=sharedPreferences.getString("company_name","");
        merchantregistration=sharedPreferences.getString("company_reg","");
        merchantaddress=sharedPreferences.getString("address","");
        Log.d("_-merchantaddress",merchantaddress);
        merchnatcontact=sharedPreferences.getString("contact_no","");
        merchantpasword=sharedPreferences.getString("password","");
        merchantyearesta=sharedPreferences.getString("year_estd","");
        merchantcertification=sharedPreferences.getString("product_cert","");
        merchantpassport=sharedPreferences.getString("passport","");
        merchant_id=sharedPreferences.getString("id","");
        et_editmerchantcmpnyname.setText(merchnatname);
        et_editmerchantcmpnyregis.setText(merchantregistration);
        et_editmerchantpersonaladdress.setText(merchantaddress);
       /* et_editmerchantpassword.setText(merchantpasword);
        et_editmerchantrepassword.setText(merchantpasword);*/
        et_editmerchantyearestablished.setText(merchantyearesta);
        et_editmerchantproductcertification.setText(merchantcertification);
        et_editmerchantpersonalcnmt.setText(merchnatcontact);
        et_editmerchantpassport.setText(merchantpassport);
        bt_editmerchantsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(0);
            }
        });
    }

    private void findViewById()
    {
        et_editmerchantcmpnyname=(EditText)findViewById(R.id.et_editmerchantcmpnyname);
        et_editmerchantcmpnyregis=(EditText)findViewById(R.id.et_editmerchantcmpnyregis);
        et_editmerchantpersonaladdress=(EditText)findViewById(R.id.et_editmerchantpersonaladdress);
        et_editmerchantpersonalcnmt=(EditText)findViewById(R.id.et_editmerchantpersonalcnmt);
        //et_editmerchantemail=(EditText)findViewById(R.id.et_editmerchantemail);
        /*et_editmerchantpassword=(EditText)findViewById(R.id.et_editmerchantpassword);
        et_editmerchantrepassword=(EditText)findViewById(R.id.et_editmerchantrepassword);*/
        et_editmerchantyearestablished=(EditText)findViewById(R.id.et_editmerchantyearestablished);
        et_editmerchantproductcertification=(EditText)findViewById(R.id.et_editmerchantproductcertification);
        et_editmerchantpassport=(EditText)findViewById(R.id.et_editmerchantpassport);

        bt_editmerchantsignup=(Button)findViewById(R.id.bt_editmerchantsignup);
    }

    private class merchantEditProfile extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String merchantaddress,merchantcontact,merchantpassport,merchantcompany,merchantemail,merchant_id,
                merchantcompanyreg, merchantyearestb,merchantprocertificate,status="Inactive";

        public merchantEditProfile(String merchant_id,String merchantaddress,String merchantcontact,String merchantpassport,
                                  String merchantcompany,
                                  String merchantcompanyreg,String merchantyearestb,String merchantprocertificate){

            this.merchant_id = merchant_id;
            this.merchantaddress = merchantaddress;
            this.merchantcontact=merchantcontact;
            this.merchantpassport = merchantpassport;
            this.merchantcompany = merchantcompany;
            this.merchantcompanyreg = merchantcompanyreg;
            this.merchantyearestb=merchantyearestb;
            this.merchantprocertificate=merchantprocertificate;
        }


        @Override
        protected JSONObject doInBackground(Object... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mid", merchant_id));
            nameValuePairs.add(new BasicNameValuePair("company_name", merchantcompany));
            nameValuePairs.add(new BasicNameValuePair("address", merchantaddress));
            nameValuePairs.add(new BasicNameValuePair("phone",merchantcontact));
            nameValuePairs.add(new BasicNameValuePair("c_reg", merchantcompanyreg));
            nameValuePairs.add(new BasicNameValuePair("year",merchantyearestb));
            nameValuePairs.add(new BasicNameValuePair("cert",merchantprocertificate));
            nameValuePairs.add(new BasicNameValuePair("passport", merchantpassport));
            nameValuePairs.add(new BasicNameValuePair("logintype", "merchant"));
            nameValuePairs.add(new BasicNameValuePair("status","1"));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regmerchants.php?function=updateMerchant", 2, nameValuePairs);
            return json;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            // super.onPostExecute(jsonObject);
            try {
                JSONObject result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equals("Success")) {
                    JSONArray array = new JSONArray(result.getString("msg"));

                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject data=new JSONObject(array.getJSONObject(i).toString());
                        //Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_LONG).show();


                        //Log.d("==_id",data.getString("_id").toString());
                        //  Log.d("loginType", data.getString("loginType").toString());

                        String company_name, email, passportno,password, address1,contact_no,company_reg,year_estd,product_cert,merchant_id;
                        // _id = data.getString("_id").toString();
                        company_name = data.getString("company_name").toString();
                        password = data.getString("password").toString();
                        email = data.getString("email").toString();
                        address1=data.getString("address").toString();
                        contact_no=data.getString("contact_no").toString();
                        company_reg=data.getString("company_reg").toString();
                        year_estd=data.getString("year_estd").toString();
                        product_cert=data.getString("product_cert").toString();
                        passportno=data.getString("passport").toString();
                        merchant_id = data.getString("id").toString();

                        Log.d("++id",data.getString("id").toString());
                        Log.d("++company_name", data.getString("company_name").toString());
                        Log.d("++email",data.getString("email").toString());
                        Log.d("++password", data.getString("password").toString());
                        Log.d("++add", data.getString("address").toString());
                        Log.d("++phone", data.getString("contact_no").toString());
                        Log.d("++c_reg",data.getString("company_reg").toString());
                        Log.d("++year_estd",data.getString("year_estd").toString());
                        Log.d("++passport",data.getString("passport").toString());
                        Log.d("++product_cert",data.getString("product_cert").toString());

                        //  loginType = data.getString("loginType").toString();


                        SharedPreferences sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("company_name", company_name);
                        editor.putString("password",password);
                        editor.putString("email", email);
                        editor.putString("address", address1);
                        editor.putString("contact_no",contact_no);
                        editor.putString("company_reg",company_reg);
                        editor.putString("year_estd",year_estd);
                        editor.putString("passport",passportno);
                        editor.putString("product_cert",product_cert);
                        editor.putString("id", merchant_id);
                        //  editor.putString("loginType", loginType);
                        // editor.putString("_id", _id);
                        editor.commit();



                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MerchantEditProfile.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.merchantupdated);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MerchantEditProfile.this, MerchantProducts.class));
                                dialogInterface.dismiss();
                            }
                        }).show();
                        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                    }



                }
                else
                {
                    //  Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                    Validations.MyAlertBox(MerchantEditProfile.this, "Please Enter Valid Credentials");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(MerchantEditProfile.this, "Connection Lost");
            }
        }
    }

    public void validate(int flowcode) {

        String status="Inactive";
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_editmerchantpersonaladdress.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantEditProfile.this, "Please Enter Merchant Personal Address");
                et_editmerchantpersonaladdress.requestFocus();
            }else if(et_editmerchantpersonalcnmt.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantEditProfile.this, "Please Enter Merchant Personal Contact");
                et_editmerchantpersonalcnmt.requestFocus();
            }
            else if (et_editmerchantpassport.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantEditProfile.this, "Please Enter Passport Number");
                et_editmerchantpassport.requestFocus();
            }
            else if(et_editmerchantcmpnyname.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantEditProfile.this,"Please enter Company Name");
            }

           /* else if (et_editmerchantpassword.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantEditProfile.this, "Please Enter Password");
                et_editmerchantpassword.requestFocus();
            } else if (et_editmerchantrepassword.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantEditProfile.this, "Please Enter ReType Password");
                et_editmerchantrepassword.requestFocus();
            }*/ else if(et_editmerchantcmpnyregis.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantEditProfile.this,"Please Enter Company registration number");
            }
            else if(et_editmerchantyearestablished.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantEditProfile.this,"Please Enter year of established");
            }
            else if(et_editmerchantproductcertification.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantEditProfile.this,"Please Enter Product Certification");
            }
            else {
                if (Validations.isConnectedToInternet(MerchantEditProfile.this)) {
                    new MerchantEditProfile.merchantEditProfile(merchant_id,et_editmerchantpersonaladdress.getText().toString(),
                            et_editmerchantpersonalcnmt.getText().toString(),et_editmerchantpassport.getText().toString(),
                            et_editmerchantcmpnyname.getText().toString(),
                            et_editmerchantcmpnyregis.getText().toString(),
                            et_editmerchantyearestablished.getText().toString(),
                            et_editmerchantproductcertification.getText().toString()).execute();

                    /*String merchant_id,String merchantaddress,String merchantcontact,String merchantpassport,
                            String merchantcompany,
                            String merchantcompanyreg,String merchantyearestb,String merchantprocertificate*/
                } else {
                    Validations.MyAlertBox(MerchantEditProfile.this, "INTERNET CONNECTION FAILED");
                }
            }
        }

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
