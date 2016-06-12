package com.example.administrator.getpet.ui.findPet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.active;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by caolin on 2016/6/11.
 */
public class activeScan extends BaseActivity {
    ImageButton back;
    Button add;
    RecyclerView recyclerView;
    adaptActiveScan scanAdapt;
    //加载等待进度圈
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_actives_scan);
        //初始化进度条
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("正在加载页面...");
        progressDialog.show();
        initView();
        bindEvent();
    }
    /**
     * 初始化页面布局
     */
    private void initView(){
        back=$(R.id.back);
        add=$(R.id.add);
        recyclerView=$(R.id.scanList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDate();
        progressDialog.dismiss();
    }
    /**
     * 加载数据
     */
    private void initDate(){
        scanAdapt =new adaptActiveScan(activeScan.this);
        scanAdapt.setOnItemClickListener(new adaptActiveScan.OnItemClickListener() {
            @Override
            public void onUserItemClicked(active userModel) {
                //
                Intent myIntent=new Intent(activeScan.this,addActives.class);
                myIntent.putExtra("activeId",userModel.id);
                startActivity(myIntent);
            }
        });
        recyclerView.setAdapter(scanAdapt);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activeScan.this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 3, getResources().getColor(R.color.pink_KK)));


        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("active","QueryList");
        httpReponse.addOrderFieldParams("starttime");
        httpReponse.addIsDescParams(true);
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d("cao",data);
                active[] actives= JSONUtil.parseArray(data,active.class);
                if(actives!=null){
                    List list = Arrays.asList(actives);
                    scanAdapt.setLosersCollection(list);
                    Log.d("cao","加载完成"+scanAdapt.getItemCount());
                }else{
                    show("没有数据");
                }
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }
    /**
     * 初始化空间的事件
     */
    private void bindEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimActivity(addActives.class);
            }
        });
    }
}
 class adaptActiveScan extends RecyclerView.Adapter<adaptActiveScan.ActiveScanViewHolder> {

    public interface OnItemClickListener {
        void onUserItemClicked(active userModel);
    }

    private List<active> activesCollection;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public adaptActiveScan(Context context) {
        this.mContext=context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activesCollection = Collections.emptyList();
    }

    @Override public int getItemCount() {
        return (this.activesCollection != null) ? this.activesCollection.size() : 0;
    }

    @Override public ActiveScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.item_active_scan, parent, false);
        return new ActiveScanViewHolder(view);
    }

    @Override public void onBindViewHolder(ActiveScanViewHolder holder, final int position) {
        final active act = this.activesCollection.get(position);
        holder.startTime.setText(TimeUtils.dateToString(act.starttime,TimeUtils.FORMAT_DATE));
        holder.endTime.setText(TimeUtils.dateToString(act.endtime,TimeUtils.FORMAT_DATE));
        holder.msg.setText(act.information);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserItemClicked(act);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setLosersCollection(List<active> losersCollection) {
        this.validateUsersCollection(losersCollection);
        this.activesCollection = losersCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateUsersCollection(Collection<active> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class ActiveScanViewHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView msg;

        public ActiveScanViewHolder(View itemView) {
            super(itemView);
            startTime=(TextView)(itemView.findViewById(R.id.startTime));
            endTime=(TextView)(itemView.findViewById(R.id.endTime));
            msg=(TextView)(itemView.findViewById(R.id.msg));
        }
    }
}
