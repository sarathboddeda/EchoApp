package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.AvailableConst;
import com.example.hp.echoapp.R;
import com.example.hp.echoapp.constructors.OrderedConst;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 11/16/2017.
 */

public class OrderedAdapter extends BaseAdapter {
    Context availableProducts;
    ArrayList<OrderedConst> availableConsts;

    LayoutInflater mInflater;


    public OrderedAdapter(Context availableProducts, ArrayList<OrderedConst> availableConsts)
    {
        this.availableProducts=availableProducts;
        this.availableConsts=availableConsts;
    }

    @Override
    public int getCount() {
        return availableConsts.size();
    }

    @Override
    public Object getItem(int i) {
        return availableConsts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup)
    {

        final OrderedAdapter.ViewHolder mHolder;

        if(ConvertView==null)
        {
            mInflater = (LayoutInflater)availableProducts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView=mInflater.inflate(R.layout.ordered_merchnant_single,null);
            mHolder = new OrderedAdapter.ViewHolder();
            mHolder.tv_myorder_title=(TextView)ConvertView.findViewById(R.id.tv_myorder_title);
            mHolder.tv_myorder_quantity=(TextView)ConvertView.findViewById(R.id.tv_myorder_quantity);
            mHolder.tv_cost=(TextView)ConvertView.findViewById(R.id.tv_cost);
            mHolder.iv_availimage=(ImageView)ConvertView.findViewById(R.id.iv_availimage);
            mHolder.tv_order=(TextView)ConvertView.findViewById(R.id.tv_order);
            mHolder.tv_orderName=(TextView)ConvertView.findViewById(R.id.tv_orderName);


            final String tv_myorder_title,tv_myorder_quantity,tv_cost,tv_proImage, tv_orderName;

            tv_myorder_title=availableConsts.get(i).getOrderedTitle();
            tv_myorder_quantity=availableConsts.get(i).getOrderedkilos();
            tv_cost=availableConsts.get(i).getOrderedcost();
            tv_orderName=availableConsts.get(i).getBuyer_name();
            tv_proImage=availableConsts.get(i).getOrderedImage();

            mHolder.tv_myorder_title.setText(tv_myorder_title);
            mHolder.tv_myorder_quantity.setText(tv_myorder_quantity);
            mHolder.tv_cost.setText(tv_cost);
            mHolder.tv_orderName.setText(tv_orderName);
            Picasso.with(availableProducts).load(tv_proImage).into(mHolder.iv_availimage);

            mHolder.tv_order.setText(tv_myorder_quantity);


            ConvertView.setTag(mHolder);


        }

        return ConvertView;



    }
    private class ViewHolder {
        private TextView tv_myorder_title;
        private TextView tv_cost;
        private TextView tv_order;
        private TextView tv_orderName;
        private TextView tv_myorder_quantity;
        private ImageView iv_availimage;

    }
}
