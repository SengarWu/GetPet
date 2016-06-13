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
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

/*
给予评价
 */
public class endEntrust extends BaseActivity implements View.OnClickListener {
    private ImageView comment;
    private Button giveComment;//给予评价按钮
    private String entrustId;
    private ProgressDialog prograss;//加载时显示的框
    private String applyComment;//评价
    private String applyUserId;//通过申请的用户id
    private String applyId;//通过的申请id
    private String newComment;//新添加的评价
    private int applyUserReputation;//要评价用户的积分
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_entrust);
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        entrustId=intent.getStringExtra("entrustId");
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
                if(n.getComment()=="好评"){
                    comment.setImageResource(R.mipmap.haoping);
                }else{
                    comment.setImageResource(R.mipmap.chaping);
                }
                showUserMessage(n.getUsers().getId());
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
                if(applyComment!=""){
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
                    final EditText comment2=new EditText(mContext);
                    a.addView(comment2);
                    new AlertDialog.Builder(mContext).setTitle("请评价")
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
                                } else {
                                    newComment = "差评";
                                }
                                if (!comment2.getText().equals("")) {
                                    newComment=comment2.getText().toString();
                                }
                            }
                            giveComment(newComment);
                        }
                    }).show();
                }
                break;
        }
    }
    public void showUserMessage(String id){

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
                }else{
                    changeUserReputation(-10);//降低用户的信誉度
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
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("Users","updateColumnsByWheres");
        httpReponse.addWhereParams("id","=",preferences.getString("id",""));
        httpReponse.addColumnParams("user_reputation",String.valueOf(applyUserReputation+i));
        httpReponse.updateColumnsByWheres( new HttpCallBack() {
            @Override
            public void Success(String data) {
                Toast.makeText(getApplicationContext(),"已更改该用户信誉度",Toast.LENGTH_LONG).show();
            }
            @Override
            public void Fail(String e) {

            }
        });

    }
}
