package com.example.administrator.getpet.ui.findPet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.attention;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by caolin on 2016/6/3.
 */
public class myAttention extends BaseActivity {
    ImageButton back;
    RecyclerView recyclerView;
    adaptAttention attentionAdapt;
    String userId="b0c19976-4859-407b-8ae0-01b68bc73ca1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_myattentions);
        initView();
        initDate();
        bindEvent();
    }
    /**
     * 初始化页面布局
     */
    private void initView(){
        back=$(R.id.back);
        recyclerView=$(R.id.attenionsList);
    }
    /**
     * 加载数据
     */
    private void initDate(){
        userId=preferences.getString("id","");
        attentionAdapt =new adaptAttention(myAttention.this);
        attentionAdapt.setOnItemClickListener(new adaptAttention.OnItemClickListener() {
            @Override
            public void onUserItemClicked(attention userModel) {
                //进入回复界面
                Intent myIntent=new Intent(myAttention.this,petReply.class);
                myIntent.putExtra("loseId",userModel.lose.id);
                startActivity(myIntent);
            }
        });
        recyclerView.setAdapter(attentionAdapt);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(myAttention.this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 3, getResources().getColor(R.color.pink_KK)));


        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("attention","QueryList");
        httpReponse.addWhereParams("userId","=",userId);
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d("cao",data);
                attention[] attentions= JSONUtil.parseArray(data,attention.class);
                if(attentions!=null){
                    List list = Arrays.asList(attentions);
                    attentionAdapt.setLosersCollection(list);
                    Log.d("cao","加载完成"+attentionAdapt.getItemCount());
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
    }
}

