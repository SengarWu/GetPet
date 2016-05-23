package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class OtherEntrustDetail extends BaseActivity implements View.OnClickListener {
   private ImageView usex;//用户性别
    private TextView username;//用户名
    private TextView award;//悬赏
    private ImageView type_other;//宠物类型
    private TextView details;//详情
    private TextView pet_chatacter;//宠物性格
    private TextView pet_age;//宠物年龄
    private TextView connect_phone;//联系电话
    private ImageView submit;//提交
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_entrust_detail);
        initView();
    }

    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        usex=(ImageView)findViewById(R.id.usersex);
        award=(TextView)findViewById(R.id.award);
        username=(TextView)findViewById(R.id.username);
        type_other=(ImageView) findViewById(R.id.type);
        details=(TextView)findViewById(R.id.details);
        pet_chatacter=(TextView)findViewById(R.id.pet_chatacter);
        pet_age=(TextView)findViewById(R.id.pet_age);
        connect_phone=(TextView)findViewById(R.id.connect_phone);
        submit=(ImageView) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
}
