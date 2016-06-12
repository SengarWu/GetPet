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
 * Created by Koreleone on 2016-06-12.
 */
public class MyReplyPostAdapter extends BaseListAdapter<postReply> {

    public MyReplyPostAdapter(Context context, List<postReply> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_myreplypost_item, null);
        }
        final postReply contract=getList().get(position);
        RoundImageView user_head= ViewHolder.get(convertView,R.id.user_head);
        TextView username=ViewHolder.get(convertView,R.id.username);
        TextView time=ViewHolder.get(convertView,R.id.publishTime);
        TextView content_summary=ViewHolder.get(convertView,R.id.content_summary);
        TextView state=ViewHolder.get(convertView,R.id.state);
        TextView seeNum=ViewHolder.get(convertView,R.id.seeNum);
        TextView replyNum=ViewHolder.get(convertView,R.id.replyNum);
        TextView award=ViewHolder.get(convertView,R.id.award);
        TextView title=ViewHolder.get(convertView,R.id.title);
        TextView Reaplyresult=ViewHolder.get(convertView,R.id.Reaplyresult);
        if(contract.getResult().equals("好评回答")){
            Reaplyresult.setText(contract.getResult());
        }
        Reaplyresult.setText(contract.getResult());
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(contract.getUsers().getPhoto()),user_head,R.mipmap.home_pet_photp);
        title.setText(contract.getPost().getTitle());
        username.setText(contract.getPost().getUsers().getNickName());
        time.setText(TimeUtils.dateToString(contract.getPost().getDate(),TimeUtils.FORMAT_DATE));
        if(contract.getPost().getMes().length()>25) {
            content_summary.setText("    " + contract.getPost().getMes().substring(0, 22)+"...");
        }else{
            content_summary.setText("    " + contract.getPost().getMes());
        }
        state.setText("状态："+contract.getPost().getState());
        seeNum.setText("浏览："+String.valueOf(contract.getPost().getSeeNum()));
        replyNum.setText("回帖："+String.valueOf(contract.getPost().getNum()));
        award.setText(String.valueOf(contract.getPost().getIntergen()));
        return convertView;
    }
}