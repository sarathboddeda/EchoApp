package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.DeliverConst;
import com.example.hp.echoapp.R;

import java.util.ArrayList;

/**
 * Created by HP on 12/8/2017.
 */

public class DeliveryAdapter extends BaseAdapter {
    Context deliveryCenter;
    ArrayList<DeliverConst> deliverConsts;




    //List<AvailableConst> availableConstList=new ArrayList<AvailableConst>();
    LayoutInflater mInflater;
    //private TextView tv_delname, tv_deldoorno, tv_delpinno, tv_delemail, tv_delphone, tv_delcity, tv_delstate;
    //private ImageView contact_check;



    public DeliveryAdapter(Context deliveryCenter, ArrayList<DeliverConst> deliverConsts) {
        this.deliveryCenter = deliveryCenter;
        this.deliverConsts = deliverConsts;

    }

    @Override
    public int getCount() {
        return deliverConsts.size();
    }

    @Override
    public Object getItem(int i) {
        return deliverConsts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        //final DeliveryAdapter.ViewHolder mHolder;
        final ViewHolder mHolder;

        if (ConvertView == null) {

            //mHolder = new ViewHolder();

            mInflater = (LayoutInflater) deliveryCenter.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView = mInflater.inflate(R.layout.deliver_single,null);
            mHolder = new ViewHolder();
            mHolder.tv_delname = (TextView) ConvertView.findViewById(R.id.tv_delname);
            mHolder.tv_deldoorno = (TextView) ConvertView.findViewById(R.id.tv_deldoorno);
            mHolder.tv_delpinno = (TextView) ConvertView.findViewById(R.id.tv_delpinno);
            mHolder.tv_delemail = (TextView) ConvertView.findViewById(R.id.tv_delemail);
            mHolder.tv_delphone = (TextView) ConvertView.findViewById(R.id.tv_delphone);
            mHolder.tv_delcity = (TextView) ConvertView.findViewById(R.id.tv_delcity);
            mHolder.tv_delstate = (TextView) ConvertView.findViewById(R.id.tv_delstate);
            //mHolder.contact_check = (ImageView) ConvertView.findViewById(R.id.contact_check);



            //tv_dlvarea=(TextView)ConvertView.findViewById(R.id.tv_dlvarea);

            String center_name = deliverConsts.get(i).getFullname();
            String center_doorno = deliverConsts.get(i).getAddress();
            String center_pinno = deliverConsts.get(i).getZipcode();
            String center_email = deliverConsts.get(i).getDelemail();
            String center_phone = deliverConsts.get(i).getMobile();
            String center_city = deliverConsts.get(i).getCity();
            String center_state = deliverConsts.get(i).getState();


            mHolder.tv_delname.setText(center_name);
            mHolder.tv_deldoorno.setText(center_doorno);
            mHolder.tv_delpinno.setText(center_pinno);
            mHolder.tv_delcity.setText(center_city);
            mHolder.tv_delemail.setText(center_email);
            mHolder.tv_delphone.setText(center_phone);
            mHolder.tv_delstate.setText(center_state);
            ConvertView.setTag(mHolder);
            return ConvertView;

        } else {
            mHolder = (ViewHolder) ConvertView.getTag();
            mHolder.tv_delname = (TextView) ConvertView.findViewById(R.id.tv_delname);
            mHolder.tv_deldoorno = (TextView) ConvertView.findViewById(R.id.tv_deldoorno);
            mHolder.tv_delpinno = (TextView) ConvertView.findViewById(R.id.tv_delpinno);
            mHolder.tv_delemail = (TextView) ConvertView.findViewById(R.id.tv_delemail);
            mHolder.tv_delphone = (TextView) ConvertView.findViewById(R.id.tv_delphone);
            mHolder.tv_delcity = (TextView) ConvertView.findViewById(R.id.tv_delcity);
            mHolder.tv_delstate = (TextView) ConvertView.findViewById(R.id.tv_delstate);
           // mHolder.contact_check = (ImageView) ConvertView.findViewById(R.id.contact_check);
            String center_name = deliverConsts.get(i).getFullname();
            String center_doorno = deliverConsts.get(i).getAddress();
            String center_pinno = deliverConsts.get(i).getZipcode();
            String center_email = deliverConsts.get(i).getDelemail();
            String center_phone = deliverConsts.get(i).getMobile();
            String center_city = deliverConsts.get(i).getCity();
            String center_state = deliverConsts.get(i).getState();

            mHolder.tv_delname.setText(center_name);
            mHolder.tv_deldoorno.setText(center_doorno);
            mHolder.tv_delpinno.setText(center_pinno);
            mHolder.tv_delcity.setText(center_city);
            mHolder.tv_delemail.setText(center_email);
            mHolder.tv_delphone.setText(center_phone);
            mHolder.tv_delstate.setText(center_state);


            return ConvertView;

        }

    }


    private class ViewHolder {
        private TextView tv_delname;
        private TextView tv_deldoorno;
        private TextView tv_delpinno;
        private TextView tv_delemail;
        private TextView tv_delphone;
        private TextView tv_delcity;
        private TextView tv_delstate;
        //private ImageView contact_check;

    }
}
