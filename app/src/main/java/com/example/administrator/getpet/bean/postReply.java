package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class postReply  implements Serializable {

public String id;
public Date time;
public String message;
public String postId;
public String userId;
public post post ;
public users users ;
public String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public com.example.administrator.getpet.bean.post getPost() {
        return post;
    }

    public void setPost(com.example.administrator.getpet.bean.post post) {
        this.post = post;
    }

    public com.example.administrator.getpet.bean.users getUsers() {
        return users;
    }

    public void setUsers(com.example.administrator.getpet.bean.users users) {
        this.users = users;
    }
}
