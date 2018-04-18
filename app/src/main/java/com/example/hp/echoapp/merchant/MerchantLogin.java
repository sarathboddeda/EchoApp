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
import android.widget.TextView;

import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 11/15/2017.
 */

public class MerchantLogin extends Activity {
    Button bt_merchantlogin,bt_merchantcreate;
    EditText et_login_merchant,et_login_merchantpass;
    TextView tv_merchantforgot;
    private String tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_login);
        et_login_merchant=(EditText)findViewById(R.id.et_login_merchant);
        et_login_merchantpass=(EditText)findViewById(R.id.et_login_merchantpass);
        tv_merchantforgot=(TextView)findViewById(R.id.tv_merchantforgot);
        bt_merchantlogin=(Button)findViewById(R.id.bt_merchantlogin);
        bt_merchantcreate=(Button)findViewById(R.id.bt_merchantcreate);
        // Get token
        tokenId = FirebaseInstanceId.getInstance().getToken();
        //Log.d("+++token",tokenId);

        bt_merchantlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*Intent merchantlogin=new Intent(MerchantLogin.this,MerchantProducts.class);
                startActivity(merchantlogin);*/
                validate(0);
            }
        });

        bt_merchantcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent merchantsignup=new Intent(MerchantLogin.this,MerchantSignup.class);
                startActivity(merchantsignup);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
        tv_merchantforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent merchantsignup=new Intent(MerchantLogin.this,MerchantForgotPassword.class);
                startActivity(merchantsignup);
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            }
        });
    }


    private class merchantLogin extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String email, password,tokenId;

        public merchantLogin(String email, String password,String tokenId) {
            this.email = email;
            this.password = password;
            this.tokenId = tokenId;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("token_id", tokenId));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regmerchants.php?function=login", 2, nameValuePairs);
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
                    JSONArray array=new JSONArray(result.getString("msg"));
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject data=new JSONObject(array.getJSONObject(i).toString());
                        Log.d("==id",data.getString("id").toString());
                        Log.d("==company_name", data.getString("company_name").toString());
                        Log.d("==email",data.getString("email").toString());
                        Log.d("==password", data.getString("password").toString());
                        Log.d("==address", data.getString("address").toString());
                        Log.d("==passporno", data.getString("passport").toString());
                        Log.d("==contact_no",data.getString("contact_no").toString());
                        Log.d("==company_reg",data.getString("company_reg").toString());
                        Log.d("==year_estd",data.getString("year_estd").toString());
                        Log.d("==product_cert",data.getString("product_cert").toString());
                        Log.d("==roletype",data.getString("roletype").toString());


                        //  Log.d("loginType", data.getString("loginType").toString());

                        String company_name, email, password,passporno, address,roletype,contact_no,company_reg,year_estd,product_cert,
                                merchant_id,merchant_passport;
                        //merchant_id = data.getString("_id").toString();
                        company_name = data.getString("company_name").toString();
                        password = data.getString("password").toString();
                        email = data.getString("email").toString();
                        address=data.getString("address").toString();
                        contact_no=data.getString("contact_no").toString();
                        year_estd=data.getString("year_estd").toString();
                        product_cert=data.getString("product_cert").toString();
                        passporno=data.getString("passport").toString();
                        merchant_id = data.getString("id").toString();
                        roletype=data.getString("roletype").toString();
                        company_reg=data.getString("company_reg").toString();
                        merchant_passport=data.getString("passport").toString();
                        //  loginType = data.getString("loginType").toString();
                        Log.d("==id",merchant_id);
                        Log.d("==company_name",company_name);
                        Log.d("==email",email);
                        Log.d("==password",password);
                        Log.d("==address",address);
                        Log.d("==contact_no",contact_no);
                        Log.d("==company_reg",company_reg);
                        Log.d("==year_estd",year_estd);
                        Log.d("==product_cert",product_cert);
                        Log.d("==passport",merchant_passport);


                        SharedPreferences sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("company_name", company_name);
                        editor.putString("password",password);
                        editor.putString("email", email);
                        editor.putString("address", address);
                        editor.putString("contact_no",contact_no);
                        editor.putString("year_estd",year_estd);
                        editor.putString("product_cert",product_cert);
                        editor.putString("id", merchant_id);
                        editor.putString("roletype",roletype);
                        editor.putString("company_reg",company_reg);
                        editor.putString("passport",merchant_passport);
                        //editor.putString("loginType", loginType);
                        //editor.putString("_id", _id);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MerchantLogin.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.merchantlogin);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(MerchantLogin.this,MerchantProducts.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                            }
                        }).show();
                    }
                }
                else
                {
                    Validations.MyAlertBox(MerchantLogin.this,"Please Enter Valid Credentials");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_login_merchant.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantLogin.this, "Please Enter email");
                et_login_merchant.requestFocus();
            } else if (et_login_merchantpass.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantLogin.this, "Please Enter Password");
                et_login_merchantpass.requestFocus();
            } else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(MerchantLogin.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    new MerchantLogin.merchantLogin(et_login_merchant.getText().toString(), et_login_merchantpass.getText().toString(),tokenId).execute();
                } else {
                    Validations.MyAlertBox(MerchantLogin.this, "INTERNET CONNECTION FAILED");
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
