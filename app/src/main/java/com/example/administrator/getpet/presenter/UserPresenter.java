package com.example.administrator.getpet.presenter;

import com.example.administrator.getpet.bean.UserBean;
import com.example.administrator.getpet.model.IUserModel;
import com.example.administrator.getpet.model.UserModelImpl;
import com.example.administrator.getpet.ui.Me.IUserView;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UserPresenter {
    private IUserModel userModel;
    private IUserView userView;

    public UserPresenter(IUserView userView)
    {
        this.userView = userView;
        userModel = new UserModelImpl();
    }

    //供UI调运
    public void saveUser(UserBean user)
    {
        userModel.setUser(user);
    }

    //供UI调运
    public void getUser()
    {
        //通过调用IUserView的方法来更新显示，设计模式运用
        //类似回调监听处理
        userView.setUser(userModel.getUser());
    }
}
