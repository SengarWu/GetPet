package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.AnswerAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.OtherEntrustAdapter;
import com.example.administrator.getpet.view.RoundImageView;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class myPostHistory extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private ZrcListView listView;
    private Handler handler;//用于接收子线程的信息以刷新主线程
    int curPage = 0;//页码
    private AnswerAdapter adapter;
    private ArrayList<postReply> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_list);
        initView();
    }

    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        //列表初始化
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

        adapter = new AnswerAdapter(this, items);
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
                Intent item = new Intent(myPostHistory.this, PostDetail.class);
                item.putExtra("postId","");
                item.putExtra("username","");
                item.putExtra("publishtime","");
                item.putExtra("content","");
                item.putExtra("pictureUrl","");
                startActivity(item);
            }
        });
    }

    private void refresh() {
    }

    private void loadMore() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                break;

        }
    }
}
