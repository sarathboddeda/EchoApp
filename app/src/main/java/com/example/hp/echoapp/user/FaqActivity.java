package com.example.hp.echoapp.user;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.FaqConstructor;
import com.example.hp.echoapp.adapter.FaqAdapter;
import com.example.hp.echoapp.JSONParser;
import com.example.hp.echoapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 2/3/2018.
 */

public class FaqActivity extends Activity {
    private ListView ll_faq;
    ArrayList<FaqConstructor> faqConstructors;
    FaqAdapter faqAdapter;
    SlidingMenu slidingMenu;
    ImageView iv_menu;
    TextView tv_faqquestion,tv_faqanswerer;
    String BaseUrl="http://media3.co.in/backend/echoapp/services2/faq.php?function=getAllfaq";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_screen);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slide_menu_width);
        slidingMenu.setFadeDegree(0.20f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.leftmenu);
        Common.SlideMenuDesign(slidingMenu, FaqActivity.this, "faqactivity");
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });

        faqConstructors=new ArrayList<FaqConstructor>();
        ll_faq=(ListView)findViewById(R.id.ll_faq);
        tv_faqquestion=(TextView)findViewById(R.id.tv_faqquestion);
        tv_faqanswerer=(TextView)findViewById(R.id.tv_faqanswerer);
        new FaqActivity.GetFaq().execute();
    }
    private class GetFaq extends AsyncTask<String,String,JSONObject>
    {
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String faqques,faqanswer,faqnub;

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
                        faqques=data.getString("question").toString();
                        faqanswer=data.getString("answer").toString();
                        faqnub=data.getString("id").toString();

                        SharedPreferences sharedPreferences = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putString("feedtitle", feedtitle);
                        editor.putString("faqques",faqques);
                        editor.putString("faqanswer",faqanswer);
                        editor.commit();


                        //Log.d("--feedtitle",feedtitle);
                        Log.d("--faqques",faqques);
                        Log.d("--faqanswer",faqanswer);



                        faqConstructors.add(new FaqConstructor(faqnub,faqques,faqanswer));

                        final FaqAdapter adapter = new FaqAdapter(FaqActivity.this, faqConstructors);
                        ll_faq.setAdapter(adapter);


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
