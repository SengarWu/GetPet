package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.MyEntrustListAdapter;
import com.example.administrator.getpet.utils.CommonUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
寄养信息主页
 */
public class EntrustMainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView addnewEntrust;//添加新寄养信息图标
    private ImageView back;//返回图标
    private ArrayList<entrust> items = new ArrayList<>();
    private ZrcListView listView;
    private Handler handler;//用于接收子线程的信息以刷新主线程
    private MyEntrustListAdapter adapter;//寄养信息列表的适配器，用于向列表填充数据
    int curPage=1;//当前的页码
   // private List<entrust> tempolist;//用于存放返回数据的列表

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

        adapter = new MyEntrustListAdapter(this, items);
        listView.setAdapter(adapter);
        if (items.size() <= 0)
        {
            listView.refresh(); // 主动下拉刷新
        }

        // 下拉刷新事件回调
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });
        // 加载更多事件回调
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        //列表点击事件
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //把一些数据传递给详情页面
                Intent item = new Intent(EntrustMainActivity.this, MyEntrustDetail.class);
                item.putExtra("entrustId",items.get(position).getId());
                item.putExtra("title",items.get(position).getTitle());
                item.putExtra("petname",items.get(position).getPet().getName());
                item.putExtra("award",String.valueOf(items.get(position).getAward()));
                item.putExtra("content",items.get(position).getDetail());
                item.putExtra("pubtime", TimeUtils.dateToString(items.get(position).getDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));
                item.putExtra("state",items.get(position).getStatus());
                item.putExtra("petId",items.get(position).getPet().getId());
                startActivity(item);
            }
        });
    }
/*
控件的点击事件处理
 */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_entrust:
                //添加新的寄养信息
                Intent item=new Intent(this, PublishEntrust.class);
                startActivity(item);
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
/*
刷新
 */
    private void refresh() {
        curPage=1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryEntrust();
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
                QueryCountsh();
            }
        });
    }
/*
查询发布过的寄养信息数目
 */
    private void QueryCountsh() {
        //http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QueryCount");
        //添加对用户的筛选
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));

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
/*
首次查询发布过的寄养新
 */
    private void QueryEntrust() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QueryList");
        httpReponse.addWhereParams("userId","=",preferences.getString("id",""));
        //添加排序的字段
        httpReponse.addOrderFieldParams("date");
        //是否为降序  true表示降序   false表示正序
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(1,10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<entrust> tempolist= Arrays.asList(JSONUtil.parseArray(data,entrust.class));
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
    加载更多寄养信息
     */
    private void Querymore(int page) {
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
                List<entrust> tempolist= Arrays.asList(JSONUtil.parseArray(data,entrust.class));
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