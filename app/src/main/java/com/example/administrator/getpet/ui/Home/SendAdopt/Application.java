package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button submit;
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
        submit=(Button)findViewById(R.id.submit);
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
                httpReponse.addWhereParams("userId","=",preferences.getString("id",""),"and");
                //调用QueryCount方法
                httpReponse.QueryCount(new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        if(Integer.valueOf(data)==0){
                            //如果没有申请过则可以申请
                            if (detail.length() == 0) {
                                Toast.makeText(mContext, "请输入申请详情", Toast.LENGTH_LONG).show();
                            } else if (connectplace.length() == 0) {
                                Toast.makeText(mContext, "联系地址不能为空", Toast.LENGTH_LONG).show();
                            } else if (connectphone.length() == 0) {
                                Toast.makeText(mContext, "联系电话不能为空", Toast.LENGTH_LONG).show();
                            } else {
                                applyApplication a = new applyApplication();
                                a.setDetail(detail.getText().toString());
                                a.setConnectPlace(connectplace.getText().toString());
                                a.setApplyMessage(detail.getText().toString());
                                a.setPhoneNumber(connectphone.getText().toString());
                                entrust en = new entrust();
                                en.setId(entrustId);
                                en.setDate(new Date());
                                a.setEntrust(en);
                                users currentUser = new users();
                                currentUser.setId(preferences.getString("id", ""));
                                a.setUsers(currentUser);
                                a.setResult(0);
                                a.setComment("未有评价");
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
                                        Toast.makeText(mContext,e.toString(), Toast.LENGTH_LONG).show();
                                       // Toast.makeText(mContext, "申请失败（已申请过，或网络不畅）", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        }else{
                            Toast.makeText(mContext, "已申请过啦", Toast.LENGTH_LONG).show();
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
