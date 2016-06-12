package com.example.administrator.getpet.ui.Home.PetCircle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublishPost extends BaseActivity implements View.OnClickListener {
    private EditText title;//标题
    private EditText content;//内容
    private ImageView submit;//提交按钮
    private EditText award;//悬赏积分
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        initView();
    }

    private void initView() {
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        award=(EditText)findViewById(R.id.award);
        submit=(ImageView)findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.submit:
                String regex = "^\\+?[1-9][0-9]*$";//只能输入正整数的正则表达式
                if(title.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "标题不能为空", Toast.LENGTH_LONG).show();
                }else if(content.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "内容不能为空", Toast.LENGTH_LONG).show();
                }else if(!match(regex,award.getText().toString())){
                    Toast.makeText(getApplicationContext(), "悬赏金额只能为正整数",Toast.LENGTH_LONG).show();
                }else {
                    post NewPost = new post();
                    //设置发布的用户
                    users user = new users();
                    user.setId(preferences.getString("id", ""));
                    NewPost.setUsers(user);
                    NewPost.setTitle(title.getText().toString());
                    NewPost.setMes(content.getText().toString());
                    NewPost.setIntergen(Integer.valueOf(award.getText().toString()));
                    //设置发帖的时间
                    NewPost.setDate(new Date());
                    NewPost.setSeeNum(0);
                    NewPost.setNum(0);
                    NewPost.setState("未结贴");
                    //发送发布帖子的数据请求
                    SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("post", "Insert");
                    httpReponse.insert(NewPost, new HttpCallBack() {
                        @Override
                        public void Success(String data) {
                            Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_LONG).show();
                            PublishPost.this.finish();
                        }

                        @Override
                        public void Fail(String e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
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

}
