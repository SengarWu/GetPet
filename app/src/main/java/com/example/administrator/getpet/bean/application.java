package com.example.administrator.getpet.bean;

import java.io.Serializable;
public class application  implements Serializable {

public String id;
public String information;
public int result;        //结果
public String reason;     //原因
public int state;         //状态，0为未审核，1为已审核
public sPet sPet ;
public Station Station ;   //救助站
public users users ;
}
