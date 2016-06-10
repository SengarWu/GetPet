package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.sql.Time;

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
    private entrust Curentrust;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_entrust_detail);
        initView();
    }

    private void initView() {
        progress = showProgress(MyEntrustDetail.this, "加载数据中...");
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
        final Intent intent=getIntent();
        entrustId=intent.getStringExtra("entrustId");
        //传入表名和方法名   方法名：QuerySinglebyid
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QuerySinglebyid");
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QuerySinglebyid(entrustId, new HttpCallBack() {
            @Override
            public void Success(String data) {
                progress.dismiss();
                Curentrust=JSONUtil.parseObject(data, entrust.class);
                title.setText(Curentrust.getTitle());
                petname.setText(Curentrust.getPet().getName());
                award.setText(String.valueOf(Curentrust.getAward()));
                details.setText("    "+Curentrust.getDetail());
                Pubtime.setText(TimeUtils.dateToString(Curentrust.getDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));

                state=Curentrust.getStatus();
                //记录下传过来的数据
                title2=Curentrust.getTitle();
                petId=Curentrust.getPet().getId();
                award2=String.valueOf(Curentrust.getAward());
                details2=Curentrust.getDetail();
            }
            @Override
            public void Fail(String e) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(),"网络忙，数据更新失败",Toast.LENGTH_LONG).show();
                title.setText(intent.getStringExtra("title"));
                petname.setText(intent.getStringExtra("petname"));
                award.setText(intent.getStringExtra("award"));
                details.setText("    "+intent.getStringExtra("content"));
                Pubtime.setText(intent.getStringExtra("pubtime"));

                state=intent.getStringExtra("state");
                //记录下传过来的数据
                title2=intent.getStringExtra("title");
                petId=intent.getStringExtra("petId");
                award2=intent.getStringExtra("award");
                details2=intent.getStringExtra("content");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancle:
                if(state.equals("正常")) {
                    //检查对应的领养申请状态
                    SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("applyApplication", "QueryListX");
                    //调加要查看的列  可以添加多列
                    httpReponse.addViewColumnsParams("id");
                    httpReponse.addViewColumnsParams("result");
                    //添加条件 比如：Address=xiaoxinzhuang
                    httpReponse.addWhereParams("entrustId", "=", entrustId);
                    //调用QueryListX方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
                    httpReponse.QueryListX(-1, 2, new HttpCallBack() {
                        @Override
                        public void Success(String data) {
                            applyApplication[] list = JSONUtil.parseArray(data, applyApplication.class);
                            if (list.length > 0) {
                                boolean T = false;
                                for (int i = 0; i < list.length; i++) {
                                    if (list[i].getResult() == 1) {
                                        T = true;
                                    }
                                }
                                if (T) {
                                    changeApplication();
                                    canclestraight();
                                } else {
                                    changeApplication();
                                    deductUser(10);
                                    canclestraight();
                                }
                            } else {
                                canclestraight();
                            }
                            MyEntrustDetail.this.finish();
                        }

                        @Override
                        public void Fail(String e) {
                            Toast.makeText(getApplicationContext(), "撤销失败，请检查网络", Toast.LENGTH_LONG).show();
                        }
                    });
                }else if (state.equals("已取消")){
                    Toast.makeText(getApplicationContext(), "已经取消了", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "事务以及完结了", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.modify:
                if(state.equals("已取消")||state.equals("已解决")){
                    Toast.makeText(getApplicationContext(),"不可修改（事务已取消,或者已同意了领养申请）",Toast.LENGTH_LONG).show();
                }else {
                    //更新寄养新目前的状态
                    SimpleHttpPostUtil httpReponse2= new SimpleHttpPostUtil("entrust","QuerySinglebywheresX");
                    //添加条件 比如：Name=12
                    httpReponse2.addWhereParams("id","=",entrustId);
                    //添加要查询的列  可以添加多列
                    httpReponse2.addViewColumnsParams("status");
                    //调用QuerySinglebywheresX方法
                    httpReponse2.QuerySinglebywheresX(new HttpCallBack() {
                        @Override
                        public void Success(String data) {
                            //接受查询结果
                            entrust aa=JSONUtil.parseObject(data,entrust.class);
                            if(aa.getStatus().equals("正常")) {
                                Intent intent = new Intent(MyEntrustDetail.this, ModifyEntrust.class);
                                intent.putExtra("title", title2);
                                intent.putExtra("petId", petId);
                                intent.putExtra("award", award2);
                                intent.putExtra("details", details2);
                                intent.putExtra("petId", petId);
                                intent.putExtra("entrustId", entrustId);
                                startActivity(intent);
                                MyEntrustDetail.this.finish();
                            }else if(aa.getStatus().equals("已取消")){
                                Toast.makeText(getApplicationContext(),"不可修改（事务已失效）",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"不可修改（已经同意了领养申请）",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void Fail(String e)
                        {
                            Toast.makeText(getApplicationContext(),"网络忙，请稍后再试",Toast.LENGTH_LONG).show();
                        }
                    });

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
        editor.putString("reputation",String.valueOf(currentRepu));
        editor.commit();
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
        httpReponse.addColumnParams("status","已取消");
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(getApplicationContext(),"撤销成功",Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(getApplicationContext(),"删除失败，请检查网络",Toast.LENGTH_LONG).show();
            }
        });
    }

    //显示加载进度栏
    private ProgressDialog showProgress(Activity activity, String hintText) {
        Activity mActivity;
        if (activity.getParent() != null) {
            mActivity = activity.getParent();
            if (mActivity.getParent() != null) {
                mActivity = mActivity.getParent();
            }
        } else {
            mActivity = activity;
        }
        final Activity finalActivity = mActivity;
        ProgressDialog window = ProgressDialog.show(finalActivity, "", hintText);
        window.getWindow().setGravity(Gravity.CENTER);

        window.setCancelable(false);
        return window;
    }
}
