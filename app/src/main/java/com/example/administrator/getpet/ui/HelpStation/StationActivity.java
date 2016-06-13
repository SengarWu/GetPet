package com.example.administrator.getpet.ui.HelpStation;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class StationActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_spet;//救助站宠物
    private RelativeLayout rl_adopt;//领养申请
    private RelativeLayout rl_activity;//周边活动
    private RelativeLayout rl_station_msg;//救助站信息

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
            case R.id.rl_spet://救助站宠物
                startAnimActivity(sPetActivity.class);
                break;
            case R.id.rl_adopt://宠物领养申请
                startAnimActivity(AdoptMsgActivity.class);
                break;
            case R.id.rl_activity://活动
                startAnimActivity(activeScan.class);
                break;
            case R.id.rl_station_msg://救助站信息
                startAnimActivity(addStation.class);
                break;
        }
    }
}
