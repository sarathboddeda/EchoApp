package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.echoapp.EditProduct;
import com.example.hp.echoapp.constructors.AvailableConst;
import com.example.hp.echoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 11/15/2017.
 */

public class AvailableAdapter extends BaseAdapter{
    Context availableProducts;
    ArrayList<AvailableConst> availableConsts;
    //List<AvailableConst> availableConstList=new ArrayList<AvailableConst>();
    LayoutInflater mInflater;
    private ImageView iv_availimage;
    private TextView tv_myorder_title,tv_myorder_prize,tv_cost,tv_dlvarea,tv_etaarea,tv_avail,tv_order,tv_bal;
    public AvailableAdapter(Context availableProducts, ArrayList<AvailableConst> availableConsts)
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
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {

        final ViewHolder mHolder;

        if(ConvertView==null)
        {
            mInflater = (LayoutInflater)availableProducts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView=mInflater.inflate(R.layout.total_single,null);
            mHolder = new ViewHolder();
            mHolder.tv_myorder_title=(TextView)ConvertView.findViewById(R.id.tv_myorder_title);
            mHolder.tv_balance=(TextView)ConvertView.findViewById(R.id.tv_bal);
            mHolder.tv_myorder_prize=(TextView)ConvertView.findViewById(R.id.tv_myorder_prize);
            mHolder.tv_status=(TextView)ConvertView.findViewById(R.id.tv_status);
            mHolder.tv_cost=(TextView)ConvertView.findViewById(R.id.tv_cost);
            mHolder.iv_availimage=(ImageView)ConvertView.findViewById(R.id.iv_availimage);
            mHolder.iv_proedit=(ImageView)ConvertView.findViewById(R.id.iv_proedit);
            mHolder.iv_proedelt=(ImageView)ConvertView.findViewById(R.id.iv_proedelt);
            mHolder.tv_avail=(TextView)ConvertView.findViewById(R.id.tv_avail);
            mHolder.tv_order=(TextView)ConvertView.findViewById(R.id.tv_order);

            final String tv_myorder_title,tv_myorder_prize,tv_cost,tv_avail,tv_order,tv_proImage,tv_balance,tv_status,
                    merchant_id,product_id;

            tv_myorder_title=availableConsts.get(i).getAvailableTitle();
            tv_myorder_prize=availableConsts.get(i).getAvailablekilos();
            tv_cost=availableConsts.get(i).getAvailablecost();
            tv_avail=availableConsts.get(i).getAvailablepro();
            tv_order=availableConsts.get(i).getAvailableorder();
            tv_proImage=availableConsts.get(i).getAvailableImage();
            tv_balance=availableConsts.get(i).getBalanceproducts();
            tv_status=availableConsts.get(i).getPro_status();
            merchant_id=availableConsts.get(i).getMerchant_id();
            product_id=availableConsts.get(i).getProduct_id();
            mHolder.tv_myorder_title.setText(tv_myorder_title);
            mHolder.tv_balance.setText(tv_balance);
            mHolder.tv_myorder_prize.setText(tv_myorder_prize);
            mHolder.tv_cost.setText(tv_cost);
            mHolder.tv_status.setText(tv_status);
            Picasso.with(availableProducts).load(tv_proImage).into(mHolder.iv_availimage);
            mHolder.tv_avail.setText(tv_avail);
            mHolder.tv_order.setText(tv_order);
            String tv_bal=availableConsts.get(i).getAvailablebal();
            mHolder.iv_proedit.setVisibility(View.GONE);
            mHolder.iv_proedelt.setVisibility(View.GONE);
            mHolder.iv_proedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent edit=new Intent(availableProducts, EditProduct.class);
                    edit.putExtra("proid",product_id);
                    availableProducts.startActivity(edit);
                }
            });

            mHolder.bt_deledit=(Button)ConvertView.findViewById(R.id.bt_deledit);
            ConvertView.setTag(mHolder);

            return ConvertView;

        }
        else {
            mHolder=(ViewHolder)ConvertView.getTag();
            mHolder.tv_myorder_title=(TextView)ConvertView.findViewById(R.id.tv_myorder_title);
            mHolder.tv_myorder_prize=(TextView)ConvertView.findViewById(R.id.tv_myorder_prize);
            mHolder.tv_cost=(TextView)ConvertView.findViewById(R.id.tv_cost);
            mHolder.iv_availimage=(ImageView)ConvertView.findViewById(R.id.iv_availimage);
            mHolder.tv_balance=(TextView)ConvertView.findViewById(R.id.tv_bal);
            mHolder.tv_avail=(TextView)ConvertView.findViewById(R.id.tv_avail);
            mHolder.tv_order=(TextView)ConvertView.findViewById(R.id.tv_order);
            mHolder.tv_status=(TextView)ConvertView.findViewById(R.id.tv_status);
            final String tv_myorder_title,tv_myorder_prize,tv_cost,tv_avail,tv_order,tv_status,tv_proImage,prodescription,merchant_id,product_id;

            tv_myorder_title=availableConsts.get(i).getAvailableTitle();
            tv_myorder_prize=availableConsts.get(i).getAvailablekilos();
            tv_cost=availableConsts.get(i).getAvailablecost();
            tv_avail=availableConsts.get(i).getAvailablepro();
            tv_order=availableConsts.get(i).getAvailableorder();
            tv_proImage=availableConsts.get(i).getAvailableImage();
            tv_status=availableConsts.get(i).getPro_status();
            merchant_id=availableConsts.get(i).getMerchant_id();
            product_id=availableConsts.get(i).getProduct_id();
            prodescription=availableConsts.get(i).getAvailabledesc();
            mHolder.tv_myorder_title.setText(tv_myorder_title);
            mHolder.tv_myorder_prize.setText(tv_myorder_prize);
            mHolder.tv_cost.setText(tv_cost);
            mHolder.tv_status.setText(tv_status);
            Picasso.with(availableProducts).load(tv_proImage).into(mHolder.iv_availimage);

            mHolder.tv_avail.setText(tv_avail);
            mHolder.tv_order.setText(tv_order);
            String tv_bal=availableConsts.get(i).getAvailablebal();
            //mHolder.bt_availedit=(Button)ConvertView.findViewById(R.id.bt_availedit);
            mHolder.bt_deledit=(Button)ConvertView.findViewById(R.id.bt_deledit);
            //mHolder.tv_bal.setText(tv_bal);
            /*mHolder.bt_availedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent appInfo = new Intent( availableProducts, UpdateProduct.class);//SharedFriends
                    Log.d("== tv_myorder_title",tv_myorder_title);
                    Log.d("== tv_cost",tv_cost);
                    Log.d("== tv_myorder_prize",tv_avail);
                    Log.d("== prodescription",prodescription);
                    Log.d("== product_id",product_id);
                    Log.d("== merchant_id",merchant_id);
                    appInfo.putExtra("title",tv_myorder_title);
                    appInfo.putExtra("tv_cost",tv_cost);
                    appInfo.putExtra("tv_avail",tv_avail);
                    appInfo.putExtra("tv_order",tv_order);
                    appInfo.putExtra("tv_proImage",tv_proImage);
                    appInfo.putExtra("prodesc",prodescription);
                    appInfo.putExtra("product_id",product_id);
                    appInfo.putExtra("merchant_id",merchant_id);
                    availableProducts.startActivity(appInfo);

                }
            });*/
            return ConvertView;
        }

        //return ConvertView;

    }
    private class ViewHolder {
        private TextView tv_myorder_title;
        private TextView tv_cost;
        private TextView tv_etaarea;
        private TextView tv_avail;
        private TextView tv_balance;
        private TextView tv_order;
        private TextView tv_myorder_prize;
        private TextView tv_status;
        private ImageView iv_availimage;
        private ImageView iv_proedit;
        private ImageView iv_proedelt;
        private Button bt_availedit;
        private Button bt_deledit;

    }

}
