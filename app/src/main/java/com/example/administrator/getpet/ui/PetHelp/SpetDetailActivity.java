package com.example.administrator.getpet.ui.PetHelp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class SpetDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private ImageButton ib_attention;
    private ImageView iv_spet_photo;
    private TextView tv_spet_age;
    private TextView tv_spet_state;
    private TextView tv_spet_character;
    private TextView tv_spet_money;
    private TextView tv_spet_station;
    private TextView tv_spet_address;
    private ImageButton ib_spet_donate;
    private ImageButton ib_spet_adopt;
    private ImageButton ib_station_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spet_detail);
        initView();
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        ib_attention = $(R.id.ib_attention);
        ib_attention.setOnClickListener(this);
        iv_spet_photo = $(R.id.iv_spet_photo);
        tv_spet_age = $(R.id.tv_spet_age);
        tv_spet_state = $(R.id.tv_spet_state);
        tv_spet_character = $(R.id.tv_spet_character);
        tv_spet_money = $(R.id.tv_spet_money);
        tv_spet_station = $(R.id.tv_spet_station);
        tv_spet_address = $(R.id.tv_spet_address);
        ib_spet_donate = $(R.id.ib_spet_donate);
        ib_spet_donate.setOnClickListener(this);
        ib_spet_adopt = $(R.id.ib_spet_adopt);
        ib_spet_adopt.setOnClickListener(this);
        ib_station_activity = $(R.id.ib_station_activity);
        ib_station_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_attention:

                break;
            case R.id.ib_spet_donate:

                break;
            case R.id.ib_spet_adopt:

                break;
            case R.id.ib_station_activity:

                break;
        }
    }
}
