package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.getpet.R;

public class Application extends AppCompatActivity implements View.OnClickListener {
    private EditText detail;
    private EditText connectplace;
    private EditText connectphone;
    private ImageView submit;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        initView();
    }

    private void initView() {
        detail=(EditText)findViewById(R.id.details);
        connectplace=(EditText)findViewById(R.id.connect_place);
        connectphone=(EditText)findViewById(R.id.connect_phone);
        submit=(ImageView)findViewById(R.id.submit);
        back=(ImageView)findViewById(R.id.back);
        submit.setOnClickListener(this);
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
