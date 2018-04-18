package com.example.hp.echoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 10/23/2017.
 */

public class LeftMenuAdapter extends BaseAdapter {
    Context context;
    private ArrayList<LeftMenuConst> leftMenuConsts;
    public LeftMenuAdapter(Context context,ArrayList<LeftMenuConst> leftMenuConsts)
    {
        this.context=context;
        this.leftMenuConsts=leftMenuConsts;
    }
    class ViewHolder {
        TextView txtTitle;

    }
    @Override
    public int getCount() {
        return leftMenuConsts.size();
    }

    @Override
    public Object getItem(int position) {
        return leftMenuConsts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LeftMenuAdapter.ViewHolder holder=null;
        LeftMenuConst leftMenuConst=(LeftMenuConst) getItem(position);
        LayoutInflater mInflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            convertView=mInflater.inflate(R.layout.leftmenu_single,null);
            holder=new LeftMenuAdapter.ViewHolder();
            holder.txtTitle=(TextView)convertView.findViewById(R.id.tv_menutitle);
            convertView.setTag(holder);
        }
        else
            holder =(LeftMenuAdapter.ViewHolder) convertView.getTag();
        holder.txtTitle.setText(leftMenuConst.getTitle());
        return convertView;
    }
}
