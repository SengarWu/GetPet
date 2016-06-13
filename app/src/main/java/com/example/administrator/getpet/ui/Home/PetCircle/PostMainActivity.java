package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;

public class PostMainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView my_post,look_post,look_pet;//我的帖子，查看帖子，查看宠物
    private ImageView back;//返回按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_main);
        initView();
    }

    private void initView() {
        my_post=(TextView)findViewById(R.id.my_post);
        look_post=(TextView)findViewById(R.id.look_post);
        look_pet=(TextView)findViewById(R.id.look_pet);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        my_post.setOnClickListener(this);
        look_post.setOnClickListener(this);
        look_pet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_post:
                Intent intent1=new Intent(PostMainActivity.this,myPostHistory.class);
                startActivity(intent1);
                break;
            case R.id.look_post:
                Intent intent2=new Intent(PostMainActivity.this,SearchPost.class);
                startActivity(intent2);
                break;
            case R.id.look_pet:
                Intent intent3=new Intent(PostMainActivity.this,LookPetActivity.class);
                startActivity(intent3);
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
}
