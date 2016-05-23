package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.bean.entrust;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-23.
 */
public class ApplicationAdapter extends BaseListAdapter<applyApplication> {
    public ApplicationAdapter(Context context, List<applyApplication> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_applicationitem, null);
        }
        final applyApplication contract=getList().get(position);
        ImageView sexIcon=(ImageView)ViewHolder.get(convertView,R.id.usersex);
        TextView reputation=(TextView) ViewHolder.get(convertView,R.id.reputation);
        TextView details=(TextView) ViewHolder.get(convertView,R.id.details);
        TextView Subtime=(TextView) ViewHolder.get(convertView,R.id.Subtime);
        ImageView state=(ImageView)ViewHolder.get(convertView,R.id.state);
        Button select=(Button)ViewHolder.get(convertView,R.id.select);
        return convertView;
    }
}
