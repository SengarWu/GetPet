package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PublishEntrust extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText title;//标题
    private EditText content;//内容
    private EditText award;//悬赏金额
    private Spinner pet_list;//宠物列表
    private TextView city;//城市
    private ImageView sumit;//提交
    private String citystr="所有城市";//城市
    private List<pet> mypetList;//个人宠物列表
    private int petIndex;
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
        showmypet();
        pet_list.setOnItemSelectedListener(this);
    }

    private void showmypet() {
        //发送http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("pet","QueryList");
        //只查询本用户的宠物
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));
        httpReponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                //解析jason
                List<pet> mypetList= Arrays.asList(JSONUtil.parseArray(data,pet.class));
                //设置宠物名称字符
                List<String> namelist=new ArrayList<String>();
                //设置默认的寄养宠物
                petIndex=mypetList.size()-1;
                //把结果显示到控件中
                for(int i=0;i<mypetList.size();i++){
                    namelist.add(mypetList.get(i).getName());
                }
                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(PublishEntrust.this,R.layout.spinner_check,namelist);

                pet_list.setAdapter(adapter2);
            }
            @Override
            public void Fail(String e)
            {
                Toast.makeText(getApplicationContext(), "暂无宠物信息",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.submit:
                boolean T=true;
                //标题不为空检查
                if(title.getText().toString()=="") {
                    T=false;
                }
                //内容不为空检查
                if(content.getText().toString()=="") {
                    T=false;
                }
                //悬赏不为空检查
                if(award.getText().toString()=="") {
                    T=false;
                }
                if(T) {
                    submit();
                }else{
                    Toast.makeText(getApplicationContext(),"请填写完整内容",Toast.LENGTH_LONG);
                }
                break;
            case R.id.city:
                startActivityForResult(new Intent(this, SelectCityActivity.class), 99);
                break;

        }
    }

    /**
     *
     */
    public void submit() {
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
        users x=new users();
        x.setId(preferences.getString("id",""));
        n_entrust.setUsers(x);
        //设置宠物
        pet entrust_pet=mypetList.get(petIndex);
        n_entrust.setPet(entrust_pet);
        if(citystr!="所有城市") {
            n_entrust.setCity(citystr);
        }else {
            n_entrust.setCity("");
        }
        //发送发布寄养信息的数据请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","Insert");
        httpReponse.insert(n_entrust, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(getApplicationContext(), "插入成功",Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(getApplicationContext(), "插入失败",Toast.LENGTH_LONG).show();
            }
        });

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        petIndex=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
