package com.example.administrator.getpet.ui.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.ui.home;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String id = preferences.getString("id","");
        Log.d(TAG, "onCreate: id:"+id);

        if (TextUtils.isEmpty(id))
        {
            //本地id为空，说明未保存登录状态
            startAnimActivity(LoginActivity.class);
            finish();
        }
        else{
            startAnimActivity(home.class);
            finish();
        }
    }
}
