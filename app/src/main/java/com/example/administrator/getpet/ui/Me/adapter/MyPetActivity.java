package com.example.administrator.getpet.ui.Me.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.ui.Me.AddPetActivity;

public class MyPetActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_add_pet;
    private GridView gv_my_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet);
        initView();
        loadData();
        setupView();
    }

    private void setupView() {

    }

    private void loadData() {

    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_add_pet = $(R.id.tv_add_pet);
        tv_add_pet.setOnClickListener(this);
        gv_my_pet = $(R.id.gv_my_pet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_pet:
                startAnimActivity(AddPetActivity.class);
                break;
        }
    }
}
