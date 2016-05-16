package com.example.administrator.getpet.ui.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.utils.StringUtils;
import com.example.administrator.getpet.utils.ToastUtils;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private EditText et_phone;
    private EditText et_password;
    private EditText et_password_again;
    private ImageButton ib_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_phone);
        et_password_again = (EditText) findViewById(R.id.et_phone);
        ib_register = (ImageButton) findViewById(R.id.ib_register);
        ib_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_register:
                String phone = et_phone.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(phone))
                {
                    ToastUtils.showToast(mContext,"手机号不能为空！");
                    return;
                }
                if (!StringUtils.isPhoneNumberValid(phone))
                {
                    ToastUtils.showToast(mContext,"请输入正确的手机号码！");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    ToastUtils.showToast(mContext,"密码不能为空！");
                    return;
                }
                if (!StringUtils.isValidPassword(password))
                {
                    ToastUtils.showToast(mContext,"密码长度必须为6-16位字符！");
                    return;
                }
                if (!password.equals(et_password_again.getText().toString()))
                {
                    ToastUtils.showToast(mContext,"密码和确认密码不一致！");
                    return;
                }
                //注册操作
                register(phone,password);
                break;
        }
    }

    private void register(String phone,String password) {

    }
}
