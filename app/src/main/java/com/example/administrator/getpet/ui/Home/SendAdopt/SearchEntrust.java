package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.OtherEntrustAdapter;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;


public class SearchEntrust extends AppCompatActivity implements View.OnClickListener {
    private String ty="所有类型";
    public String city="";
    private DrawerLayout petlayout;
    private ImageView cat,dog,fish,other;
    private ZrcListView listView;
    private Handler handler;//用于接收子线程的信息以刷新主线程
    int curPage = 0;//页码
    private OtherEntrustAdapter adapter;
    private ArrayList<entrust> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_entrust);
        initView();
    }

    private void initView() {
        petlayout=(DrawerLayout)findViewById(R.id.drawer_select);
        cat=(ImageView)findViewById(R.id.cat);
        dog=(ImageView)findViewById(R.id.dog);
        fish=(ImageView)findViewById(R.id.fish);
        other=(ImageView)findViewById(R.id.other);
        cat.setOnClickListener(this);
        dog.setOnClickListener(this);
        fish.setOnClickListener(this);
        other.setOnClickListener(this);
        listView = (ZrcListView)findViewById(R.id.other_entrust_list);
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

        adapter = new OtherEntrustAdapter(this, items);
        listView.setAdapter(adapter);

        if (items.size() <= 0)
        {
            listView.refresh(); // 主动下拉刷新
        }

        // 下拉刷新事件回调（可选）
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //Intent item = new Intent(SearchQuestion.this, ShowQuesiondetails.class);

               // startActivity(item);
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryCountEntrust();
            }
        });
    }

    private void QueryCountEntrust() {
    }

    private void refresh() {
        curPage=0;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryEntrust();//queryProxy为自定义的查询类
            }
        });
    }

    private void QueryEntrust() {
        
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.cat:
                break;
            case R.id.dog:
                break;
            case R.id.fish:
                break;
            case R.id.other:
                break;
        }
    }
}
