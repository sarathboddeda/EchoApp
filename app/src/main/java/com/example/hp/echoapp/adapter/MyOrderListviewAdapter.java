package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.MyOrderConst;
import com.example.hp.echoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 10/23/2017.
 */

public class MyOrderListviewAdapter extends BaseAdapter {

    Context myOrdersActivity;
    ArrayList<MyOrderConst> myOrderConsts;
    LayoutInflater mInflater;

    public MyOrderListviewAdapter(Context myOrdersActivity, ArrayList<MyOrderConst> myOrderConsts) {
        this.myOrdersActivity = myOrdersActivity;
        this.myOrderConsts = myOrderConsts;

    }


    @Override
    public int getCount() {
        return myOrderConsts.size();
    }

    @Override
    public Object getItem(int i) {
        return myOrderConsts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        //final DeliveryAdapter.ViewHolder mHolder;
        final MyOrderListviewAdapter.ViewHolder mHolder;

        if (ConvertView == null) {

            mInflater = (LayoutInflater) myOrdersActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView = mInflater.inflate(R.layout.myorder_singlelist, null);
            mHolder = new MyOrderListviewAdapter.ViewHolder();
            mHolder.tv_myorders_title = (TextView) ConvertView.findViewById(R.id.tv_myorders_title);
            mHolder.tv_myorder_prize = (TextView) ConvertView.findViewById(R.id.tv_myorder_prize);
            mHolder.tv_myorder_delivery = (TextView) ConvertView.findViewById(R.id.tv_myorder_delivery);
            mHolder.iv_myorderproductimage = (ImageView) ConvertView.findViewById(R.id.iv_myorderproductimage);


            //tv_dlvarea=(TextView)ConvertView.findViewById(R.id.tv_dlvarea);

            String tv_myorders_title = myOrderConsts.get(i).getProductTitle();
            String tv_myorder_prize = myOrderConsts.get(i).getProductWeight();
            String tv_myorder_delivery = myOrderConsts.get(i).getProductDelivery();
            String iv_myorderproductimage = myOrderConsts.get(i).getProductImage();


            mHolder.tv_myorders_title.setText(tv_myorders_title);
            mHolder.tv_myorder_prize.setText(tv_myorder_prize);
            mHolder.tv_myorder_delivery.setText(tv_myorder_delivery);
            Picasso.with(myOrdersActivity).load(iv_myorderproductimage).into(mHolder.iv_myorderproductimage);

            ConvertView.setTag(mHolder);
            return ConvertView;

        } else {
            mHolder = (MyOrderListviewAdapter.ViewHolder) ConvertView.getTag();
            mHolder.tv_myorders_title = (TextView) ConvertView.findViewById(R.id.tv_myorders_title);
            mHolder.tv_myorder_prize = (TextView) ConvertView.findViewById(R.id.tv_myorder_prize);
            mHolder.tv_myorder_delivery = (TextView) ConvertView.findViewById(R.id.tv_myorder_delivery);

            // mHolder.contact_check = (ImageView) ConvertView.findViewById(R.id.contact_check);
            String tv_myorders_title = myOrderConsts.get(i).getProductTitle();
            String tv_myorder_prize = myOrderConsts.get(i).getProductWeight();
            String tv_myorder_delivery = myOrderConsts.get(i).getProductDelivery();
            String iv_myorderproductimage = myOrderConsts.get(i).getProductImage();

            mHolder.tv_myorders_title.setText(tv_myorders_title);
            mHolder.tv_myorder_prize.setText(tv_myorder_prize);
            mHolder.tv_myorder_delivery.setText(tv_myorder_delivery);
            Picasso.with(myOrdersActivity).load(iv_myorderproductimage).into(mHolder.iv_myorderproductimage);
            return ConvertView;

        }

    }

    private class ViewHolder {
        private TextView tv_myorders_title;
        private TextView tv_myorder_prize;
        private TextView tv_myorder_delivery;
        private ImageView iv_myorderproductimage;

    }
}
