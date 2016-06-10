package com.example.administrator.getpet.ui.Me;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.ui.Me.adapter.MyPetAdapter;
import com.example.administrator.getpet.utils.GetPictureUtils;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPetActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyPetActivity";

    private ImageButton ib_back;
    private TextView tv_add_pet;
    private ListView lv_my_pet;
    private MyPetAdapter adapter;
    private pet[] petArry;
    private List<Map<String, Object>> listItems;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progress = new ProgressDialog(this);
        progress.setMessage("正在加载数据...");
        progress.show();
        loadData();
    }

    private void setupView() {
        listItems=getData();
        adapter = new MyPetAdapter(mContext,listItems);
        lv_my_pet.setAdapter(adapter);
    }

    private void loadData() {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("pet","QueryList");
        String id = preferences.getString("id","");
        Log.d(TAG, "loadData: id:"+id);
        httpReponse.addWhereParams("userId","=",id);
        httpReponse.QueryList(-1, 3, new HttpCallBack() {
            @Override
            public void Success(String data) {
                Log.d(TAG, "Success: data:"+data);
                progress.dismiss();
                petArry = JSONUtil.parseArray(data,pet.class);
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

    private List<Map<String,Object>> getData() {

        List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < petArry.length; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("pet_photo", GetPictureUtils.GetPicture(petArry.length)[i]);
            listItem.put("pet_name",String.valueOf(petArry[i].name));
            listItem.put("pet_age",String.valueOf(petArry[i].age));
            listItem.put("pet_category",String.valueOf(petArry[i].category.name));
            listItem.put("pet_character",String.valueOf(petArry[i].character));
            listItems.add(listItem);
        }
        return listItems;
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_add_pet = $(R.id.tv_add_pet);
        tv_add_pet.setOnClickListener(this);
        lv_my_pet = $(R.id.lv_my_pet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_pet:
                startAnimActivity(AddPetActivity.class);
                break;
        }
    }
}
