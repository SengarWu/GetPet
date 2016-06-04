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
import com.example.administrator.getpet.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-30.
 */
public class OtherPostAdapter extends BaseListAdapter<post> {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
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
        TextView award=ViewHolder.get(convertView,R.id.award);
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
        time.setText(contract.getDate().getYear()+"-"+contract.getDate().getMonth()+"-"+contract.getDate().getDay());
        content_summary.setText(contract.getMes().substring(0,25));
        award.setText(contract.getIntergen());
        return convertView;
    }
}
