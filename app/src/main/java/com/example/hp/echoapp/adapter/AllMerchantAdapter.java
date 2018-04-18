package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.AllMerchantConst;
import com.example.hp.echoapp.R;

import java.util.ArrayList;

/**
 * Created by HP on 1/8/2018.
 */

public class AllMerchantAdapter extends BaseAdapter {
    Context getallmerchant;
    ArrayList<AllMerchantConst> allMerchantConsts;
    LayoutInflater mInflater;

    public AllMerchantAdapter(Context getallmerchant, ArrayList<AllMerchantConst> allMerchantConsts) {
        this.getallmerchant = getallmerchant;
        this.allMerchantConsts = allMerchantConsts;

    }

    @Override
    public int getCount() {
        return allMerchantConsts.size();
    }

    @Override
    public Object getItem(int i) {
        return allMerchantConsts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        //final DeliveryAdapter.ViewHolder mHolder;
        final AllMerchantAdapter.ViewHolder mHolder;

        if (ConvertView == null) {

            mInflater = (LayoutInflater) getallmerchant.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView = mInflater.inflate(R.layout.allmerchant_single,null);
            mHolder = new AllMerchantAdapter.ViewHolder();
            mHolder.tv_merchtname = (TextView) ConvertView.findViewById(R.id.tv_merchtname);
            mHolder.tv_merchtnumber = (TextView) ConvertView.findViewById(R.id.tv_merchtnumber);



            //tv_dlvarea=(TextView)ConvertView.findViewById(R.id.tv_dlvarea);

            String tv_merchtname = allMerchantConsts.get(i).getMerchantname();
            String center_doorno = allMerchantConsts.get(i).getMerchantnumber();


            mHolder.tv_merchtname.setText(tv_merchtname);
            mHolder.tv_merchtnumber.setText(center_doorno);

            ConvertView.setTag(mHolder);
            return ConvertView;

        } else {
            mHolder = (AllMerchantAdapter.ViewHolder) ConvertView.getTag();
            mHolder.tv_merchtname = (TextView) ConvertView.findViewById(R.id.tv_merchtname);
            mHolder.tv_merchtnumber = (TextView) ConvertView.findViewById(R.id.tv_merchtnumber);

            // mHolder.contact_check = (ImageView) ConvertView.findViewById(R.id.contact_check);
            String tv_merchtname = allMerchantConsts.get(i).getMerchantname();
            String tv_merchtnumber = allMerchantConsts.get(i).getMerchantnumber();

            mHolder.tv_merchtname.setText(tv_merchtname);
            mHolder.tv_merchtnumber.setText(tv_merchtnumber);



            return ConvertView;

        }
    }
    private class ViewHolder {
        private TextView tv_merchtname;
        private TextView tv_merchtnumber;

    }
}
