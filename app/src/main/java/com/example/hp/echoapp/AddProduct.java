package com.example.hp.echoapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.hp.echoapp.merchant.TotalProducts;
import com.squareup.picasso.Picasso;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by HP on 11/29/2017.
 */

public class AddProduct extends Activity
{
    EditText et_proname,et_proprice,et_proquanitity,et_descpro;
    Button bt_upload,bt_capture,bt_addproduct;
    ImageView iv_proimage;
    Spinner sp_category;
    String merchant_id,filePath,imageUploadValue;
    private Uri uri;
    SharedPreferences sharedPreferences;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    //public static String BASE_URL = "162.251.83.200/~wwwm3phpteam/echoapp/services2/products.php?function=addImage";
    public static String BASE_URL = "http://104.236.67.117:3001/invoice/upload";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);
        et_proname = (EditText) findViewById(R.id.et_proname);
        et_proprice=(EditText)findViewById(R.id.et_proprice);


        et_proquanitity=(EditText)findViewById(R.id.et_proquanitity);
        et_descpro=(EditText)findViewById(R.id.et_descpro);
        bt_upload=(Button)findViewById(R.id.bt_upload);
        //bt_capture=(Button)findViewById(R.id.bt_capture);
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
        /*bt_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraIntent();
            }
        });*/
        iv_proimage=(ImageView) findViewById(R.id.iv_proimage);
        iv_proimage.setVisibility(View.GONE);
        bt_addproduct=(Button)findViewById(R.id.bt_addproduct);
        //sp_category=(Spinner)findViewById(R.id.sp_category);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        merchant_id=sharedPreferences.getString("id",merchant_id);
        Log.d("--merchant_id",merchant_id);
        bt_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(0);
            }
        });
        Picasso.with(AddProduct.this)
                .load(Uri.parse(sharedPreferences.getString("userProfileImage", "")))
                .placeholder(R.drawable.brinjal)
                .into(iv_proimage);

    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
            {


                onSelectFromGalleryResult(data);

            }
               // onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
            {

                onCaptureImageResult(data);
            }
                //onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        iv_proimage.setVisibility(View.VISIBLE);

        iv_proimage.setImageBitmap(bm);


    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv_proimage.setVisibility(View.VISIBLE);
        iv_proimage.setImageBitmap(thumbnail);


    }


    private void imageUpload(final String imagepath)
    {
        Log.d("--image",imagepath);
        SimpleMultiPartRequest smr= new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        // imageUploadValue=response.toString();
                        //Toast.makeText(UpdateProfile.this, imageUploadValue, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            imageUploadValue = jObj.getString("data");
                            Log.d("cameraImageUrl", imageUploadValue);

                            //   Toast.makeText(getApplicationContext(), imageUploadValue, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            // Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }


        });

        smr.addFile("getImage", imagepath);
        MyApplication.getInstance().addToRequestQueue(smr);

    }



    private class AddPro extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String proName,proPrice,proQuantity,proDesc,proCat,productImage,status="Inactive",merchant_id;

        public AddPro(String merchant_id ,String proName, String proPrice, String proQuantity, String proDesc, String productImage) {
            this.merchant_id=merchant_id;
            this.proName = proName;
            this.proPrice = proPrice;
            this.proQuantity = proQuantity;
            this.proDesc = proDesc;
            this.proCat = proCat;
            this.productImage = productImage;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            nameValuePairs= new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            nameValuePairs.add(new BasicNameValuePair("pname",proName));
            nameValuePairs.add(new BasicNameValuePair("price",proPrice));
            nameValuePairs.add(new BasicNameValuePair("quantity",proQuantity));
            nameValuePairs.add(new BasicNameValuePair("description",proDesc));
            nameValuePairs.add(new BasicNameValuePair("image",productImage));
            nameValuePairs.add(new BasicNameValuePair("status",status));
            json =JSONParser.makeServiceCall("http://162.251.83.200/~wwwm3phpteam/echoapp/services2/products.php?function=addproduct",2,nameValuePairs);
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

                        String proName,proPrice, proQuantity,proDesc;
                        // _id = data.getString("_id").toString();
                        proName = data.getString("product_name").toString();
                        proPrice=data.getString("price").toString();
                        proQuantity = data.getString("quantity").toString();
                        proDesc = data.getString("description").toString();

                        Log.d("--product_name", data.getString("product_name").toString());
                        Log.d("--price",data.getString("price").toString());
                        Log.d("--category", data.getString("category").toString());
                        Log.d("--image",data.getString("image").toString());
                        Log.d("--quantity", data.getString("quantity").toString());
                        Log.d("--description", data.getString("description").toString());
                        //  loginType = data.getString("loginType").toString();
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", proName);
                        editor.putString("price",proPrice);
                        editor.putString("quantity", proQuantity);
                        editor.putString("description", proDesc);
                        //  editor.putString("loginType", loginType);
                        // editor.putString("_id", _id);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddProduct.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.addproduct);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(AddProduct.this,TotalProducts.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                            }
                        }).show();
                    }
                }
                else
                {
                    Validations.MyAlertBox(AddProduct.this,"Please Enter Valid Credentials");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(jsonObject);
        }
    }
    public void validate(int flowcode) {
        // TODO Auto-generated method stub
        Log.d("Test", String.valueOf(flowcode));
        if (flowcode == 0) {
            if (et_proname.getText().toString().equals("")) {
                Validations.MyAlertBox(AddProduct.this, "Please Enter Product name");
                et_proname.requestFocus();
            } else if (et_proprice.getText().toString().equals("")) {
                Validations.MyAlertBox(AddProduct.this, "Please Enter Product Price");
                et_proprice.requestFocus();
            } else if(et_proquanitity.getText().toString().equals("")) {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                Validations.MyAlertBox(AddProduct.this,"Please Enter Quantity");
                et_proquanitity.requestFocus();
            } else if(et_descpro.getText().toString().equals(""))
            {
                Validations.MyAlertBox(AddProduct.this,"Please Enter Description");
                et_descpro.requestFocus();
            }
            else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(AddProduct.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                   // String proName, String proPrice, String proQuantity, String proDesc, String proCat, String proImage)

                    new AddProduct.AddPro(merchant_id,et_proname.getText().toString(), et_proprice.getText().toString(),et_proquanitity.getText().toString(),
                            et_descpro.getText().toString(),iv_proimage.toString()).execute();
                    iv_proimage.setVisibility(View.INVISIBLE);
                } else {
                    Validations.MyAlertBox(AddProduct.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
    }
}
