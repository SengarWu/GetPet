package com.example.administrator.getpet.ui.HelpStation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.application;
import com.example.administrator.getpet.utils.GetPictureUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdoptMsgActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AdoptMsgActivity";

    private ImageButton ib_back;//返回按钮
    private ListView lv_adopt;//列表

    private AdoptAdapter adapter;//列表适配器

    private application[] applicationArry;//数据数组

    private List<Map<String, Object>> listItems;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_msg);
        initView();
        progress = new ProgressDialog(this);
        progress.setMessage("加载中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        if (listItems != null)
        {
            listItems.clear();
        }
        loadData();
    }

    private void loadData() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("application","QueryList");
        String id = preferences.getString("id","");
        Log.d(TAG, "loadData: id:"+id);
        httpReponse.addWhereParams("rsId","=",id);
        httpReponse.QueryList(-1, 1, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                progress.dismiss();
                applicationArry = JSONUtil.parseArray(data,application.class);
                setupView();
                return;
            }

            @Override
            public void Fail(String e) {
                progress.dismiss();
                Log.d(TAG, "Fail: "+e);
                ToastUtils.showToast(mContext,e);
            }
        });
    }

    private void setupView() {
        listItems=getData();
        adapter = new AdoptAdapter(mContext,listItems);
        lv_adopt.setAdapter(adapter);
        lv_adopt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdoptMsgActivity.this, AdoptDetailActivity.class);
                Bundle data = new Bundle(); //Bundle对象用于传递果种对象
                application apply = applicationArry[position]; //点中的对象
                data.putSerializable("application", apply);
                intent.putExtras(data);
                intent.putExtra("length",applicationArry.length);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < applicationArry.length; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("pet_photo", GetPictureUtils.GetPicture(applicationArry.length)[i]);
            listItem.put("pet_name",applicationArry[i].sPet.name);
            listItem.put("username",applicationArry[i].users.nickName);
            if (applicationArry[i].state == 0)
            {
                listItem.put("state","未审核");
            }
            else if (applicationArry[i].state == 1)
            {
                listItem.put("state","已审核");
            }
            else
            {
                listItem.put("state","审核未通过");
            }
            listItem.put("reason",applicationArry[i].reason);
            listItems.add(listItem);
        }
        return listItems;
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        lv_adopt = $(R.id.lv_adopt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
        }
    }

}
