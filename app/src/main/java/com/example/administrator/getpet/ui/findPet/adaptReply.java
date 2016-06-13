package com.example.administrator.getpet.ui.findPet;

/**
 * Created by caolin on 2016/6/2.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.bean.reply;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class adaptReply extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

public interface OnItemClickListener {
    void onUserItemClicked(reply userModel);
}

    private String userId="";
    private String loseId="";
    private List<reply> losersCollection;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    //建立枚举 2个item 类型
    public enum item_type {
        loser,
        others
    }
    public adaptReply(Context context,String userId,String loseId) {
        this.userId=userId;
        this.loseId=loseId;
        this.mContext=context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.losersCollection = Collections.emptyList();
    }

    @Override public int getItemCount() {
        return (this.losersCollection != null) ? this.losersCollection.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == item_type.loser.ordinal()) {
            return new LoserViewHolder(layoutInflater.inflate(R.layout.item_seek_reply_loser, parent, false));
        } else {
            return new ReplyViewHolder(layoutInflater.inflate(R.layout.item_seek_reply_others, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final reply replyer = this.losersCollection.get(position);
        if (holder instanceof LoserViewHolder) {
            LoserViewHolder LoserView=(LoserViewHolder) holder;
            String replyTime=TimeUtils.getDescriptionTimeFromTimestamp(TimeUtils.dateToLong(replyer.replyTime));
            LoserView.time.setText(replyTime);
            LoserView.msg.setText(replyer.replyMessage);
            ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(replyer.users.photo),LoserView.photo,R.mipmap.home_pet_photp);
        } else if (holder instanceof ReplyViewHolder) {
            ReplyViewHolder LoserView=(ReplyViewHolder) holder;
            String replyTime=TimeUtils.getDescriptionTimeFromTimestamp(TimeUtils.dateToLong(replyer.replyTime));
            LoserView.time.setText(replyTime);
            LoserView.msg.setText(replyer.replyMessage);
            LoserView.publish.setText(replyer.users.nickName);
            ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(replyer.users.photo),LoserView.photo,R.mipmap.home_pet_photp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        final reply replyer = this.losersCollection.get(position);
        if(replyer.users.id.equals(userId)){
            return item_type.loser.ordinal();
        }else{
            return item_type.others.ordinal();
        }
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setLosersCollection(List<reply> losersCollection) {
        this.validateUsersCollection(losersCollection);
        this.losersCollection = losersCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateUsersCollection(Collection<reply> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView msg;
        TextView publish;

        ImageView photo;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            time=(TextView)(itemView.findViewById(R.id.time));
            msg=(TextView)(itemView.findViewById(R.id.msg));
            publish=(TextView)(itemView.findViewById(R.id.publish));
            photo=(ImageView)(itemView.findViewById(R.id.photo));
        }
    }
    static class LoserViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView msg;

        ImageView photo;

        public LoserViewHolder(View itemView) {
            super(itemView);
            time=(TextView)(itemView.findViewById(R.id.time));
            msg=(TextView)(itemView.findViewById(R.id.msg));
            photo=(ImageView)(itemView.findViewById(R.id.photo));
        }
    }
}
