package com.example.administrator.getpet.ui.PetHelp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

public class DonateActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private EditText et_donate_money;
    private EditText et_donate_words;
    private TextView tv_donate_money;
    private Button btn_donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        initView();
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
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("Users","Insert");
    }
}
