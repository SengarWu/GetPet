package com.example.administrator.getpet.ui.Me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.indentified;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class Identity3Activity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Identity3Activity";

    private ImageButton ib_back;
    private EditText et_income;
    private EditText et_qq;
    private EditText et_wechat;
    private EditText et_others;
    private ImageButton ib_submit;

    private String name;
    private String marStatus;
    private String origin;
    private String company;
    private String post;

    private float income;
    private String qq;
    private String wechat;
    private String others;

    private ProgressDialog progress;

    private static final String EXIT_APP_ACTION = "exitActivity";

    private final int SUCCESS = 1001;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:

                    startAnimActivity(AccountActivity.class);
                    //广播通知退出Identity2,Identity1
                    Intent intent = new Intent();
                    intent.setAction(EXIT_APP_ACTION);
                    sendBroadcast(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity3);
        loadData();
        initView();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null)
        {
            name = intent.getStringExtra("name");
            marStatus = intent.getStringExtra("marStatus");
            origin = intent.getStringExtra("origin");
            company = intent.getStringExtra("company");
            post = intent.getStringExtra("post");
        }
        else
        {
            ToastUtils.showToast(mContext,"数据加载失败");
        }
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        et_income = $(R.id.et_income);
        et_qq = $(R.id.et_qq);
        et_wechat = $(R.id.et_wechat);
        et_others = $(R.id.et_others);
        ib_submit = $(R.id.ib_submit);
        ib_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_submit:
                try
                {
                    income = Float.parseFloat(et_income.getText().toString());
                }
                catch (Exception e)
                {
                    ToastUtils.showToast(mContext,"月收入输入有误！");
                    return;
                }
                qq = et_qq.getText().toString();
                wechat = et_wechat.getText().toString();
                others = et_others.getText().toString();
                submit();
                break;
        }
    }

    private void submit() {
        String userId = preferences.getString("id","");
        if (TextUtils.isEmpty(userId))
        {
            ToastUtils.showToast(mContext,"获取数据有误，请尝试重新登录");
            return;
        }
        progress = new ProgressDialog(Identity3Activity.this);
        progress.setMessage("请等待...");
        progress.show();
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("indentified","Insert");
        indentified indent = new indentified();
        indent.name = name;
        indent.marStatus = marStatus;
        indent.origin = origin;
        indent.company = company;
        indent.post = post;
        indent.income = income;
        indent.qq = qq;
        indent.wechat = wechat;
        indent.others = others;
        users user = new users();
        user.id = userId;
        indent.users = user;
        httpReponse.insert(indent, new HttpCallBack() {
            @Override
            public void Success(String data) {
                progress.dismiss();
                ToastUtils.showToast(mContext,"认证成功！");
                clear();
                handler.sendEmptyMessageDelayed(SUCCESS,1000);
                Log.d(TAG, "Success: data:"+data);
            }

            @Override
            public void Fail(String e) {
                progress.dismiss();
                ToastUtils.showToast(mContext,"认证失败!"+e);
                Log.d(TAG, "Success: e:"+e);
            }
        });
    }

    private void clear() {
        et_income.setText("");
        et_others.setText("");
        et_qq.setText("");
        et_wechat.setText("");
    }
}
