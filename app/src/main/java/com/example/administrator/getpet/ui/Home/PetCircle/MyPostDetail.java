package com.example.administrator.getpet.ui.Home.PetCircle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.post;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.AnswerAdapter;
import com.example.administrator.getpet.ui.Home.PetCircle.Adapter.AnswerAdapterWithComment;
import com.example.administrator.getpet.utils.CommonUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;
import com.example.administrator.getpet.view.RoundImageView;
import com.example.administrator.getpet.view.xlistview.SimpleFooter;
import com.example.administrator.getpet.view.xlistview.SimpleHeader;
import com.example.administrator.getpet.view.xlistview.ZrcListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyPostDetail extends BaseActivity implements View.OnClickListener {
    private ImageView back;//返回按钮
    private RoundImageView user_head;//记录用户的头像
    private TextView username;//用户名
    private TextView title;//标题
    private TextView time;//发帖时间
    private TextView content_summary;//内容
    private TextView state;//帖子状态
    private TextView seeNum;//浏览次数
    private TextView award;//悬赏积分
    private TextView replyNum;//回复条数
    private EditText new_reply;///回复文本框
    private Button comment;//回复按钮
    private AnswerAdapterWithComment adapter;//用于向回帖列表填充数据的适配器
    private ArrayList<postReply> items = new ArrayList<>();//用于记录搜索到的回复
    private ZrcListView listView;//列表
    private Handler handler;//用于刷新列表
    private post Post;//记录传过来的帖子记录
    private int curPage=1;//分页查询回复页码
    private RelativeLayout pageView;//当前页码布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_detail);
        initView();
    }

    private void initView() {
        /*
        获取传过来的数据
         */
        Intent intent=getIntent();
        Post=(post)intent.getSerializableExtra("post");
        /*
        控件的获取
         */
        back=(ImageView)findViewById(R.id.back);
        user_head=(RoundImageView)findViewById(R.id.user_head);
        username=(TextView)findViewById(R.id.username);
        title=(TextView)findViewById(R.id.title) ;
        time=(TextView)findViewById(R.id.publishTime);
        content_summary=(TextView)findViewById(R.id.content_summary);
        state=(TextView)findViewById(R.id.state);
        award=(TextView)findViewById(R.id.award);
        seeNum=(TextView)findViewById(R.id.seeNum);
        replyNum=(TextView)findViewById(R.id.replyNum);
        new_reply=(EditText)findViewById(R.id.new_reply);
        comment=(Button)findViewById(R.id.comment);
        pageView=(RelativeLayout)findViewById(R.id.pageView);
        comment.setOnClickListener(this);
        back.setOnClickListener(this);
        pageView.requestFocus();//页面获取焦点
        /*
        将帖子的相关数据显示到页面中
         */
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(Post.getUsers().getPhoto()),user_head,R.mipmap.home_pet_photp);
        username.setText(Post.getUsers().getNickName());
        title.setText(Post.getTitle());
        content_summary.setText("    "+Post.getMes());
        state.setText(Post.getState());
        award.setText(String.valueOf(Post.getIntergen()));
        seeNum.setText("浏览:"+String.valueOf(Post.getSeeNum()+1));
        replyNum.setText("回复:"+String.valueOf(Post.getNum()));
        time.setText(TimeUtils.dateToString(Post.getDate(),TimeUtils.FORMAT_DATE));
        //列表的初始化
        listView = (ZrcListView)findViewById(R.id.answer_list);
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

        adapter = new AnswerAdapterWithComment(this, items,Post.getState());
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

    /*
    刷新列表
     */
    private void refresh() {
        curPage=1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                QueryAnswer();//queryProxy为自定义的查询类
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
                QueryCountAnswer();
            }
        });
    }

    /*
    首次查询回复
     */
    private void QueryAnswer() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("postReply","QueryList");
        //查询本帖的相关评论
        httpReponse.addWhereParams("postId","=",Post.getId());
        httpReponse.QueryList(1,10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<postReply> tempolist= Arrays.asList(JSONUtil.parseArray(data,postReply.class));
                if (tempolist.size() != 0) {
                    if (CommonUtils.isNotNull(tempolist)) {//监测网络等是否可用
                        items.clear();
                        adapter.addAll(tempolist);
                        //判断数据是否已经查询完毕
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
                        listView.stopLoadMore();//停止加载数据
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    listView.setRefreshFail("暂时没有回复");
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
    查询回复数目以确定是否继续查询
     */
    private void QueryCountAnswer() {
        //http请求
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("postReply","QueryCount");
        //查询本帖的相关评论
        httpReponse.addWhereParams("postId","=",Post.getId());
        //调用QueryCount方法
        httpReponse.QueryCount(new HttpCallBack() {
            @Override
            public void Success(String data) {
                Integer x=Integer.valueOf(data);
                //如果数据总数大于已经显示的数目，则加载更多
                if(x> items.size()){
                    curPage++;
                    QueryMore(curPage);
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
    private void QueryMore(int page){
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("postReply","QueryList");
        //查询本帖的相关评论
        httpReponse.addWhereParams("postId","=",Post.getId());
        httpReponse.QueryList(page,10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                List<postReply> tempolist= Arrays.asList(JSONUtil.parseArray(data,postReply.class));
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
        switch(v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.comment:
                /*
                验证输入
                 */
                if(new_reply.getText().toString().equals("")){
                    Toast.makeText(mContext, "回复内容不可为空", Toast.LENGTH_LONG).show();
                }else {
                    SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("postReply", "Insert");
                    postReply reply = new postReply();
                    reply.setMessage(new_reply.getText().toString());
                    reply.setTime(new Date());
                    /*
                    设置回哪张贴
                     */
                    reply.setPost(Post);
                    /*
                    设置回复人
                    */
                    users current=new users();
                    current.setId(preferences.getString("id",""));
                    reply.setUsers(current);
                    reply.setResult("普通回答");
                    //调用insert方法
                    httpReponse.insert(reply, new HttpCallBack() {
                        @Override
                        public void Success(String data) {
                            Toast.makeText(mContext, "回帖成功", Toast.LENGTH_LONG).show();
                            new_reply.setText("");
                            pageView.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            listView.refresh();
                        }

                        @Override
                        public void Fail(String e) {
                            Toast.makeText(mContext, "回帖失败，请检查网络", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
    }
}
