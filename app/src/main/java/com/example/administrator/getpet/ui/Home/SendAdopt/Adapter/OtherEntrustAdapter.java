package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-21.
 */
public class OtherEntrustAdapter extends BaseListAdapter<entrust> {
    public OtherEntrustAdapter(Context context, List<entrust> items) {
        super(context, items);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_otherentrust, null);
        }
        final entrust contract=getList().get(position);
        TextView title=(TextView)ViewHolder.get(convertView,R.id.title);
        ImageView sex_icon=(ImageView)ViewHolder.get(convertView,R.id.sex_icon);
        TextView username=(TextView)ViewHolder.get(convertView,R.id.username);
        TextView award=(TextView)ViewHolder.get(convertView,R.id.award);
        ImageView pet_type=(ImageView)ViewHolder.get(convertView,R.id.type);
        TextView time=(TextView)ViewHolder.get(convertView,R.id.time);
        TextView city=(TextView)ViewHolder.get(convertView,R.id.city);
        switch (contract.getUsers().getSex()) {
            case "男":
                sex_icon.setImageResource(R.mipmap.boy);
                break;
            case "女":
                sex_icon.setImageResource(R.mipmap.girl);
                break;
            default:
                sex_icon.setImageResource(R.mipmap.notknow);
                break;
        }
        title.setText(contract.getTitle());
        username.setText(contract.getUsers().getNickName());
        award.setText(String.valueOf(contract.getAward()));
        switch (contract.getPet().getCategory().getName()){
            case "猫":
                pet_type.setImageResource(R.mipmap.cat_type);
                break;
            case "狗":
                pet_type.setImageResource(R.mipmap.type_dog);
                break;
            case "鱼":
                pet_type.setImageResource(R.mipmap.type_fish);
                break;
            case "其他":
                pet_type.setImageResource(R.mipmap.type_other);
                break;
        }
        city.setText(contract.getCity());
        time.setText(TimeUtils.dateToString(contract.getDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));
        return convertView;
    }
}
