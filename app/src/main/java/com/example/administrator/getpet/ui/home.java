package com.example.administrator.getpet.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.view.DragLayout;
import com.nineoldandroids.view.ViewHelper;

public class home extends Activity implements View.OnClickListener {

    private DragLayout dl;
    private ImageView iv_icon;
    private LinearLayout ll3;   //我的关注
    private LinearLayout ll4;   //我的宠物
    private LinearLayout ll5;   //个人信息
    private LinearLayout ll6;   //消息通知
    private LinearLayout ll7;   //交易记录
    private LinearLayout ll8;   //退出账号

    private ImageButton ib_xinxiqiang;
    private LinearLayout ll_jyly;
    private LinearLayout ll_jiuzhu;
    private LinearLayout ll_xunhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDragLayout();
        initView();
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_icon.setOnClickListener(this);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll3.setOnClickListener(this);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll4.setOnClickListener(this);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll5.setOnClickListener(this);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll6.setOnClickListener(this);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        ll7.setOnClickListener(this);
        ll8 = (LinearLayout) findViewById(R.id.ll8);
        ll8.setOnClickListener(this);
        ib_xinxiqiang = (ImageButton) findViewById(R.id.ib_xinxiqiang);
        ib_xinxiqiang.setOnClickListener(this);
        ll_jyly = (LinearLayout) findViewById(R.id.ll_jyly);
        ll_jyly.setOnClickListener(this);
        ll_jiuzhu = (LinearLayout) findViewById(R.id.ll_jiuzhu);
        ll_jiuzhu.setOnClickListener(this);
        ll_xunhui = (LinearLayout) findViewById(R.id.ll_xunhui);
        ll_xunhui.setOnClickListener(this);
    }


    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_icon:
                dl.open();
                break;
            case R.id.ll3:

                break;
            case R.id.ll4:

                break;
            case R.id.ll5:

                break;
            case R.id.ll6:

                break;
            case R.id.ll7:

                break;
            case R.id.ll8:

                break;
            case R.id.ib_xinxiqiang:

                break;
            case R.id.ll_jyly:

                break;
            case R.id.ll_jiuzhu:

                break;
            case R.id.ll_xunhui:

                break;
        }
    }
}
