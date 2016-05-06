package com.example.administrator.getpet.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.view.DragLayout;
import com.nineoldandroids.view.ViewHelper;

public class home extends Activity {

    private DragLayout dl;
    private ImageView iv_icon;
    private LinearLayout ll3;   //我的关注
    private LinearLayout ll4;   //我的宠物
    private LinearLayout ll5;   //个人信息
    private LinearLayout ll6;   //交易记录
    private LinearLayout ll7;   //
    private LinearLayout ll8;

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
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
    }


    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }
}
