package com.yiren.live;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.location.LocationClient;
import com.tencent.bugly.crashreport.CrashReport;
import com.yiren.live.lean.CustomUserProvider;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by Administrator on 2016/9/1.
 * Author: XuDeLong
 */
public class MyApplication extends Application {

    public LocationService locationService;
    public LocationClient locationClient;
    private final String APP_ID = "DTCTGbNvyvp1FjheNpY3dxNI-gzGzoHsz";
    private final String APP_KEY = "HFCnJIoJA19gkWSSjCsXcfiN";
    String balance = "0";
    private static Context mContext;

    public static Context getGlobalContext() {
        return mContext;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        MultiDex.install(this);
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        //locationService = new LocationService(getApplicationContext());
        locationClient = new LocationClient(this);
        CrashReport.initCrashReport(getApplicationContext(), "ba9ce894d4", true);
    }
}
