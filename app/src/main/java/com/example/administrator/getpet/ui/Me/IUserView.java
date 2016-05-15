package com.example.administrator.getpet.ui.Me;

import com.example.administrator.getpet.bean.UserBean;

/**
 * Created by Administrator on 2016/5/12.
 */
public interface IUserView {
    //给UI显示数据的方法
    void setUser(UserBean user);
    //从UI取数据的方法
    UserBean getUser();
}
