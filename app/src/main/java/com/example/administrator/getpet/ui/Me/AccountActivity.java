package com.example.administrator.getpet.ui.Me;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.view.RoundImageView;

public class AccountActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private RoundImageView riv_photo;
    private TextView tv_nickname;
    private TextView tv_phone;
    private FrameLayout frame_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
        loadData();
    }

    private void loadData() {
        //检查账号验证情况,切换
    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        riv_photo = (RoundImageView) findViewById(R.id.riv_photo);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
