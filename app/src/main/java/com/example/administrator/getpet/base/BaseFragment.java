package com.example.administrator.getpet.base;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/3/14.
 */
public class BaseFragment extends Fragment {
    public SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = CustomApplication.getmInstance().getSharedPreferences("user",
                CustomApplication.MODE_PRIVATE);//声明保存的文件名，且权限为私有;
    }
}
