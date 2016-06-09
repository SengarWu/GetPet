package com.example.administrator.getpet.ui.Me;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class EditIntroduceActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "EditIntroduceActivity";

    private ImageButton ib_back;
    private TextView tv_finish;
    private EditText et_nickname;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private EditText et_age;
    private EditText et_address;
    private EditText et_phone;
    private EditText et_occupation;
    private EditText et_personal;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_introduce);
        initView();
        loadData();
    }

    private void loadData() {
        et_nickname.setText(preferences.getString("nickName",""));
        et_age.setText(preferences.getInt("age",0));
        et_address.setText(preferences.getString("address",""));
        et_phone.setText(preferences.getString("phone",""));
        et_personal.setText(preferences.getString("personal",""));
        et_occupation.setText(preferences.getString("occupation",""));
        String sex = preferences.getString("sex","");
        if (!TextUtils.isEmpty(sex))
        {
            if (sex.equals("男"))
            {
                rb_man.setChecked(true);
            }
            else
            {
                rb_woman.setChecked(true);
            }
        }
    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_finish = $(R.id.tv_finish);
        tv_finish.setOnClickListener(this);
        et_nickname = $(R.id.et_nickname);
        rb_man = $(R.id.rb_man);
        rb_woman = $(R.id.rb_woman);
        et_age = $(R.id.et_age);
        et_address = $(R.id.et_address);
        et_phone = $(R.id.et_phone);
        et_occupation = $(R.id.et_occupation);
        et_personal = $(R.id.et_personal);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_finish:
                progress = new ProgressDialog(mContext);
                progress.setMessage("请等待...");
                progress.show();
                submit();
                break;
        }
    }

    private void submit() {
        String userId = preferences.getString("id","");
        String nickname = et_nickname.getText().toString();
        String age = et_age.getText().toString();
        String address = et_address.getText().toString();
        String phone = et_phone.getText().toString();
        String occupation = et_occupation.getText().toString();
        String personal = et_personal.getText().toString();
        String sex = "男";
        if (rb_man.isChecked())
        {
            sex = "男";
        }
        else
        {
            sex = "女";
        }
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("users","updateColumnsById");
        httpReponse.addColumnParams("nickname",nickname);
        httpReponse.addColumnParams("age",age);
        httpReponse.addColumnParams("address",address);
        httpReponse.addColumnParams("phone",phone);
        httpReponse.addColumnParams("occupation",occupation);
        httpReponse.addColumnParams("personal",personal);
        httpReponse.addColumnParams("sex",sex);
        httpReponse.updateColumnsById(userId, new HttpCallBack() {
            @Override
            public void Success(String data) {
                //修改成功
                progress.dismiss();
                Log.d(TAG, "Success: data:"+data);
                ToastUtils.showToast(mContext,"修改成功!");
                finish();
            }

            @Override
            public void Fail(String e) {
                ToastUtils.showToast(mContext,"修改失败!"+e);
                Log.d(TAG, "Fail: e:"+e);
            }
        });
    }
}
