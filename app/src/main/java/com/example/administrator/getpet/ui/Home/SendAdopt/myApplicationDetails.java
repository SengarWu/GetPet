package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

public class myApplicationDetails extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView sex;
    private TextView username;
    private TextView pet_character;
    private TextView award;
    private ImageView type;
    private TextView details;
    private TextView apply_message;
    private TextView connect_phone;
    private TextView connect_place;
    private ImageView comment_result;
    private Button remove;
    private Button modify;
    private applyApplication apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application_details);
        initView();
    }

    private void initView() {
        title=(TextView)findViewById(R.id.title);
        sex=(ImageView)findViewById(R.id.sex);
        username=(TextView)findViewById(R.id.username);
        pet_character=(TextView)findViewById(R.id.pet_chatacter);
        award=(TextView)findViewById(R.id.award);
        type=(ImageView) findViewById(R.id.type);
        details=(TextView)findViewById(R.id.details);
        apply_message=(TextView)findViewById(R.id.apply_message);
        connect_phone=(TextView)findViewById(R.id.connect_phone);
        connect_place=(TextView)findViewById(R.id.connect_place);
        comment_result=(ImageView) findViewById(R.id.comment_result);
        remove=(Button)findViewById(R.id.remove);
        modify=(Button)findViewById(R.id.modify);
        remove.setOnClickListener(this);
        modify.setOnClickListener(this);
        //显示数据详情
        Intent intent = this.getIntent();
        apply=(applyApplication)intent.getSerializableExtra("applyApplication");
        title.setText(apply.getEntrust().getTitle());
        if(apply.getEntrust().getUsers().getSex().equals("男")){
            sex.setImageResource(R.mipmap.boy);
        }else if(apply.getEntrust().getUsers().getSex().equals("女")){
            sex.setImageResource(R.mipmap.girl);
        }
        username.setText(apply.getEntrust().getUsers().getNickName());
        pet_character.setText(apply.getEntrust().getPet().getCharacter());
        award.setText(String.valueOf(apply.getEntrust().getAward()));
        switch (apply.getEntrust().getPet().getCategory().getName()){
            case "猫":
                type.setImageResource(R.mipmap.cat_type);
                break;
            case "狗":
                type.setImageResource(R.mipmap.dog);
                break;
            case "鱼":
                type.setImageResource(R.mipmap.type_fish);
                break;
            case "其他":
                type.setImageResource(R.mipmap.type_other);
                break;
        }
        details.setText("      "+apply.getEntrust().getDetail()+"\n"+"联系电话："+apply.getEntrust().getUsers().getPhone());
        apply_message.setText("      "+apply.getApplyMessage());
        connect_phone.setText(apply.getPhoneNumber());
        connect_place.setText(apply.getConnectPlace());
        if(apply.getComment().equals("好评")){
            comment_result.setImageResource(R.mipmap.haoping);
        }else if(apply.getComment().equals("好评")){
            comment_result.setImageResource(R.mipmap.chaping);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.remove:
                if(apply.getResult()==2){
                    Toast.makeText(this, "改信息已被取消", Toast.LENGTH_LONG).show();
                }
                if(apply.getResult()==0){
                    removeStraight();
                }
                if(apply.getResult()==1){
                    removeStraightAndDeduce();
                }
                break;
            case R.id.modify:
                if(apply.getResult()!=2){
                      Intent x=new Intent(myApplicationDetails.this,changeApplyApplication.class);
                      Bundle bundle = new Bundle();
                      bundle.putSerializable("applyApplication", apply);
                      x.putExtras(bundle);
                      startActivity(x);
                      this.finish();
                }else{
                    Toast.makeText(this, "改信息已被取消", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void removeStraightAndDeduce() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",apply.getId());
        //注意使用第二个条件时  要加上和其他条件关系
        httpReponse.addColumnParams("result","2");
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(myApplicationDetails.this, "撤回成功", Toast.LENGTH_LONG).show();
                deductUser(5);
                changeentrustTOnormal();
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(myApplicationDetails.this, "撤回失败，请检查网络", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeentrustTOnormal() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","updateColumnsById");
        String id=apply.getEntrust().getId();

        httpReponse.addColumnParams("status","正常");
        httpReponse.updateColumnsById(id, new HttpCallBack() {
            @Override
            public void Success(String data) {
;                Toast.makeText(myApplicationDetails.this, "信息已还原", Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
                 Toast.makeText(myApplicationDetails.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeStraight() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",apply.getId());
        //注意使用第二个条件时  要加上和其他条件关系
        httpReponse.addColumnParams("result","2");
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(myApplicationDetails.this, "已撤回", Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(myApplicationDetails.this, "撤回失败，请检查网络", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deductUser(int x) {
        int currentRepu=Integer.valueOf(preferences.getString("reputation",""));
        currentRepu=currentRepu-x;
        //修改指定列根据条件
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("Users","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",preferences.getString("id",""));
        httpReponse.addColumnParams("user_reputation",String.valueOf(currentRepu));
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(getApplicationContext(),"信誉度下降",Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        });
        editor.putString("reputation",String.valueOf(currentRepu));
        editor.commit();
    }
}
