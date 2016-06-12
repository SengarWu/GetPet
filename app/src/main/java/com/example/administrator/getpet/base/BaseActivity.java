package com.example.administrator.getpet.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.getpet.utils.CommonUtils;
import com.example.administrator.getpet.utils.ToastUtils;

/**
 * Created by Administrator on 2016/3/14.
 */
public class BaseActivity extends FragmentActivity {
    protected Context mContext;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext =getApplicationContext();
        preferences = getSharedPreferences("user",MODE_PRIVATE);//声明保存的文件名，且权限为私有;
        editor = preferences.edit();
        if (!isNetConnected())
        {
            ToastUtils.showToast(mContext,"当前网络不可用，请检查网络");
            return;
        }
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public boolean isNetConnected() {
        return CommonUtils.isNetworkAvailable(mContext);
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    protected   <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
    public void show(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }
}


