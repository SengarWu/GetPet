package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;

public class EntrustMainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView addnewEntrust;//添加新寄养信息图标
    private ImageView back;//返回图标
    private ArrayList<entrust> items = new ArrayList<>();
    private ZrcListView listView;
    private Handler handler;//用于接收子线程的信息以刷新主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_main);
        init();
    }

    private void init() {
        addnewEntrust=(ImageView)findViewById(R.id.add_entrust);
        back=(ImageView)findViewById(R.id.back);
        addnewEntrust.setOnClickListener(this);
        back.setOnClickListener(this);
        listView = (ZrcListView)findViewById(R.id.my_entrust_list);
        handler = new Handler();
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xffee71a1);
        header.setCircleColor(0xffee71a1);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xffee71a1);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.top_item_in);
        listView.setItemAnimForBottomIn(R.anim.bottom_item_in);

        /*adapter = new SeekHelpAdapter(this, items);
        listView.setAdapter(adapter);*/

        if (items.size() <= 0)
        {
            listView.refresh(); // 主动下拉刷新
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_entrust:
                //Intent item=new Intent(this, PublishEntrust.class);
                //startActivity(item);
                break;
            case R.id.back:
                break;
        }
    }
}