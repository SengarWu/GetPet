package com.example.administrator.getpet.ui.HelpStation;

import android.app.ProgressDialog;
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
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.StringUtils;
import com.example.administrator.getpet.utils.ToastUtils;

public class AdminLoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AdminLoginActivity";

    private EditText et_phone;
    private EditText et_password;
    private ImageButton ib_enter;
    private TextView tv_register;

    private String phone;
    private String password;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        initView();

    }

    private void initView() {
        et_phone = $(R.id.et_phone);
        et_password = $(R.id.et_password);
        ib_enter = $(R.id.ib_enter);
        ib_enter.setOnClickListener(this);
        tv_register = $(R.id.tv_register);
        tv_register.setOnClickListener(this);
        progress = new ProgressDialog(AdminLoginActivity.this);
        progress.setMessage("正在登录...");
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_enter:
                phone = et_phone.getText().toString();
                password = et_password.getText().toString();
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
                progress.show();
                login();
                break;
            case R.id.tv_register:
                startAnimActivity(RegisterAdminActivity.class);
                break;
        }
    }

    private void login() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("users","login");
        httpReponse.addParams("phone",phone);
        httpReponse.addParams("password",password);
        httpReponse.send(new HttpCallBack() {
            @Override
            public void Success(String data) {
                progress.dismiss();
                Log.i(TAG, "Success: data:"+data);
                users user = JSONUtil.parseObject(data,users.class);
                if (user.identify == 0)
                {
                    ToastUtils.showToast(mContext,"您没有登录救助站权限哦！");
                    return;
                }
                editor.putString("id",user.id);
                editor.putString("phone",user.phone);
                editor.commit();
                ToastUtils.showToast(mContext,"登录成功！");
                startAnimActivity(StationActivity.class);
                finish();
            }

            @Override
            public void Fail(String e) {
                Log.i(TAG, "Fail: e:"+e);
                progress.dismiss();
                ToastUtils.showToast(mContext,e);
            }
        });
    }
}
