package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.Date;

public class changeApplyApplication extends BaseActivity implements View.OnClickListener {
    private EditText detail;//详情
    private EditText connectplace;//联系地址
    private EditText connectphone;//联系电话
    private Button submit;//提交
    private ImageView back;//返回
    private applyApplication apply;//记录前一界面传来的信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_apply_application);
        initView();
    }
    private void initView() {
        Intent intent = this.getIntent();
        apply=(applyApplication)intent.getSerializableExtra("applyApplication");
        detail=(EditText)findViewById(R.id.details);
        connectplace=(EditText)findViewById(R.id.connect_place);
        connectphone=(EditText)findViewById(R.id.connect_phone);
        submit=(Button)findViewById(R.id.submit);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        connectphone.setText(apply.getPhoneNumber());
        connectplace.setText(apply.getConnectPlace());
        detail.setText(apply.getDetail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                //验证输入信息
                if (detail.getText().toString() == "") {
                    Toast.makeText(mContext, "请输入申请详情", Toast.LENGTH_LONG).show();
                } else if (connectplace.getText().toString() == "") {
                    Toast.makeText(mContext, "联系地址不能为空", Toast.LENGTH_LONG).show();
                } else if (connectphone.getText().toString() == "") {
                    Toast.makeText(mContext, "联系电话不能为空", Toast.LENGTH_LONG).show();
                } else {
                    SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","updateColumnsByWheres");
                    httpReponse.addWhereParams("id","=",apply.getId());
                    httpReponse.addColumnParams("phoneNumber",connectphone.getText().toString());
                    httpReponse.addColumnParams("connectPlace",connectplace.getText().toString());
                    httpReponse.addColumnParams("detail",detail.getText().toString());
                    httpReponse.updateColumnsByWheres( new HttpCallBack() {
                        @Override
                        public void Success(String data) {
                            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void Fail(String e) {
                            Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_LONG).show();
                        }
                    });
                }

                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
}
