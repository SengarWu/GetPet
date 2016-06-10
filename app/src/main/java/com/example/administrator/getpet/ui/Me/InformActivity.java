package com.example.administrator.getpet.ui.Me;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.reply;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformActivity extends BaseActivity {

    private static final String TAG = "InformActivity";

    private ImageButton ib_back;
    private ProgressDialog progress;
    private ListView list_inform;
    private String[] reply = {"kk与2016/6/7 20：19 回复你：对啊！","kk与2016/6/7 20：29 回复你：可以的！","kk与2016/6/7 20：40 回复你：联系我吧！"};
    private reply[] replyArry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        initView();
        progress = new ProgressDialog(this);
        progress.setMessage("正在加载数据...");
        progress.show();
        loadData();
    }

    private void loadData() {
        //
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("reply","QueryList");
        String id = preferences.getString("id","");
        httpReponse.addWhereParams("userId","=",id);
        httpReponse.QueryList(-1, 1, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                progress.dismiss();
                replyArry = JSONUtil.parseArray(data,reply.class);
                setupView();
                return;
            }
            @Override
            public void Fail(String e) {
                Log.d(TAG, "Fail: "+e);
                progress.dismiss();
                ToastUtils.showToast(mContext,e);
                return;
            }
        });
    }

    private void setupView() {
        /*reply = new String[replyArry.length];
        for (int i = 0; i < replyArry.length; i++) {
            reply[0p./i] = replyArry[i].loseId +"于"+ replyArry[i].replyTime +"回复你：" + replyArry[i].replyMessage;
        }*/
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < reply.length; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("tv_reply",reply[i]);
            listItems.add(listItem);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.inform_item,
                new String[]{"tv_reply"},new int[]{R.id.tv_reply});
        list_inform.setAdapter(adapter);
    }

    private void initView() {
        list_inform = $(R.id.list_inform);
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
