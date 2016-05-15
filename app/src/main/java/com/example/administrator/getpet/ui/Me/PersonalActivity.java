package com.example.administrator.getpet.ui.Me;

import android.os.Bundle;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseActivity;
import com.example.administrator.getpet.bean.UserBean;
import com.example.administrator.getpet.presenter.UserPresenter;

public class PersonalActivity extends BaseActivity implements IUserView {

    private UserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();

    }

    private void initView() {
        userPresenter = new UserPresenter(this);
    }

    @Override
    public void setUser(UserBean user) {

    }

    @Override
    public UserBean getUser() {
        UserBean user = new UserBean();

        return user;
    }
}
