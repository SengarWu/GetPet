package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class EntrustAdoptMainActivity extends BaseActivity implements View.OnClickListener {
    private TextView entrust;
    private TextView adopt;
    private TextView applyhistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_adopt_main);
        initView();
    }

    private void initView() {
        entrust=(TextView)findViewById(R.id.entrust);
        adopt=(TextView)findViewById(R.id.adopt);
        applyhistory=(TextView)findViewById(R.id.applyhistory);
        entrust.setOnClickListener(this);
        adopt.setOnClickListener(this);
        applyhistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.entrust:
                Intent intent =new Intent(this,EntrustMainActivity.class);
                startActivity(intent);
                break;
            case R.id.adopt:
                Intent intent2=new Intent(this,SearchEntrust.class);
                startActivity(intent2);
                break;
            case R.id.applyhistory:
                Intent intent3=new Intent(this,ApplicationHistory.class);
                startActivity(intent3);
                break;
        }
    }
}
