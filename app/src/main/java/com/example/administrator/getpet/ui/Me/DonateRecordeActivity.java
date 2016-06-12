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
import com.example.administrator.getpet.bean.donate;
import com.example.administrator.getpet.utils.GetPictureUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;
import com.example.administrator.getpet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonateRecordeActivity extends BaseActivity {

    private static final String TAG = "DonateRecordeActivity";

    private ImageButton ib_back;
    private ListView lv_donate;
    private ProgressDialog progress;
    private donate[] donateArry;
    /*private int[] image; //图片
    private String[] time = {"2016/6/7 20:00","2016/6/7 20:10","2016/6/7 20:20"};
    private String[] pet_name = {"小胖","妮妮","婷婷"};
    private String[] money = {"10","20","30"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_recorde);
        initView();
        progress = new ProgressDialog(this);
        progress.setMessage("正在加载数据...");
        progress.show();
        loadData();
    }

    private void loadData() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("donate","QueryList");
        String id = preferences.getString("id","");
        Log.d(TAG, "loadData: id:"+id);
        httpReponse.addWhereParams("userId","=",id);
        httpReponse.QueryList(-1, 1, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                progress.dismiss();
                donateArry = JSONUtil.parseArray(data,donate.class);
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
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < donateArry.length; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("image", GetPictureUtils.GetPicture(donateArry.length)[i]);
            listItem.put("time", TimeUtils.dateToString(donateArry[i].time,"yyyy-MM-dd HH:mm:ss"));
            listItem.put("pet_name",donateArry[i].sPet.name);
            listItem.put("money",donateArry[i].money);
            listItem.put("message",donateArry[i].message);
            listItems.add(listItem);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.donate_record_item,
                new String[]{"image","time","pet_name","money","message"},new int[]{R.id.riv_pet_photo,R.id.tv_time,
                R.id.tv_spet_name,R.id.tv_money,R.id.tv_message});
        lv_donate.setAdapter(adapter);
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_donate = $(R.id.lv_donate);
    }
}
