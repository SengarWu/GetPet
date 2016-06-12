package com.example.administrator.getpet.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.sPet;
import com.example.administrator.getpet.ui.Me.DonateRecordeActivity;
import com.example.administrator.getpet.ui.Me.InformActivity;
import com.example.administrator.getpet.ui.Me.MyAttentionActivity;
import com.example.administrator.getpet.ui.Me.PersonalActivity;
import com.example.administrator.getpet.ui.Me.SpetDetailActivity;
import com.example.administrator.getpet.ui.Me.adapter.sPetAdapter;
import com.example.administrator.getpet.ui.findPet.myAttention;
import com.example.administrator.getpet.ui.findPet.myPublishes;
import com.example.administrator.getpet.ui.findPet.petDetail;
import com.example.administrator.getpet.ui.findPet.petScan;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;
import com.example.administrator.getpet.view.DragLayout;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home extends BaseActivity implements View.OnClickListener {

    private DragLayout dl;
    private ImageView iv_icon;
    private LinearLayout ll3;   //我的关注
    private LinearLayout ll4;   //我的宠物
    private LinearLayout ll5;   //个人信息
    private LinearLayout ll6;   //消息通知
    private LinearLayout ll7;   //交易记录
    private LinearLayout ll8;   //退出账号

    private ImageButton ib_xinxiqiang;
    private LinearLayout ll_jyly;
    private LinearLayout ll_jiuzhu;
    private LinearLayout ll_xunhui;
    private GridView gv_jiuzhu;

    sPet[] sPetArry ; //定义接收服务器数据的数组

    private ProgressDialog progress;

    private final int LOADSUCCESS = 1001;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case LOADSUCCESS:
                    setupView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDragLayout();
        initView();
        progress = new ProgressDialog(this);
        progress.setMessage("正在加载数据，请稍后...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        LoadData();
        //setupView();
    }

    /**
     * 显示宠物列表
     */
    private void setupView() {
        List<Map<String, Object>> listItems=getData();// 创建一个List集合，List集合的元素是Map
        gv_jiuzhu.setAdapter(new sPetAdapter(mContext,listItems));
        // 为ListView的列表项的单击事件绑定事件监听器
        gv_jiuzhu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(home.this, SpetDetailActivity.class);
                Bundle data = new Bundle(); //Bundle对象用于传递果种对象
                /*
                Seeds seeds = SaveData(position); //种子对象，用于传递数据
                data.putSerializable("seeds", seeds);
                intent.putExtras(data);
                startActivity(intent);*/
            }
        });
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < sPetArry.length; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("pet_photo",sPetArry[i].photo);
            listItem.put("pet_name",sPetArry[i].name);
            listItems.add(listItem);
        }
        return listItems;
    }

    /**
     * 从网络宠物加载数据
     */
    private void LoadData() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("sPet","QueryListX");
        httpReponse.addViewColumnsParams("name");
        httpReponse.addViewColumnsParams("photo");
        httpReponse.addIsDescParams(true);
        httpReponse.QueryListX(1, 10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                //Json解析，反序列化sPet
                Log.d("caolin",data);
//                sPetArry = JSONUtil.parseArray(data,sPet.class);
                progress.dismiss();
            }

            @Override
            public void Fail(String e) {
                progress.dismiss();
                ToastUtils.showToast(mContext,e);
            }
        });

    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_icon.setOnClickListener(this);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll3.setOnClickListener(this);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll4.setOnClickListener(this);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll5.setOnClickListener(this);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll6.setOnClickListener(this);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        ll7.setOnClickListener(this);
        ll8 = (LinearLayout) findViewById(R.id.ll8);
        ll8.setOnClickListener(this);
        ib_xinxiqiang = (ImageButton) findViewById(R.id.ib_xinxiqiang);
        ib_xinxiqiang.setOnClickListener(this);
        ll_jyly = (LinearLayout) findViewById(R.id.ll_jyly);
        ll_jyly.setOnClickListener(this);
        ll_jiuzhu = (LinearLayout) findViewById(R.id.ll_jiuzhu);
        ll_jiuzhu.setOnClickListener(this);
        ll_xunhui = (LinearLayout) findViewById(R.id.ll_xunhui);
        ll_xunhui.setOnClickListener(this);
        gv_jiuzhu = (GridView) findViewById(R.id.gv_jiuzhu);
    }


    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_icon:
                dl.open();
                break;
            case R.id.ll3://我的关注
                //startAnimActivity(MyAttentionActivity.class);
                startAnimActivity(myAttention.class);
                break;
            case R.id.ll4://我的宠物
                //startAnimActivity(MyPetActivity.class);
                break;
            case R.id.ll5://个人信息
                startAnimActivity(PersonalActivity.class);
                break;
            case R.id.ll6://消息通知
                //startAnimActivity(InformActivity.class);
                startAnimActivity(myPublishes.class);
                break;
            case R.id.ll7://交易记录
                startAnimActivity(DonateRecordeActivity.class);
                break;
            case R.id.ll8://退出账号

                break;
            case R.id.ib_xinxiqiang://信息墙

                break;
            case R.id.ll_jyly:////寄养领养

                break;
            case R.id.ll_jiuzhu: //救助

                break;
            case R.id.ll_xunhui://寻回
                Intent myIntent=new Intent(home.this,petScan.class);
                startActivity(myIntent);
                break;
        }
    }
}
