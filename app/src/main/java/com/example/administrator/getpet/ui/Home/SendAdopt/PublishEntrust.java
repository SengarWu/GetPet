package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublishEntrust extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText title;//标题
    private EditText content;//内容
    private EditText award;//悬赏金额
    private Spinner pet_list;//宠物列表
    private TextView city;//城市
    private ImageView sumit;//提交
    private String citystr="所有城市";//城市
    private List<pet> mypetList;//个人宠物列表
    private int petIndex;//页码
    private ImageView back;//返回按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_entrust);
        init();
    }

    private void init() {
        //界面控件的获取与初始化
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        pet_list=(Spinner) findViewById(R.id.pet_list);
        award=(EditText)findViewById(R.id.award);
        city=(TextView) findViewById(R.id.city);
        sumit=(ImageView) findViewById(R.id.submit);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        city.setOnClickListener(this);
        sumit.setOnClickListener(this);
        showmypet();
        pet_list.setOnItemSelectedListener(this);
    }
/*
  显示用户的宠物信息
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
                //设置默认的寄养宠物
                petIndex=mypetList.size()-1;
                //把结果显示到控件中
                for(int i=0;i<mypetList.size();i++){
                    namelist.add(mypetList.get(i).getName());
                }
                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(PublishEntrust.this,R.layout.spinner_check,namelist);

                pet_list.setAdapter(adapter2);
            }
            @Override
            public void Fail(String e)
            {
                Toast.makeText(getApplicationContext(), "暂无宠物信息",Toast.LENGTH_LONG).show();
            }
        });
    }
/*
     处理控件点击事件
*/
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.submit:
                boolean T=true;
                //标题不为空检查
                if(title.getText().toString()=="") {
                    T=false;
                }
                //内容不为空检查
                if(content.getText().toString()=="") {
                    T=false;
                }
                //悬赏不为空检查
                if(award.getText().toString()=="") {
                    T=false;
                }
                if(T) {
                    submit();
                }else{
                    Toast.makeText(getApplicationContext(),"请填写完整内容",Toast.LENGTH_LONG);
                }
                break;
            case R.id.city:
                startActivityForResult(new Intent(this, SelectCityActivity.class), 99);
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }

    /**
     *
     */
    public void submit() {
        //验证用户的输入
        String regex = "^\\+?[1-9][0-9]*$";
        if(title.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "标题不可为空",Toast.LENGTH_LONG).show();
        }else if(content.getText().equals("")){
            Toast.makeText(getApplicationContext(), "内容不可为空",Toast.LENGTH_LONG).show();
        }else if(!match(regex,award.getText().toString())){
            Toast.makeText(getApplicationContext(), "悬赏金额只能为正整数",Toast.LENGTH_LONG).show();
        }else {
            //对象初始化
            entrust n_entrust = new entrust();
            n_entrust.setTitle(title.getText().toString());
            n_entrust.setDetail(content.getText().toString());
            n_entrust.setAward(Integer.valueOf(award.getText().toString()));
            n_entrust.setDate(new Date());
            //设置状态
            n_entrust.setStatus("正常");
            //设置用户
            users x = new users();
            x.setId(preferences.getString("id", ""));
            n_entrust.setUsers(x);
            //设置宠物
            pet entrust_pet = mypetList.get(petIndex);
            n_entrust.setPet(entrust_pet);
            if (citystr != "所有城市") {
                n_entrust.setCity(citystr);
            } else {
                n_entrust.setCity("所有城市");
            }
            //发送发布寄养信息的数据请求
            SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("entrust", "Insert");
            httpReponse.insert(n_entrust, new HttpCallBack() {
                @Override
                public void Success(String data) {
                    Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_LONG).show();
                    PublishEntrust.this.finish();
                }

                @Override
                public void Fail(String e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    /*
    用于正则表达式验证的方法
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
/*
接受返回的城市字符
 */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {
            switch (resultCode) {
                case 99:
                    String receive=data.getStringExtra("lngCityName");
                    city.setText(receive);
                    if(!receive.equals("所有城市")) {
                        citystr = data.getStringExtra("lngCityName");
                    }else{
                        citystr="";
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        petIndex=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
