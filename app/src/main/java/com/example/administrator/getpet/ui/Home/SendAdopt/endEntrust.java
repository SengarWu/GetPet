package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.view.RoundImageView;

/*
给予评价
 */
public class endEntrust extends BaseActivity implements View.OnClickListener {
    private ImageView comment;//评价结果图
    private Button giveComment;//给予评价按钮
    private String entrustId;
    private ProgressDialog prograss;//加载时显示的框
    private String applyComment;//评价
    private String applyUserId;//通过申请的用户id
    private String applyId;//通过的申请id
    private String newComment;//新添加的评价
    private int applyUserReputation;//要评价用户的积分
    private RoundImageView user_head;//用户头像
    private TextView username;//用户名
    private TextView occupation;//职业
    private TextView phonenumber;//电话
    private TextView reputation;//信誉
    private TextView personal;//个人简介
    private TextView apply_message;//申请信息
    private TextView age;//申请信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_entrust);
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        entrustId=intent.getStringExtra("entrustId");
        /*
        界面控件的获取
         */
        user_head=(RoundImageView)findViewById(R.id.user_head);
        username=(TextView)findViewById(R.id.username);
        occupation=(TextView)findViewById(R.id.occupation);
        phonenumber=(TextView)findViewById(R.id.phonenumber);
        reputation=(TextView)findViewById(R.id.reputation);
        personal=(TextView)findViewById(R.id.personal);
        apply_message=(TextView)findViewById(R.id.apply_message);
        age=(TextView)findViewById(R.id.age);
        comment=(ImageView)findViewById(R.id.comment);
        giveComment=(Button)findViewById(R.id.giveComment);
        giveComment.setOnClickListener(this);
        prograss=new ProgressDialog(this);
        prograss.setMessage("正在加载数据");
        prograss.setCanceledOnTouchOutside(false);
        prograss.show();
        searchApplication();//显示对应的领养申请信息

    }
/*
查询领养申请
 */
    private void searchApplication() {
        //传入表名和方法名   方法名：QuerySinglebywheresX
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QuerySinglebywheres");
        httpReponse.addWhereParams("entrustId","=",entrustId);
        httpReponse.addWhereParams("result","=","1","and");
        httpReponse.QuerySinglebywheres(new HttpCallBack() {
            @Override
            public void Success(String data) {
                applyApplication n= JSONUtil.parseObject(data,applyApplication.class);
                applyComment=n.getComment();
                applyUserId=n.getUsers().getId();
                applyUserReputation=n.getUsers().getUser_reputation();
                applyId=n.getId();
                if(n.getComment().equals("好评")){
                    comment.setImageResource(R.mipmap.haoping);
                }else if(n.getComment().equals("差评")){
                    comment.setImageResource(R.mipmap.chaping);
                }
                showUserMessage(n);
                prograss.dismiss();
            }
            @Override
            public void Fail(String e)
            {
                Toast.makeText(endEntrust.this,"网络错误", Toast.LENGTH_LONG).show();
                prograss.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.giveComment:
                //判断是否已经评价过
                if(!applyComment.equals("未有评价")){
                    Toast.makeText(endEntrust.this,"不可重复评价", Toast.LENGTH_LONG).show();
                }else{
                    //弹出消息框，选择给予好评还是差评
                    LinearLayout a=new LinearLayout(this);
                    a.setOrientation(LinearLayout.VERTICAL);
                    RadioGroup rb=new RadioGroup(this);
                    final RadioButton good=new RadioButton(this);
                    good.setText("好评");
                    final RadioButton bad=new RadioButton(this);
                    bad.setText("差评");
                    rb.setOrientation(LinearLayout.HORIZONTAL);
                    rb.addView(good);
                    rb.addView(bad);
                    a.addView(rb);
                    new AlertDialog.Builder(endEntrust.this).setTitle("请评价")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(a).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if (good.isChecked() || bad.isChecked()) {
                                if (good.isChecked()) {
                                    newComment = "好评";
                                    applyComment = "好评";
                                } else {
                                    newComment = "差评";
                                    applyComment = "差评";
                                }
                            }
                            giveComment(newComment);
                        }
                    }).show();
                }
                break;
        }
    }
    /*
    信息显示
     */
    public void showUserMessage(applyApplication apply){

        ImageDownLoader.showNetImage(this, HttpPostUtil.getImagUrl(HttpPostUtil.getImagUrl(apply.getUsers().getPhoto())),user_head,R.mipmap.icon_dog);
        username.setText(apply.getUsers().getNickName());
        occupation.setText(apply.getUsers().getOccupation());
        phonenumber.setText(apply.getUsers().getPhone());
        reputation.setText(String.valueOf(apply.getUsers().getUser_reputation()));
        age.setText(String.valueOf(apply.getUsers().getAge()));
        personal.setText(apply.getUsers().getPersonal());
        apply_message.setText("    "+apply.getDetail()+"\n"+"    联系方式："+apply.getPhoneNumber()+"\n"+"    联系地址："+apply.getConnectPlace());
        if(apply.getComment().equals("好评")){
            comment.setImageResource(R.mipmap.haoping);
        }else if(apply.getComment().equals("差评")){
            comment.setImageResource(R.mipmap.chaping);
        }

    }

/*
给予评价函数
 */
    public void giveComment(final String com){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",applyId);
        httpReponse.addColumnParams("comment",com);
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data){
                Toast.makeText(endEntrust.this,"评价成功", Toast.LENGTH_LONG).show();
                if(com.equals("好评")){
                    changeUserReputation(10);//增加用户的信誉度
                    comment.setImageResource(R.mipmap.haoping);
                }else{
                    changeUserReputation(-10);//降低用户的信誉度
                    comment.setImageResource(R.mipmap.chaping);
                }
            }
            @Override
            public void Fail(String e) {
                Toast.makeText(endEntrust.this,"操作失败，请检查网络", Toast.LENGTH_LONG).show();
            }
        });
    }

//更改用户信誉的函数
    private void changeUserReputation(int i){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("users","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",applyUserId);
        httpReponse.addColumnParams("user_reputation",String.valueOf(applyUserReputation+i));
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(getApplicationContext(),"已更改该用户信誉度",Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {
;
            }
        });

    }
}
