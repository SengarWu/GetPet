package com.example.administrator.getpet.ui.findPet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.Station;
import com.example.administrator.getpet.bean.active;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caolin on 2016/6/11.
 */
public class addActives extends BaseActivity implements View.OnClickListener {
    //需要数据
    String activeId="";
    String userId="b0c19976-4859-407b-8ae0-01b68bc73ca1";
    //页面控件
    EditText startTime;
    EditText endTime;
    EditText msg;

    ImageButton back;
    Button save;
    //加载等待进度圈
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_operation);
        //初始化进度条
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("正在加载页面...");
        progressDialog.show();
        //渲染页面
        initView();
        //初始化页面
        initDate();
        //绑定事件
        bindEvent();
        //关闭加载进度条
        dismissProgressDialog();
    }

    /**
     * 初始化页面布局
     */
    private void initView(){
        back=$(R.id.back);
        save=$(R.id.save);
        startTime=$(R.id.startTime);
        endTime=$(R.id.endTime);
        msg=$(R.id.msg);
    }

    /**
     * 初始化空间的事件
     */
    private void bindEvent(){
        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    private void initDate(){
        userId=preferences.getString("id","");
        if(getIntent()!=null){
            activeId=getIntent().getStringExtra("activeId");
        }
        if(activeId==null||activeId.equals("")){
            save.setText("保存");
            Date now=new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DATE, 1);
            startTime.setText(TimeUtils.dateToString(cal.getTime(),TimeUtils.FORMAT_DATE));
            cal.add(Calendar.DATE, 1);
            endTime.setText(TimeUtils.dateToString(cal.getTime(),TimeUtils.FORMAT_DATE));
            msg.setText("");
        }else{
            save.setText("删除");
            //传入表名和方法名   方法名：QuerySinglebyid
            SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("active","QuerySinglebyid");
            //调用QuerySinglebyid方法 第一个参数是id
            httpReponse.QuerySinglebyid(activeId, new HttpCallBack() {
                @Override
                public void Success(String data) {
                   active act= JSONUtil.parseObject(data,active.class);
                    if(act!=null){
                        startTime.setText(TimeUtils.dateToString(act.starttime,TimeUtils.FORMAT_DATE));
                        endTime.setText(TimeUtils.dateToString(act.endtime,TimeUtils.FORMAT_DATE));
                        msg.setText(act.information);
                    }
                }
                @Override
                public void Fail(String e) {
                    show(e);
                }
            });
        }
        //关闭加载条
        dismissProgressDialog();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back: finish();break;
            case R.id.save:
                if(activeId==null||activeId.equals("")){
                    save();
                }else{
                    delete();
                }
                break;
        }
    }

    /**
     * 发布宠物丢失
     */
    private void save(){
        if(!isValid()){
            return;
        }

        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("active","Insert");
        active act=new active();
        Station sta=new Station();
        sta.id=userId;
        act.Station=sta;
        act.starttime=TimeUtils.stringToDate(startTime.getText().toString().trim(),TimeUtils.FORMAT_DATE);
        act.endtime=TimeUtils.stringToDate(endTime.getText().toString().trim(),TimeUtils.FORMAT_DATE);
        act.information=msg.getText().toString().trim();

        httpReponse.insert(act, new HttpCallBack() {
            @Override
            public void Success(String data) {
                show("活动添加成功");
                finish();
            }
            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }

    /**
     * 关闭加载进度条
     */
    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 验证发布信息
     * @return  是否有效
     */
    private boolean isValid(){


        String ms=msg.getText().toString().trim();
        if(ms.trim().equals("")){
            show("请输入描述信息");
            return false;
        }
        if(ms.trim().length()>20){
            show("描述信息不能超过二十个字");
            return false;
        }


        //验证时间格式
        String expression = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
        Pattern pattern = Pattern.compile(expression);
        //开始时间
        Matcher matcher = pattern.matcher(startTime.getText().toString().trim());
        if(!matcher.matches()){
            show("开始时间格式不正确！");
            return false;
        }
        //结束时间
        matcher = pattern.matcher(endTime.getText().toString().trim());
        if(!matcher.matches()){
            show("结束时间格式不正确！");
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            sdf.setLenient(false);
            //开始时间
            Date startDate =sdf.parse(startTime.getText().toString().trim());
            Date now=new Date();
            int num=startDate.compareTo(now);
            if (num!=1){
                show("开始时间必须要大于当前时间！");
                return false;
            }
            //结束时间
            Date endDate =sdf.parse(endTime.getText().toString().trim());
            num=endDate.compareTo(startDate);
            if (num!=1){
                show("结束时间必须要大于开始时间！");
                return false;
            }

            return true;
        }catch (Exception e){
            show("时间格式不正确！");
            return false;
        }

    }
    private  void delete(){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("active","Delete");
        httpReponse.delete(activeId, new HttpCallBack() {
            @Override
            public void Success(String data) {

                show("已删除");
                finish();
            }

            @Override
            public void Fail(String e) {
                show(e);
            }
        });
    }

}


