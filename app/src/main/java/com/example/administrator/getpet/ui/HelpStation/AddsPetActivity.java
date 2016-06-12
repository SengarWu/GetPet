package com.example.administrator.getpet.ui.HelpStation;

import android.app.ProgressDialog;
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
import com.example.administrator.getpet.bean.sPet;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class AddsPetActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddsPetActivity";

    private ImageButton ib_back;
    private TextView tv_finish;
    private EditText et_spet_name;
    private EditText et_spet_age;
    private EditText et_spet_state;
    private EditText et_spet_money;
    private EditText et_spet_address;

    private String spet_name;
    private String spet_age;
    private String spet_state;
    private String spet_money;
    private String spet_address;

    private sPet spet;

    private ProgressDialog progress;

    private final int SUCCESS = 1001;
    private Handler handler = new Handler(){
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
        setContentView(R.layout.activity_adds_pet);
        initView();
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_finish = $(R.id.tv_finish);
        tv_finish.setOnClickListener(this);
        et_spet_name = $(R.id.et_spet_name);
        et_spet_age = $(R.id.et_spet_age);
        et_spet_state = $(R.id.et_spet_state);
        et_spet_money = $(R.id.et_spet_money);
        et_spet_address = $(R.id.et_spet_address);
        progress = new ProgressDialog(this);
        progress.setMessage("请稍后...");
        progress.setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_finish:
                spet_name = et_spet_name.getText().toString();
                spet_age = et_spet_age.getText().toString();
                spet_state = et_spet_state.getText().toString();
                spet_money = et_spet_money.getText().toString();
                spet_address = et_spet_address.getText().toString();
                if (TextUtils.isEmpty(spet_name))
                {
                    ToastUtils.showToast(mContext,"名字不能为空");
                    return;
                }
                if (TextUtils.isEmpty(spet_age))
                {
                    ToastUtils.showToast(mContext,"年龄不能为空");
                    return;
                }
                if (TextUtils.isEmpty(spet_state))
                {
                    ToastUtils.showToast(mContext,"健康状况不能为空");
                    return;
                }
                if (TextUtils.isEmpty(spet_address))
                {
                    ToastUtils.showToast(mContext,"地址不能为空");
                    return;
                }
                try {
                    spet = new sPet();
                    spet.name = spet_name;
                    spet.age = Integer.parseInt(spet_age);
                    spet.state = spet_state;
                    spet.money = Double.parseDouble(spet_money);
                    spet.address = spet_address;
                    Station station = new Station();
                    station.id = preferences.getString("id","");
                    Log.d(TAG, "onClick: id:"+station.id);
                    spet.Station = station;
                }
                catch (Exception e)
                {
                    ToastUtils.showToast(mContext,e.toString());
                }
                progress.show();
                addSPet();
                break;
        }
    }

    private void addSPet() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("sPet","Insert");
        httpReponse.insert(spet, new HttpCallBack() {
            @Override
            public void Success(String data) {
                progress.dismiss();
                ToastUtils.showToast(mContext,"添加成功！");
                Log.d(TAG, "Success: data:"+data);
                clear();
                //延迟一秒再跳转
                handler.sendEmptyMessageDelayed(SUCCESS,1000);
                return;
            }

            @Override
            public void Fail(String e) {
                Log.d(TAG, "Fail: "+e);
                progress.dismiss();
                ToastUtils.showToast(mContext,e);
            }
        });
    }

    private void clear() {
        et_spet_address.setText("");
        et_spet_age.setText("");
        et_spet_money.setText("");
        et_spet_name.setText("");
        et_spet_state.setText("");
    }
}
