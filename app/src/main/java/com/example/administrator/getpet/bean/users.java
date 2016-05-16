package com.example.administrator.getpet.bean;

import java.io.Serializable;
public class users  implements Serializable {

    public String id;               //id
    public String phone;            //手机号
    public String nickName;         //昵称
    public String sex;              //性别
    public int age;                 //年龄
    public String address;          //所在地
    public String email;            //邮箱
    public String personal;         //个人说明
    public String occupation;       //职业
    public indentified indentified; //是否认证
    /**
     * 用户类型0为普通用户，1为救助站用户
     */
    public int indentify;
    public int user_reputation;     //声望
}
