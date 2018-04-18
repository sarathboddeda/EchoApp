package com.example.hp.echoapp.merchant;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.hp.echoapp.user.SignupScreen;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HP on 11/15/2017.
 */

public class MerchantSignup extends Activity {
    Button bt_merchantsignup;
    TextView tv_merchntlogin;
    Button bt_upload;
    ProgressDialog progressDialog;
    String mediaPath,image_path;
    ImageView iv_proimage;
    EditText et_merchantcmpnyname,et_merchantcmpnyregis,et_merchantpersonaladdress,et_merchantpersonalcnt,
            et_merchantpassport,et_merchantemail,et_merchantpassword, et_merchantrepassword,
            et_merchantyearestablished,et_merchantproductcertification;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Permissions for gallery intent
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(MerchantSignup.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MerchantSignup.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MerchantSignup.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else
            {
                //do something
            }
        } else {
            //do something
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        5);
            }
        }


        if (Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        et_merchantpersonaladdress=(EditText)findViewById(R.id.et_merchantpersonaladdress);
        et_merchantpersonalcnt=(EditText)findViewById(R.id.et_merchantpersonalcnmt);
        et_merchantemail=(EditText)findViewById(R.id.et_merchantemail);
        et_merchantpassport=(EditText)findViewById(R.id.et_merchantpassport);
        et_merchantcmpnyname=(EditText)findViewById(R.id.et_merchantcmpnyname);
        et_merchantcmpnyregis=(EditText)findViewById(R.id.et_merchantcmpnyregis);
        et_merchantyearestablished=(EditText)findViewById(R.id.et_merchantyearestablished);
        et_merchantpassword=(EditText)findViewById(R.id.et_merchantpassword);
        et_merchantrepassword=(EditText)findViewById(R.id.et_merchantrepassword);
        et_merchantproductcertification=(EditText)findViewById(R.id.et_merchantproductcertification);
        bt_merchantsignup=(Button)findViewById(R.id.bt_merchantsignup);
        tv_merchntlogin=(TextView)findViewById(R.id.tv_merchntlogin);
        bt_upload=(Button)findViewById(R.id.bt_upload);
        //iv_proimage=(ImageView)findViewById(R.id.iv_proimage);

       /* bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });*/

        tv_merchntlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent merchantlogin=new Intent(MerchantSignup.this,MerchantLogin.class);
                startActivity(merchantlogin);
            }
        });
        bt_merchantsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent merchantsignup=new Intent(MerchantSignup.this,MerchantProducts.class);
                startActivity(merchantsignup);*/
               validate(0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                iv_proimage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();



                if (Build.VERSION.SDK_INT > 15) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                // Map is used to multipart the file using okhttp3.RequestBody
                File file = new File(mediaPath);

                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
                //Call<ResponseBody> call = getResponse.uploadFile(fileToUpload);


                Call<ResponseBody> call = getResponse.uploadFile(fileToUpload);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("Resp","Success");
                        progressDialog.dismiss();
                        try {
                            String responseString = response.body().string();
                            Log.d("Response", responseString);
                            JSONObject jObject = new JSONObject(responseString);
                            //String userName = jObject.getString("userName");
                           // {"status":"1","path":"http://162.251.83.200/~wwwm3phpteam/echoapp/services2/uploads/1513074813099.jpg"}
                            image_path = jObject.getString("path");
                            Toast.makeText(MerchantSignup.this,""+image_path,Toast.LENGTH_SHORT).show();

                            Validations.MyAlertBox(MerchantSignup.this,"Image Uploaded Successfully");

                            Log.d("image_path: ",image_path);

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Resp","Fail");
                        Log.d("Resp","Fail"+call.toString());
                        Log.d("Resp","Fail"+t.getMessage()+" "+t.toString());
                    }
                });

            }
            else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


    /*  *******  This service is merchant signup ******

          http://162.251.83.200/~wwwm3phpteam/echoapp/services2/regmerchants.php?function=addMerchant
   */
    private class merchantuserSignup extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String merchantaddress,merchantcontact,merchantpassport,merchantcompany,merchantemail,merchantpasword,
                merchantcompanyreg, merchantyearestb,merchantprocertificate,status="Inactive";

        public merchantuserSignup(String merchantaddress,String merchantcontact,String merchantpassport,
                          String merchantcompany, String merchantemail,String merchantpasword,
                                  String merchantcompanyreg,String merchantyearestb,String merchantprocertificate){

            this.merchantaddress = merchantaddress;
            this.merchantcontact=merchantcontact;
            this.merchantpassport = merchantpassport;
            this.merchantcompany = merchantcompany;
            this.merchantemail=merchantemail;
            this.merchantpasword=merchantpasword;
            this.merchantcompanyreg = merchantcompanyreg;
            this.merchantyearestb=merchantyearestb;
            this.merchantprocertificate=merchantprocertificate;


        }


        @Override
        protected JSONObject doInBackground(Object... strings) {
            String merttitle = merchantcompany.substring(0,1).toUpperCase() + merchantcompany.substring(1);

            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("company_name", merttitle));
            nameValuePairs.add(new BasicNameValuePair("email",merchantemail));
            nameValuePairs.add(new BasicNameValuePair("password",merchantpasword));
            nameValuePairs.add(new BasicNameValuePair("address", merchantaddress));
            nameValuePairs.add(new BasicNameValuePair("phone",merchantcontact));
            nameValuePairs.add(new BasicNameValuePair("c_reg", merchantcompanyreg));
            nameValuePairs.add(new BasicNameValuePair("year",merchantyearestb));
            nameValuePairs.add(new BasicNameValuePair("cert",merchantprocertificate));
            nameValuePairs.add(new BasicNameValuePair("passport", merchantpassport));
            nameValuePairs.add(new BasicNameValuePair("status",status));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regmerchants.php?function=addMerchant", 2, nameValuePairs);
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


                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
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



                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MerchantSignup.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.merchantSignup);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MerchantSignup.this, MerchantLogin.class));
                                dialogInterface.dismiss();
                            }
                        }).show();
                        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                    }



                }
                else
                {
                    //  Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                    Validations.MyAlertBox(MerchantSignup.this, "Please Enter Valid Credentials");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(MerchantSignup.this, "Connection Lost");
            }
        }
    }
    public void onClick(View v) {
    }


    public void validate(int flowcode) {

        String status="Inactive";
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_merchantpersonaladdress.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantSignup.this, "Please Enter Merchant Personal Address");
                et_merchantpersonaladdress.requestFocus();
            }else if(et_merchantpersonalcnt.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantSignup.this, "Please Enter Merchant Personal Contact");
                et_merchantpersonalcnt.requestFocus();
            }
            else if (et_merchantpassport.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantSignup.this, "Please Enter Passport Number");
                et_merchantpassport.requestFocus();
            }
            else if(et_merchantcmpnyname.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantSignup.this,"Please enter Company Name");
            }
            else if (!Validations.email(et_merchantemail.getText().toString())) {
                Validations.MyAlertBox(MerchantSignup.this, "Please Enter Email");
                et_merchantemail.requestFocus();
            }
            else if (et_merchantpassword.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantSignup.this, "Please Enter Password");
                et_merchantpassword.requestFocus();
            } else if (et_merchantrepassword.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantSignup.this, "Please Enter ReType Password");
                et_merchantrepassword.requestFocus();
            } else if(et_merchantcmpnyregis.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantSignup.this,"Please Enter Company registration number");
            }
            else if(et_merchantyearestablished.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantSignup.this,"Please Enter year of established");
            }
            else if(et_merchantproductcertification.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantSignup.this,"Please Enter Product Certification");
            }
            else {
                if (Validations.isConnectedToInternet(MerchantSignup.this)) {
                    new merchantuserSignup(et_merchantpersonaladdress.getText().toString(),
                            et_merchantpersonalcnt.getText().toString(),
                            et_merchantpassport.getText().toString(),
                            et_merchantcmpnyname.getText().toString(),
                            et_merchantemail.getText().toString(),
                            et_merchantpassword.getText().toString(),
                            et_merchantcmpnyregis.getText().toString(),
                            et_merchantyearestablished.getText().toString(),
                            et_merchantproductcertification.getText().toString()).execute();
                } else {
                    Validations.MyAlertBox(MerchantSignup.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
        else
        {
            Validations.MyAlertBox(MerchantSignup.this,"Email already exits");

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
