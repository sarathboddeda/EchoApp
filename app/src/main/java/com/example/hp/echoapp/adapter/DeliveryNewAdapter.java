package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.DeliverynewCons;
import com.example.hp.echoapp.InertCheckBox;
import com.example.hp.echoapp.R;

import java.util.ArrayList;

/**
 * Created by HP on 1/26/2018.
 */

public class DeliveryNewAdapter extends BaseAdapter {
    Context deliveryCenter;
    ArrayList<DeliverynewCons> deliverynewCons;
    ArrayList<String> deladdress;
    LayoutInflater mInflater;
    InertCheckBox singleitemCheckBox;



    public DeliveryNewAdapter(Context deliveryCenter, ArrayList<DeliverynewCons> deliverynewCons) {
        this.deliveryCenter = deliveryCenter;
        this.deliverynewCons = deliverynewCons;

        mInflater = (LayoutInflater) deliveryCenter
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return deliverynewCons.size();
    }

    @Override
    public Object getItem(int i) {
        return deliverynewCons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {



        View view = ConvertView;


        if (view == null) {

            view = mInflater.inflate(R.layout.delivery_singlenew, viewGroup, false);
        }



        ((TextView) view.findViewById(R.id.tv_delnew)).setText(deliverynewCons.get(i).getAddress());
            singleitemCheckBox=(InertCheckBox) view.findViewById(R.id.singleitemCheckBox);


            //tv_dlvarea=(TextView)ConvertView.findViewById(R.id.tv_dlvarea);
        singleitemCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<deliverynewCons.size();i++) {
                    //Toast.makeText(this, "==data val"+data.get(i).toString(), Toast.LENGTH_SHORT).show();
                    Log.d("==", deliverynewCons.get(i).getAddress());
                }
            }
        });


        return view;

        }

}


