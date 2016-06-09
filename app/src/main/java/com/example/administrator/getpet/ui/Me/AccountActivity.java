package com.example.administrator.getpet.ui.Me;

import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.view.RoundImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class AccountActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private RoundImageView riv_photo;
    private TextView tv_nickname;
    private TextView tv_phone;
    private FrameLayout frame_layout;

    IdentityFragment identityFragment = new IdentityFragment();
    NoIdentityFragment noIdentityFragment = new NoIdentityFragment();

    private final int LOADSUCCESS = 1001;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case LOADSUCCESS:
                    riv_photo.setImageBitmap(bitmap);//改头像
                    break;
            }
        }
    };

    // 代表从网络下载得到的图片
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
        loadData();
    }

    private void loadData() {
        //检查账号验证情况,切换
        //加载用户信息
        String photo = preferences.getString("photo","");
        String nickname = preferences.getString("nickName","");
        String phone = preferences.getString("phone","");
        String indentifiedId = preferences.getString("indentifiedId","");
        if (!TextUtils.isEmpty(photo))
        {
            String s_url = HttpPostUtil.getImagUrl(photo);
            getImage(s_url);
        }
        if (!TextUtils.isEmpty(nickname))
        {
            tv_nickname.setText(nickname);
        }
        if (!TextUtils.isEmpty(phone))
        {
            tv_phone.setText(phone);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!TextUtils.isEmpty(indentifiedId))
        {
            //未验证
            transaction.replace(R.id.frame_layout,noIdentityFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {
            //已验证
            transaction.replace(R.id.frame_layout,identityFragment);
            transaction.addToBackStack(null);
            transaction.commit();
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
        riv_photo = (RoundImageView) findViewById(R.id.riv_photo);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
