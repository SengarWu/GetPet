package com.example.administrator.getpet.ui.HelpStation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;

public class ApplyAdoptActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_finish;
    private EditText et_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_adopt);
        initView();
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_finish = $(R.id.tv_finish);
        tv_finish.setOnClickListener(this);
        et_reason = $(R.id.et_reason);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_finish:

                break;
        }
    }
}
