package com.example.administrator.getpet.ui.Me;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.view.RoundImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private RoundImageView personal_iv_photo;
    private TextView personal_tv_nickname;
    private TextView personal_tv_sex;
    private TextView personal_tv_age;
    private TextView personal_tv_address;
    private RelativeLayout personal_rl_account;
    private RelativeLayout personal_rl_introduce;
    private Button personal_btn_edit;

    private final int LOADSUCCESS = 1001;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case LOADSUCCESS:
                    personal_iv_photo.setImageBitmap(bitmap);//改头像
                    break;
            }
        }
    };

    // 代表从网络下载得到的图片
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        setupView();
    }

    private void setupView() {
        String photo = preferences.getString("photo","");
        String nickname = preferences.getString("nickName","");
        String sex = preferences.getString("sex","");
        int age = preferences.getInt("age",0);
        String address = preferences.getString("address","");
        if (!TextUtils.isEmpty(photo))
        {
            String s_url = HttpPostUtil.getImagUrl(photo);
            getImage(s_url);
        }
        if (!TextUtils.isEmpty(nickname))
        {
            personal_tv_nickname.setText(nickname);
        }
        if (!TextUtils.isEmpty(sex))
        {
            personal_tv_sex.setText(sex);
        }
        if (age != 0)
        {
            personal_tv_age.setText(String.valueOf(age));
        }
        if (!TextUtils.isEmpty(address))
        {
            personal_tv_address.setText(address);
        }

    }

    private void getImage(final String surl) {
        new Thread()
        {
            public void run()
            {
                try
                {
                    // 定义一个URL对象
                    URL url = new URL(surl);
                    // 打开该URL对应的资源的输入流
                    InputStream is = url.openStream();
                    // 从InputStream中解析出图片
                    bitmap = BitmapFactory.decodeStream(is);
                    // 发送消息、通知UI组件显示该图片
                    handler.sendEmptyMessage(LOADSUCCESS);
                    is.close();
                    // 再次打开URL对应的资源的输入流
                    is = url.openStream();
                    // 打开手机文件对应的输出流
                    OutputStream os = openFileOutput("photo.png"
                            , MODE_PRIVATE);
                    byte[] buff = new byte[1024];
                    int hasRead = 0;
                    // 将URL对应的资源下载到本地
                    while((hasRead = is.read(buff)) > 0)
                    {
                        os.write(buff, 0 , hasRead);
                    }
                    is.close();
                    os.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        personal_iv_photo = (RoundImageView) findViewById(R.id.personal_iv_photo);
        personal_tv_nickname = (TextView) findViewById(R.id.personal_tv_nickname);
        personal_tv_sex = (TextView) findViewById(R.id.personal_tv_sex);
        personal_tv_age = (TextView) findViewById(R.id.personal_tv_age);
        personal_tv_address = (TextView) findViewById(R.id.personal_tv_address);
        personal_rl_account = (RelativeLayout) findViewById(R.id.personal_rl_account);
        personal_rl_account.setOnClickListener(this);
        personal_rl_introduce = (RelativeLayout) findViewById(R.id.personal_rl_introduce);
        personal_rl_introduce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.personal_rl_account:
                startAnimActivity(AccountActivity.class);
                break;
            case R.id.personal_rl_introduce:
                startAnimActivity(IntroduceActivity.class);
                break;
        }
    }
}
