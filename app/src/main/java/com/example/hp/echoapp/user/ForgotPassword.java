package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 1/12/2018.
 */

public class ForgotPassword extends Activity
{
    EditText et_frgpassword;
    Button bt_frgotbtn;
    String frgpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regusers.php?function=BuyerforgotPassword", 2, nameValuePairs);
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
                       //Toast.makeText(ForgotPassword.this, "success "+email, Toast.LENGTH_SHORT).show();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ForgotPassword.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage(R.string.frgtEmailSent);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(ForgotPassword.this, LoginScreen.class));
                            dialogInterface.dismiss();
                        }
                    }).show();
                } else if (result.getString("status").toString().equals("failed")) {
                    String email = result.getString("message");

                    // Toast.makeText(ForgotPassword.this, "fail "+email, Toast.LENGTH_SHORT).show();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ForgotPassword.this);
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
                Validations.MyAlertBox(ForgotPassword.this, "Please Enter email");
                et_frgpassword.requestFocus();
            } else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(ForgotPassword.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    Log.d("email", et_frgpassword.getText().toString());
                    new ForgotPassword.forgetPassword(et_frgpassword.getText().toString()).execute();
                } else {
                    Validations.MyAlertBox(ForgotPassword.this, "INTERNET CONNECTION FAILED");
                }
            }
        }

    }

}