package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.OtherPostAdapter;
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

/**
 * Created by Koreleone on 2016-06-11.
 */
public class OtherPostListFragment extends Fragment {
    private View parentView;//所属视图
    private ZrcListView listView;//数据列表
    private Handler handler;
    private ArrayList<post> items = new ArrayList<>();//用于记录查询过的贴
    int curPage = 1;//页码
    private OtherPostAdapter adapter;//用于向列表填充数据的适配器
    public String orderBy;//按照什么排序
    public String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_anything_list, container, false);
        initView();
        return parentView;
    }

    private void initView() {
        listView = (ZrcListView)parentView.findViewById(R.id.zlv_anything);//ZrcListView为自定义的列表控件
        handler = new Handler();
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xffee71a1);
        header.setCircleColor(0xffee71a1);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(0xffee71a1);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.top_item_in);
        listView.setItemAnimForBottomIn(R.anim.bottom_item_in);

        adapter = new OtherPostAdapter(getActivity(), items);
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
                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("post","updateColumnsById");
                httpReponse.addColumnParams("seeNum",String.valueOf(items.get(position).getSeeNum()+1));
                httpReponse.updateColumnsById(items.get(position).getId(), new HttpCallBack() {
                    @Override
                    public void Success(String data) {

                    }
                    @Override
                    public void Fail(String e) {

                    }
                });
                items.get(position).setSeeNum(items.get(position).getSeeNum()+1);
                Intent item = new Intent(getActivity(), PostDetail.class);
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
        curPage=0;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryPost();//queryProxy为自定义的查询类
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
查询帖子数目
 */
    private void QueryCount() {
        //http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("post","QueryCount");
        httpReponse.addWhereParams("userId","!=",userId);
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
    首次查询帖子
     */
    private void QueryPost() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("post","QueryList");
        httpReponse.addWhereParams("userId","!=",userId);
        //添加排序的字段
        httpReponse.addOrderFieldParams(orderBy);
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
    加载更多帖子
     */
    private void Querymore(int page) {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("post","QueryList");
        httpReponse.addWhereParams("userId","!=",userId);
        //添加排序的字段
        httpReponse.addOrderFieldParams(orderBy);
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
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                listView.stopLoadMore();
            }
        });
    }
}
