package com.example.administrator.getpet.ui.findPet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by caolin on 2016/6/1.
 */
public class adaptScan extends RecyclerView.Adapter<adaptScan.ScanViewHolder> {

    public interface OnItemClickListener {
        void onUserItemClicked(lose userModel);
    }

    private List<lose> losersCollection;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private  Context mContext;

    public adaptScan(Context context) {
        this.mContext=context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.losersCollection = Collections.emptyList();
    }

    @Override public int getItemCount() {
        return (this.losersCollection != null) ? this.losersCollection.size() : 0;
    }

    @Override public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.item_seek_scan, parent, false);
        return new ScanViewHolder(view);
    }

    @Override public void onBindViewHolder(ScanViewHolder holder, final int position) {
        final lose loserModel = this.losersCollection.get(position);
        holder.loseTime.setText(TimeUtils.dateToString(loserModel.loseTime,TimeUtils.FORMAT_DATE));
        holder.loser.setText(loserModel.users.nickName);
        holder.msg.setText(loserModel.loseMessage);
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(loserModel.pet.photo),holder.petPhoto,R.mipmap.home_pet_photp);
        Log.d("caol",HttpPostUtil.getImagUrl(loserModel.pet.photo));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (adaptScan.this.onItemClickListener != null) {
                    adaptScan.this.onItemClickListener.onUserItemClicked(loserModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setLosersCollection(List<lose> losersCollection) {
        this.validateUsersCollection(losersCollection);
        this.losersCollection = losersCollection;
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

    static class ScanViewHolder extends RecyclerView.ViewHolder {
        EditText loseTime;
        EditText loser;
        EditText msg;

        ImageView petPhoto;

        public ScanViewHolder(View itemView) {
            super(itemView);
            loseTime=(EditText)(itemView.findViewById(R.id.loseTime));
            loser=(EditText)(itemView.findViewById(R.id.loser));
            msg=(EditText)(itemView.findViewById(R.id.msg));
            petPhoto=(ImageView)(itemView.findViewById(R.id.petPhoto));
        }
    }
}
