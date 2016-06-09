package com.example.administrator.getpet.ui.Me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class IntroduceActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_edit;
    private TextView tv_nickname;
    private TextView tv_sex;
    private TextView tv_age;
    private TextView tv_address;
    private TextView tv_phone;
    private TextView tv_personal;
    private TextView tv_occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        initView();
        loadData();
    }

    private void loadData() {
        tv_nickname.setText(preferences.getString("nickName",""));
        tv_sex.setText(preferences.getString("sex",""));
        tv_age.setText(preferences.getInt("age",0));
        tv_address.setText(preferences.getString("address",""));
        tv_phone.setText(preferences.getString("phone",""));
        tv_personal.setText(preferences.getString("personal",""));
        tv_occupation.setText(preferences.getString("occupation",""));
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_edit = $(R.id.tv_edit);
        tv_edit.setOnClickListener(this);
        tv_nickname = $(R.id.tv_nickname);
        tv_sex = $(R.id.tv_sex);
        tv_age = $(R.id.tv_age);
        tv_address = $(R.id.tv_address);
        tv_phone = $(R.id.tv_phone);
        tv_personal = $(R.id.tv_personal);
        tv_occupation = $(R.id.tv_occupation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_edit:
                startAnimActivity(EditIntroduceActivity.class);
                break;
        }
    }
}
