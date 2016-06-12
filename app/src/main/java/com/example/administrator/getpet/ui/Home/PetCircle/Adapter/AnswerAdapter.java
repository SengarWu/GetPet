package com.example.administrator.getpet.ui.Home.PetCircle.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.BaseListAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ViewHolder;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.TimeUtils;
import com.example.administrator.getpet.view.RoundImageView;
import java.util.List;

/**
 * Created by Koreleone on 2016-05-30.
 */
public class AnswerAdapter extends BaseListAdapter<postReply>{
    public AnswerAdapter(Context context, List<postReply> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_answer_item, null);
        }
        final postReply contract=getList().get(position);
        RoundImageView user_head= ViewHolder.get(convertView,R.id.user_head);
        TextView username=ViewHolder.get(convertView,R.id.username);
        TextView time=ViewHolder.get(convertView,R.id.time);
        TextView content=ViewHolder.get(convertView,R.id.content);
        TextView result=ViewHolder.get(convertView,R.id.result);

        username.setText(contract.getUsers().getNickName());
        time.setText(TimeUtils.dateToString(contract.getTime(),TimeUtils.FORMAT_DATE)+":");
        content.setText(contract.getMessage());
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(contract.getUsers().getPhoto()),user_head,R.mipmap.home_pet_photp);
        if(contract.getResult().equals("好评回答")){
            result.setText("好评回答");
        }
        return convertView;
    }
}
