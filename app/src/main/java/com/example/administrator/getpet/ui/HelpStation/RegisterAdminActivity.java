package com.example.administrator.getpet.ui.HelpStation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.StringUtils;
import com.example.administrator.getpet.utils.ToastUtils;

public class RegisterAdminActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterAdminActivity";

    private EditText et_phone;
    private EditText et_password;
    private EditText et_password_again;
    private TextView tv_login;
    private Button btn_register;

    private String phone;
    private String password;
    private String password_again;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        initView();
    }

    private void initView() {
        et_phone = $(R.id.et_phone);
        et_password = $(R.id.et_password);
        et_password_again = $(R.id.et_password_again);
        tv_login = $(R.id.tv_login);
        tv_login.setOnClickListener(this);
        btn_register = $(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_login:
                startAnimActivity(AdminLoginActivity.class);
                break;
            case R.id.btn_register:
                phone = et_phone.getText().toString();
                password = et_password.getText().toString();
                password_again = et_password_again.getText().toString();
                if (TextUtils.isEmpty(phone))
                {
                    ToastUtils.showToast(mContext,"手机号不能为空！");
                    return;
                }
                if (!StringUtils.isPhoneNumberValid(phone))
                {
                    ToastUtils.showToast(mContext,"手机号格式有误！");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    ToastUtils.showToast(mContext,"密码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(password_again))
                {
                    ToastUtils.showToast(mContext,"确认密码不能为空！");
                    return;
                }
                if (!password.equals(password_again))
                {
                    ToastUtils.showToast(mContext,"两次输入的密码不一致！");
                    return;
                }
                progress = new ProgressDialog(RegisterAdminActivity.this);
                progress.setMessage("正在注册...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                register();
                break;
        }
    }

    private void register() {
        SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("users", "register");
        httpReponse.addParams("phone",phone);
        httpReponse.addParams("password",password);
        httpReponse.addParams("identify",1);
        httpReponse.send(new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.i(TAG, "Success: data:"+data);
                //Json解析，反序列化user
                users user = JSONUtil.parseObject(data,users.class);
                if (user.id != null)
                {
                    progress.dismiss();
                    ToastUtils.showToast(mContext,"注册成功！正在跳转");
                    RegisterAdminActivity.this.finish();
                }
            }
            @Override
            public void Fail(String e) {
                progress.dismiss();
                ToastUtils.showToast(mContext,e);
            }
        });
    }
}
