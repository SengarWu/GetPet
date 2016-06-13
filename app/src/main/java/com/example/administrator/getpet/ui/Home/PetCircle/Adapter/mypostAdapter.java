package com.example.administrator.getpet.ui.Home.PetCircle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.BaseListAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ViewHolder;
import com.example.administrator.getpet.utils.TimeUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class mypostAdapter extends BaseListAdapter<post> {
    public mypostAdapter(Context context, List<post> items) {
        super(context, items);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_mypost_item, null);
        }
        final post contract=getList().get(position);
        TextView title= ViewHolder.get(convertView,R.id.title);
        TextView time=ViewHolder.get(convertView,R.id.publishTime);
        TextView content_summary=ViewHolder.get(convertView,R.id.mes);
        TextView award=ViewHolder.get(convertView,R.id.award);
        TextView state=ViewHolder.get(convertView,R.id.state);
        TextView seeNum=ViewHolder.get(convertView,R.id.seeNum);
        TextView replyNum=ViewHolder.get(convertView,R.id.replyNum);
        title.setText(contract.getTitle());
        time.setText(TimeUtils.dateToString(contract.getDate(),TimeUtils.FORMAT_DATE));
        content_summary.setText("      "+contract.getMes());
        state.setText(contract.getState());
        award.setText(String.valueOf(contract.getIntergen()));
        seeNum.setText("浏览："+String.valueOf(contract.getSeeNum()));
        replyNum.setText("回复："+String.valueOf(contract.getNum()));
        return convertView;
    }
}
