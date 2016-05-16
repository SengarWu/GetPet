package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.entrust;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-16.
 */
public class MyEntrustListAdapter extends BaseListAdapter<entrust> {
    public MyEntrustListAdapter(Context context, List<entrust> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.myentrustlist_item, null);
        }
        final entrust contract=getList().get(position);
        TextView title=(TextView) ViewHolder.get(convertView,R.id.title);
        TextView pet=(TextView) ViewHolder.get(convertView,R.id.pet);
        TextView applynum=(TextView) ViewHolder.get(convertView,R.id.applynum);
        TextView award=(TextView) ViewHolder.get(convertView,R.id.award);
        TextView publishTime=(TextView) ViewHolder.get(convertView,R.id.publishTime);
        if(contract.getTitle()!=null) {
            title.setText(contract.getTitle());
        }
        if(contract.getPet()!=null) {
            title.setText(contract.getPet().name);
        }
        title.setText(String.valueOf(contract.getAward()));
        if(contract.getDate()!=null) {
            title.setText(String.valueOf(contract.getDate()));
        }
        return convertView;
    }
}
