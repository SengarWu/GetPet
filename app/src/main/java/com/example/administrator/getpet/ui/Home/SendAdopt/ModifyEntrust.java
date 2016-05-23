package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.administrator.getpet.R;

public class ModifyEntrust extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;//返回
    private EditText award;//悬赏金额
    private Spinner pet_list;//宠物列表
    private EditText content;//寄养信息内容
    private Button modify;//修改提交按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_entrust);
        initView();
    }

    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        award=(EditText)findViewById(R.id.award);
        pet_list=(Spinner)findViewById(R.id.pet_list);
        content=(EditText)findViewById(R.id.content);
        modify=(Button)findViewById(R.id.modify);
        back.setOnClickListener(this);
        modify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.modify:

                break;
        }
    }
}
