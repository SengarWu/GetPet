package com.example.administrator.getpet.ui.findPet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.attention;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by caolin on 2016/6/1.
 */
public class petScan extends BaseActivity {
    ImageButton back;
    Button publish;
    RecyclerView recyclerView;
    adaptScan scanAdapt;
    //加载等待进度圈
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_scan);
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
        publish=$(R.id.publish);
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
        scanAdapt =new adaptScan(petScan.this);
        scanAdapt.setOnItemClickListener(new adaptScan.OnItemClickListener() {
            @Override
            public void onUserItemClicked(lose userModel) {
                //进入回复界面
                Intent myIntent=new Intent(petScan.this,petReply.class);
                myIntent.putExtra("loseId",userModel.id);
                startActivity(myIntent);
            }
        });
        recyclerView.setAdapter(scanAdapt);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(petScan.this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 3, getResources().getColor(R.color.pink_KK)));


        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("lose","QueryList");
        httpReponse.addOrderFieldParams("uploadTime");
        httpReponse.addIsDescParams(true);
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d("cao",data);
                lose[] loses= JSONUtil.parseArray(data,lose.class);
                if(loses!=null){
                     List list = Arrays.asList(loses);
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
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimActivity(publishPet.class);
            }
        });
    }
}
