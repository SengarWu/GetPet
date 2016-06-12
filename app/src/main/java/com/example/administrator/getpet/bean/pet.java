package com.example.administrator.getpet.bean;

import java.io.Serializable;
public class pet  implements Serializable {

    public String id;
    public String name;            //名字
    public int age;             //年龄
    public String photo;        //头像
    public String character;    //性格
    public users users ;
    public category category ; //类别

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }
}
