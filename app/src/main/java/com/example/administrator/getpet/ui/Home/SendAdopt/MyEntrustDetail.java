package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.getpet.R;

public class MyEntrustDetail extends AppCompatActivity implements View.OnClickListener {
    private Button cancle;
    private Button modify;
    private Button lookapply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_entrust_detail);
        initView();
    }

    private void initView() {
        cancle=(Button)findViewById(R.id.cancle);
        modify=(Button)findViewById(R.id.modify);
        lookapply=(Button)findViewById(R.id.look_apply);
        cancle.setOnClickListener(this);
        modify.setOnClickListener(this);
        lookapply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancle:
                this.finish();
                break;
            case R.id.modify:

                break;
            case R.id.look_apply:
                Intent intent=new Intent(this,LookApplication.class);
                startActivity(intent);
                break;
        }
    }
}
