package com.example.administrator.getpet.ui.HelpStation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.application;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class AdoptDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AdoptDetailActivity";

    private ImageButton ib_back;
    private ImageView iv_spet_photo;
    private TextView tv_spet_name;
    private TextView tv_apply_username;
    private TextView tv_apply_state;
    private TextView tv_apply_reason;
    private Button btn_agree;

    private application apply;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_detail);
        initView();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent == null)
        {
            ToastUtils.showToast(mContext,"数据加载错误！");
            return;
        }
        apply = (application) intent.getSerializableExtra("application");
        if (apply == null)
        {
            ToastUtils.showToast(mContext,"数据加载错误！");
            return;
        }
        setupView();
    }

    private void setupView() {
        tv_spet_name.setText(apply.sPet.name);
        tv_apply_username.setText(apply.users.nickName);
        if (apply.state == 0)
        {
            tv_apply_state.setText("未审核");
            btn_agree.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_apply_state.setText("已审核");
            btn_agree.setVisibility(View.INVISIBLE);
        }
        tv_apply_reason.setText(apply.reason);
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        iv_spet_photo = $(R.id.iv_spet_photo);
        tv_spet_name = $(R.id.tv_spet_name);
        tv_apply_username = $(R.id.tv_apply_username);
        tv_apply_state = $(R.id.tv_apply_state);
        tv_apply_reason = $(R.id.tv_apply_reason);
        btn_agree = $(R.id.btn_agree);
        btn_agree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_agree:
                progress = new ProgressDialog(this);
                progress.setMessage("请稍后...");
                progress.setCanceledOnTouchOutside(true);
                progress.show();
                if (apply != null)
                {
                    if (apply.state == 0)
                    {
                        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("application","updateColumnsById");
                        httpReponse.addColumnParams("state","1");
                        httpReponse.updateColumnsById(apply.id, new HttpCallBack() {
                            @Override
                            public void Success(String data) {
                                progress.dismiss();
                                Log.d(TAG, "Success: data:"+data);
                                ToastUtils.showToast(mContext,"操作成功！");
                                tv_apply_state.setText("已审核");
                            }

                            @Override
                            public void Fail(String e) {
                                progress.dismiss();
                                Log.d(TAG, "Fail: e:"+e);
                                ToastUtils.showToast(mContext,e);
                            }
                        });
                    }
                }

                break;
        }
    }
}
