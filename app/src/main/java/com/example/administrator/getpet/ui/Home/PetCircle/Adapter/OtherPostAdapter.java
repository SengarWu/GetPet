package com.example.administrator.getpet.ui.Home.PetCircle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.BaseListAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ViewHolder;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.TimeUtils;
import com.example.administrator.getpet.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-30.
 */
public class OtherPostAdapter extends BaseListAdapter<post> {

    public OtherPostAdapter(Context context, List<post> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_otherpost_item, null);
        }
        final post contract=getList().get(position);
        RoundImageView user_head=ViewHolder.get(convertView,R.id.user_head);
        TextView username=ViewHolder.get(convertView,R.id.username);
        TextView time=ViewHolder.get(convertView,R.id.publishTime);
        TextView content_summary=ViewHolder.get(convertView,R.id.content_summary);
        TextView state=ViewHolder.get(convertView,R.id.state);
        TextView seeNum=ViewHolder.get(convertView,R.id.seeNum);
        TextView replyNum=ViewHolder.get(convertView,R.id.replyNum);
        TextView award=ViewHolder.get(convertView,R.id.award);
        TextView title=ViewHolder.get(convertView,R.id.title);
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(contract.getUsers().getPhoto()),user_head,R.mipmap.home_pet_photp);
        title.setText(contract.getTitle());
        username.setText(contract.getUsers().getNickName());
        time.setText(TimeUtils.dateToString(contract.getDate(),TimeUtils.FORMAT_DATE));
        if(contract.getMes().length()>25) {
            content_summary.setText("    " + contract.getMes().substring(0, 22)+"...");
        }else{
            content_summary.setText("    " + contract.getMes());
        }
        state.setText("状态："+contract.getState());
        seeNum.setText("浏览："+String.valueOf(contract.getSeeNum()));
        replyNum.setText("回帖："+String.valueOf(contract.getNum()));
        award.setText(String.valueOf(contract.getIntergen()));
        return convertView;
    }
}
