package com.example.administrator.getpet.ui.HelpStation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.sPet;
import com.example.administrator.getpet.utils.GetPictureUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sPetActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "sPetActivity";

    private ImageButton ib_back;
    private TextView tv_add_spet;
    private sPetAdapter adapter;
    private ListView lv_spet;

    private sPet[] spetArry;


    private List<Map<String, Object>> listItems;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_pet);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress = new ProgressDialog(this);
        progress.setMessage("加载中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        loadData();
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_add_spet = $(R.id.tv_add_spet);
        tv_add_spet.setOnClickListener(this);
        lv_spet = $(R.id.lv_spet);
    }

    private void loadData() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("sPet","QueryList");
        String id = preferences.getString("id","");
        Log.d(TAG, "loadData: id:"+id);
        httpReponse.addWhereParams("StationId","=",id);
        httpReponse.QueryList(-1, 1, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                progress.dismiss();
                spetArry = JSONUtil.parseArray(data,sPet.class);
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
        adapter = new sPetAdapter(mContext,listItems);
        lv_spet.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < spetArry.length; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("pet_photo", GetPictureUtils.GetPicture(spetArry.length)[i]);
            listItem.put("pet_name",spetArry[i].name);
            listItem.put("pet_age",String.valueOf(spetArry[i].age));
            listItem.put("pet_state",spetArry[i].state);
            listItem.put("pet_money",String.valueOf(spetArry[i].money));
            listItem.put("pet_address",spetArry[i].address);
            listItems.add(listItem);
        }
        return listItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_spet:
                startAnimActivity(AddsPetActivity.class);
                break;
        }
    }
}
