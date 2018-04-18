package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.echoapp.adapter.NewsFeedAdapter;
import com.example.hp.echoapp.constructors.NewsFeedConst;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 2/2/2018.
 */

public class NewsFeedActivity extends Activity {
    private ListView ll_webview;
    ArrayList<NewsFeedConst> newsFeedConst;
    NewsFeedAdapter newsFeedAdapter;
    SlidingMenu slidingMenu;
    ImageView iv_menu,iv_srcnews;
    TextView tv_srcnew;
    private String BaseUrl="http://media3.co.in/backend/echoapp/services2/newsfeed.php?function=getAllnewsfeed";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_screen);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        Common.SlideMenuDesign(slidingMenu, NewsFeedActivity.this, "newsfeedsactivity");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });

        newsFeedConst=new ArrayList<NewsFeedConst>();
        ll_webview=(ListView)findViewById(R.id.ll_webview);
        iv_srcnews=(ImageView)findViewById(R.id.iv_srcnews);
        tv_srcnew=(TextView)findViewById(R.id.tv_srcnew);
        new NewsFeedActivity.GetNewsFeeds().execute();

        ll_webview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent uri = new Intent(Intent.ACTION_VIEW);
                String url=newsFeedConst.get(i).getFeedlink();
                Log.d("--url",url);
                uri.setData(Uri.parse(url));
                startActivity(uri);

            }
        });

    }
    private class GetNewsFeeds extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String feedtitle,feedimage,feedurl;

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs=new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("uid",merchant_id));
            json= JSONParser.makeServiceCall(BaseUrl,2,nameValuePairs);
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONObject result = null;
            try {
                result = new JSONObject(jsonObject.toString());

                if (result.getString("status").toString().equals("Success")) {

                    JSONArray array = new JSONArray(result.getString("data"));

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject data = new JSONObject(array.getJSONObject(i).toString());

                        //feedtitle=data.getString("image").toString();
                        feedimage=data.getString("product_image").toString();
                        feedurl=data.getString("url").toString();

                        SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putString("feedtitle", feedtitle);
                        editor.putString("product_image",feedimage);
                        editor.putString("url",feedurl);
                        editor.commit();


                        //Log.d("--feedtitle",feedtitle);
                        Log.d("--feedimage",feedimage);
                        Log.d("--feedurl",feedurl);



                        newsFeedConst.add(new NewsFeedConst(feedurl,feedurl,feedimage));

                            final NewsFeedAdapter adapter = new NewsFeedAdapter(NewsFeedActivity.this, newsFeedConst);
                            ll_webview.setAdapter(adapter);


                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*catch (Exception e1){
                Validations.MyAlertBox(TotalProducts.this,getResources().getString(R.string.connectionLost));
            }*/
        }
    }
}
