package com.example.administrator.getpet.ui.Home.PetCircle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.BaseListAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ViewHolder;
import com.example.administrator.getpet.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-31.
 */
public class myAnswerAdapter extends BaseListAdapter<postReply> {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private TextView username;
    private TextView time;
    private TextView content;
    private ImageView select;
    public myAnswerAdapter(Context context, List<postReply> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_answerwithselect, null);
        }
        final postReply contract=getList().get(position);
        RoundImageView user_head= ViewHolder.get(convertView,R.id.user_head);
        username=ViewHolder.get(convertView,R.id.username);
        time=ViewHolder.get(convertView,R.id.publishTime);
        content=ViewHolder.get(convertView,R.id.content);
        select=ViewHolder.get(convertView,R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(contract.getUsers().getPhoto()!="")
        {
            imageLoader.displayImage(contract.getUsers().getPhoto(), user_head, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
        username.setText(contract.getUsers().getNickName());
        time.setText(contract.getTime().toString());
        content.setText(contract.getMessage());
        return null;
    }
}
