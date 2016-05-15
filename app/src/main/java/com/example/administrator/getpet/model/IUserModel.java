package com.example.administrator.getpet.model;

import com.example.administrator.getpet.bean.UserBean;

/**
 * Created by Administrator on 2016/5/12.
 */

//Model层抽象接口：
public interface  IUserModel {
    //从数据提供者获取数据方法
    UserBean getUser();
    void setUser(UserBean user);
}
