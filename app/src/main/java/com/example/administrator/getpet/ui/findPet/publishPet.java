package com.example.administrator.getpet.ui.findPet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.lose;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caolin on 2016/5/30.
 */
public class publishPet extends BaseActivity implements View.OnClickListener {
    //需要数据
    String userId="";
    //发布宠物的主键
    pet pet;
    //页面控件
    Spinner petName;
    private ArrayAdapter<String> adapter;
    private List<String> list=new ArrayList<String>();
    private List<pet> petList = new ArrayList<pet>();

    EditText address;
    EditText phone;
    EditText msg;
    EditText time;
    ImageButton back;
    Button publish;
    //加载等待进度圈
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_publish);
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
        publish=$(R.id.publish);
        petName=$(R.id.petName);
        address=$(R.id.address);
        phone=$(R.id.phone);
        time=$(R.id.time);
        msg=$(R.id.msg);
    }

    /**
     * 初始化空间的事件
     */
    private void bindEvent(){
        back.setOnClickListener(this);
        publish.setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    private void initDate(){
        userId=preferences.getString("id","");
        SimpleHttpPostUtil httpSponse=new SimpleHttpPostUtil("pet","QueryList");
        httpSponse.addWhereParams("userId","=",userId);
        httpSponse.QueryList(-1,2, new HttpCallBack() {
            @Override
            public void Success(String data) {
                pet[] pets=JSONUtil.parseArray(data,pet.class);
                //Collection<pet> pets=JSONUtil.parseArray(data,pet.class);
                if(pets!=null){
                    petList=Arrays.asList(pets);
//                    for (pet pet : pets) {
//                        petList.add(pet);
//                    }
                    loadSpinner();
                }else{
                    show("请先添加要发布的宠物");
                }
            }
            @Override
            public void Fail(String e) {
                show("数据加载失败");
                //show(e);
            }
        });
        //关闭加载条
        dismissProgressDialog();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back: finish();break;
            case R.id.publish: publish();break;
        }
    }

    /**
     * 发布宠物丢失
     */
    private void publish(){
//        //发送图片
//        HttpPostUtil u = new HttpPostUtil("File","FileUpload");
//        u.addFileParameter("img1", Environment.getExternalStorageDirectory() + "//DCIM//Camera//a1.JPG");
//        u.send(new HttpCallBack() {
//            @Override
//            public void Success(final String data) {
//                Toast.makeText(publishPet.this, data, Toast.LENGTH_LONG).show();
//                Log.d("caolin",data);
//            }
//
//            @Override
//            public void Fail(final String e) {
//                Toast.makeText(publishPet.this, e, Toast.LENGTH_LONG).show();
//            }
//        });
        if(!isValid()){
            return;
        }


        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("lose","Insert");
        lose loser=new lose();
        loser.loseAddress=address.getText().toString();
        loser.loseMessage=msg.getText().toString();
        loser.loseTime= TimeUtils.stringToDate(time.getText().toString(),TimeUtils.FORMAT_DATE);
//        loser.uploadTime=TimeUtils.stringToDate(TimeUtils.getCurrentTime(TimeUtils.FORMAT_DATE),TimeUtils.FORMAT_DATE);
        loser.uploadTime=new Date();
        loser.phone=phone.getText().toString();

        users us=new users();
        us.id=userId;
        loser.users=us;

        pet pt=new pet();
        pt.id=pet.id;
        loser.pet=pt;


        httpReponse.insert(loser, new HttpCallBack() {
            @Override
            public void Success(String data) {
                show("发布成功");
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

        if(pet==null){
            show("请先将宠物添加到我的宠物里");
            return false;
        }
        String add=address.getText().toString().trim();
        if(add.trim().equals("")){
            show("请输入地址");
            return false;
        }
        if(add.trim().length()>15){
            show("地址不能超过十五个字");
            return false;
        }
        String ms=msg.getText().toString().trim();
        if(ms.trim().equals("")){
            show("请输入描述信息");
            return false;
        }
        if(ms.trim().length()>15){
            show("描述信息不能超过二十五个字");
            return false;
        }
        String ph=phone.getText().toString().trim();
        if(ph.trim().equals("")){
            show("请输入联系信息");
            return false;
        }

        //验证电话号码
        String expression = "^((13[0-9])|(15[^4\\D])|(18[0-9]))\\d{8}$";
        //String expression = "^((13[0-9])|(15[^4,^\\D])|(18[0,5-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(ph);
        if(!matcher.matches()){
            show("手机号码格式不正确！");
            return false;
        }
        //验证时间格式
        expression = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(time.getText().toString().trim());
        if(!matcher.matches()){
            show("时间格式不正确！");
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            sdf.setLenient(false);
            Date date =sdf.parse(time.getText().toString().trim());
            Date now=new Date();
            int num=date.compareTo(now);
            if (num!=-1){
                show("时间必须要小于当前时间！");
                return false;
            }
//            String priDate=TimeUtils.dateToString(date,"yyyy-MM-dd");
//            if(!priDate.equals(time.getText().toString().trim())){
//                show("时间越界！");
//                return  false;
//            }
            return true;
        }catch (Exception e){
            show("时间格式不正确！");
            return false;
        }

    }

    /**
     * 加载下拉列表数据
     */
    private void loadSpinner(){
        for (pet pet : petList) {
            list.add(pet.name);
        }
        if(list!=null &&list.size()>0){
            pet=petList.get(0);
        }

        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
        //第三步：将适配器添加到下拉列表上
        petName.setAdapter(adapter);
//        petName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                petId=petList.get(position).id;
//            }
//        });
        petName.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,View view, int position, long id) {
                        pet=petList.get(position);
                        //show(petId);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //show("2");
                        show("请先将宠物添加到我的宠物里！！");
                    }
                });
//        //第四步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
//        petName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }


}

