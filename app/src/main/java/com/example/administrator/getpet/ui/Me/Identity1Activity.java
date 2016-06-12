package com.example.administrator.getpet.ui.Me;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.ui.Me.receiver.IdentityExitReceiver;

public class Identity1Activity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_next;

    //定义退出应用的广播
    private IdentityExitReceiver exitReceiver = new IdentityExitReceiver();
    private static final String EXIT_APP_ACTION = "exitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity1);
        initView();
        //注册广播
        registerExitReceiver();
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_next = $(R.id.tv_next);
        tv_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_next:
                startAnimActivity(Identity2Activity.class);
                break;
        }
    }

    private void registerExitReceiver() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }

    private void unRegisterExitReceiver() {
        unregisterReceiver(exitReceiver);
    }
}
