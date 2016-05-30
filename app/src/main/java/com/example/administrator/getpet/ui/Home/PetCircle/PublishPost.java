package com.example.administrator.getpet.ui.Home.PetCircle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class PublishPost extends BaseActivity implements View.OnClickListener {
    private EditText title;
    private EditText content;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        initView();

    }

    private void initView() {
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.submit:
                break;
        }
    }
}
