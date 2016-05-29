package com.example.administrator.getpet.ui.Me;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class MyPetActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_add;
    private GridView gv_my_pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet);
        initView();
    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        gv_my_pet = (GridView) findViewById(R.id.gv_my_pet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:

                break;
            case R.id.tv_add:

                break;
        }
    }
}
