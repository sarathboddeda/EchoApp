package com.example.hp.echoapp.delivery;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by HP on 12/18/2017.
 */

public class UpdateDeliveryCenter extends Activity {
    SlidingMenu slidingMenu;
    Button bt_updatedeliver;
    TextView tv_login;
    ImageView iv_menu;
    public static final String BASE_URL="";
    SharedPreferences sharedPreferences;
    String logintype="Delivery";
    EditText et_updatefullname,et_updateaddress,et_updatetown,et_updatestate,et_updatezipcode,et_updatemobile,et_updatedelemailname,
            et_updatedelpassword,et_updatedelcnfrmpassword,
            et_updatecountry;
    String delverid,updatefullname,updateaddress,updatetown,updatestate,updatezipcode,updatemobile,updatedelemailname,
            updatedelpassword,updatedelcnfrmpassword,
            updatecountry;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_deliverycenter);
        bt_updatedeliver=(Button)findViewById(R.id.bt_updatedeliver);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        DeliveryCommon.SlideMenuDesign(slidingMenu, UpdateDeliveryCenter.this, "shoppingcart");
        //tv_login=(TextView)findViewById(R.id.tv_login);
        iv_menu=(ImageView)findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        DeliveryCommon.SlideMenuDesign(slidingMenu, UpdateDeliveryCenter.this, "deliveryeditprofile");
        et_updatefullname=(EditText)findViewById(R.id.et_updatefullname);
        et_updateaddress=(EditText)findViewById(R.id.et_updateaddress);
        et_updatecountry=(EditText)findViewById(R.id.et_updatecountry);
        //et_updatedelemailname=(EditText)findViewById(R.id.et_updatedelemailname);
        //et_updatedelpassword=(EditText)findViewById(R.id.et_updatedelpassword);
        et_updatestate=(EditText)findViewById(R.id.et_updatestate);
        et_updatetown=(EditText)findViewById(R.id.et_updatetown);
        //et_updatedelcnfrmpassword=(EditText)findViewById(R.id.et_updatedelcnfrmpassword);
        et_updatezipcode=(EditText)findViewById(R.id.et_updatezipcode);
        //et_updatemobile=(EditText)findViewById(R.id.et_updatemobile);




        sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);
        delverid = sharedPreferences.getString("id", "").toString();
        updatefullname = sharedPreferences.getString("name", "").toString();
        updateaddress = sharedPreferences.getString("address", "").toString();
        updatetown = sharedPreferences.getString("city", "").toString();
        updatestate = sharedPreferences.getString("state", "").toString();
        updatezipcode = sharedPreferences.getString("zipcode", "").toString();
        updatemobile = sharedPreferences.getString("phone", "").toString();
        updatedelemailname = sharedPreferences.getString("email", "").toString();
        updatedelpassword = sharedPreferences.getString("password", "").toString();
        updatecountry = sharedPreferences.getString("country", "").toString();


        et_updatefullname.setText(updatefullname);
        et_updateaddress.setText(updateaddress);
        et_updatecountry.setText(updatecountry);
        et_updatestate.setText(updatestate);
        et_updatezipcode.setText(updatezipcode);
        et_updatetown.setText(updatetown);
        //et_updatemobile.setText(updatemobile);

        bt_updatedeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(0);
            }
        });

    }

    /*  *******  This service is for update deliverycenter Signup******

        http://162.251.83.200/~wwwm3phpteam/echoapp/services2/deliverycenters.php?function=UpdateDeliverycenter
   */
    private class deliveryCenterUpdate extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String _id,fullname,delemail,password, address,state,zipcode,country,city, mobile,status="Active";

        public deliveryCenterUpdate(String fullname, String address, String state,
                          String city, String zipcode, String country, String status) {
            this.fullname = fullname;
            this.delemail = delemail;
            this.address = address;
            this.state = state;
            this.city=city;
            this.zipcode = zipcode;
            this.country = country;
            this.mobile = mobile;
            this.status = status;
        }

        @Override
        protected JSONObject doInBackground(Object... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("did", delverid));
            nameValuePairs.add(new BasicNameValuePair("name", fullname));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            nameValuePairs.add(new BasicNameValuePair("country",country));
            nameValuePairs.add(new BasicNameValuePair("zipcode",zipcode));
            //nameValuePairs.add(new BasicNameValuePair("logintype",logintype));
            nameValuePairs.add(new BasicNameValuePair("status",status));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/deliverycenters.php?function=UpdateDeliverycenter", 2, nameValuePairs);
            Log.d("--jasan", String.valueOf(json));
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
                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++)
                    {

                        JSONObject data=new JSONObject(array.getJSONObject(i).toString());
                        //Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_LONG).show();
                        Log.d("-_+data",data.toString());
                        String fullname,delemail,password,address,mobile,state,city,country,zipcode;

                       Log.d("+=id", data.getString("id").toString());
                        Log.d("+=name", data.getString("name").toString());
                        Log.d("+=password", data.getString("password").toString());
                        Log.d("+=phone", data.getString("phone").toString());
                        Log.d("+=address",data.getString("address").toString());
                        Log.d("+=state", data.getString("state").toString());
                        Log.d("+=city",data.getString("city").toString());
                        Log.d("+=country", data.getString("country").toString());
                        Log.d("+=zipcode", data.getString("zipcode").toString());





                        SharedPreferences sharedPreferences = getSharedPreferences("DeliveryLoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", data.getString("id").toString());
                        editor.putString("name", data.getString("name").toString());
                        editor.putString("password", data.getString("password").toString());
                        editor.putString("phone", data.getString("phone").toString());
                        editor.putString("address",data.getString("address").toString());
                        editor.putString("state", data.getString("state").toString());
                        editor.putString("city",data.getString("city").toString());
                        editor.putString("country", data.getString("country").toString());
                        editor.putString("zipcode", data.getString("zipcode").toString());
                        editor.putString("status", status);
                        editor.commit();


                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateDeliveryCenter.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.deliveryupdate);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(UpdateDeliveryCenter.this, DelivercenterHome.class));
                                dialogInterface.dismiss();
                            }
                        }).show();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(UpdateDeliveryCenter.this, "Connection Lost");
            }
        }
    }
    public void onClick(View v) {
    }


    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        String status="1";
        if (flowcode == 0) {
            if (et_updatefullname.getText().toString().equals("")) {
                Validations.MyAlertBox(UpdateDeliveryCenter.this, "Please Enter First name");
                et_updatefullname.requestFocus();
            }

            else if(et_updateaddress.getText().toString().equals(""))
            {
                Validations.MyAlertBox(UpdateDeliveryCenter.this, "Please Enter Last Name");
                et_updateaddress.requestFocus();
            }
            else if (et_updatetown.getText().toString().equals("")) {
                Validations.MyAlertBox(UpdateDeliveryCenter.this, "Please Enter Phone Number");
                et_updatetown.requestFocus();
            }
            else if(et_updatecountry.getText().toString().equals(""))
            {
                Validations.MyAlertBox(UpdateDeliveryCenter.this,"Please Enter your Country");
                et_updatecountry.requestFocus();
            }
            else if(et_updatestate.getText().toString().equals(""))
            {
                Validations.MyAlertBox(UpdateDeliveryCenter.this,"Please Enter the update the state");
                et_updatestate.requestFocus();
            }
            else if (et_updatezipcode.getText().toString().equals("")) {
                Validations.MyAlertBox(UpdateDeliveryCenter.this, "Please update the Zipcode");
                et_updatezipcode.requestFocus();
            }
            else
            {
                if (Validations.isConnectedToInternet(UpdateDeliveryCenter.this))
                {

                    new UpdateDeliveryCenter.deliveryCenterUpdate(et_updatefullname.getText().toString(),
                            et_updateaddress.getText().toString(),et_updatetown.getText().toString(),et_updatestate.getText().toString(),
                            et_updatezipcode.getText().toString(),et_updatecountry.getText().toString()
                            ,status
                    ).execute();

                   /* String fullname, String delemail, String password, String address, String state,
                        String town, String zipcode, String country, String mobile, String status*/
                }
                else
                {
                    Validations.MyAlertBox(UpdateDeliveryCenter.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
        else
        {
            Validations.MyAlertBox(UpdateDeliveryCenter.this,"Email already exits");

        }
    }
}
