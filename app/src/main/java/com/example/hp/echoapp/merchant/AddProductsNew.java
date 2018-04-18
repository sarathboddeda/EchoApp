package com.example.hp.echoapp.merchant;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.misc.AsyncTask;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.Validations;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.getInstance;

/**
 * Created by HP on 12/16/2017.
 */

public class AddProductsNew extends Activity {
    EditText et_proname,et_proprice,et_proquanitity,et_descpro,et_date,et_time;
    Button bt_upload,bt_addproduct;
    SlidingMenu slidingMenu;
    ImageView iv_proimage,iv_menu;
    SharedPreferences sharedPreferences;
    private String merchant_id;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    String mediaPath;
    String image_path;
    String ageS;
    Integer ageInt;
    private int mDay,mMonth,mYear;
    final Calendar c =getInstance();
    static final int DATE_DIALOG_ID = 999;
    private ImageLoadingUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);

        Calendar today = Calendar.getInstance();
        //Log.d("++todsy",today);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Permissions for gallery intent
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(AddProductsNew.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AddProductsNew.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddProductsNew.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
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



        sharedPreferences = getSharedPreferences("merchantLoginDetails", Context.MODE_PRIVATE);
        merchant_id = sharedPreferences.getString("id", "").toString();
        Log.d("merchant_id",merchant_id);

        merchantCommon.SlideMenuDesign(slidingMenu, AddProductsNew.this, "shoppingcart");
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
        et_date=(EditText)findViewById(R.id.et_date);
        et_time=(EditText)findViewById(R.id.et_time);
        bt_upload=(Button)findViewById(R.id.bt_upload);
        bt_addproduct=(Button)findViewById(R.id.bt_addproduct);
        iv_proimage=(ImageView)findViewById(R.id.iv_proimage);

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        bt_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //uploadFile();
                validate(0);

                //Log.d("val",image_path);


            }
        });




        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                if (v == et_date) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                        datePickerDialog = new DatePickerDialog(AddProductsNew.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // set day of month , month and year value in the edit text
                                        et_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();

                }

            }
        });




        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == et_time) {

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddProductsNew.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                   // et_time.setText(hourOfDay + ":" + minute);

                                    et_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }

            }
        });


    }


    //Image Uploading <code>

    public void uploadFile() {

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
                //mediaPath = cursor.getString(columnIndex);
                mediaPath=compressImage(cursor.getString(columnIndex));
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                iv_proimage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();



                if (Build.VERSION.SDK_INT > 15) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                // Map is used to multipart the file using okhttp3.RequestBody
                String mainImage =compressImage(mediaPath);
                File file = new File(mainImage);




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
                           // Toast.makeText(AddProductsNew.this,""+image_path,Toast.LENGTH_SHORT).show();

                            Validations.MyAlertBox(AddProductsNew.this,"Image Uploaded Successfully");

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
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            Log.d("*expe",e.toString());
        }

    }
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath,options);
      // Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.id.tv_status, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;



        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = utils.calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16*1024];

        try{
            bmp = BitmapFactory.decodeFile(filePath,options);
        }
        catch(OutOfMemoryError exception){
            exception.printStackTrace();

        }
        try{
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        }
        catch(OutOfMemoryError exception){
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float)options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth()/2, middleY - bmp.getHeight() / 2, new Paint());


        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/"+ System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private class AddPro extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String proName,proPrice,proQuantity,proDesc,proCat,productImage,status="0",merchant_id,pid,datetime;




        public AddPro(String merchant_id ,String proName, String proPrice, String proQuantity, String proDesc, String productImage,String datetime) {
            this.merchant_id=merchant_id;
            this.proName = proName;
            this.proPrice = proPrice;
            this.proQuantity = proQuantity;
            this.proDesc = proDesc;
            this.productImage = productImage;
            this.datetime=datetime;
        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            String producttitle = proName.substring(0,1).toUpperCase() + proName.substring(1);
            Log.d("__producttitle",producttitle);

            nameValuePairs= new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mid",merchant_id));
            nameValuePairs.add(new BasicNameValuePair("pname",producttitle));
            nameValuePairs.add(new BasicNameValuePair("price",proPrice));
            nameValuePairs.add(new BasicNameValuePair("quantity",proQuantity));
            nameValuePairs.add(new BasicNameValuePair("tot_stock",proQuantity));
            nameValuePairs.add(new BasicNameValuePair("description",proDesc));
            nameValuePairs.add(new BasicNameValuePair("image",productImage));
            nameValuePairs.add(new BasicNameValuePair("date_delivery",datetime));
            nameValuePairs.add(new BasicNameValuePair("status",status));
            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/products.php?function=addproduct",2,nameValuePairs);
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
                        String proName,proPrice,proQuantity,proDesc,proCat,productImage,status="Inactive",merchant_id,datetime;
                        pid = data.getString("id").toString();
                        proName = data.getString("product_name").toString();
                        proPrice=data.getString("price").toString();
                        proQuantity = data.getString("quantity").toString();
                        proDesc = data.getString("description").toString();
                        productImage=data.getString("image").toString();
                        datetime=data.getString("date_added").toString();

                        Log.d("--product_name",proName);
                        Log.d("--price",proPrice);
                        Log.d("--category", data.getString("category").toString());
                        Log.d("--quantity", proQuantity);
                        Log.d("--description", proDesc);
                        Log.d("--Image",productImage);
                        Log.d("--datetime",datetime);
                        Log.d("--pid",pid);
                        //  loginType = data.getString("loginType").toString();
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("product_name", proName);
                        editor.putString("price",proPrice);
                        editor.putString("quantity", proQuantity);
                        editor.putString("description", proDesc);
                        editor.putString("image",productImage);
                        //editor.putString("loginType", loginType);
                        //editor.putString("_id", _id);
                        editor.commit();

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddProductsNew.this);
                        builder.setTitle(R.string.alertTitle);
                        builder.setMessage(R.string.addproduct);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in=new Intent(AddProductsNew.this,MerchantDeliveryCenter.class);
                                in.putExtra("pid",pid);
                                startActivity(in);
                                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            }
                        }).show();
                    }
                }
                else
                {
                    Validations.MyAlertBox(AddProductsNew.this,"Please Enter Valid Credentials");

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
            if (et_proname.getText().toString().equals("")) {
                Validations.MyAlertBox(AddProductsNew.this, "Please Enter Product name");
                et_proname.requestFocus();
            } else if (et_proprice.getText().toString().equals("")) {
                Validations.MyAlertBox(AddProductsNew.this, "Please Enter Product Price");
                et_proprice.requestFocus();
            }
            else if(et_date.getText().toString().equals(""))
            {
                Validations.MyAlertBox(AddProductsNew.this,"Please select the Date");
                et_date.requestFocus();
            }
            else if(et_time.getText().toString().equals(""))
            {
                Validations.MyAlertBox(AddProductsNew.this,"Please select the Time");
                et_time.requestFocus();
            }
            else if(et_proquanitity.getText().toString().equals("")) {
                //Toast.makeText(getBaseContext(), "Invalid Data ", Toast.LENGTH_SHORT).show();
                Validations.MyAlertBox(AddProductsNew.this,"Please Enter Quantity");
                et_proquanitity.requestFocus();
            } else if(et_descpro.getText().toString().equals(""))
            {
                Validations.MyAlertBox(AddProductsNew.this,"Please Enter Description");
                et_descpro.requestFocus();
            }
            else if(iv_proimage.getDrawable()==null)
            {
                Validations.MyAlertBox(AddProductsNew.this,"Please select the image");
            }

            else {

                //uploadFile();
                if (Validations.isConnectedToInternet(AddProductsNew.this)) {
                    //Toast.makeText(getBaseContext(), "internet Data ", Toast.LENGTH_SHORT).show();
                    // String proName, String proPrice, String proQuantity, String proDesc, String proCat, String proImage)
                    //iv_proimage.setVisibility(View.INVISIBLE);
                    String date=et_date.getText().toString();
                    String time=et_time.getText().toString();
                    String datetime=date + " " + time;
                    Log.d("^^image",image_path);
                    new AddProductsNew.AddPro(merchant_id,et_proname.getText().toString(), et_proprice.getText().toString(),et_proquanitity.getText().toString(),
                            et_descpro.getText().toString(),image_path,datetime).execute();
                } else {
                    Validations.MyAlertBox(AddProductsNew.this, "INTERNET CONNECTION FAILED");
                }
            }
        }
    }
   /* private String getdatevalidation(int year, int month, int day){
        Calendar merdate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        merdate.set(year, month, day);

        int age = today.get(Calendar.YEAR) - merdate.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < merdate.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        ageInt = new Integer(age);
        Log.d("**age", String.valueOf(ageInt));
        ageS = ageInt.toString();


        return ageS;
    }*/




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
