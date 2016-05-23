package com.example.administrator.getpet.utils;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.getpet.bean.Columns;
import com.example.administrator.getpet.bean.Wheres;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by caolin on 2016/3/4.
 */
public class SimpleHttpPostUtil{

    Exception ee;
    HttpCallBack callBack;
    String data;
    URL url;
    HttpURLConnection conn;
    Map<String, String> textParams = new HashMap<String, String>();
    List<Columns> columns = new ArrayList<Columns>();
    List<String> viewColumns = new ArrayList<String>();
    List<Wheres> wheres = new ArrayList<Wheres>();
    DataOutputStream ds;
    //构造函数
    public SimpleHttpPostUtil(String className,String method){
        try{
            this.url=new URL(staticConfig.baseUrl+className+"/"+method);
        }catch(Exception e){
        }
    }
    public SimpleHttpPostUtil(String postFix){
        try{
            this.url=new URL(staticConfig.baseUrl+postFix);
        }catch(Exception e){
        }
    }
    public SimpleHttpPostUtil(URL url){
          this.url=url;
    }
    //处理 器
    public Handler mHandler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    callBack.Success(data);
                    break;
                default:
                    Exception e=ee;
                    callBack.Fail(data);
                    break;
            }
        }
    };
    //增加一个普通字符串数据到form表单数据中
    public void addParams(String name,Object value) {
        textParams.put(name, String.valueOf(value));
    }
    //增加一个where条件到form表单数据中
    public void addWhereParams(String key,String operation,String value) {
        Wheres wh=new Wheres();
        wh.key=key;
        wh.value=value;
        wh.operation=operation;
        wh.relation="";
        wheres.add(wh);
    }
    //增加一个where条件到form表单数据中
    public void addWhereParams(String key,String operation,String value,String relation) {
        Wheres wh=new Wheres();
        wh.key=key;
        wh.value=value;
        wh.operation=operation;
        wh.relation=relation;
        wheres.add(wh);
    }
    //增加一个columns值到form表单数据中
    public void addColumnParams(String key,String value) {
        Columns cl=new Columns();
        cl.key=key;
        cl.value=value;
        columns.add(cl);
    }
    //增加一个column值到form表单数据中
    public void addViewColumnsParams(String value) {
        viewColumns.add(value);
    }
    //增加一个orderField条件到form表单数据中
    public void addOrderFieldParams(String value) {
        addParams(staticConfig.orderField,value);
    }
    //增加一个orderField条件到form表单数据中
    public void addIsDescParams(boolean value) {
        addParams(staticConfig.isDesc,value);
    }
    //增加一个页码到form表单数据中
    public void skip(int value) {
        addParams(staticConfig.index,value);
    }
    //增加一个页数到form表单数据中
    public void limit(int value) {
        addParams(staticConfig.size,value);
    }
    // 清空所有已添加的form表单数据
    public void clearParameters() {
        textParams.clear();
    }
    // 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
    public void send(HttpCallBack callBack){
        this.callBack=callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    initConnection();
                    ds = new DataOutputStream(conn.getOutputStream());
                    writeStringParams();
                    if (conn.getResponseCode() != 200){
                        throw new Exception("请求url失败");
                    }else{
                        InputStream in = conn.getInputStream();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buff = new byte[1024];
                        int len;
                        while ((len = in.read(buff)) != -1) {
                            out.write(buff, 0, len);
                        }
                        byte[] retdata=out.toByteArray();
                        out.close();
                        String result = new String(retdata);
                        JSONObject ob=new JSONObject(result);
                        String str=ob.getString("code");
                        if (str==null||str.equals("")){
                            data="请求失败";
                            mHandler.sendEmptyMessage(0);
                        }else{
                            data=ob.getString("result");
                            if(str.equals("1")){
                                mHandler.sendEmptyMessage(1);
                            }else{
                                mHandler.sendEmptyMessage(0);
                            }
                        }
                    }

                }
                catch (Exception e){
                    ee=e;
                    data=e.getMessage();
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();

    }
    //文件上传的connection的一些必须设置
    private void initConnection() throws Exception {
        conn = (HttpURLConnection) this.url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(6000); //连接超时为6秒
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
    }
    //字符串数据
    private void writeStringParams() throws Exception {
        StringBuilder buf = new StringBuilder();
        String name;
        String value;
        //基本参数
        Set<String> keySet = textParams.keySet();
        if(keySet.size()>0){
            for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
                name = it.next();
                value = textParams.get(name);
                buf.append(name).append("=")
                        .append(encode(value))
                        .append("&");
            }
        }
        //条件参数
        if(wheres.size()>0){
            buf.append(staticConfig.wheres).append("=")
                    .append(encode(JSONUtil.toJSON(wheres)))
                    .append("&");
        }

        //属性参数
        if(columns.size()>0){
            buf.append(staticConfig.columns).append("=")
                    .append(encode(JSONUtil.toJSON(columns)))
                    .append("&");
        }
        //视图列参数
        if(viewColumns.size()>0){
            buf.append(staticConfig.columns).append("=")
                    .append(encode(JSONUtil.toJSON(viewColumns)))
                    .append("&");
        }
        //去掉最后一个&
        buf.deleteCharAt(buf.length() - 1);
        ds.write(buf.toString().getBytes());
    }
    //编码方式
    private String encode(Object value) throws Exception{
        return URLEncoder.encode(String.valueOf(value), "UTF-8");
    }
    //方法
    public <T> void insert(T tClass,HttpCallBack httpCall){
        textParams.put(staticConfig.entity, JSONUtil.toJSON(tClass));
        send(httpCall);
    }
    public <T> void update(T tClass,HttpCallBack httpCall){
        String str=JSONUtil.toJSON(tClass);
        textParams.put(staticConfig.entity, str);
        send(httpCall);
    }
    public void delete(Object id,HttpCallBack httpCall){
        addParams(staticConfig.id, id);
        send(httpCall);
    }
    public void QueryList(int index, int size, String wheres, String orderField,HttpCallBack httpCall){
        skip(index);
        limit(size);
        addOrderFieldParams(orderField);
        send(httpCall);
    }
    public void QueryList(int index, int size, String wheres,HttpCallBack httpCall){
        skip(index);
        limit(size);
        send(httpCall);
    }
    public void QueryList(int index, int size,HttpCallBack httpCall){
        skip(index);
        limit(size);
        send(httpCall);
    }
    public void QueryListX(int index, int size, String wheres, String orderField,HttpCallBack httpCall){
        skip(index);
        limit(size);
        addOrderFieldParams(orderField);
        send(httpCall);
    }
    public void QueryListX(int index, int size, String wheres,HttpCallBack httpCall){
        skip(index);
        limit(size);
        send(httpCall);
    }
    public void QueryListX(int index, int size,HttpCallBack httpCall){
        skip(index);
        limit(size);
        send(httpCall);
    }
    public void QuerySinglebywheres(HttpCallBack httpCall){
        send(httpCall);
    }
    public void QuerySinglebywheresX(HttpCallBack httpCall){
        send(httpCall);
    }
    public void QuerySinglebyid(Object id,HttpCallBack httpCall){
        addParams(staticConfig.id, id);
        send(httpCall);
    }
    public void updateColumnsById(Object id,HttpCallBack httpCall){
        addParams(staticConfig.id, id);
        send(httpCall);
    }
    public void QueryCount(HttpCallBack httpCall){
        send(httpCall);
    }
    public void updateColumnsByWheres(HttpCallBack httpCall){
        send(httpCall);
    }
}
