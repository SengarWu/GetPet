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
import com.example.administrator.getpet.utils.GetPictureUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class AdoptDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AdoptDetailActivity";

    private ImageButton ib_back;//返回按钮
    private ImageView iv_spet_photo;//宠物图片
    private TextView tv_spet_name;//宠物名字
    private TextView tv_apply_username;//申请用户
    private TextView tv_apply_state;//申请状态
    private TextView tv_apply_reason;//申请原因
    private Button btn_agree;//
    private Button btn_disagree;//
    private application apply;//
//
    private ProgressDialog progress;//
//
    private int length;//数据集长度
    private int position;//当前点击项

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
        length = intent.getIntExtra("length",0);
        position = intent.getIntExtra("position",0);
        setupView();
    }

    private void setupView() {
        iv_spet_photo.setImageResource(GetPictureUtils.GetPicture(length)[position]);
        tv_spet_name.setText(apply.sPet.name);
        tv_apply_username.setText(apply.users.nickName);
        if (apply.state == 0)
        {
            tv_apply_state.setText("未审核");
            btn_agree.setVisibility(View.VISIBLE);
            btn_disagree.setVisibility(View.VISIBLE);
        }
        else if (apply.state == 1)
        {
            tv_apply_state.setText("已审核");
            btn_agree.setVisibility(View.INVISIBLE);
            btn_disagree.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv_apply_state.setText("审核未通过");
            btn_agree.setVisibility(View.INVISIBLE);
            btn_disagree.setVisibility(View.INVISIBLE);
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
        btn_disagree = $(R.id.btn_disagree);
        btn_disagree.setOnClickListener(this);
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
                                btn_agree.setVisibility(View.INVISIBLE);
                                btn_disagree.setVisibility(View.INVISIBLE);
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
            case R.id.btn_disagree:
                progress = new ProgressDialog(this);
                progress.setMessage("请稍后...");
                progress.setCanceledOnTouchOutside(true);
                progress.show();
                if (apply != null)
                {
                    if (apply.state == 0)
                    {
                        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("application","updateColumnsById");
                        httpReponse.addColumnParams("state","2");
                        httpReponse.updateColumnsById(apply.id, new HttpCallBack() {
                            @Override
                            public void Success(String data) {
                                progress.dismiss();
                                Log.d(TAG, "Success: data:"+data);
                                ToastUtils.showToast(mContext,"操作成功！");
                                tv_apply_state.setText("审核未通过");
                                btn_agree.setVisibility(View.INVISIBLE);
                                btn_disagree.setVisibility(View.INVISIBLE);
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
