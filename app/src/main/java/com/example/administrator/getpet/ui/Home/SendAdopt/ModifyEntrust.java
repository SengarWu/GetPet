package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyEntrust extends BaseActivity implements View.OnClickListener {
    private ImageView back;//返回
    private EditText award;//悬赏金额
    private EditText title;//标题
    private Spinner pet_list;//宠物列表
    private EditText content;//寄养信息内容
    private Button modify;//修改提交按钮
    private List<pet> mypetList;//个人宠物列表
    private String petId;//宠物信息id
    private String entrustId;//寄养信息id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_entrust);
        initView();
    }

    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        award=(EditText)findViewById(R.id.award);
        pet_list=(Spinner)findViewById(R.id.pet_list);
        content=(EditText)findViewById(R.id.content);
        title=(EditText)findViewById(R.id.title);
        modify=(Button)findViewById(R.id.modify);
        back.setOnClickListener(this);
        modify.setOnClickListener(this);
        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        award.setText(intent.getStringExtra("award"));
        content.setText(intent.getStringExtra("details"));
        petId=intent.getStringExtra("petId");
        entrustId=intent.getStringExtra("entrustId");
        showmypet();//查询用户的宠物并显示为列表
    }
    /*
    查询当前用户录入的所有宠物
     */
    private void showmypet() {
        //发送http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("pet","QueryList");
        //只查询本用户的宠物
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));
        httpReponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                //解析jason
                mypetList= Arrays.asList(JSONUtil.parseArray(data,pet.class));
                //设置宠物名称字符
                List<String> namelist=new ArrayList<String>();
                //把结果显示到控件中
                for(int i=0;i<mypetList.size();i++){
                    namelist.add(mypetList.get(i).getName());
                }
                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(ModifyEntrust.this,R.layout.spinner_check,namelist);
                pet_list.setAdapter(adapter2);
                for(int i=0;i<mypetList.size();i++){
                    if(mypetList.get(i).getId().equals(petId));
                    {
                        pet_list.setSelection(i);
                    }
                }
            }
            @Override
            public void Fail(String e)
            {
                Toast.makeText(getApplicationContext(), "暂无宠物信息",Toast.LENGTH_LONG).show();
            }
        });
    }
/*
点击事件处理
 */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.modify:
                //修改寄养信息
                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","updateColumnsById");
                httpReponse.addColumnParams("title",title.getText().toString());
                httpReponse.addColumnParams("detail",content.getText().toString());
                httpReponse.addColumnParams("award",award.getText().toString());
                httpReponse.addColumnParams("petId",mypetList.get(pet_list.getSelectedItemPosition()).getId());
                httpReponse.updateColumnsById(entrustId, new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void Fail(String e) {
                        Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}
