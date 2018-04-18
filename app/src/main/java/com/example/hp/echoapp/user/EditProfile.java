package com.example.hp.echoapp.user;

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
 * Created by HP on 10/21/2017.
 */

public class EditProfile extends Activity {
    SlidingMenu slidingMenu;
    ImageView iv_menu;
    EditText et_editfrstname,et_editlastname,et_editemail,et_editphone,et_editpassword,et_editretyepassword,et_editaddress;
    Button bt_buyeredit;
    String buyer_id,buyerfirstname,buyerlastname,buyeremail,buyerphone,buyeraddress,buyerpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        Common.SlideMenuDesign(slidingMenu, EditProfile.this, "productdetails");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        findViewById();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        buyerfirstname=sharedPreferences.getString("firstname","");
        buyer_id=sharedPreferences.getString("id","");
        buyerlastname=sharedPreferences.getString("lastname","");
        buyeremail=sharedPreferences.getString("email","");
        buyerphone=sharedPreferences.getString("phone","");
        buyerpassword=sharedPreferences.getString("password","");
        buyeraddress=sharedPreferences.getString("address","");
        et_editfrstname.setText(buyerfirstname);
        et_editlastname.setText(buyerlastname);
        //et_editphone.setText(buyerphone);
        //et_editemail.setText(buyeremail);
        //et_editpassword.setText(buyerpassword);
        //et_editretyepassword.setText(buyerpassword);
        et_editaddress.setText(buyeraddress);
        Log.d("++buyer_id",buyer_id);
        Log.d("++buyerfrstname",buyerfirstname);
        Log.d("++buyerlastname",buyerlastname);
        //Log.d("++buyeremail",buyeremail);
        //Log.d("++buyerpassword",buyerpassword);
        //Log.d("++buyerphone",buyerphone);
        Log.d("++buyeraddress",buyeraddress);

        bt_buyeredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(0);
            }
        });

    }

    private void findViewById()
    {
        et_editfrstname=(EditText)findViewById(R.id.et_editfrstname);
        et_editlastname=(EditText)findViewById(R.id.et_editlastname);
        //et_editphone=(EditText)findViewById(R.id.et_editphone);
        //et_editemail=(EditText)findViewById(R.id.et_editemail);
       // et_editpassword=(EditText)findViewById(R.id.et_editpassword);
        //et_editretyepassword=(EditText)findViewById(R.id.et_editretyepassword);
        et_editaddress=(EditText)findViewById(R.id.et_editaddress);
        bt_buyeredit=(Button)findViewById(R.id.bt_buyeredit);




    }

    public void validate(int flowcode) {

        String status="Inactive";
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_editfrstname.getText().toString().equals("")) {
                Validations.MyAlertBox(EditProfile.this, "Please Enter your firstName");
                et_editfrstname.requestFocus();
            }else if (et_editlastname.getText().toString().equals("")) {
                Validations.MyAlertBox(EditProfile.this, "Please Enter your last Name");
                et_editlastname.requestFocus();
            }
           /* else if (et_editemail.getText().toString().equals("")) {
                Validations.MyAlertBox(EditProfile.this, "Please Enter Your Email");
                et_editemail.requestFocus();
            }*/
           /* else if(et_editpassword.getText().toString().equals(""))
            {
                Validations.MyAlertBox(EditProfile.this,"Please enter password");
                et_editpassword.requestFocus();
            }*/
           /* else if(et_editretyepassword.getText().toString().equals(""))
            {
                Validations.MyAlertBox(EditProfile.this,"Please enter re type Password");
                et_editretyepassword.requestFocus();
            }
            else if(!et_editretyepassword.getText().toString().equals(et_editpassword.getText().toString()))
            {
                Validations.MyAlertBox(EditProfile.this,"Password doesnt match");
                et_editretyepassword.requestFocus();

            }*/
           /* else if(et_editphone.getText().toString().equals(""))
            {
                Validations.MyAlertBox(EditProfile.this, "Please Enter your Contact number");
                et_editphone.requestFocus();
            }*/
            else if(et_editaddress.getText().toString().equals(""))
            {
                Validations.MyAlertBox(EditProfile.this, "Please Enter your Address");
                et_editaddress.requestFocus();
            }



            else {
                if (Validations.isConnectedToInternet(EditProfile.this)) {
                    new EditProfile.editProfile(buyer_id,et_editfrstname.getText().toString(),
                            et_editlastname.getText().toString(),
                            //et_editemail.getText().toString(),
                           // et_editpassword.getText().toString(),
                            //et_editphone.getText().toString(),
                            et_editaddress.getText().toString()).execute();
                } else {
                    Validations.MyAlertBox(EditProfile.this, "INTERNET CONNECTION FAILED");
                }
            }
        }

    }
    private class editProfile extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String buyerid,buyerfrstname,buyerlastname,buyeremail,buyerpassword,buyerphone,buyeraddress,
                status="Inactive";

        public editProfile(String buyerid,String buyerfrstname,String buyerlastname, String buyeraddress){

            this.buyerid = buyerid;
            this.buyerfrstname=buyerfrstname;
            this.buyerlastname = buyerlastname;
            this.buyeremail = buyeremail;
            this.buyerpassword=buyerpassword;
            this.buyerphone=buyerphone;
            this.buyeraddress = buyeraddress;
        }


        @Override
        protected JSONObject doInBackground(Object... strings) {
            String buyerttitle = buyerfrstname.substring(0,1).toUpperCase() + buyerfrstname.substring(1);

            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("uid", buyerid));
            nameValuePairs.add(new BasicNameValuePair("firstname", buyerttitle));
            nameValuePairs.add(new BasicNameValuePair("lastname",buyerlastname));
            nameValuePairs.add(new BasicNameValuePair("address",buyeraddress));
            nameValuePairs.add(new BasicNameValuePair("status","1"));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regusers.php?function=updateUser", 2, nameValuePairs);
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

                        String buyerfrstname1, buyerlastname1, buyeremail1, buyerpassword1,buyerphone1,buyeraddress1,buyer_id1;
                        buyer_id1 = data.getString("id").toString();
                        buyerfrstname1 = data.getString("firstname").toString();
                        buyerlastname1 = data.getString("lastname").toString();
                        buyerpassword1 = data.getString("password").toString();
                        //buyeremail1=data.getString("email").toString();
                        //buyerphone1=data.getString("company_reg").toString();
                        buyeraddress1=data.getString("address").toString();


                        Log.d("++buyer_id",buyer_id1);
                        Log.d("++buyerfrstname",buyerfrstname1);
                        Log.d("++buyerlastname",buyerlastname1);
                        //Log.d("++buyeremail",buyeremail1);
                        //Log.d("++buyerpassword",buyerpassword1);
                        //Log.d("++buyerphone",buyerphone1);
                        Log.d("++buyeraddress",buyeraddress1);



                        //  loginType = data.getString("loginType").toString();


                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstname", buyerfrstname);
                        editor.putString("lastname",buyerlastname);
                        //editor.putString("password", buyerpassword);
                        //editor.putString("email", buyeremail);
                        //editor.putString("phone", buyerphone);
                        editor.putString("address", buyeraddress);
                        //editor.putString("id", buyer_id);
                        editor.commit();



                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditProfile.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.buyerUpdates);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(EditProfile.this, UserProductGridview.class));
                                dialogInterface.dismiss();
                            }
                        }).show();
                        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                    }

                }
                else
                {
                    //  Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                    Validations.MyAlertBox(EditProfile.this, "Please Enter Valid Credentials");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(EditProfile.this, "Connection Lost");
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
