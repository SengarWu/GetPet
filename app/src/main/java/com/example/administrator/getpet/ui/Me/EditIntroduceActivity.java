package com.example.administrator.getpet.ui.Me;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    private ImageButton ib_back;//返回按钮
    private TextView tv_finish;//完成
    private EditText et_nickname;//
    private RadioButton rb_man;//
    private RadioButton rb_woman;//
    private EditText et_age;//
    private EditText et_address;//
    private EditText et_phone;//
    private EditText et_occupation;//
    private EditText et_personal;//
//
    private String userId;//id
    private String nickname;//昵称
    private String age;//年龄
    private String address;//地址
    private String phone;//手机号
    private String occupation;//职业
    private String personal;//
    private String sex;//性别

    private ProgressDialog progress;

    private final int SUCCESS = 1001;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    editor.putString("nickName",nickname);
                    editor.putString("sex",sex);
                    editor.putInt("age",Integer.valueOf(age));
                    editor.putString("address",address);
                    editor.putString("phone",phone);
                    editor.putString("personal",personal);
                    editor.putString("occupation",occupation);
                    editor.commit();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_introduce);
        initView();
        loadData();
    }

    /**
     * 加载本地数据
     */
    private void loadData() {
        et_nickname.setText(preferences.getString("nickName",""));
        et_age.setText(String.valueOf(preferences.getInt("age",0)));
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

    /**
     *
     */
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
                progress = new ProgressDialog(this);
                progress.setMessage("请等待...");
                progress.show();
                submit();
                break;
        }
    }

    /**
     *提交数据到服务器
     */
    private void submit() {
        userId = preferences.getString("id","");
        nickname = et_nickname.getText().toString();
        age = et_age.getText().toString();
        address = et_address.getText().toString();
        phone = et_phone.getText().toString();
        occupation = et_occupation.getText().toString();
        personal = et_personal.getText().toString();
        sex = "男";
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
                handler.sendEmptyMessageDelayed(SUCCESS,1000);
                return;
            }

            @Override
            public void Fail(String e) {
                ToastUtils.showToast(mContext,"修改失败!"+e);
                Log.d(TAG, "Fail: e:"+e);
            }
        });
    }
}
