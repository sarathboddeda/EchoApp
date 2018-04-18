package com.example.hp.echoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.misc.AsyncTask;
import com.example.hp.echoapp.constructors.AvailableConst;
import com.example.hp.echoapp.merchant.ApiConfig;
import com.example.hp.echoapp.merchant.AppConfig;
import com.example.hp.echoapp.merchant.TotalProducts;
import com.example.hp.echoapp.merchant.merchantCommon;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

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
 * Created by HP on 1/4/2018.
 */

public class UpdateProduct extends Activity {
    EditText et_editproname,et_editproprice,et_editproquanitity,et_editdescpro;
    Button bt_editupload,bt_editproduct;
    SlidingMenu slidingMenu;
    ImageView iv_editproimage,iv_menu;
    SharedPreferences sharedPreferences;
    String merchant_id,editproname,editprocost,editproquanity,editprodesc,editproImage,product_id;
    String mediaPath;
    String image_path;
    ProgressDialog progressDialog;
    ListView availableproductsLV;
    ArrayList<AvailableConst> availableConsts;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
        merchant_id = sharedPreferences.getString("id", "").toString();
        Log.d("merchant_id",merchant_id);

        merchantCommon.SlideMenuDesign(slidingMenu, UpdateProduct.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });

        et_editproname=(EditText)findViewById(R.id.et_editproname);
        et_editproprice=(EditText)findViewById(R.id.et_editproprice);
        et_editproquanitity=(EditText)findViewById(R.id.et_editproquanitity);
        et_editdescpro=(EditText)findViewById(R.id.et_editdescpro);
        bt_editproduct=(Button)findViewById(R.id.bt_editaddproduct);
        iv_editproimage=(ImageView)findViewById(R.id.iv_editproimage);
        //merchant_id=getIntent().getStringExtra("merchantid").toString();

        editproname=getIntent().getStringExtra("title").toString();
        editprocost=getIntent().getStringExtra("tv_cost").toString();
        editproquanity=getIntent().getStringExtra("tv_avail").toString();
        editprodesc=getIntent().getStringExtra("prodesc").toString();
        editproImage=getIntent().getStringExtra("tv_proImage").toString();
        product_id=getIntent().getStringExtra("product_id").toString();
        Log.d("== editproname",editproname);
        Log.d("== editprocost",editprocost);
        Log.d("== editproquanity",editproquanity);
        Log.d("== editprodesc",editprodesc);
        Log.d("== product_id",product_id);
        et_editproname.setText(editproname);
        et_editproprice.setText(editprocost);
        et_editproquanitity.setText(editproquanity);
        et_editdescpro.setText(editprodesc);
        Picasso.with(this).load(editproImage).into(iv_editproimage);

        image_path=editproImage;



       /* bt_editupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });*/

        bt_editproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //uploadFile();
                validate(0);

                //Log.d("val",image_path);


            }
        });

        //new UpdateProduct.Updatepro().execute();

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
                iv_editproimage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

                progressDialog.show();

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
                        progressDialog.dismiss();
                        Log.d("Resp","Success");
                        try {
                            String responseString = response.body().string();
                            Log.d("Response", responseString);
                            JSONObject jObject = new JSONObject(responseString);
                            //                    String userName = jObject.getString("userName");
//{"status":"1","path":"http://162.251.83.200/~wwwm3phpteam/echoapp/services2/uploads/1513074813099.jpg"}
                            image_path = jObject.getString("path");
                            Toast.makeText(UpdateProduct.this,""+image_path,Toast.LENGTH_SHORT).show();
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

    private class Updatepro extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String proName,proPrice,proQuantity,proDesc,proCat,productImage,status="Inactive",merchant_id,product_id;

        public Updatepro(String merchant_id ,String product_id,String proName, String proPrice, String proQuantity, String proDesc, String productImage) {
            this.merchant_id=merchant_id;
            this.proName = proName;
            this.proPrice = proPrice;
            this.proQuantity = proQuantity;
            this.proDesc = proDesc;
            this.product_id = product_id;
            this.productImage = productImage;
        }




        @Override
        protected JSONObject doInBackground(String... strings) {

            nameValuePairs= new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mid",merchant_id));
            nameValuePairs.add(new BasicNameValuePair("pid",product_id));
            nameValuePairs.add(new BasicNameValuePair("pname",proName));
            nameValuePairs.add(new BasicNameValuePair("price",proPrice));
            nameValuePairs.add(new BasicNameValuePair("quantity",proQuantity));
            nameValuePairs.add(new BasicNameValuePair("tot_stock",proQuantity));
            nameValuePairs.add(new BasicNameValuePair("description",proDesc));
            nameValuePairs.add(new BasicNameValuePair("image",productImage));
            nameValuePairs.add(new BasicNameValuePair("status",status));
            json =JSONParser.makeServiceCall("http://162.251.83.200/~wwwm3phpteam/echoapp/services2/products.php?function=Updateproduct",2,nameValuePairs);
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            try {
                JSONObject result= new JSONObject(jsonObject.toString());
                if(result.getString("status").equals("Success"))
                {
                    JSONArray array=new JSONArray(result.getString("data"));
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject data=new JSONObject(array.getJSONObject(i).toString());


                        //  Log.d("loginType", data.getString("loginType").toString());

                        //String proName,proPrice, proQuantity,proDesc,productimage;
                        // _id = data.getString("_id").toString();
                        proName = data.getString("product_name").toString();
                        proPrice=data.getString("price").toString();
                        proQuantity = data.getString("quantity").toString();
                        proDesc = data.getString("description").toString();
                        //productImage=data.getString("image").toString();

                        Log.d("--product_name", data.getString("product_name").toString());
                        Log.d("--price",data.getString("price").toString());
                        Log.d("--category", data.getString("category").toString());
                        //Log.d("--image",data.getString("image").toString());
                        Log.d("--quantity", data.getString("quantity").toString());
                        Log.d("--description", data.getString("description").toString());
                        Log.d("--Image",data.getString("image").toString());
                        //  loginType = data.getString("loginType").toString();
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", proName);
                        editor.putString("price",proPrice);
                        editor.putString("quantity", proQuantity);
                        editor.putString("description", proDesc);
                        //editor.putString("image",productImage);
                        //  editor.putString("loginType", loginType);
                        // editor.putString("_id", _id);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateProduct.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.addproduct);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(UpdateProduct.this,TotalProducts.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            }
                        }).show();
                    }
                }
                else
                {
                    Validations.MyAlertBox(UpdateProduct.this,"Please Enter Valid Credentials");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(jsonObject);
        }
    }

    public void validate(int flowcode) {
        //String image_path;



        // TODO Auto-generated method stub
        //Log.d("Test", String.valueOf(image_path));
        //Log.d("**image_path",image_path);

        if (flowcode == 0) {
            if (et_editproname.getText().toString().equals("")) {
                Validations.MyAlertBox(UpdateProduct.this, "Please Enter Product name");
                et_editproname.requestFocus();
            } else if (et_editproprice.getText().toString().equals("")) {
                Validations.MyAlertBox(UpdateProduct.this, "Please Enter Product Price");
                et_editproprice.requestFocus();
            } else if(et_editproquanitity.getText().toString().equals("")) {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                Validations.MyAlertBox(UpdateProduct.this,"Please Enter Quantity");
                et_editproquanitity.requestFocus();
            } else if(et_editdescpro.getText().toString().equals(""))
            {
                Validations.MyAlertBox(UpdateProduct.this,"Please Enter Description");
                et_editdescpro.requestFocus();
            }
           /* else if(iv_editproimage.getDrawable()==null)
            {
                Validations.MyAlertBox(UpdateProduct.this,"Please select the image");
            }*/

            else {

                //uploadFile();
                if (Validations.isConnectedToInternet(UpdateProduct.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    // String proName, String proPrice, String proQuantity, String proDesc, String proCat, String proImage)
                    //iv_proimage.setVisibility(View.INVISIBLE);
                    Log.d("^^image",image_path);
                    /*new UpdateProduct.Updatepro(merchant_id.toString(),product_id.toString(),et_editproname.getText().toString(),
                    et_editproprice.getText().toString(),
                            et_editproquanitity.getText().toString(),
                            et_editdescpro.getText().toString(),image_path).execute();*/
                    new UpdateProduct.Updatepro(merchant_id,product_id,et_editproname.getText().toString(),
                            et_editproprice.getText().toString(),et_editproquanitity.getText().toString(),
                            et_editdescpro.getText().toString(),image_path).execute();
                } else {
                    Validations.MyAlertBox(UpdateProduct.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
    }
}
