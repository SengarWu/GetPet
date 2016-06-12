package com.example.administrator.getpet.ui.findPet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.attention;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by caolin on 2016/6/3.
 */
public class adaptPublish extends RecyclerView.Adapter<adaptPublish.publishViewHolder> {

    public interface OnItemClickListener {
        void onUserItemClicked(lose attentionModel);
    }

    private List<lose> attentionsCollection;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public adaptPublish(Context context) {
        this.mContext=context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.attentionsCollection = Collections.emptyList();
    }

    @Override public int getItemCount() {
        return (this.attentionsCollection != null) ? this.attentionsCollection.size() : 0;
    }

    @Override
    public void onBindViewHolder(publishViewHolder holder, int position) {
        final lose loseModel = this.attentionsCollection.get(position);
        holder.loseTime.setText(TimeUtils.dateToString(loseModel.loseTime,TimeUtils.FORMAT_DATE));
        holder.loser.setText(loseModel.users.nickName);
        holder.msg.setText(loseModel.loseMessage);
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(loseModel.pet.photo),holder.petPhoto, R.mipmap.home_pet_photp);
        Log.d("caol",HttpPostUtil.getImagUrl(loseModel.pet.photo));
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserItemClicked(loseModel);
                }
            }
        });
    }


    @Override
    public adaptPublish.publishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.item_seek_scan, parent, false);
        return new publishViewHolder(view);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setLosersCollection(List<lose> attentionsCollection) {
        this.validateUsersCollection(attentionsCollection);
        this.attentionsCollection = attentionsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateUsersCollection(Collection<lose> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class publishViewHolder extends RecyclerView.ViewHolder {
        EditText loseTime;
        EditText loser;
        EditText msg;

        ImageView petPhoto;
        View myView;

        public publishViewHolder(View itemView) {
            super(itemView);
            myView=itemView;

            loseTime=(EditText)(itemView.findViewById(R.id.loseTime));
            loser=(EditText)(itemView.findViewById(R.id.loser));
            msg=(EditText)(itemView.findViewById(R.id.msg));
            petPhoto=(ImageView)(itemView.findViewById(R.id.petPhoto));
        }
    }
}

