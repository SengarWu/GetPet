package com.example.administrator.getpet.model;

import com.example.administrator.getpet.bean.UserBean;

/**
 * Created by Administrator on 2016/5/12.
 */
//Model层抽象实现：
public class UserModelImpl implements IUserModel {
    //存储数据
    private UserBean user = new UserBean();
    @Override
    public UserBean getUser() {
        //存储数据操作

        return user;
    }

    @Override
    public void setUser(UserBean user) {
        //存储数据

        this.user = user;
    }
}
