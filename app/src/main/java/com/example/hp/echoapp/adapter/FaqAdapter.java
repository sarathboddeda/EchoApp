package com.example.hp.echoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.echoapp.constructors.FaqConstructor;
import com.example.hp.echoapp.R;

import java.util.ArrayList;

/**
 * Created by HP on 2/3/2018.
 */

public class FaqAdapter extends BaseAdapter {
    Context faqActivity;
    ArrayList<FaqConstructor> faqConstructors;
    LayoutInflater mInflater;
    public FaqAdapter(Context faqActivity,ArrayList<FaqConstructor> faqConstructors)
    {
        this.faqActivity=faqActivity;
        this.faqConstructors=faqConstructors;
    }
    @Override
    public int getCount() {
        return faqConstructors.size();
    }

    @Override
    public Object getItem(int i) {
        return faqConstructors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View ConvertView, ViewGroup viewGroup) {
        final FaqAdapter.ViewHolder mHolder;

        if(ConvertView==null)
        {
            mInflater = (LayoutInflater)faqActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConvertView=mInflater.inflate(R.layout.faq_single,null);
            mHolder = new FaqAdapter.ViewHolder();
            mHolder.tv_faqquestion=(TextView)ConvertView.findViewById(R.id.tv_faqquestion);
            mHolder.tv_faqanswerer=(TextView)ConvertView.findViewById(R.id.tv_faqanswerer);
            mHolder.tv_faqnum=(TextView)ConvertView.findViewById(R.id.tv_faqnum);

            int j;


            final String tv_faqquestion,tv_faqanswerer,tv_faqnum;
            j=i+1;
            tv_faqnum= String.valueOf(j);

            tv_faqquestion=faqConstructors.get(i).getFaqquestion();
            tv_faqanswerer=faqConstructors.get(i).getFaqanswer();
            mHolder.tv_faqquestion.setText(tv_faqquestion);
            mHolder.tv_faqanswerer.setText(tv_faqanswerer);
            mHolder.tv_faqnum.setText(tv_faqnum);

            ConvertView.setTag(mHolder);
        }

        return ConvertView;
    }

    private class ViewHolder {
        private TextView tv_faqquestion;
        private TextView tv_faqanswerer;
        private TextView tv_faqnum;
    }
}
