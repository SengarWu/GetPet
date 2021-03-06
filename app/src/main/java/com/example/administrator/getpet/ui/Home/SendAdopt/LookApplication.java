package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ApplicationAdapter;
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

public class LookApplication extends BaseActivity implements View.OnClickListener {
    private ZrcListView listView;
    private Handler handler;//用于接收子线程的信息以刷新主线程
    int curPage = 1;//页码
    private ApplicationAdapter adapter;
    private ArrayList<applyApplication> items = new ArrayList<>();
    private ImageView back;//返回按钮
    //private List<applyApplication> tempolist;//用于存放返回数据的列表
    private String entrustId;//记录寄养信息id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_application);
        initView();
    }
/*
寄养信息id
 */
    private void initView() {
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        Intent intent=getIntent();
        entrustId=intent.getStringExtra("entrustId");
        //列表的初始化
        listView = (ZrcListView)findViewById(R.id.application_list);
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

        adapter = new ApplicationAdapter(this, items);
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

    //刷新
    private void refresh() {
        curPage=1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryApplyApplication();
            }
        });
    }

    /*
    查询对应寄养信息的领养申请
     */
    private void QueryApplyApplication() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QueryList");
        httpReponse.addWhereParams("entrustId","=",entrustId);//添加对寄养信息的约束
        //按照申请日期排序
        httpReponse.addOrderFieldParams("applyDate");
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(1,4, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<applyApplication> tempolist= Arrays.asList(JSONUtil.parseArray(data,applyApplication.class));
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

    private void loadMore(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryCountsh();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
        }
    }

    private void QueryCountsh() {
        //http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QueryCount");
        //添加对用户的筛选
        httpReponse.addWhereParams("entrustId","=",entrustId);
        //调用QueryCount方法
        httpReponse.QueryCount(new HttpCallBack() {
            @Override
            public void Success(String data) {
                Integer x=Integer.valueOf(data);
                if(x> items.size()){
                    curPage++;
                    Querymore(curPage);
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

    private void Querymore(int page) {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QueryList");
        httpReponse.addWhereParams("entrustId","=",entrustId);
        //添加排序的字段
        httpReponse.addOrderFieldParams("date");
        //是否为降序  true表示降序   false表示正序
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(page,4, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<applyApplication> tempolist= Arrays.asList(JSONUtil.parseArray(data,applyApplication.class));
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
}
