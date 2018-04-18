package com.example.hp.echoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.misc.AsyncTask;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.hp.echoapp.merchant.TotalProducts;
import com.example.hp.echoapp.merchant.merchantCommon;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by HP on 12/1/2017.
 */

public class MerchantProductAdding extends Activity {
    EditText et_proname,et_proprice,et_proquanitity,et_descpro;
    Button bt_upload,bt_addproduct;
    SlidingMenu slidingMenu;
    ImageView iv_proimage,iv_menu;
    String mediaPath;
    ProgressDialog progressDialog;
    private String userChoosenTask,filePath,ImageUploadValue,merchant_id;

    private int REQUEST_CAMERA = 100, SELECT_FILE = 1;
    static final int PICK_IMAGE_REQUEST = 1;
    SharedPreferences sharedPreferences;
    public static final String logmessage = "Man Activity : ";

    private Uri uri;
    public static String BASE_URL = "http://104.236.67.117:3001/invoice/upload";
   //public static String BASE_URL = "http://www.gadeal.com/services/upload_image.php";
   // public static String BASE_URL="http://www.gadeal.com/services/products.php?function=addImage";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
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



        merchantCommon.SlideMenuDesign(slidingMenu, MerchantProductAdding.this, "shoppingcart");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        et_proname=(EditText)findViewById(R.id.et_proname);
        et_proprice=(EditText)findViewById(R.id.et_proprice);
        et_proquanitity=(EditText)findViewById(R.id.et_proquanitity);
        et_descpro=(EditText)findViewById(R.id.et_descpro);
        bt_upload=(Button)findViewById(R.id.bt_upload);
        bt_addproduct=(Button)findViewById(R.id.bt_addproduct);
        iv_proimage=(ImageView)findViewById(R.id.iv_proimage);

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        bt_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(0);
            }
        });


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ImgUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MerchantProductAdding.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = ImgUtility.checkPermission(MerchantProductAdding.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST) {
                /*uri = data.getData();
                filePath = getPath(uri);
                Log.d("picUri", uri.toString());
                Log.d("filePath", filePath);
               // Bitmap photo = (Bitmap) data.getExtras().get("data");

                iv_proimage.setImageURI(uri);

               // Uri tempUri = getImageUri(getApplicationContext(), photo);

                if (filePath != null) {
                    imageUpload(filePath);

                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }
*/
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

                /*Uri selectedMediaUri = data.getData();
                Log.d("--uri", String.valueOf(selectedMediaUri));
                fileupload2(selectedMediaUri);*/
            } else if (requestCode == REQUEST_CAMERA) {

                Bitmap photo = (Bitmap) data.getExtras().get("message");
                iv_proimage.setImageBitmap(photo);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                filePath=finalFile.toString();
                if (filePath != null) {
                    imageUpload(filePath);
                    Log.d("--file",filePath);
                    Toast.makeText(this, "image upload sucess", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        Log.d("url",result);
        return result;
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
                            ImageUploadValue = jObj.getString("data");
                            Log.d("cameraImageUrl", ImageUploadValue);

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
            nameValuePairs.add(new BasicNameValuePair("image",ImageUploadValue));
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
                        Log.d("--Image",data.getString("image").toString());
                        //  loginType = data.getString("loginType").toString();
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", proName);
                        editor.putString("price",proPrice);
                        editor.putString("quantity", proQuantity);
                        editor.putString("description", proDesc);
                        editor.putString("image",ImageUploadValue);
                        //  editor.putString("loginType", loginType);
                        // editor.putString("_id", _id);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MerchantProductAdding.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.addproduct);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(MerchantProductAdding.this,TotalProducts.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            }
                        }).show();
                    }
                }
                else
                {
                    Validations.MyAlertBox(MerchantProductAdding.this,"Please Enter Valid Credentials");

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
                Validations.MyAlertBox(MerchantProductAdding.this, "Please Enter Product name");
                et_proname.requestFocus();
            } else if (et_proprice.getText().toString().equals("")) {
                Validations.MyAlertBox(MerchantProductAdding.this, "Please Enter Product Price");
                et_proprice.requestFocus();
            } else if(et_proquanitity.getText().toString().equals("")) {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                Validations.MyAlertBox(MerchantProductAdding.this,"Please Enter Quantity");
                et_proquanitity.requestFocus();
            } else if(et_descpro.getText().toString().equals(""))
            {
                Validations.MyAlertBox(MerchantProductAdding.this,"Please Enter Description");
                et_descpro.requestFocus();
            }
            else {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                if (Validations.isConnectedToInternet(MerchantProductAdding.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    // String proName, String proPrice, String proQuantity, String proDesc, String proCat, String proImage)
                    //iv_proimage.setVisibility(View.INVISIBLE);
                    new MerchantProductAdding.AddPro(merchant_id,et_proname.getText().toString(), et_proprice.getText().toString(),et_proquanitity.getText().toString(),
                            et_descpro.getText().toString(),iv_proimage.toString()).execute();
                } else {
                    Validations.MyAlertBox(MerchantProductAdding.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
    }
}
