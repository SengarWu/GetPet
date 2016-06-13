package com.example.administrator.getpet.ui.HelpStation;

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
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.Station;
import com.example.administrator.getpet.bean.application;
import com.example.administrator.getpet.bean.sPet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class ApplyAdoptActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ApplyAdoptActivity";

    private ImageButton ib_back;
    private TextView tv_finish;
    private EditText et_reason;
    ProgressDialog progress;

    private String spet_id;
    private String station_id;

    private final int SUCCESS = 1001;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_adopt);
        initView();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent == null)
        {
            ToastUtils.showToast(mContext,"数据加载失败！");
            return;
        }
        spet_id = intent.getStringExtra("spet_id");
        station_id = intent.getStringExtra("station_id");
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
                if (TextUtils.isEmpty(et_reason.getText().toString()))
                {
                    ToastUtils.showToast(mContext,"申请原因不能为空哦！");
                    return;
                }
                progress = new ProgressDialog(this);
                progress.setMessage("在正提交审核...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                adoptRequest();
                break;
        }
    }

    private void adoptRequest() {
        //传入表名和方法名
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("application","Insert");
        application apply = new application();
        apply.reason = et_reason.getText().toString();
        apply.state = 0;
        sPet spet = new sPet();
        spet.id = spet_id;
        apply.sPet = spet;
        Station station = new Station();
        station.id = station_id;
        apply.Station = station;
        users user = new users();
        user.id = preferences.getString("id","");
        apply.users = user;
        httpReponse.insert(apply, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                progress.dismiss();
                ToastUtils.showToast(mContext,"申请领养成功，请耐心等待审核");
                handler.sendEmptyMessageDelayed(SUCCESS,1000);
                return;
            }

            @Override
            public void Fail(String e) {
                progress.dismiss();
                ToastUtils.showToast(mContext,"提交审核失败！"+e);
            }
        });
    }
}
