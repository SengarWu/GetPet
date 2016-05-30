package com.example.administrator.getpet.ui.Home.PetCircle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;

public class SearchPost extends BaseActivity {
    private ZrcListView listView;
    int curPage = 0;//页码
    private Handler handler;//用于接收子线程的信息以刷新主线程
    private ArrayList<post> items = new ArrayList<>();//显示到列表上的数据集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        initView();
    }

    private void initView() {

    }
}
