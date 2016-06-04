package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.OtherPostAdapter;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;

public class SearchPost extends BaseActivity implements View.OnClickListener {
    private ZrcListView listView;
    int curPage = 0;//页码
    private Handler handler;//用于接收子线程的信息以刷新主线程
    private ArrayList<post> items = new ArrayList<>();//显示到列表上的数据集合
    private OtherPostAdapter adapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        initView();
    }

    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);

        //列表的初始化
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

        adapter = new OtherPostAdapter(this, items);
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
                Intent item = new Intent(SearchPost.this, PostDetail.class);
                item.putExtra("username","");
                item.putExtra("publishtime","");
                item.putExtra("content","");
                item.putExtra("award","");
                item.putExtra("picture1Url1","");
                item.putExtra("picture1Url2","");
                item.putExtra("picture1Url3","");
                startActivity(item);
            }
        });
    }

    private void refresh() {

    }

    private void loadMore() {
    }

    private void QueryPost(){

    }

    private void QueryCount(){

    }

    private void QueryMorePost(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
        }
    }
}
