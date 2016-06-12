package com.example.administrator.getpet.ui.findPet;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.attention;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

/**
 * Created by caolin on 2016/5/30.
 */
public class petDetail extends BaseActivity implements View.OnClickListener {

    String loseId="";
    String userId="";
    attention attModel;
    lose model;
    EditText publishTime;
    EditText loser;
    EditText phone;
    EditText petName;
    EditText petMsg;
    EditText  loseTime;
    EditText loseAddress;
    EditText loseMsg;
    ImageView petPhoto;
    ImageView loserPhoto;
    //返回图片
    ImageButton back;
    //改变的图片
    ImageView handlerPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_detail);
        initView();
        initDate();
        bindEvent();
    }
    /**
     * 初始化页面布局
     */
    private void initView(){
       publishTime=$(R.id.publishTime);
       loser=$(R.id.loser);
       phone=$(R.id.phone);
       petName=$(R.id.petName);
       petMsg=$(R.id.petMsg);
        loseTime=$(R.id.loseTime);
       loseAddress=$(R.id.loseAddress);
       loseMsg=$(R.id.loseMsg);
        petPhoto=$(R.id.petPhoto);
        loserPhoto=$(R.id.loserPhoto);
        back=$(R.id.back);
        handlerPhoto=$(R.id.handlerPhoto);
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

    /**
     * 加载数据
     */
    private void initDate(){
        userId=preferences.getString("id","");
        if(getIntent()!=null){
            loseId=getIntent().getStringExtra("loseId");
        }
        //传入表名和方法名   方法名：QuerySinglebyid
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("lose","QuerySinglebyid");
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QuerySinglebyid(loseId, new HttpCallBack() {
            @Override
            public void Success(String data) {
                model= JSONUtil.parseObject(data,lose.class);
                if(model!=null){
                    loadUser();
                    initPhoto();
                }else{
                    show("数据加载失败");
                }
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    private  void initPhoto(){
        if(model.users.id.equals(userId)){
            handlerPhoto.setImageResource(R.mipmap.seek_publish_remove);
            handlerPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePublish();
                }
            });
        }else{
            SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("attention","QuerySinglebywheres");
            httpReponse.addWhereParams("loseId","=",loseId);
            //调用QuerySinglebyid方法 第一个参数是id
            httpReponse.QuerySinglebywheres( new HttpCallBack() {
                @Override
                public void Success(String data) {
                    attModel= JSONUtil.parseObject(data,attention.class);
                    if(attModel!=null){
                        handlerPhoto.setImageResource(R.mipmap.seek_attention_cancel);
                        cancellAttention();
                    }else{
                        handlerPhoto.setImageResource(R.mipmap.seek_attention_add);
                        addAttention();
                    }
                }
                @Override
                public void Fail(String e) {
                    show(e);
                }
            });
        }
    }

    private void addAttention(){
        //handlerPhoto.setImageResource(R.mipmap.seek_attention_cancel);
        handlerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention att=new attention();
                att.concernTime= TimeUtils.stringToDate(TimeUtils.getCurrentTime(TimeUtils.FORMAT_DATE),TimeUtils.FORMAT_DATE);
                users us=new users();
                us.id=userId;
                att.users=us;

                att.lose=model;

                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("attention","Insert");
                httpReponse.insert(att, new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        handlerPhoto.setImageResource(R.mipmap.seek_attention_cancel);
                        cancellAttention();
                        show("已添加关注");
                    }

                    @Override
                    public void Fail(String e) {
                        show(e);
                    }
                });

            }
        });
    }

    private void cancellAttention(){

        //handlerPhoto.setImageResource(R.mipmap.seek_attention_cancel);
        handlerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("attention","Delete");
                httpReponse.delete(attModel.id, new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        handlerPhoto.setImageResource(R.mipmap.seek_attention_add);
                        addAttention();
                        show("已取消关注");
                    }

                    @Override
                    public void Fail(String e) {
                        show(e);
                    }
                });
            }
        });
    }

    private void deletePublish(){
       
        SimpleHttpPostUtil httpReponse2= new SimpleHttpPostUtil("lose","Delete");
        httpReponse2.delete(model.id, new HttpCallBack() {
            @Override
            public void Success(String data) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void Fail(String e) {
                //show(e);
                show("已经有人添加关注或发表回复,不能删除");
            }
        });
    }

    private void loadUser(){
        publishTime.setText(TimeUtils.dateToString(model.uploadTime,TimeUtils.FORMAT_DATE));
        loser.setText(model.users.nickName);
        phone.setText(model.phone);
        petName.setText(model.pet.name);
        petMsg.setText(model.pet.character);

        loseTime.setText(TimeUtils.dateToString(model.loseTime,TimeUtils.FORMAT_DATE));
        loseAddress.setText(model.loseAddress);
        loseMsg.setText(model.loseMessage);
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(model.pet.photo),petPhoto,R.mipmap.home_pet_photp);
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(model.users.photo),loserPhoto,R.mipmap.home_pet_photp);

    }
}
