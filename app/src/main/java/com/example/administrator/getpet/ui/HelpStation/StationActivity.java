package com.example.administrator.getpet.ui.HelpStation;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class StationActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_spet;
    private RelativeLayout rl_adopt;
    private RelativeLayout rl_activity;
    private RelativeLayout rl_station_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        initView();
    }

    private void initView() {
        rl_spet = $(R.id.rl_spet);
        rl_spet.setOnClickListener(this);
        rl_adopt = $(R.id.rl_adopt);
        rl_adopt.setOnClickListener(this);
        rl_activity = $(R.id.rl_activity);
        rl_activity.setOnClickListener(this);
        rl_station_msg = $(R.id.rl_station_msg);
        rl_station_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rl_spet:
                startAnimActivity(sPetActivity.class);
                break;
            case R.id.rl_adopt:
                startAnimActivity(AdoptMsgActivity.class);
                break;
            case R.id.rl_activity:
                startAnimActivity(StationActiveActivity.class);
                break;
            case R.id.rl_station_msg:
                startAnimActivity(StationMsgActivity.class);
                break;
        }
    }
}
