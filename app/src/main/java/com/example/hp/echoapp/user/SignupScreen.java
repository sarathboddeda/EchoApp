package com.example.hp.echoapp.user;

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
import com.example.hp.echoapp.merchant.AddProductsNew;
import com.example.hp.echoapp.merchant.ApiConfig;
import com.example.hp.echoapp.merchant.AppConfig;

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
 * Created by HP on 10/17/2017.
 */

public class SignupScreen extends Activity
{
    Button bt_signup;
    TextView tv_login;
    public static final String BASE_URL="";
    String logintype="Buyer";
    Button bt_upload;
    ProgressDialog progressDialog;
    String mediaPath,image_path;
    ImageView iv_proimage;
    EditText et_frstname,et_lstnme,et_phnenumber,et_email,et_password,et_confirm,et_useraddress,et_useraddresscity,et_useraddressstate;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupscreen);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Permissions for gallery intent
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(SignupScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SignupScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SignupScreen.this,
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

        bt_signup=(Button)findViewById(R.id.bt_signup);
        tv_login=(TextView)findViewById(R.id.tv_login);
        et_frstname=(EditText)findViewById(R.id.et_frstname);
        et_lstnme=(EditText)findViewById(R.id.et_lstnme);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phnenumber=(EditText)findViewById(R.id.et_phnenumber);
        et_password=(EditText)findViewById(R.id.et_password);
        et_confirm=(EditText)findViewById(R.id.et_confirm);
        et_useraddress=(EditText)findViewById(R.id.et_useraddress);
        et_useraddresscity=(EditText)findViewById(R.id.et_useraddresscity);
        et_useraddressstate=(EditText)findViewById(R.id.et_useraddressstate);
        bt_upload=(Button)findViewById(R.id.bt_upload);
        iv_proimage=(ImageView)findViewById(R.id.iv_proimage);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(Validations.isConnectedToInternet(SignupScreen.this)) {
                    new SignupScreen().userSignUp(et_frstname.getText().toString(),et_lstnme.getText().toString(),
                            et_phnenumber.getText().toString(), et_email.getText().toString(), et_password.getText().toString(),
                           ).execute();
                }else {
                    Validations.MyAlertBox(SignupScreen.this,"Internet Connection Lost");
                }
                Intent signup=new Intent(SignupScreen.this,UserProductGridview.class);
                startActivity(signup);*/
                validate(0);
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signuplogin=new Intent(SignupScreen.this,LoginScreen.class);
                startActivity(signuplogin);
            }
        });


       /* bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });*/


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
//{"status":"1","path":"http://162.251.83.200/~wwwm3phpteam/echoapp/services2/uploads/1513074813099.jpg"}
                            image_path = jObject.getString("path");
                            Toast.makeText(SignupScreen.this,""+image_path,Toast.LENGTH_SHORT).show();

                            Validations.MyAlertBox(SignupScreen.this,"Image Uploaded Successfully");

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

    /*  *******  This service is for displays list of audio files*******

          http://162.251.83.200/~wwwm3phpteam/echoapp/services2/regusers.php?function=addUser
   */
    private class userSignup extends AsyncTask<Object, Object, JSONObject>
    {

        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String firstname,lastname,email, password, mobile,status="Inactive",proimage;


        public userSignup(String firstname,String lastname,String email, String password, String mobile,String proimage){

            this.firstname = firstname;
            this.lastname=lastname;
            this.email = email;
            this.password = password;
            this.mobile = mobile;
            this.proimage = proimage;


        }


        @Override
        protected JSONObject doInBackground(Object... strings) {
            String merfirst = firstname.substring(0,1).toUpperCase() + firstname.substring(1);
            String merlst = lastname.substring(0,1).toUpperCase() + lastname.substring(1);

            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("firstname", merfirst));
            nameValuePairs.add(new BasicNameValuePair("lastname",merlst));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("phone", mobile));
            nameValuePairs.add(new BasicNameValuePair("status",status));
           //nameValuePairs.add(new BasicNameValuePair("image",proimage));
            nameValuePairs.add(new BasicNameValuePair("logintype",logintype));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/regusers.php?function=addUser", 2, nameValuePairs);
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
                        Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_LONG).show();
                        Log.d("_id", data.getString("id").toString());
                        Log.d("==userName", data.getString("firstname").toString());
                        Log.d("==email", data.getString("email").toString());
                        Log.d("==password", data.getString("password").toString());
                        Log.d("==phone", data.getString("phone").toString());
                        //Log.d("==_id",data.getString("_id").toString());
                        //  Log.d("loginType", data.getString("loginType").toString());

                        String firstname, lastname, email, password, phone, _id, status;
                        _id = data.getString("id").toString();
                        firstname = data.getString("firstname").toString();
                        lastname = data.getString("lastname").toString();
                        password = data.getString("password").toString();
                        email = data.getString("email").toString();
                        phone = data.getString("phone").toString();
                       // proimage=data.getString("profile_image").toString();
                        status = data.getString("status").toString();


                        SharedPreferences sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", _id);
                        editor.putString("firstname", firstname);
                        editor.putString("lastname", lastname);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("phone", phone);
                        editor.putString("status", status);
                        //editor.putString("proimage", proimage);
                        editor.commit();


                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignupScreen.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.usersignup);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(SignupScreen.this, UserProductGridview.class));
                                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                                dialogInterface.dismiss();
                            }
                        }).show();
                }



                }
                else
                {
                    //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                    Validations.MyAlertBox(SignupScreen.this, "Email Already Exit");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                Validations.MyAlertBox(SignupScreen.this, "Connection Lost");
            }
        }
    }
    public void onClick(View v) {
    }


    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        if (flowcode == 0) {
            if (et_frstname.getText().toString().equals("")) {
                Validations.MyAlertBox(SignupScreen.this, "Please Enter First name");
                et_frstname.requestFocus();
            }else if(et_lstnme.getText().toString().equals(""))
            {
                Validations.MyAlertBox(SignupScreen.this, "Please Enter Last Name");
                et_lstnme.requestFocus();
            }
            else if (et_phnenumber.getText().toString().equals("")) {
                Validations.MyAlertBox(SignupScreen.this, "Please Enter Phone Number");
                et_phnenumber.requestFocus();
            }
            else if (!Validations.email(et_email.getText().toString())) {
                Validations.MyAlertBox(SignupScreen.this, "Please Enter Email");
                et_email.requestFocus();
            }
            else if (et_password.getText().toString().equals("")) {
                Validations.MyAlertBox(SignupScreen.this, "Please Enter Password");
                et_password.requestFocus();
            } else if (et_confirm.getText().toString().equals("")) {
                Validations.MyAlertBox(SignupScreen.this, "Please Enter ReType Password");
                et_password.requestFocus();
            }  else
                {
                if (Validations.isConnectedToInternet(SignupScreen.this))
                {
                    new userSignup(et_frstname.getText().toString(),et_lstnme.getText().toString(), et_email.getText().toString(),
                            et_password.getText().toString(), et_phnenumber.getText().toString(),image_path).execute();
                }
                else
                {
                    Validations.MyAlertBox(SignupScreen.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
        else
        {
            Validations.MyAlertBox(SignupScreen.this,"Email already exits");

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
