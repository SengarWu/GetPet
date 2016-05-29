package com.example.administrator.getpet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.getpet.base.CustomApplication;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Administrator on 2016/5/28.
 */
public class PictureUtils {
    private Bitmap bitmap;

    private final int LOADSUCCESS = 1001;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case LOADSUCCESS:
                    break;
            }
        }
    };

    public Bitmap loadImage(final String s_url)
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    // 定义一个URL对象
                    URL url = new URL(s_url);
                    // 打开该URL对应的资源的输入流
                    InputStream is = url.openStream();
                    // 从InputStream中解析出图片
                    bitmap = BitmapFactory.decodeStream(is);
                    // 发送消息、通知UI组件显示该图片
                    handler.sendEmptyMessage(0x123);
                    is.close();
                    // 再次打开URL对应的资源的输入流
                    is = url.openStream();
                    // 打开手机文件对应的输出流
                    OutputStream os = CustomApplication.getmInstance().openFileOutput("crazyit.png", CustomApplication.getmInstance().MODE_PRIVATE);
                    byte[] buff = new byte[1024];
                    int hasRead = 0;
                    // 将URL对应的资源下载到本地
                    while((hasRead = is.read(buff)) > 0)
                    {
                        os.write(buff, 0 , hasRead);
                    }
                    is.close();
                    os.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
        return bitmap;
    }
}
