package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class pet  implements Serializable {

public String id;
public int name;
public int age;
public String phone;
public String character;
public users users ;
public category category ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public com.example.administrator.getpet.bean.users getUsers() {
        return users;
    }

    public void setUsers(com.example.administrator.getpet.bean.users users) {
        this.users = users;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public com.example.administrator.getpet.bean.category getCategory() {
        return category;
    }

    public void setCategory(com.example.administrator.getpet.bean.category category) {
        this.category = category;
    }
}
