package com.example.hp.echoapp.delivery;

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
import com.example.hp.echoapp.merchant.ApiConfig;
import com.example.hp.echoapp.merchant.AppConfig;
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
 * Created by HP on 12/15/2017.
 */

public class DeliverySignup extends Activity {
    Button bt_deliver;
    TextView tv_login;
    public static final String BASE_URL="";
    String logintype="Delivery";
    Button bt_upload;
    ProgressDialog progressDialog;
    String mediaPath,image_path;
    ImageView iv_proimage;
    EditText et_fullname,et_address,et_town,et_state,et_zipcode,et_mobile,et_delemailname,et_delpassword,et_delcnfrmpassword,
    et_country;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Permissions for gallery intent
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(DeliverySignup.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(DeliverySignup.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DeliverySignup.this,
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

        bt_deliver=(Button)findViewById(R.id.bt_deliver);
        tv_login=(TextView)findViewById(R.id.tv_login);
        et_fullname=(EditText)findViewById(R.id.et_fullname);
        et_address=(EditText)findViewById(R.id.et_address);
        et_country=(EditText)findViewById(R.id.et_country);
        et_delemailname=(EditText)findViewById(R.id.et_delemailname);
        et_delpassword=(EditText)findViewById(R.id.et_delpassword);
        et_state=(EditText)findViewById(R.id.et_state);
        et_town=(EditText)findViewById(R.id.et_town);
        et_delcnfrmpassword=(EditText)findViewById(R.id.et_delcnfrmpassword);
        et_zipcode=(EditText)findViewById(R.id.et_zipcode);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
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


        bt_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                //Set the Image in ImageView for Previewing the Media
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
                            //{"status":"1","path":"http://162.251.83.200/~wwwm3phpteam/echoapp/services2/uploads/1513074813099.jpg"}
                            image_path = jObject.getString("path");
                            Toast.makeText(DeliverySignup.this,""+image_path,Toast.LENGTH_SHORT).show();

                            Validations.MyAlertBox(DeliverySignup.this,"Image Uploaded Successfully");

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


    /*  *******  This service is for deliverycenter Signup******

         http://162.251.83.200/~wwwm3phpteam/echoapp/services2/deliverycenters.php?function=addDeliverycenter
   */
    private class deliverySignup extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String _id,fullname,delemail,password, address,state,zipcode,country,city, mobile,status="Inactive";

        public deliverySignup(String fullname, String delemail, String password, String address, String state,
                          String city, String zipcode, String country, String mobile, String status) {
            this.fullname = fullname;
            this.delemail = delemail;
            this.password = password;
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
            nameValuePairs.add(new BasicNameValuePair("name", fullname));
            nameValuePairs.add(new BasicNameValuePair("email",delemail));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            nameValuePairs.add(new BasicNameValuePair("country",country));
            nameValuePairs.add(new BasicNameValuePair("zipcode",zipcode));
            nameValuePairs.add(new BasicNameValuePair("phone",mobile));
            nameValuePairs.add(new BasicNameValuePair("status",status));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/deliverycenters.php?function=addDeliverycenter", 2, nameValuePairs);
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
                        Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_LONG).show();
                        String fullname,delemail,password,address,mobile,state,city,country,zipcode;

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
                        status = data.getString("status").toString();


                        Log.d("_id", data.getString("id").toString());
                        Log.d("==name", data.getString("name").toString());
                        Log.d("==email", data.getString("email").toString());
                        Log.d("==address", data.getString("address").toString());
                        Log.d("==password",data.getString("password").toString());
                        Log.d("==city", data.getString("city").toString());
                        Log.d("==state",data.getString("state").toString());
                        Log.d("==country",data.getString("country").toString());
                        Log.d("==zipcode",data.getString("zipcode").toString());
                        Log.d("==phone",data.getString("phone").toString());
                        //Log.d("==_id",data.getString("_id").toString());
                        //  Log.d("loginType", data.getString("loginType").toString());

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
                        editor.putString("status", status);
                        editor.commit();


                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DeliverySignup.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.deliverysignup);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(DeliverySignup.this, UpdateDeliveryCenter.class));
                                dialogInterface.dismiss();
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            }
                        }).show();
                    }



                }
                else
                {
                    //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                    Validations.MyAlertBox(DeliverySignup.this, "Email Already Exist");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(DeliverySignup.this, "Connection Lost");
            }
        }
    }
    public void onClick(View v) {
    }


    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        String status="Inactive";
        if (flowcode == 0) {
            if (et_fullname.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliverySignup.this, "Please Enter First name");
                et_fullname.requestFocus();
            }
            else if(et_delemailname.getText().toString().equals(""))
            {
                Validations.MyAlertBox(DeliverySignup.this,"Please Enter the email id");
                et_delemailname.requestFocus();
            }
            else if(et_delpassword.getText().toString().equals(""))
            {
                Validations.MyAlertBox(DeliverySignup.this,"Please Enter password");
                et_delpassword.requestFocus();
            }
            else if(et_delcnfrmpassword.getText().toString().equals(""))
            {
                Validations.MyAlertBox(DeliverySignup.this,"Please Re-enter Password");
                et_delcnfrmpassword.requestFocus();
            }
            else if(et_address.getText().toString().equals(""))
            {
                Validations.MyAlertBox(DeliverySignup.this, "Please Enter Last Name");
                et_address.requestFocus();
            }
            else if (et_town.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliverySignup.this, "Please Enter Phone Number");
                et_town.requestFocus();
            }
            else if(et_country.getText().toString().equals(""))
            {
                Validations.MyAlertBox(DeliverySignup.this,"Please Enter your Country");
                et_country.requestFocus();
            }
            else if (et_zipcode.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliverySignup.this, "Please Enter Password");
                et_zipcode.requestFocus();
            } else if (et_mobile.getText().toString().equals("")) {
                Validations.MyAlertBox(DeliverySignup.this, "Please Enter ReType Password");
                et_zipcode.requestFocus();
            }
            else
            {
                if (Validations.isConnectedToInternet(DeliverySignup.this))
                {
                    new deliverySignup(et_fullname.getText().toString(),et_delemailname.getText().toString(),
                            et_delpassword.getText().toString(),
                            et_address.getText().toString(),et_state.getText().toString(), et_town.getText().toString()
                            ,et_zipcode.getText().toString(),et_country.getText().toString(),
                            et_mobile.getText().toString(),status
                   ).execute();

                   /* String fullname, String delemail, String password, String address, String state,
                        String town, String zipcode, String country, String mobile, String status*/
                }
                else
                {
                    Validations.MyAlertBox(DeliverySignup.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
        else
        {
            Validations.MyAlertBox(DeliverySignup.this,"Email already exits");

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
