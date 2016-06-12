package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.baidu.mapapi.map.Text;*/
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
    private ImageView back;//
    private TextView title;//标题
    private TextView pubTime;//发布时间
    private String entrustId;//寄养信息的Id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_entrust_detail);
        initView();
    }

    private void initView() {
        title=(TextView)findViewById(R.id.title);
        pubTime=(TextView)findViewById(R.id.Pubtime);
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
        Intent intent=getIntent();
        entrustId=intent.getStringExtra("entrustId");
        if(intent.getStringExtra("sex").equals("男")){
            usex.setImageResource(R.mipmap.boy);
        }else{
            usex.setImageResource(R.mipmap.girl);
        }
        title.setText(intent.getStringExtra("title"));
        username.setText(intent.getStringExtra("username"));
        award.setText(intent.getStringExtra("award"));
        switch (intent.getStringExtra("type")){
            case "猫":
                type_other.setImageResource(R.mipmap.cat_type);
                break;
            case "狗":
                type_other.setImageResource(R.mipmap.dog);
                break;
            case "鱼":
                type_other.setImageResource(R.mipmap.type_fish);
                break;
            case "其他":
                type_other.setImageResource(R.mipmap.type_other);
                break;
        }
        details.setText("    "+intent.getStringExtra("details"));
        pubTime.setText(intent.getStringExtra("Pubtime"));
        pet_chatacter.setText(intent.getStringExtra("pet_chatacter"));
        pet_age.setText(intent.getStringExtra("pet_age"));
        connect_phone.setText(intent.getStringExtra("connect_phone"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                //进入申请领养界面
                Intent intent2=new Intent(OtherEntrustDetail.this,Application.class);
                intent2.putExtra("entrustId",entrustId);
                startActivity(intent2);
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
}
