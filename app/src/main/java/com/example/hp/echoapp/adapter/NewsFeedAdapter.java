package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.NewsFeedConst;
import com.example.hp.echoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 2/3/2018.
 */

public class NewsFeedAdapter extends BaseAdapter {
    Context newsFeedActivity;
    ArrayList<NewsFeedConst> newsFeedConsts;
    LayoutInflater mInflater;
    public NewsFeedAdapter(Context newsFeedActivity, ArrayList<NewsFeedConst> newsFeedConsts)
    {
        this.newsFeedActivity=newsFeedActivity;
        this.newsFeedConsts=newsFeedConsts;
    }


    @Override
    public int getCount() {
        return newsFeedConsts.size();
    }

    @Override
    public Object getItem(int i) {
        return newsFeedConsts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        final NewsFeedAdapter.ViewHolder mHolder;

        if(ConvertView==null)
        {
            mInflater = (LayoutInflater)newsFeedActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView=mInflater.inflate(R.layout.newsfeed_single,null);
            mHolder = new NewsFeedAdapter.ViewHolder();
            mHolder.tv_srcnew=(TextView)ConvertView.findViewById(R.id.tv_srcnew);
            mHolder.iv_srcnews=(ImageView)ConvertView.findViewById(R.id.iv_srcnews);

            final String tv_srcnew,iv_srcnews;

            tv_srcnew=newsFeedConsts.get(i).getFeedtitle();
            iv_srcnews=newsFeedConsts.get(i).getFeedimage();
            mHolder.tv_srcnew.setText(tv_srcnew);
            Picasso.with(newsFeedActivity).load(iv_srcnews).into(mHolder.iv_srcnews);
            ConvertView.setTag(mHolder);
        }

        return ConvertView;
    }
    private class ViewHolder {
        private TextView tv_srcnew;
        private ImageView iv_srcnews;
    }
}
