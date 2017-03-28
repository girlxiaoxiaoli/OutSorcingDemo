package com.zz.guojin.outsorcingdemo;

import android.app.Application;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

/**
 * Created by Administrator on 2017/3/21.
 */

public class OutApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNoHttp();

    }

    private void initNoHttp() {
        NoHttp.initialize(this, new NoHttp.Config()
                        // 使用HttpURLConnection
//        .setNetworkExecutor(new URLConnectionNetworkExecutor())
                        // 或者使用OkHttp
                        .setNetworkExecutor(new OkHttpNetworkExecutor())
                        .setConnectTimeout(60 * 1000)
                        .setReadTimeout(60 * 1000)

        );
        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpSample");// 设置NoHttp打印Log的tag。
    }
}
