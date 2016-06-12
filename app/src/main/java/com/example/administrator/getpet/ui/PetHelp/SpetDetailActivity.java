package com.example.administrator.getpet.ui.PetHelp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.application;
import com.example.administrator.getpet.bean.sPet;
import com.example.administrator.getpet.utils.GetPictureUtils;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class SpetDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private ImageView iv_spet_photo;
    private TextView tv_spet_name;
    private TextView tv_spet_age;
    private TextView tv_spet_state;
    private TextView tv_spet_money;
    private TextView tv_spet_station;
    private TextView tv_spet_address;
    private ImageButton ib_spet_donate;
    private ImageButton ib_spet_adopt;
    private ImageButton ib_station_activity;
    private sPet spet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spet_detail);
        initView();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null)
        {
            spet = (sPet) intent.getSerializableExtra("spet");
            if (spet == null)
            {
                ToastUtils.showToast(mContext,"获取数据失败！");
                return;
            }
            int length = intent.getIntExtra("length",0);
            int position = intent.getIntExtra("position",0);
            iv_spet_photo.setImageResource(GetPictureUtils.GetPicture(length)[position]);
            tv_spet_name.setText(spet.name);
            tv_spet_age.setText(String.valueOf(spet.age));
            tv_spet_state.setText(spet.state);
            tv_spet_money.setText(String.valueOf(spet.money));
            tv_spet_station.setText(spet.Station.name);
            tv_spet_address.setText(spet.address);
        }
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        iv_spet_photo = $(R.id.iv_spet_photo);
        tv_spet_name = $(R.id.tv_spet_name);
        tv_spet_age = $(R.id.tv_spet_age);
        tv_spet_state = $(R.id.tv_spet_state);
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
            case R.id.ib_spet_donate://捐赠
                Intent intent = new Intent(SpetDetailActivity.this,DonateActivity.class);
                intent.putExtra("id",spet.id);
                startActivity(intent);
                break;
            case R.id.ib_spet_adopt://领养
                AlertDialog.Builder builder = new AlertDialog.Builder(SpetDetailActivity.this);
                builder.setMessage("您确认领养该宠物"+spet.name+"吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        adoptRequest();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.ib_station_activity://萌圈
                Intent intent1 = new Intent();

                break;
        }
    }

    private void adoptRequest() {
        //传入表名和方法名
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("application","Insert");
        application apply = new application();

    }
}
