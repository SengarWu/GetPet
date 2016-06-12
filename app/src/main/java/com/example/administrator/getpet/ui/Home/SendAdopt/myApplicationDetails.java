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
    private TextView title;//标题
    private ImageView sex;//性别
    private TextView username;//用户名
    private TextView pet_character;//宠物性格
    private TextView award;//悬赏
    private ImageView type;//宠物类别
    private TextView details;//详情
    private TextView apply_message;//申请信息集合
    private ImageView comment_result;//评价结果
    private Button remove;//撤回
    private Button modify;//更改
    private applyApplication apply;//记录前一个页面传过来的领养申请信息
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
        details.setText("      "+apply.getEntrust().getDetail()+"\n"+"      联系电话："+apply.getEntrust().getUsers().getPhone());
        apply_message.setText("      "+apply.getDetail()+"\n"+"      联系地址"+apply.getConnectPlace()+"\n"+"      联系电话"+apply.getPhoneNumber());
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
                //先判断领养申请信息的状态
                if(apply.getResult()==2){
                    //已经失效了则不做任何操作
                    Toast.makeText(this, "改信息已被取消", Toast.LENGTH_LONG).show();
                }
                if(apply.getResult()==0){
                    //状态为正常则直接撤回
                    removeStraight();
                }
                if(apply.getResult()==1){
                    //如果申请已经被接受则除了撤回还要降低用户积分
                    removeStraightAndDeduce();
                }
                break;
            case R.id.modify:
                //如果信息没有被取消则可以修改
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
/*
撤回申请并降低用户积分
 */
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
/*
把对应的寄养信息状态改为正常
 */
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
/*
直接撤回
 */
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
/*
降低用户积分
 */
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
