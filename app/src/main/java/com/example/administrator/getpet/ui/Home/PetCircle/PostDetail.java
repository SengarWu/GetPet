package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.AnswerAdapter;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.OtherPostAdapter;
import com.example.administrator.getpet.view.RoundImageView;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class PostDetail extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private RoundImageView user_head;
    private TextView username;
    private TextView time;
    private TextView content_summary;
    private TextView award;
    private EditText new_reply;
    private Button comment;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private AnswerAdapter adapter;
    private ArrayList<postReply> items = new ArrayList<>();
    private ZrcListView listView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        back=(ImageView)findViewById(R.id.back);
        user_head=(RoundImageView)findViewById(R.id.user_head);
        username=(TextView)findViewById(R.id.username);
        time=(TextView)findViewById(R.id.publishTime);
        content_summary=(TextView)findViewById(R.id.content_summary);
        award=(TextView)findViewById(R.id.award);
        back.setOnClickListener(this);
        imageLoader.displayImage(intent.getStringExtra("pictureurl"), user_head, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        username.setText(intent.getStringExtra("username"));
        time.setText(intent.getStringExtra("publishtime"));
        content_summary.setText(intent.getStringExtra("content"));

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
            }
        });
    }

    private void loadMore() {

    }

    private void refresh() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                break;
            case R.id.comment:
                break;
        }
    }
}
