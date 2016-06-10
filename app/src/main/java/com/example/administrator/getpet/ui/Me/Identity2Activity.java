package com.example.administrator.getpet.ui.Me;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.ui.Me.receiver.IdentityExitReceiver;
import com.example.administrator.getpet.utils.ToastUtils;

public class Identity2Activity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private EditText et_name;
    private EditText et_marStatus;
    private EditText et_origin;
    private EditText et_company;
    private EditText et_post;
    private ImageButton ib_next;

    //定义退出应用的广播
    private IdentityExitReceiver exitReceiver = new IdentityExitReceiver();
    private static final String EXIT_APP_ACTION = "exitActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity2);
        initView();
        //注册广播
        registerExitReceiver();
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        et_name = $(R.id.et_name);
        et_marStatus = $(R.id.et_marStatus);
        et_origin = $(R.id.et_origin);
        et_company = $(R.id.et_company);
        et_post = $(R.id.et_post);
        ib_next = $(R.id.ib_next);
        ib_next.setOnClickListener(this);

    }

    private void registerExitReceiver() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_next:
                String name = et_name.getText().toString();
                String marStatus = et_marStatus.getText().toString();
                String origin = et_origin.getText().toString();
                String company = et_company.getText().toString();
                String post = et_post.getText().toString();
                if (TextUtils.isEmpty(name))
                {
                    ToastUtils.showToast(mContext,"姓名不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(marStatus))
                {
                    ToastUtils.showToast(mContext,"婚姻状况不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(origin))
                {
                    ToastUtils.showToast(mContext,"籍贯不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(company))
                {
                    ToastUtils.showToast(mContext,"工作单位不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(post))
                {
                    ToastUtils.showToast(mContext,"职务不能为空！");
                    return;
                }
                Intent intent = new Intent(Identity2Activity.this,Identity3Activity.class);
                intent.putExtra("name",name);
                intent.putExtra("marStatus",marStatus);
                intent.putExtra("origin",origin);
                intent.putExtra("company",company);
                intent.putExtra("post",post);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }

    private void unRegisterExitReceiver() {
        unregisterReceiver(exitReceiver);
    }

}
