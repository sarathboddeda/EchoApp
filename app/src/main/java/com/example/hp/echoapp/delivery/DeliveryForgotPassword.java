package com.example.hp.echoapp.delivery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.example.hp.echoapp.merchant.MerchantForgotPassword;
import com.example.hp.echoapp.merchant.MerchantLogin;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 2/23/2018.
 */

public class DeliveryForgotPassword extends Activity {
    EditText et_frgpassword;
    Button bt_frgotbtn;
    String frgpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        et_frgpassword = (EditText) findViewById(R.id.et_frgpassword);
        bt_frgotbtn = (Button) findViewById(R.id.bt_frgotbtn);
        frgpassword = et_frgpassword.getText().toString();
        Log.d("++fort", frgpassword);

        bt_frgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(0);
            }
        });
    }
    private class forgetPassword extends AsyncTask<String, String, JSONObject> {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String email;

        public forgetPassword(String email) {
            this.email = email;

        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", email));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/deliverycenters.php?function=DeliveryforgotPassword", 2, nameValuePairs);
            return json;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //super.onPostExecute(jsonObject);
            try {
                JSONObject result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equals("Success")) {

                    String email = result.getString("data");
                    //Toast.makeText(MerchantForgotPassword.this, "success "+email, Toast.LENGTH_SHORT).show();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DeliveryForgotPassword.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage(R.string.frgtEmailSent);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(DeliveryForgotPassword.this, DeliveryLogin.class));
                            dialogInterface.dismiss();
                        }
                    }).show();
                } else if (result.getString("status").toString().equals("Failed")) {
                    String email = result.getString("message");

                    // Toast.makeText(ForgotPassword.this, "fail "+email, Toast.LENGTH_SHORT).show();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DeliveryForgotPassword.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage(R.string.frgtEmailFail);
                    builder.setPositiveButton(R.string.ok, null).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_frgpassword.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliveryForgotPassword.this, "Please Enter email");
                et_frgpassword.requestFocus();
            } else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(DeliveryForgotPassword.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    Log.d("email", et_frgpassword.getText().toString());
                    new DeliveryForgotPassword.forgetPassword(et_frgpassword.getText().toString()).execute();
                } else {
                    Validations.MyAlertBox(DeliveryForgotPassword.this, "INTERNET CONNECTION FAILED");
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
