package com.example.administrator.getpet.ui.Home.PetCircle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchPost extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private RadioButton rb_post_seeNum, rb_activity_replyNum,rb_myComment;//用于选择排序方式的按钮组
    private ViewPager vp_post;//用于存储小界面的框
    List<Fragment> list = null;//用于记录有多少个小界面供切换
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        initView();
    }

    private void initView() {
        rb_post_seeNum=(RadioButton)findViewById(R.id.seeNum);
        rb_activity_replyNum=(RadioButton)findViewById(R.id.replyNum);
        rb_myComment=(RadioButton)findViewById(R.id.myComment);
        vp_post=(ViewPager)findViewById(R.id.post_list);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        rb_activity_replyNum.setOnClickListener(this);
        rb_post_seeNum.setOnClickListener(this);
        rb_myComment.setOnClickListener(this);
        list = new ArrayList<>();
        OtherPostListFragment BySeeNum=new OtherPostListFragment();
        BySeeNum.orderBy="seeNum";
        BySeeNum.userId=preferences.getString("id","");
        OtherPostListFragment ReplyNum=new OtherPostListFragment();
        ReplyNum.orderBy="num";
        ReplyNum.userId=preferences.getString("id","");
        MyReplyPostFragment mycomment=new MyReplyPostFragment();
        list.add(BySeeNum);
        list.add(ReplyNum);
        list.add(mycomment);
        ZxzcAdapter zxzc = new ZxzcAdapter(getSupportFragmentManager(), list);
        vp_post.setAdapter(zxzc);

        //滑动切换
        vp_post.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                        rb_post_seeNum.setChecked(true);
                        break;
                    case 1:
                        rb_activity_replyNum.setChecked(true);
                        break;
                    case 2:
                        rb_myComment.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        rb_post_seeNum.setChecked(true);
    }

    /*
    点击按钮切换
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == rb_post_seeNum.getId()) {
            vp_post.setCurrentItem(0);
        } else if (i == rb_activity_replyNum.getId()) {
            vp_post.setCurrentItem(1);
        }else if (i == rb_myComment.getId()) {
            vp_post.setCurrentItem(2);
        }
    }
/*
小界面的适配器
 */
    class ZxzcAdapter extends FragmentStatePagerAdapter {

        List<Fragment> list;

        public ZxzcAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back://返回界面
                this.finish();
                break;
            case R.id.seeNum://切换页面
                vp_post.setCurrentItem(0);
                break;
            case R.id.replyNum://切换页面
                vp_post.setCurrentItem(1);
                break;
            case R.id.myComment://切换页面
                vp_post.setCurrentItem(2);
                break;
        }
    }
}
