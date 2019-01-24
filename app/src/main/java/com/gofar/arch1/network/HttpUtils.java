package com.gofar.arch1.network;

import com.gofar.library.network.HttpRetrofitManager;

/**
 * @author lcf
 * @date 24/1/2019 下午 6:17
 * @since 1.0
 */
public class HttpUtils {
    public static void init(){
        HttpRetrofitManager.getInstance().init(ApiPath.BASE_URL);
    }
}
