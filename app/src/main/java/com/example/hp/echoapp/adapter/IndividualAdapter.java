package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.echoapp.IndividualConstructor;
import com.example.hp.echoapp.R;

import java.util.ArrayList;

/**
 * Created by HP on 11/17/2017.
 */

public class IndividualAdapter extends BaseAdapter {
    Context individualDetails;
    ArrayList<IndividualConstructor> individualConstructors;
    LayoutInflater mInflater;

    private TextView tv_indivtitle,tv_indivdate,tv_indivqty;
    public IndividualAdapter(Context individualDetails, ArrayList<IndividualConstructor> individualConstructors)
    {
        this.individualDetails=individualDetails;
        this.individualConstructors=individualConstructors;
    }
    @Override
    public int getCount() {
        return individualConstructors.size();
    }

    @Override
    public Object getItem(int i) {
        return individualConstructors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        if(ConvertView==null)
        {
            mInflater = (LayoutInflater)individualDetails.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView=mInflater.inflate(R.layout.individualorder_single,null);
            tv_indivtitle=(TextView)ConvertView.findViewById(R.id.tv_indivtitle);
            tv_indivdate=(TextView)ConvertView.findViewById(R.id.tv_indivdate);
            tv_indivqty=(TextView)ConvertView.findViewById(R.id.tv_indivqty);
            tv_indivtitle.setText(individualConstructors.get(i).getIndividualTitle());
            tv_indivdate.setText(individualConstructors.get(i).getIndividualDate());
            tv_indivqty.setText(individualConstructors.get(i).getIndividualQty());

        }
        return ConvertView;
    }
}
