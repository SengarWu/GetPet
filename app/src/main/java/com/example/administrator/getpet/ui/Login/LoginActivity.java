package com.example.administrator.getpet.ui.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.utils.StringUtils;
import com.example.administrator.getpet.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_station;
    private EditText et_phone;
    private EditText et_password;
    private ImageButton ib_register;
    private ImageButton ib_login;
    private ImageButton ib_find_password;

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
        ib_register = (ImageButton) findViewById(R.id.ib_register);
        ib_register.setOnClickListener(this);
        ib_login = (ImageButton) findViewById(R.id.ib_login);
        ib_login.setOnClickListener(this);
        ib_find_password = (ImageButton) findViewById(R.id.ib_find_password);
        ib_find_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_register:
                startAnimActivity(RegisterActivity.class);
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
                //登录操作
                login(phone,password);
                break;
            case R.id.ib_find_password:
                startAnimActivity(FindPasswordActivity.class);
                break;
        }
    }

    private void login(String phone, String password) {

    }
}
