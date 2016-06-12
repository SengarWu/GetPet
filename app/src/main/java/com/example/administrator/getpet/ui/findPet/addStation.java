package com.example.administrator.getpet.ui.findPet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.Station;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caolin on 2016/6/11.
 */
public class addStation extends BaseActivity implements View.OnClickListener {
    //需要数据
    String userId="b0c19976-4859-407b-8ae0-01b68bc73ca1";
    boolean isAdd=true;
    Station station;

    EditText name;
    EditText address;
    EditText phone;
    ImageButton back;
    Button save;
    //加载等待进度圈
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_msg);
        //初始化进度条
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("正在加载页面...");
        progressDialog.show();
        //渲染页面
        initView();
        //初始化页面
        initDate();
        //绑定事件
        bindEvent();
        //关闭加载进度条
        dismissProgressDialog();
    }

    /**
     * 初始化页面布局
     */
    private void initView(){
        back=$(R.id.back);
        save=$(R.id.save);
        address=$(R.id.address);
        name=$(R.id.name);
        phone=$(R.id.phone);
    }

    /**
     * 初始化空间的事件
     */
    private void bindEvent(){
        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    private void initDate(){


        //传入表名和方法名   方法名：QuerySinglebyid
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("Station","QuerySinglebyid");
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QuerySinglebyid(userId, new HttpCallBack() {
            @Override
            public void Success(String data) {
                station= JSONUtil.parseObject(data,Station.class);
                if(station!=null){
                    isAdd=false;
                    loadStation();
                }else{
                    isAdd=true;
                }
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
        //关闭加载条
        dismissProgressDialog();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back: finish();break;
            case R.id.save:

                save();break;
        }
    }

    /**
     * 发布宠物丢失
     */
    private void save(){
        if(!isValid()){
            return;
        }
        //数据
        station=new Station();
        station.id=userId;
        station.name=name.getText().toString().trim();
        station.Address=address.getText().toString().trim();
        station.Tel=phone.getText().toString().trim();
        //操作 添加
        if(isAdd){
            SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("Station","Insert");


            httpReponse.insert(station, new HttpCallBack() {
                @Override
                public void Success(String data) {
                    show("发布成功");
                    finish();
                }
                @Override
                public void Fail(String e) {
                    show(e);
                }
            });
        }else{
            //操作 修改
            SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("Station","Update");
            //调用update方法
            httpReponse.update(station, new HttpCallBack() {
                @Override
                public void Success(String data) {
                    show("修改成功");
                    finish();
                }
                @Override
                public void Fail(String e)
                {
                    show(e);
                }
            });
        }

    }

    /**
     * 关闭加载进度条
     */
    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 验证发布信息
     * @return  是否有效
     */
    private boolean isValid(){
        String na=name.getText().toString().trim();
        if(na.trim().equals("")){
            show("请输入描述信息");
            return false;
        }
        if(na.trim().length()>10){
            show("描述信息不能超过10个字");
            return false;
        }


        String add=address.getText().toString().trim();
        if(add.trim().equals("")){
            show("请输入地址");
            return false;
        }
        if(add.trim().length()>15){
            show("地址不能超过25个字");
            return false;
        }

        String ph=phone.getText().toString().trim();
        if(ph.trim().equals("")){
            show("请输入联系信息");
            return false;
        }
        //验证电话号码
        String expression = "^((13[0-9])|(15[^4\\D])|(18[0-9]))\\d{8}$";
        //String expression = "^((13[0-9])|(15[^4,^\\D])|(18[0,5-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(ph);
        if(!matcher.matches()){
            show("手机号码格式不正确！");
            return false;
        }

        return true;



    }
    private void loadStation(){
        name.setText(station.name);
        address.setText(station.Address);
        phone.setText(station.Tel);
    }
}


