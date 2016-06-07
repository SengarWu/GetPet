package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

public class MyEntrustDetail extends BaseActivity implements View.OnClickListener {
    private Button cancle;
    private Button modify;
    private Button lookapply;
    private TextView title;
    private TextView petname;
    private TextView award;
    private TextView details;
    private TextView Pubtime;
    private String entrustId;
    private String state;
    private String title2;
    private String petId;
    private String award2;
    private String details2;
    private Button giveComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_entrust_detail);
        initView();
    }

    private void initView() {
        cancle=(Button)findViewById(R.id.cancle);
        modify=(Button)findViewById(R.id.modify);
        lookapply=(Button)findViewById(R.id.look_apply);
        title=(TextView)findViewById(R.id.title);
        petname=(TextView)findViewById(R.id.petname);
        award=(TextView)findViewById(R.id.award);
        details=(TextView)findViewById(R.id.details);
        Pubtime=(TextView)findViewById(R.id.Pubtime);
        giveComment=(Button)findViewById(R.id.giveComment);
        cancle.setOnClickListener(this);
        modify.setOnClickListener(this);
        lookapply.setOnClickListener(this);
        giveComment.setOnClickListener(this);
        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        petname.setText(intent.getStringExtra("petname"));
        award.setText(intent.getStringExtra("award"));
        details.setText(intent.getStringExtra("content"));
        Pubtime.setText(intent.getStringExtra("pubtime"));
        entrustId=intent.getStringExtra("entrustId");
        state=intent.getStringExtra("state");
        //记录下传过来的数据
        title2=intent.getStringExtra("title");
        petId=intent.getStringExtra("petId");
        award2=intent.getStringExtra("award");
        details2=intent.getStringExtra("content");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancle:
                //检查对应的领养申请状态
                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QueryListX");
                //调加要查看的列  可以添加多列
                httpReponse.addViewColumnsParams("id");
                httpReponse.addViewColumnsParams("result");
                //添加条件 比如：Address=xiaoxinzhuang
                httpReponse.addWhereParams("entrustId","=",entrustId);
                //调用QueryListX方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
                httpReponse.QueryListX(-1,2, new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        applyApplication[] list= JSONUtil.parseArray(data,applyApplication.class);
                        if(list.length>0){
                            boolean T=false;
                            for(int i=0;i<list.length;i++){
                                if(list[i].getResult()==1){
                                      T=true;
                                }
                            }
                            if(T){
                                changeApplication();
                                canclestraight();
                            }else{
                                changeApplication();
                                deductUser(10);
                                canclestraight();
                            }
                        }else{
                            canclestraight();
                        }
                    }
                    @Override
                    public void Fail(String e)
                    {
                        Toast.makeText(getApplicationContext(),"删除失败，请检查网络",Toast.LENGTH_LONG).show();
                    }
                });
                this.finish();
                break;
            case R.id.modify:
                if(state=="1"||state=="2"){
                    Toast.makeText(getApplicationContext(),"事务已失效或完结",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(this, ModifyEntrust.class);
                    intent.putExtra("title",title2);
                    intent.putExtra("petId",petId);
                    intent.putExtra("award",award2);
                    intent.putExtra("details",details2);
                    intent.putExtra("petId",petId);
                    intent.putExtra("entrustId",entrustId);
                    startActivity(intent);
                }
                break;
            case R.id.look_apply:
                Intent intent2=new Intent(this,LookApplication.class);
                intent2.putExtra("entrustId",entrustId);
                startActivity(intent2);
                break;
            case R.id.giveComment:
                if(state=="2"){
                    Intent intent=new Intent(this,endEntrust.class);
                    intent.putExtra("entrustId",entrustId);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"尚未接受任何申请",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * 降低用户的积分
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

            }
        });
    }

    /**
     * 让对应的所有领养申请失效
     */
    private void changeApplication() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","updateColumnsByWheres");
        httpReponse.addWhereParams("entrustId","=",entrustId);
        httpReponse.addColumnParams("result","2");
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
            }
            @Override
            public void Fail(String e) {

            }
        });
    }

    /**
     * 撤销寄养信息
     */
    private void canclestraight() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",entrustId);
        httpReponse.addColumnParams("status","1");
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(getApplicationContext(),"删除失败，请检查网络",Toast.LENGTH_LONG).show();
            }
        });
    }

}
