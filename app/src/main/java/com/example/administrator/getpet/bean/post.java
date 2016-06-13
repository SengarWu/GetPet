package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class post  implements Serializable {

    public String id;
    public String userId;
    public String title;
    public String mes;
    public Date date;
    public int num;
    public int intergen;
    public int seeNum;
    public String state;
    public users users ;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIntergen() {
        return intergen;
    }

    public void setIntergen(int intergen) {
        this.intergen = intergen;
    }

    public int getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(int seeNum) {
        this.seeNum = seeNum;
    }

    public com.example.administrator.getpet.bean.users getUsers() {
        return users;
    }

    public void setUsers(com.example.administrator.getpet.bean.users users) {
        this.users = users;
    }
}
