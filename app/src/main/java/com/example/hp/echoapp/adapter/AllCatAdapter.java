package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.AllcategoryConst;
import com.example.hp.echoapp.R;

import java.util.ArrayList;

/**
 * Created by HP on 1/10/2018.
 */

public class AllCatAdapter extends BaseAdapter {
    Context getAllcategory;
    ArrayList<AllcategoryConst> allcategoryConsts;
    LayoutInflater mInflater;

    public AllCatAdapter(Context getAllcategory, ArrayList<AllcategoryConst> allcategoryConsts) {
        this.getAllcategory = getAllcategory;
        this.allcategoryConsts = allcategoryConsts;
    }

    @Override
    public int getCount() {
        return allcategoryConsts.size();
    }

    @Override
    public Object getItem(int i) {
        return allcategoryConsts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        final AllCatAdapter.ViewHolder mHolder;

        if (ConvertView == null) {

            mInflater = (LayoutInflater) getAllcategory.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView = mInflater.inflate(R.layout.category_single,null);
            mHolder = new AllCatAdapter.ViewHolder();
            mHolder.tv_catname = (TextView) ConvertView.findViewById(R.id.tv_cat_name);




            //tv_dlvarea=(TextView)ConvertView.findViewById(R.id.tv_dlvarea);

            String tv_catname = allcategoryConsts.get(i).getCatname();



            mHolder.tv_catname.setText(tv_catname);

            ConvertView.setTag(mHolder);
            return ConvertView;

        } else {
            mHolder = (AllCatAdapter.ViewHolder) ConvertView.getTag();
            mHolder.tv_catname = (TextView) ConvertView.findViewById(R.id.tv_cat_name);

            // mHolder.contact_check = (ImageView) ConvertView.findViewById(R.id.contact_check);
            String tv_catname = allcategoryConsts.get(i).getCatname();


            mHolder.tv_catname.setText(tv_catname);




            return ConvertView;

        }
    }
    private class ViewHolder {
        private TextView tv_catname;


    }
}
