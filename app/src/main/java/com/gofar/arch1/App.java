package com.gofar.arch1;

import android.app.Application;

import com.gofar.arch1.network.HttpUtils;

/**
 * @author lcf
 * @date 10/12/2018 下午 3:34
 * @since 1.0
 */
public class App extends Application {
    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpUtils.init();
    }

    public static App getApp() {
        return sApp;
    }
}
