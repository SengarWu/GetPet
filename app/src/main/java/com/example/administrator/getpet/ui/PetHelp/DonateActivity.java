package com.example.administrator.getpet.ui.PetHelp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.donate;
import com.example.administrator.getpet.bean.sPet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

import java.util.Calendar;

public class DonateActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DonateActivity";

    private ImageButton ib_back;
    private EditText et_donate_money;
    private EditText et_donate_words;
    private TextView tv_donate_money;
    private Button btn_donate;
    private String id;

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
        setContentView(R.layout.activity_donate);
        initView();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent == null)
        {
            ToastUtils.showToast(mContext,"数据加载有误！");
            return;
        }
        id = intent.getStringExtra("id");
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        et_donate_money = $(R.id.et_donate_money);
        et_donate_money.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        tv_donate_money.setText(et_donate_money.getText().toString());
                    }
                }
        );
        et_donate_words = $(R.id.et_donate_words);
        tv_donate_money = $(R.id.tv_donate_money);
        btn_donate = $(R.id.btn_donate);
        btn_donate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_donate:
                AlertDialog.Builder builder = new AlertDialog.Builder(DonateActivity.this);
                builder.setMessage("您确认为该宠物捐赠"+tv_donate_money.getText().toString()+"元吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        donateRequest();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
        }
    }

    private void donateRequest() {
        //传入表名和方法名
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("donate","Insert");
        donate dona = new donate();
        dona.money = Double.parseDouble(et_donate_money.getText().toString());
        dona.message = et_donate_words.getText().toString();
        Calendar c = Calendar.getInstance();
        dona.time = c.getTime();
        sPet spet = new sPet();
        spet.id = id;
        dona.sPet = spet;
        users user = new users();
        user.id = preferences.getString("id","");
        dona.users = user;
        httpReponse.insert(dona, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                ToastUtils.showToast(mContext,"捐赠成功！感谢您的捐赠！");
                handler.sendEmptyMessageDelayed(SUCCESS,1000);
                return;
            }

            @Override
            public void Fail(String e) {
                Log.d(TAG, "Fail: e:"+e);
                ToastUtils.showToast(mContext,"捐赠失败!"+e);
            }
        });
    }
}
