package com.example.administrator.getpet.utils;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpPostUtil {
    URL url;
    String data;
    HttpCallBack callBack;
    //处理 器
    public Handler mHandler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    callBack.Success(data);
                    break;
                default:
                    callBack.Fail(data);
                    break;
            }
        }
    };
    HttpURLConnection conn;
    String boundary = "--------httppost123";
    Map<String, String> textParams = new HashMap<String, String>();
    Map<String, File> fileparams = new HashMap<String, File>();
    DataOutputStream ds;
    public HttpPostUtil(String className,String method){
        try{
            this.url=new URL(staticConfig.baseUrl+className+"/"+method);
        }catch(Exception e){
        }
    }
    public HttpPostUtil(String postFix){
        try{
            this.url=new URL(staticConfig.baseUrl+postFix);
        }catch(Exception e){
        }
    }
    public HttpPostUtil(URL url){
        this.url=url;
    }
    //增加一个普通字符串数据到form表单数据中
    public void addTextParameter(String name, Object value) {
        textParams.put(name,String.valueOf(value));
    }
    //增加一个文件到form表单数据中
    public void addFileParameter(String name,String path) {
        File file =new File(path);
        fileparams.put(name, file);
    }
    // 清空所有已添加的form表单数据
    public void clearAllParameters() {
        textParams.clear();
        fileparams.clear();
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
                    writeFileParams();
                    writeStringParams();
                    paramsEnd();
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
                        final String result = new String(retdata);
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
//        //解码
//        java.net.URLDecoder   urlDecoder   =   new   java.net.URLDecoder();
//        String   data   =     urlDecoder.decode(result, "UTF-8");
                }catch (Exception e){
                    data=e.getMessage();
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();

    }

    //文件上传的connection的一些必须设置
    private void initConnection() throws Exception {
        conn = (HttpURLConnection) this.url.openConnection();
        conn.setDoOutput(true);conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(6000); //连接超时为6秒
        conn.setReadTimeout(6000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Language", "zh-cn");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
    }
    //普通字符串数据
    private void writeStringParams() throws Exception {
        Set<String> keySet = textParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            String name = it.next();
            String value = textParams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name
                    + "\"\r\n");
            ds.writeBytes("\r\n");
            ds.writeBytes(encode(value).getBytes() + "\r\n");
        }
    }

    /**
     * http格式 网络传送图片
     * @throws Exception 出错处理
     */
    private void writeFileParams() throws Exception {
        //文件的地址set集合
        Set<String> keySet = fileparams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            String name = it.next();
            File value = fileparams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name
                    + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
            ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }
    }
    //获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
    private String getContentType(File f) throws Exception {
      return "application/octet-stream";  // 此行不再细分是否为图片，全部作为application/octet-stream 类型
    }
    //把文件转换成字节数组
    private byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }
    //添加结尾数据
    private void paramsEnd() throws Exception {
        ds.writeBytes("--" + boundary + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }
    // 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
    private String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }
    //获取url地址
    public static String  getImagUrl(String id){
            return staticConfig.baseUrl+"File/Image?id="+id;
    }
}