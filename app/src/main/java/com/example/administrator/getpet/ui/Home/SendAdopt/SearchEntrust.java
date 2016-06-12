package com.example.administrator.getpet.ui.Home.SendAdopt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.OtherEntrustAdapter;
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


public class SearchEntrust extends BaseActivity implements View.OnClickListener {
    private String tyId ="所有类型";
    public String citystr="所有城市";
    private DrawerLayout petlayout;//用于类型筛选的侧拉框
    private ImageView cat,dog,fish,other;//各个宠物类型的选择图标
    private TextView alltype;//所有类型的图标
    private ZrcListView listView;//用于显示寄养信息列表
    private Handler handler;//用于接收子线程的信息以刷新主线程
    int curPage = 1;//页码
    private OtherEntrustAdapter adapter;//用于向列表填充数据的适配器
    private ArrayList<entrust> items = new ArrayList<>();//用于记录已经查询到的寄养信息条数
    private TextView city;//用于显示城市
    //private List<entrust> tempolist;//用于存放返回数据的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_entrust);
        initView();
    }

    private void initView() {
        petlayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        cat=(ImageView)findViewById(R.id.cat);
        dog=(ImageView)findViewById(R.id.dog);
        fish=(ImageView)findViewById(R.id.fish);
        other=(ImageView)findViewById(R.id.other);
        city=(TextView)findViewById(R.id.tv_city);
        alltype=(TextView)findViewById(R.id.alltype);
        city.setOnClickListener(this);
        cat.setOnClickListener(this);
        dog.setOnClickListener(this);
        fish.setOnClickListener(this);
        other.setOnClickListener(this);
        alltype.setOnClickListener(this);
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
                Intent intent=new Intent(SearchEntrust.this,OtherEntrustDetail.class);
                intent.putExtra("title", items.get(position).getTitle());
                intent.putExtra("sex",items.get(position).getUsers().getSex());
                intent.putExtra("username",items.get(position).getUsers().getNickName());
                intent.putExtra("type",items.get(position).getPet().getCategory().getName());
                intent.putExtra("details",items.get(position).getDetail());
                intent.putExtra("Pubtime", TimeUtils.dateToString(items.get(position).getDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));
                intent.putExtra("pet_chatacter",items.get(position).getPet().getCharacter());
                intent.putExtra("pet_age",String.valueOf(items.get(position).getPet().getAge()));
                intent.putExtra("connect_phone",items.get(position).getUsers().getPhone());
                intent.putExtra("entrustId",items.get(position).getId());
                intent.putExtra("award",String.valueOf(items.get(position).getAward()));
                startActivity(intent);
            }
        });
    }

    private void refresh() {
        curPage=1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryEntrust();//queryProxy为自定义的查询类
            }
        });
    }
    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Querymore(curPage);
            }
        });
    }
/*
    private void QueryCountEntrust() {//存疑
        //http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QueryListByCategoryName");
        httpReponse.addWhereParams("userId","!=",preferences.getString("id",""));
        if(citystr.equals("所有城市")) {
            httpReponse.addWhereParams("city", "=", citystr, "and");
        }
        httpReponse.addWhereParams("index","=",String.valueOf(curPage),"and");
        httpReponse.addWhereParams("size","=","10","and");
        if(tyId .equals("所有类型")){
            httpReponse.addWhereParams("categoryName","=", tyId,"and");
        }
        //只查询状态为正常的寄养信息
        httpReponse.addWhereParams("status","=","正常","and");
        //调用QueryCount方法
        httpReponse.send(new HttpCallBack() {
            @Override
            public void Success(String data) {
                Integer x=Integer.valueOf(data);
                if(x > items.size()){
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
    }*/
/*
加载更多
 */
    private void Querymore(int Page) {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QueryList");
        httpReponse.addWhereParams("userId","!=",preferences.getString("id",""));
        if(!citystr.equals("所有城市")) {
            httpReponse.addWhereParams("city", "=", citystr, "and");
        }
        if(!tyId .equals("所有类型")){
            httpReponse.addWhereParams("categoryId", "=",  tyId,"and");
        }
        //只查询状态为正常的寄养信息
        httpReponse.addWhereParams("status","=","正常","and");
        //添加排序的字段
        httpReponse.addOrderFieldParams("date");
        //是否为降序  true表示降序   false表示正序
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(Page,10,new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<entrust> tempolist= Arrays.asList(JSONUtil.parseArray(data,entrust.class));
                if(tempolist.size()<10) {//数据如果小于10条，表示是最后一页了，停止加载更多
                    if (CommonUtils.isNotNull(tempolist)) {
                        adapter.addAll(tempolist);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setLoadMoreSuccess();
                    listView.stopLoadMore();
                }else{
                    if (CommonUtils.isNotNull(tempolist)) {
                        adapter.addAll(tempolist);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setLoadMoreSuccess();
                    curPage++;
                }
            }
            @Override
            public void Fail(String e)
            {
                Toast.makeText(mContext, "已到底", Toast.LENGTH_LONG).show();
                listView.stopLoadMore();
            }
        });
    }

    private void QueryEntrust() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("entrust","QueryList");
        httpReponse.addWhereParams("userId","!=",preferences.getString("id",""));
        if(!citystr.equals("所有城市")) {
            httpReponse.addWhereParams("city", "=", citystr, "and");
        }
        if(!tyId .equals("所有类型")){
            httpReponse.addParams("categoryId", tyId);
       }
        //只查询状态为正常的寄养信息
        httpReponse.addWhereParams("status","=","正常","and");
        //添加排序的字段
        httpReponse.addOrderFieldParams("date");
        //是否为降序  true表示降序   false表示正序
        httpReponse.addIsDescParams(true);
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryList(1,10,new HttpCallBack() {
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
                            curPage++;
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
                Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
                listView.setRefreshFail("加载失败");
                items.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.cat:
                tyId ="1";
                petlayout.closeDrawer(GravityCompat.START);
                listView.setSelection(0);
                listView.refresh();
                break;
            case R.id.dog:
                tyId ="2";
                petlayout.closeDrawer(GravityCompat.START);
                listView.setSelection(0);
                listView.refresh();
                break;
            case R.id.fish:
                tyId ="3";
                petlayout.closeDrawer(GravityCompat.START);
                listView.setSelection(0);
                listView.refresh();
                break;
            case R.id.other:
                tyId ="4";
                petlayout.closeDrawer(GravityCompat.START);
                listView.setSelection(0);
                listView.refresh();
                break;
            case R.id.alltype:
                tyId ="所有类型";
                petlayout.closeDrawer(GravityCompat.START);
                listView.setSelection(0);
                listView.refresh();
                break;
            case R.id.tv_city:
                startActivityForResult(new Intent(this, SelectCityActivity.class), 99);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {
            switch (resultCode) {
                case 99:
                    String receive=data.getStringExtra("lngCityName");
                    city.setText(receive);
                    citystr=receive;
                    listView.setSelection(0);
                    listView.refresh();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
