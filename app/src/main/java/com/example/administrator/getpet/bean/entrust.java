package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class entrust  implements Serializable {

    public String id;
    public String title;
    public String detail;
    public int award;
    public String city;
    public String status;
    public Date date;
    public users users ;
    public pet pet ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public com.example.administrator.getpet.bean.users getUsers() {
        return users;
    }

    public void setUsers(com.example.administrator.getpet.bean.users users) {
        this.users = users;
    }

    public com.example.administrator.getpet.bean.pet getPet() {
        return pet;
    }

    public void setPet(com.example.administrator.getpet.bean.pet pet) {
        this.pet = pet;
    }
}
