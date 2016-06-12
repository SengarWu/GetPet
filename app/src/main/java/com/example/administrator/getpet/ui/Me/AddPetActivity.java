package com.example.administrator.getpet.ui.Me;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.category;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.ToastUtils;

public class AddPetActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddPetActivity";

    private ImageButton ib_back;
    private TextView tv_save;
    private EditText et_pet_name;
    private EditText et_pet_age;
    private EditText et_pet_character;
    private Spinner sp_pet_category;
    //private ImageView iv_pet_photo;
    //private ImageButton ib_pet_photo;

    private ProgressDialog progressDialog;

    private category[] cateArry;
    private String[] cateNameArry;
    private String[] cateIdArry;

    private final int LOADSUCCESS = 1001;


    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case LOADSUCCESS:
                    setupData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        initView();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载数据，请稍后！");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        loadData();
    }

    private void setupData() {
        Log.d(TAG, "setupData: cateArry.length:"+cateArry.length);
        cateNameArry = new String[cateArry.length];
        cateIdArry = new String[cateArry.length];
        //为类型数据赋值
        for (int i = 0; i < cateArry.length; i++) {
            cateNameArry[i] = cateArry[i].name;
            cateIdArry[i] = cateArry[i].id;
        }
        //数组配置器下拉菜单赋值用
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,cateNameArry);
        sp_pet_category.setAdapter(adapter);
    }

    private void loadData() {
        //加载宠物类型
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("category","QueryListX");
        httpReponse.addViewColumnsParams("id");
        httpReponse.addViewColumnsParams("name");
        httpReponse.QueryListX(-1, 10, new HttpCallBack() {
            @Override
            public void Success(String data) {
                progressDialog.dismiss();
                Log.d(TAG, "Success: data:"+data);
                cateArry = JSONUtil.parseArray(data,category.class);
                handler.sendEmptyMessage(LOADSUCCESS);
            }

            @Override
            public void Fail(String e) {
                progressDialog.dismiss();
                ToastUtils.showToast(mContext,e);
            }
        });
    }

    private void initView() {
        ib_back = $(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_save = $(R.id.tv_save);
        tv_save.setOnClickListener(this);
        et_pet_name = $(R.id.et_pet_name);
        et_pet_age = $(R.id.et_pet_age);
        et_pet_character = $(R.id.et_pet_character);
        sp_pet_category = $(R.id.sp_pet_category);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_save:
                String pet_name = et_pet_name.getText().toString();
                int pet_age = Integer.parseInt(et_pet_age.getText().toString());
                String pet_character = et_pet_character.getText().toString();
                String pet_category_Id = getCategoryId(sp_pet_category.getSelectedItem().toString());
                String userId = preferences.getString("id","");
                if (TextUtils.isEmpty(userId))
                {
                    ToastUtils.showToast(mContext,"数据加载有误，请重新登录！");
                    return;
                }
                if (TextUtils.isEmpty(pet_name))
                {
                    ToastUtils.showToast(mContext,"宠物名不能为空呢！");
                    return;
                }
                if (pet_age < 1 || pet_age > 99)
                {
                    ToastUtils.showToast(mContext,"年龄范围为1-99哦！");
                    return;
                }
                if (TextUtils.isEmpty(pet_character))
                {
                    pet_character = "";
                }
                pet pet = new pet();
                pet.name = pet_name;
                pet.age = pet_age;
                pet.character = pet_character;
                //图片
                //pet.photo =

                category ch=new category();
                ch.id=pet_category_Id;
                pet.category = ch;
                users user = new users();
                user.id = userId;
                pet.users = user;

                progressDialog.setMessage("正在上传数据...");
                progressDialog.show();
                submit(pet);
                break;
            /*case R.id.ib_pet_photo:
                //点击进行图片上传

                break;
            case R.id.iv_pet_photo:
                //打开图片查看器

                break;*/
        }
    }

    private String getCategoryId(String s) {
        int j=0;
        for (int i = 0; i < cateNameArry.length; i++) {
            if (s == cateNameArry[i])
            {
                break;
            }
            else
            {
                j++;
            }
        }
        return cateIdArry[j];
    }

    private void submit(pet pet) {
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("pet","Insert");
        httpReponse.insert(pet,new HttpCallBack() {
            @Override
            public void Success(String data) {
                progressDialog.dismiss();
                Log.d(TAG, "Success: data:"+data);
                ToastUtils.showToast(mContext,"添加成功！");
                clear();
                finish();

            }

            @Override
            public void Fail(String e) {
                Log.d(TAG, "Fail: "+e);
                progressDialog.dismiss();
                ToastUtils.showToast(mContext,e);
            }
        });
    }

    private void clear() {
        et_pet_age.setText("");
        et_pet_character.setText("");
        et_pet_name.setText("");
        /*iv_pet_photo.setVisibility(View.GONE);
        ib_pet_photo.setVisibility(View.VISIBLE);*/
    }
}
