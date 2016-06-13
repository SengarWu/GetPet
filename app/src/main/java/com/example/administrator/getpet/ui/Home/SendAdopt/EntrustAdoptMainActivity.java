package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class EntrustAdoptMainActivity extends BaseActivity implements View.OnClickListener {
    private TextView entrust;//跳转到寄养界面
    private TextView adopt;//跳转到领养界面
    private TextView applyhistory;//跳转到申请历史界面
    private ImageView back;//返回图标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_adopt_main);
        initView();//界面初始化
    }

    private void initView() {
        entrust=(TextView)findViewById(R.id.entrust);
        adopt=(TextView)findViewById(R.id.adopt);
        applyhistory=(TextView)findViewById(R.id.applyhistory);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        entrust.setOnClickListener(this);
        adopt.setOnClickListener(this);
        applyhistory.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.entrust://寄养
                Intent intent =new Intent(this,EntrustMainActivity.class);
                startActivity(intent);
                break;
            case R.id.adopt://领养
                Intent intent2=new Intent(this,SearchEntrust.class);
                startActivity(intent2);
                break;
            case R.id.applyhistory://领养申请历史
                Intent intent3=new Intent(this,ApplicationHistory.class);
                startActivity(intent3);
                break;
            case R.id.back://返回
                this.finish();
                break;
        }
    }
}
