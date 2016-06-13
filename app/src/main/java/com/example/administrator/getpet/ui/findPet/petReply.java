package com.example.administrator.getpet.ui.findPet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.reply;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by caolin on 2016/6/2.
 */
public class petReply extends BaseActivity {
    final static int PET_DETAIL=1;
    //标题栏
    ImageButton back;
    Button reply;
    //列表
    RecyclerView recyclerView;
    //列表相关
    adaptReply replyAdapt;
    lose loseUser;
    String loseId="007ca23c-083d-4b0b-832f-6649bb5f9838";
    String userId="b0c19976-4859-407b-8ae0-01b68bc73ca1";
    //丢失详情
    EditText loseTime;
    EditText loser;
    EditText loseAddress;
    ImageView petPhoto;

    View detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_reply);
        initView();
        bindEvent();
    }
    @Override
    protected void onStart() {
        super.onStart();
        initDate();
    }
    /**
     * 初始化页面布局
     */
    private void initView(){
        detail=$(R.id.detail);
        back=$(R.id.back);
        reply=$(R.id.reply);
        recyclerView=$(R.id.replyList);

        loseTime=$(R.id.loseTime);
        loser=$(R.id.loser);
        loseAddress=$(R.id.loseAddress);
        petPhoto=$(R.id.petPhoto);
    }
    /**
     * 加载数据
     */
    private void initDate(){

        userId=preferences.getString("id","");
        if(getIntent()!=null){
            loseId=getIntent().getStringExtra("loseId");
        }
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("lose","QuerySinglebyid");
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QuerySinglebyid(loseId, new HttpCallBack() {
            @Override
            public void Success(String data) {
                loseUser= JSONUtil.parseObject(data,lose.class);
                if(loseUser!=null){
                    loadDetail();
                    loadReplyList();
                }
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }

    /**
     * 初始化丢失信息（发布信息）
     */
    private void loadDetail(){
        loseTime.setText(TimeUtils.dateToString(loseUser.loseTime,TimeUtils.FORMAT_DATE));
        loseAddress.setText(loseUser.loseAddress);
        loser.setText(loseUser.users.nickName);
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(loseUser.pet.photo),petPhoto,R.mipmap.home_pet_photp);
    }

    /**
     * 初始化发布信息的回复信息
     */
    private void loadReplyList(){
        replyAdapt =new adaptReply(petReply.this,userId,loseId);
        replyAdapt.setOnItemClickListener(new adaptReply.OnItemClickListener() {
            @Override
            public void onUserItemClicked(reply userModel) {

            }
        });
        recyclerView.setAdapter(replyAdapt);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(petReply.this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 3, getResources().getColor(R.color.pink_KK)));


        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("reply","QueryList");
        httpReponse.addWhereParams("loseId","=",loseId);
        httpReponse.addOrderFieldParams("replyTime");
        httpReponse.addIsDescParams(false);
        //调用QuerySinglebyid方法 第一个参数是id
        httpReponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d("cao",data);
                reply[] replyes= JSONUtil.parseArray(data,reply.class);
                if(replyes!=null){
                    List list = Arrays.asList(replyes);
                    replyAdapt.setLosersCollection(list);
                    Log.d("cao","加载完成"+replyAdapt.getItemCount());
                }else{
                    show("没有数据");
                }
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }
    /**
     * 初始化空间的事件
     */
    private void bindEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(petReply.this);
                et.setHint("内容字数必须小于15个字！");

                new AlertDialog.Builder(petReply.this).setTitle("回复内容")

                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.trim().equals("")) {
                                    show("内容不能为空！发布失败");
                                    return;
                                }
                                else if(input.trim().length()>15){
                                    show("内容不能超过十五个字");
                                    return;
                                }
                                else {
                                    loadReply(input);
                                }
                            }
                        } )
                        .setNegativeButton("取消", null)
                        .show();

            }
        });
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入回复界面
                Intent myIntent=new Intent(petReply.this,petDetail.class);
                myIntent.putExtra("loseId",loseId);
                startActivityForResult(myIntent,PET_DETAIL);
            }
        });
    }

    /**
     * 发布留言信息
     * @param msg 内容
     */
    private void loadReply(String msg){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("reply","Insert");
        reply replyModel=new reply();
        replyModel.replyMessage=msg;

        users usersModel=new users();
        usersModel.id=userId;
        replyModel.users=usersModel;



//        lose loseModel=new lose();
//        loseModel.id=loseId;
//        loseModel.loseTime=TimeUtils.stringToDate(TimeUtils.getCurrentTime(TimeUtils.FORMAT_DATE),TimeUtils.FORMAT_DATE);

        replyModel.lose=loseUser;

        //replyModel.replyTime=TimeUtils.stringToDate(TimeUtils.getCurrentTime(TimeUtils.FORMAT_DATE),TimeUtils.FORMAT_DATE);
        replyModel.replyTime=new Date();

        httpReponse.insert(replyModel, new HttpCallBack() {
            @Override
            public void Success(String data) {
                loadReplyList();
                show("留言成功");
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case PET_DETAIL:
               finish();
                break;
            default:
                break;
        }
    }
}
