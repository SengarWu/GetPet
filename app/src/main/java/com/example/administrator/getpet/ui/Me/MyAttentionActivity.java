package com.example.administrator.getpet.ui.Me;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class MyAttentionActivity extends BaseActivity {

    private ImageButton ib_back;
    private GridView gv_my_attention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initView();
        loadData();
        setupView();
    }

    private void setupView() {

    }

    private void loadData() {

    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gv_my_attention = (GridView) findViewById(R.id.gv_my_attention);

    }
}
