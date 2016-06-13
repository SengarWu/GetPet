package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.AnswerAdapter;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.mypostAdapter;
import com.example.administrator.getpet.utils.CommonUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class myPostHistory extends BaseActivity implements View.OnClickListener {
    private ImageView back;//返回按钮
    private ZrcListView listView;//列表
    private Handler handler;//用于接收子线程的信息以刷新主线程
    int curPage = 1;//页码
    private mypostAdapter adapter;//帖子适配器
    private ArrayList<post> items = new ArrayList<>();//用于记录查询结果的
    private TextView publishNew;//发布新帖按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_list);
        initView();
    }

    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        publishNew=(TextView)findViewById(R.id.publishNew);
        publishNew.setOnClickListener(this);
        //列表初始化
        listView = (ZrcListView)findViewById(R.id.mypost_list);
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

        adapter = new mypostAdapter(this, items);
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
                Intent item = new Intent(myPostHistory.this, MyPostDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", items.get(position));
                item.putExtras(bundle);
                startActivity(item);
            }
        });
    }

    /*
    刷新
     */
    private void refresh() {
        curPage=1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryPost();
            }
        });
    }
    /*
    加载更多
     */
    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryCount();
            }
        });
    }

    /*
首次查询帖子列表
 */
    private void QueryPost(){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("post","QueryList");
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));
        //添加排序的字段
        httpReponse.addOrderFieldParams("date");
        //是否为降序  true表示降序   false表示正序
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(1,10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<post> tempolist= Arrays.asList(JSONUtil.parseArray(data,post.class));
                if (tempolist.size() != 0) {
                    if (CommonUtils.isNotNull(tempolist)) {//监测网络等是否可用
                        items.clear();
                        adapter.addAll(tempolist);
                        if (tempolist.size() < 10) {
                            listView.setRefreshSuccess("加载完成"); // 通知加载完成
                            listView.stopLoadMore();
                        } else {
                            listView.setSelection(0);
                            listView.setRefreshSuccess("加载成功"); // 通知加载成功
                            listView.startLoadMore(); // 开启LoadingMore功能
                        }
                    } else {
                        listView.setRefreshSuccess("暂无数据");
                        listView.stopLoadMore();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    listView.setRefreshFail("抱歉，没搜到任何结果");
                    items.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void Fail(String e)
            {
                listView.setRefreshFail("加载失败");
                items.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
/*
查询帖子总数判断是否继续查询
 */
    private void QueryCount(){
        //http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("post","QueryCount");
        //添加对用户的筛选
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));

        //调用QueryCount方法
        httpReponse.QueryCount(new HttpCallBack() {
            @Override
            public void Success(String data) {
                Integer x=Integer.valueOf(data);
                if(x> items.size()){
                    curPage++;
                    QueryMorePost(curPage);
                }else{
                    listView.stopLoadMore();
                }
            }
            @Override
            public void Fail(String e)
            {
                listView.stopLoadMore();
            }
        });
    }
/*
查询更多
 */
    private void QueryMorePost(int page){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QueryList");
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));
        //添加排序的字段
        httpReponse.addOrderFieldParams("date");
        //是否为降序  true表示降序   false表示正序
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(page,10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<post> tempolist= Arrays.asList(JSONUtil.parseArray(data,post.class));
                if (CommonUtils.isNotNull(tempolist)) {
                    adapter.addAll(tempolist);
                }
                adapter.notifyDataSetChanged();
                listView.setLoadMoreSuccess();
            }
            @Override
            public void Fail(String e)
            {
                Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
                listView.stopLoadMore();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.publishNew://进入发布新帖子的界面
                Intent intent=new Intent(myPostHistory.this,PublishPost.class);
                startActivity(intent);
                break;
        }
    }
}
