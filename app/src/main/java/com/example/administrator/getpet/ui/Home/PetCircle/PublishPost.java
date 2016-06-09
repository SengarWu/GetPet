package com.example.administrator.getpet.ui.Home.PetCircle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.post;

import java.util.Calendar;

public class PublishPost extends BaseActivity implements View.OnClickListener {
    private EditText title;
    private EditText content;
    private Button submit;
    private EditText award;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        initView();
    }

    private void initView() {
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        award=(EditText)findViewById(R.id.award);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.submit:
                post npost=new post();
               // npost.userId=
                 npost.setTitle(title.getText().toString());
                 npost.setMes(content.getText().toString());
                npost.setIntergen(Integer.valueOf(award.getText().toString()));
                //获取系统当前时间
                Calendar a= Calendar.getInstance();
                java.util.Date d=a.getTime();
                String datestr=String.valueOf(d.getYear())+"/"+String.valueOf(d.getMonth())+"/"+
                        String.valueOf(d.getDate())+" "+String.valueOf(d.getHours())+":"+String.valueOf(d.getMinutes())
                        +":"+String.valueOf(d.getSeconds());
                npost.setDate(d);

                break;
        }
    }
}
