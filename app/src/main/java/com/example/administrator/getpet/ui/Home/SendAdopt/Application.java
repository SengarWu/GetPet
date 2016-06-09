package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.Date;

public class Application extends BaseActivity implements View.OnClickListener {
    private EditText detail;
    private EditText connectplace;
    private EditText connectphone;
    private ImageView submit;
    private ImageView back;
    private String entrustId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        initView();
    }

    private void initView() {
        detail=(EditText)findViewById(R.id.details);
        connectplace=(EditText)findViewById(R.id.connect_place);
        connectphone=(EditText)findViewById(R.id.connect_phone);
        submit=(ImageView)findViewById(R.id.submit);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        Intent intent=getIntent();
        entrustId=intent.getStringExtra("entrustId");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                //传入表名和方法名   方法名：QueryCount
                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QueryCount");
                httpReponse.addWhereParams("entrustId","=",entrustId);
                httpReponse.addWhereParams("userId","=",preferences.getString("Id",""),"and");
                //调用QueryCount方法
                httpReponse.QueryCount(new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        if(Integer.valueOf("data")>0){
                            //如果没有申请过则可以申请
                            if (detail.getText().toString() == "") {
                                Toast.makeText(mContext, "请输入申请详情", Toast.LENGTH_LONG).show();
                            } else if (connectplace.getText().toString() == "") {
                                Toast.makeText(mContext, "联系地址不能为空", Toast.LENGTH_LONG).show();
                            } else if (connectphone.getText().toString() == "") {
                                Toast.makeText(mContext, "联系电话不能为空", Toast.LENGTH_LONG).show();
                            } else {
                                applyApplication a = new applyApplication();
                                a.setDetail(detail.getText().toString());
                                a.setConnectPlace(connectplace.getText().toString());
                                a.setPhoneNumber(connectphone.getText().toString());
                                entrust e = new entrust();
                                e.setId(entrustId);
                                a.setEntrust(e);
                                users currentUser = new users();
                                currentUser.setId(preferences.getString("Id", ""));
                                a.setResult(0);
                                a.setComment("");
                                a.setApplyDate(new Date());

                                //传入表名和方法名
                                SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("applyApplication", "Insert");
                                //调用insert方法
                                httpReponse.insert(a, new HttpCallBack() {
                                    @Override
                                    public void Success(String data) {
                                        Toast.makeText(mContext, "申请成功", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void Fail(String e) {
                                        Toast.makeText(mContext, "申请失败（已申请过，或网络不畅）", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        }else{
                            Toast.makeText(mContext, "申请失败（已申请过）", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void Fail(String e)
                    {
                        Toast.makeText(mContext, "申请失败,网络不畅", Toast.LENGTH_LONG).show();
                    }
                });

                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
}
