package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
 * Created by HP on 10/17/2017.
 */

public class LoginScreen extends Activity
{
    Button bt_login,bt_create;
    EditText et_login_user,et_login_pass;
    TextView tv_logintitle,tv_forgot;
    public int user=0;
    private String tokenId;
@Override
protected   void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_screen);
    bt_login = (Button)findViewById(R.id.bt_login);
    bt_create=(Button)findViewById(R.id.bt_create);
    tv_logintitle=(TextView)findViewById(R.id.tv_logintitle);
    tv_forgot=(TextView)findViewById(R.id.tv_forgot);
    et_login_user=(EditText)findViewById(R.id.et_login_user);
    et_login_pass=(EditText)findViewById(R.id.et_login_pass);
    // Get token
    tokenId = FirebaseInstanceId.getInstance().getToken();
    //Log.d("+++token",tokenId);

    bt_login.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            /* Intent login=new Intent(LoginScreen.this,UserProductGridview.class);
            startActivity(login);*/
            validate(0);
        }
    });
    tv_forgot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent frgt=new Intent(LoginScreen.this,ForgotPassword.class);
            startActivity(frgt);
        }
    });
    bt_create.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent create=new Intent(LoginScreen.this,SignupScreen.class);
            startActivity(create);
        }
    });
    ///font applyling
    Typeface billfont=Typeface.createFromAsset(getAssets(),"fonts/BillyArgelFont.ttf");
    //tv_logintitle.setTypeface(billfont);


}
private class UserLogin extends AsyncTask<String,String,JSONObject>
{
    private ArrayList<NameValuePair> nameValuePairs;
    JSONObject json;
    String email, password,token;

    public UserLogin(String email, String password,String token) {
        this.email = email;
        this.password = password;
        this.token=token;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("token_id", token));
        json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regusers.php?function=login", 2, nameValuePairs);
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
                JSONArray array=new JSONArray(result.getString("data"));
                for(int i=0;i<array.length();i++)
                {
                    JSONObject data=new JSONObject(array.getJSONObject(i).toString());
                    Log.d("firstname", data.getString("firstname").toString());
                    Log.d("lastname",data.getString("lastname").toString());
                    Log.d("email", data.getString("email").toString());
                    Log.d("password", data.getString("password").toString());
                    Log.d("phone", data.getString("phone").toString());
                    Log.d("roletype",data.getString("roletype").toString());
                    Log.d("address",data.getString("address").toString());
                    //  Log.d("loginType", data.getString("loginType").toString());

                    String firstname,lastname, email, password,address, phone,user_id,roletype,buyerImge;

                    // _id = data.getString("_id").toString();
                    firstname = data.getString("firstname").toString();
                    lastname=data.getString("lastname").toString();
                    password = data.getString("password").toString();
                    email = data.getString("email").toString();
                    phone = data.getString("phone").toString();
                    user_id = data.getString("id").toString();
                    address = data.getString("address").toString();
                    roletype=data.getString("roletype").toString();
                    //buyerImge=data.getString("proimage").toString();
                    //loginType = data.getString("loginType").toString();


                    SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("firstname", firstname);
                    editor.putString("lastname",lastname);
                    editor.putString("password", password);
                    editor.putString("email", email);
                    editor.putString("phone", phone);
                    editor.putString("id", user_id);
                    editor.putString("address", address);
                    //editor.putString("buyerImge", buyerImge);
                    editor.putString("roletype", roletype);
                    //  editor.putString("loginType", loginType);
                    // editor.putString("_id", _id);
                    editor.commit();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginScreen.this);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage(R.string.userlogin);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent in=new Intent(LoginScreen.this,UserProductGridview.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                        }
                    }).show();
                }
            }
            else
            {
                Validations.MyAlertBox(LoginScreen.this,"Please Enter Valid Credentials");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}
    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_login_user.getText().toString().equals("")) {
                Validations.MyAlertBox(LoginScreen.this, "Please Enter email");
                et_login_user.requestFocus();
            } else if (et_login_pass.getText().toString().equals("")) {
                Validations.MyAlertBox(LoginScreen.this, "Please Enter Password");
                et_login_pass.requestFocus();
            } else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(LoginScreen.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    new LoginScreen.UserLogin(et_login_user.getText().toString(), et_login_pass.getText().toString(),tokenId).execute();
                } else {
                    Validations.MyAlertBox(LoginScreen.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
    }
}
