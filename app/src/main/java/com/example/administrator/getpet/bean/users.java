package com.example.administrator.getpet.bean;

import java.io.Serializable;
public class users  implements Serializable {

    public String id;               //id
    public String phone;            //手机号
    public String password;         //密码
    public String nickName;         //昵称
    public String sex;              //性别
    public int age;                 //年龄
    public String address;          //所在地
    public String email;            //邮箱
    public String personal;         //个人说明
    public String occupation;       //职业
    public indentified indentified; //认证信息
    public String photo;            //头像
    /**
     * 用户类型0为普通用户，1为救助站用户
     */
    public int identify;
    public int user_reputation;     //信誉度


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public com.example.administrator.getpet.bean.indentified getIndentified() {
        return indentified;
    }

    public void setIndentified(com.example.administrator.getpet.bean.indentified indentified) {
        this.indentified = indentified;
    }

    public int getIndentify() {
        return identify;
    }

    public void setIndentify(int indentify) {
        this.identify = indentify;
    }

    public int getUser_reputation() {
        return user_reputation;
    }

    public void setUser_reputation(int user_reputation) {
        this.user_reputation = user_reputation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
