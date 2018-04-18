package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.R;
import com.example.hp.echoapp.constructors.UserProductConstructor;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by HP on 10/17/2017.
 */

public class UserProductAdapter extends BaseAdapter {
    Context userProductGridView;
    ArrayList<UserProductConstructor> userProductConstructors;
    private ImageView iv_productimage;
    private TextView tv_price,tv_title,bt_productbuy;

    LayoutInflater mInflater;

    public UserProductAdapter(Context userProductGridView,ArrayList<UserProductConstructor> userProductConstructors){
        this.userProductGridView=userProductGridView;
        this.userProductConstructors= userProductConstructors;
    }

    @Override
    public int getCount() {
        return userProductConstructors.size();
    }

    @Override
    public Object getItem(int i) {
        return userProductConstructors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
            View gridView=convertView;

        if(convertView==null)
        {
            mInflater=(LayoutInflater)userProductGridView.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = mInflater.inflate(R.layout.singlegridviewproduct, null); // inflate the layout
            tv_title=(TextView)gridView.findViewById(R.id.tv_title);
            tv_price=(TextView)gridView.findViewById(R.id.tv_price);
            bt_productbuy=(TextView) gridView.findViewById(R.id.bt_productbuy);
            iv_productimage=(ImageView)gridView.findViewById(R.id.iv_productimage);
            tv_title.setText(userProductConstructors.get(i).getProducttitle());
            tv_price.setText(""+userProductConstructors.get(i).getProductprice());
            bt_productbuy.setText(userProductConstructors.get(i).getProductbuy());
            Picasso.with(userProductGridView).load(userProductConstructors.get(i).getProductimage()).into(iv_productimage);
        }
        return gridView;
    }
}
