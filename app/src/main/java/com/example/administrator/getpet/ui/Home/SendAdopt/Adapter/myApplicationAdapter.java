package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.ui.Home.SendAdopt.Application;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-23.
 */
public class myApplicationAdapter extends BaseListAdapter<applyApplication> {
    public myApplicationAdapter(Context context, List<applyApplication> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.myentrustlist_item, null);
        }
        final applyApplication contract=getList().get(position);
        TextView title=(TextView) ViewHolder.get(convertView,R.id.title);
        TextView award=(TextView) ViewHolder.get(convertView,R.id.award);
        ImageView type=(ImageView)ViewHolder.get(convertView,R.id.type);
        TextView time=(TextView)ViewHolder.get(convertView,R.id.time);
        award.setText(contract.getEntrust().getTitle());
        if(contract.getEntrust().getAward()!=0) {
            title.setText(contract.getEntrust().getTitle());
        }
        if(contract.getEntrust().getPet().getCategory().getName()!=null) {
            switch (contract.getEntrust().getPet().getCategory().getName()){
                case "猫":
                    type.setImageResource(R.mipmap.cat_type);
                    break;
                case "狗":
                    type.setImageResource(R.mipmap.type_dog);
                    break;
                case "鱼":
                    type.setImageResource(R.mipmap.type_fish);
                    break;
                case "其他":
                    type.setImageResource(R.mipmap.type_other);
                    break;
            }

        }
        if(contract.getApplyDate()!=null) {
            time.setText(String.valueOf(contract.getApplyDate()));
        }
        return convertView;
    }
}
