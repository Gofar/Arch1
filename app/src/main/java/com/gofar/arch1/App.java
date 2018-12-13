package com.gofar.arch1;

import android.app.Application;

/**
 * @author lcf
 * @date 10/12/2018 下午 3:34
 * @since 1.0
 */
public class App extends Application {
    private static App sApp;

    public static App getApp() {
        return sApp;
    }
}
