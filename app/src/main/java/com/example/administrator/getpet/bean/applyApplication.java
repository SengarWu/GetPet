package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class applyApplication  implements Serializable {

        public String id;//申请id
        public String detail;//详情
        public String phoneNumber;//联系电话
        public int result;//申请结果
        public String comment;//评价
        public Date applyDate;//申请时间
        public String applyMessage;//申请信息
        public String connectPlace;//联系地址
        public entrust entrust ;//寄养信息
        public users users ;//申请的用户

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyMessage() {
        return applyMessage;
    }

    public void setApplyMessage(String applyMessage) {
        this.applyMessage = applyMessage;
    }

    public String getConnectPlace() {
        return connectPlace;
    }

    public void setConnectPlace(String connectPlace) {
        this.connectPlace = connectPlace;
    }

    public com.example.administrator.getpet.bean.entrust getEntrust() {
        return entrust;
    }

    public void setEntrust(com.example.administrator.getpet.bean.entrust entrust) {
        this.entrust = entrust;
    }

    public com.example.administrator.getpet.bean.users getUsers() {
        return users;
    }

    public void setUsers(com.example.administrator.getpet.bean.users users) {
        this.users = users;
    }
}
