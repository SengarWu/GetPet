package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;

public class PublishEntrust extends BaseActivity implements View.OnClickListener {
    private EditText title;//标题
    private EditText content;//内容
    private EditText award;//悬赏金额
    private Spinner pet_list;//宠物列表
    private TextView city;//城市
    private ImageView sumit;//提交
    private String citystr;//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_entrust);
        init();
    }

    private void init() {
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        pet_list=(Spinner) findViewById(R.id.pet_list);
        award=(EditText)findViewById(R.id.award);
        city=(TextView) findViewById(R.id.city);
        sumit=(ImageView) findViewById(R.id.submit);
        city.setOnClickListener(this);
        sumit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.submit:
                entrust n_entrust=new entrust();
                n_entrust.setTitle(title.getText().toString());
                n_entrust.setDetail(content.getText().toString());
                //n_entrust.setPetId(pet_list.getSelectedItem().toString());
                //n_entrust.setUser_id();
                n_entrust.setAward(Integer.valueOf(award.getText().toString()));
                //获取系统当前时间
                Calendar a= Calendar.getInstance();
                java.util.Date d=a.getTime();
                String datestr=String.valueOf(d.getYear())+"/"+String.valueOf(d.getMonth())+"/"+
                        String.valueOf(d.getDate())+" "+String.valueOf(d.getHours())+":"+String.valueOf(d.getMinutes())
                        +":"+String.valueOf(d.getSeconds());
                Date date= TimeUtils.stringToDate(datestr,TimeUtils.FORMAT_DATE_TIME_SECOND);
                n_entrust.setDate(date);
                //设置用户

                //设置宠物

                n_entrust.setCity(citystr);
                //发送发布寄养信息的数据请求

                break;
            case R.id.city:
                startActivityForResult(new Intent(this, SelectCityActivity.class), 99);
                break;
        }
    }

    public void submit() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","Insert");

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {
            switch (resultCode) {
                case 99:
                    String receive=data.getStringExtra("lngCityName");
                    city.setText(receive);
                    if(!receive.equals("所有城市")) {
                        citystr = data.getStringExtra("lngCityName");
                    }else{
                        citystr="";
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
