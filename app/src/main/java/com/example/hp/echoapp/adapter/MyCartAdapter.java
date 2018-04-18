package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.Validations;
import com.example.hp.echoapp.constructors.MyCartContructor;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.user.LoginScreen;
import com.example.hp.echoapp.user.UserProductGridview;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.hp.echoapp.user.MyCart.userid;

/**
 * Created by HP on 11/16/2017.
 */

public class MyCartAdapter extends BaseAdapter {
    Context mycart;
    String proid;
    ArrayList<MyCartContructor> myCartContructors;
    LayoutInflater mInflater;
    private ImageView iv_mycartimage;
    private ImageView iv_crtdel;
    private TextView tv_mycart_title,tv_mycart_prize,tv_mycartcost,tv_mycartmerchant,tv_delwithout,tv_delwith;


    public MyCartAdapter(Context mycart, ArrayList<MyCartContructor> myCartContructors)
    {
        this.mycart=mycart;
        this.myCartContructors=myCartContructors;
    }

    @Override
    public int getCount() {
        return myCartContructors.size();
    }

    @Override
    public Object getItem(int i) {
        return myCartContructors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {

        if(ConvertView==null)
        {
           LayoutInflater mInflater=(LayoutInflater)mycart.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView =mInflater.inflate(R.layout.mycart_single,null);
            iv_mycartimage=(ImageView)ConvertView.findViewById(R.id.iv_mycartimage);
            iv_crtdel=(ImageView)ConvertView.findViewById(R.id.iv_crtdel);
            tv_mycart_title=(TextView)ConvertView.findViewById(R.id.tv_mycart_title);
            tv_mycart_prize=(TextView)ConvertView.findViewById(R.id.tv_mycart_prize);
            tv_mycartcost=(TextView)ConvertView.findViewById(R.id.tv_mycartcost);
            tv_mycartmerchant=(TextView)ConvertView.findViewById(R.id.tv_mycartmerchant);
            //tv_delwithout=(TextView)ConvertView.findViewById(R.id.tv_delwithout);
            //tv_delwith=(TextView)ConvertView.findViewById(R.id.tv_delwith);
            Picasso.with(mycart).load(myCartContructors.get(i).getMyCartImage()).into(iv_mycartimage);
            tv_mycart_title.setText(myCartContructors.get(i).getMyCartName());
            tv_mycart_prize.setText(myCartContructors.get(i).getMyCartWeight());
            tv_mycartcost.setText(myCartContructors.get(i).getMyCartCost());
            tv_mycartmerchant.setText(myCartContructors.get(i).getMyCartMerchant());
            proid=myCartContructors.get(i).getMyproid();
            iv_crtdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MyCartAdapter.DelCart(proid,userid).execute();
                }
            });
            //tv_delwithout.setText(myCartContructors.get(i).getMtCartDelwithout());
            //tv_delwith.setText(myCartContructors.get(i).getMyCartDelwith());
        }

        return ConvertView;
    }

    private class DelCart extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        JSONObject json;
        String pid, uid,token;

        public DelCart(String pid, String uid) {
            this.pid = pid;
            this.uid = uid;

        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("uid", uid));
            nameValuePairs.add(new BasicNameValuePair("pid", pid));

            json = JSONParser.makeServiceCall("http://media3.co.in/backend/echoapp/services2/orders.php?function=deleteCartProducts", 2, nameValuePairs);
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
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mycart);
                    builder.setTitle(R.string.alertTitle);
                    builder.setMessage("Product deleted from Cart");
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent in=new Intent(mycart,UserProductGridview.class);
                            mycart.startActivity(in);
                           // overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                        }
                    }).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }
}
