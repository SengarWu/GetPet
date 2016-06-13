package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class praise  implements Serializable {

public String id;
public int num;
public Date time;
public String userId;
public String petId;
public pet pet ;
public users users ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public com.example.administrator.getpet.bean.pet getPet() {
        return pet;
    }

    public void setPet(com.example.administrator.getpet.bean.pet pet) {
        this.pet = pet;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public com.example.administrator.getpet.bean.users getUsers() {
        return users;
    }

    public void setUsers(com.example.administrator.getpet.bean.users users) {
        this.users = users;
    }
}
