package com.example.administrator.getpet.ui.Me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseFragment;

public class IdentityFragment extends BaseFragment {

    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_age;
    private TextView tv_marStatus;
    private TextView tv_address;
    private TextView tv_company;
    private TextView tv_post;
    private TextView tv_income;
    private TextView tv_phone;
    private TextView tv_qq;
    private TextView tv_wechat;
    private TextView tv_others;

    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_identity, container, false);
        initView();
        setupView();
        return parentView;
    }

    private void setupView() {
        String indentifiedId = preferences.getString("indentifiedId","");
        if (!TextUtils.isEmpty(indentifiedId))
        {
            String name = preferences.getString("name","");
            String sex = preferences.getString("sex","");
            int age = preferences.getInt("age",0);
            String marStatus = preferences.getString("marStatus","");
            String address = preferences.getString("address","");
            String company = preferences.getString("company","");
            String post = preferences.getString("post","");
            String income = preferences.getString("sex","");
            String phone = preferences.getString("income","");
            String qq = preferences.getString("qq","");
            String wechat = preferences.getString("wechat","");
            String others = preferences.getString("others","");
            tv_name.setText(name);
            tv_sex.setText(sex);
            tv_age.setText(String.valueOf(age));
            tv_marStatus.setText(marStatus);
            tv_address.setText(address);
            tv_company.setText(company);
            tv_post.setText(post);
            tv_income.setText(income);
            tv_phone.setText(phone);
            tv_qq.setText(qq);
            tv_wechat.setText(wechat);
            tv_others.setText(others);
        }
    }

    private void initView() {
        tv_name = (TextView) parentView.findViewById(R.id.tv_name);
        tv_sex = (TextView) parentView.findViewById(R.id.tv_sex);
        tv_age = (TextView) parentView.findViewById(R.id.tv_age);
        tv_marStatus = (TextView) parentView.findViewById(R.id.tv_marStatus);
        tv_address = (TextView) parentView.findViewById(R.id.tv_address);
        tv_company = (TextView) parentView.findViewById(R.id.tv_company);
        tv_post = (TextView) parentView.findViewById(R.id.tv_post);
        tv_income = (TextView) parentView.findViewById(R.id.tv_income);
        tv_phone = (TextView) parentView.findViewById(R.id.tv_phone);
        tv_qq = (TextView) parentView.findViewById(R.id.tv_qq);
        tv_wechat = (TextView) parentView.findViewById(R.id.tv_wechat);
        tv_others = (TextView) parentView.findViewById(R.id.tv_others);
    }
}
