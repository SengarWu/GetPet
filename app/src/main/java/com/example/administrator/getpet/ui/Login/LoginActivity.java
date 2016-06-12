package com.example.administrator.getpet.ui.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.ui.HelpStation.AdminLoginActivity;
import com.example.administrator.getpet.ui.home;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.StringUtils;
import com.example.administrator.getpet.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private TextView tv_station;
    private EditText et_phone;
    private EditText et_password;
    private ImageButton ib_login_register;
    private ImageButton ib_login;
    private ImageButton ib_find_password;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tv_station = (TextView) findViewById(R.id.tv_station);
        tv_station.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        ib_login_register = (ImageButton) findViewById(R.id.ib_login_register);
        ib_login_register.setOnClickListener(this);
        ib_login = (ImageButton) findViewById(R.id.ib_login);
        ib_login.setOnClickListener(this);
        ib_find_password = (ImageButton) findViewById(R.id.ib_find_password);
        ib_find_password.setOnClickListener(this);
        progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("正在登录...");
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_station:
                startAnimActivity(AdminLoginActivity.class);
                break;
            case R.id.ib_login_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,RESULT_OK);
                break;
            case R.id.ib_login:
                String password = et_password.getText().toString();
                String phone = et_phone.getText().toString();
                if (TextUtils.isEmpty(phone))
                {
                    ToastUtils.showToast(mContext,"手机号不能为空！");
                    return;
                }
                if (!StringUtils.isPhoneNumberValid(phone))
                {
                    ToastUtils.showToast(mContext,"手机号格式不正确！");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    ToastUtils.showToast(mContext,"密码不能为空！");
                    return;
                }
                if (!StringUtils.isValidPassword(password))
                {
                    ToastUtils.showToast(mContext,"密码长度必须为6-16位！");
                    return;
                }
                //显示进度条
                progress.show();
                //登录操作
                login(phone,password);
                break;
            case R.id.ib_find_password:
                startAnimActivity(FindPasswordActivity.class);
                break;
        }
    }

    /**
     * 登录
     * @param phone
     * @param password
     */
    private void login(String phone, String password) {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("users","login");
        httpReponse.addParams("phone",phone);
        httpReponse.addParams("password",password);
        httpReponse.send(new HttpCallBack() {
            @Override
            public void Success(String data) {
                //Log.i(TAG, "Success: data:"+data);
                //Json解析，反序列化user
                users user = JSONUtil.parseObject(data,users.class);
                //将服务器返回的用户信息保存到本地
                editor.putString("id",user.id);
                editor.putString("phone",user.phone);
                editor.putString("nickName",user.nickName);
                editor.putString("sex",user.sex);
                editor.putInt("age",user.age);
                editor.putString("address",user.address);
                editor.putString("email",user.email);
                editor.putString("personal",user.personal);
                editor.putString("occupation",user.occupation);
                editor.putString("photo",user.photo);
                editor.putString("reputation",String.valueOf(user.getUser_reputation()));
                if (user.indentified != null)
                {
                    editor.putString("indentifiedId",user.indentified.id);
                    editor.putString("name",user.indentified.name);
                    editor.putString("marStatus",user.indentified.marStatus);
                    editor.putString("origin",user.indentified.origin);
                    editor.putString("company",user.indentified.company);
                    editor.putString("post",user.indentified.post);
                    editor.putFloat("income",user.indentified.income);
                    editor.putString("qq",user.indentified.qq);
                    editor.putString("wechat",user.indentified.wechat);
                    editor.putString("others",user.indentified.others);
                }
                else
                {
                    editor.putString("indentifiedId","");
                    editor.putString("name","");
                    editor.putString("marStatus","");
                    editor.putString("origin","");
                    editor.putString("company","");
                    editor.putString("post","");
                    editor.putFloat("income",0);
                    editor.putString("qq","");
                    editor.putString("wechat","");
                    editor.putString("others","");
                }
                //提交
                editor.commit();
                progress.dismiss();
                ToastUtils.showToast(mContext,"登录成功！");
                startAnimActivity(home.class);
                finish();
            }

            @Override
            public void Fail(String e) {
                progress.dismiss();
                ToastUtils.showToast(mContext,e);
                Log.i(TAG, "Fail: "+e);
            }
        });
    }
}
