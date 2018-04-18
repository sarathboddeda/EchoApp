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
import android.widget.TextView;

import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 12/15/2017.
 */

public class DeliveryLogin extends Activity {
    Button bt_dellogin,bt_delcreate;
    EditText et_dellogin_user,et_dellogin_pass;
    TextView tv_delforgot;
    public int user=0;
    @Override
    protected   void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_login);
        bt_dellogin = (Button)findViewById(R.id.bt_dellogin);
        bt_delcreate=(Button)findViewById(R.id.bt_delcreate);
        et_dellogin_user=(EditText)findViewById(R.id.et_dellogin_user);
        et_dellogin_pass=(EditText)findViewById(R.id.et_dellogin_pass);
        tv_delforgot=(TextView)findViewById(R.id.tv_delforgot);
        bt_dellogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

           /* Intent login=new Intent(LoginScreen.this,UserProductGridview.class);
            startActivity(login);*/
                validate(0);



            }


        });
        bt_delcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create=new Intent(DeliveryLogin.this,DeliverySignup.class);
                startActivity(create);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
        tv_delforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create=new Intent(DeliveryLogin.this,DeliveryForgotPassword.class);
                startActivity(create);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });

    }
    private class UserLogin extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String email, password;

        public UserLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/deliverycenters.php?function=login", 2, nameValuePairs);
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
                        Log.d("_id", data.getString("id").toString());
                        //Log.d("==name", data.getString("name").toString());
                        Log.d("==email", data.getString("email").toString());
                        Log.d("==address", data.getString("address").toString());
                        Log.d("==password",data.getString("password").toString());
                        Log.d("==city", data.getString("city").toString());
                        Log.d("==state",data.getString("state").toString());
                        Log.d("==country",data.getString("country").toString());
                        Log.d("==zipcode",data.getString("zipcode").toString());
                        Log.d("==phone",data.getString("phone").toString());
                        Log.d("==roletype",data.getString("roletype").toString());

                        String _id,fullname,delemail,password,address,mobile,state,city,country,zipcode,roletype,status="Inactive";

                        _id = data.getString("id").toString();
                        fullname = data.getString("name").toString();
                        delemail = data.getString("email").toString();
                        password = data.getString("password").toString();
                        address = data.getString("address").toString();
                        mobile = data.getString("phone").toString();
                        state = data.getString("state").toString();
                        city = data.getString("city").toString();
                        country = data.getString("country").toString();
                        zipcode = data.getString("zipcode").toString();
                        roletype = data.getString("roletype").toString();
                        status = data.getString("status").toString();

                        SharedPreferences sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", _id);
                        editor.putString("name", fullname);
                        editor.putString("email", delemail);
                        editor.putString("password", password);
                        editor.putString("phone", mobile);
                        editor.putString("address", address);
                        editor.putString("state", state);
                        editor.putString("city", city);
                        editor.putString("country", country);
                        editor.putString("zipcode", zipcode);
                        editor.putString("roletype", roletype);
                        editor.putString("status", status);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DeliveryLogin.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.delLogin);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(DeliveryLogin.this,DelivercenterHome.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            }
                        }).show();
                    }
                }
                else
                {
                    Validations.MyAlertBox(DeliveryLogin.this,"Please Enter Valid Credentials");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }
    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_dellogin_user.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliveryLogin.this, "Please Enter email");
                et_dellogin_user.requestFocus();
            } else if (et_dellogin_pass.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliveryLogin.this, "Please Enter Password");
                et_dellogin_pass.requestFocus();
            } else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(DeliveryLogin.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    new DeliveryLogin.UserLogin(et_dellogin_user.getText().toString(), et_dellogin_pass.getText().toString()).execute();
                } else {
                    Validations.MyAlertBox(DeliveryLogin.this, "INTERNET CONNECTION FAILED");
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
