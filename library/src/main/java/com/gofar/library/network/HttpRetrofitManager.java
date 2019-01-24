package com.gofar.library.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gofar.library.BuildConfig;
import com.gofar.library.network.https.TrustAllCerts;
import com.gofar.library.network.https.TrustAllHostnameVerifier;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit封装
 *
 * @author lcf
 * @date 15/1/2019 下午 1:57
 * @since 1.0
 */
public class HttpRetrofitManager {
    /**
     * default connect timeout
     */
    private static final long CONNECT_TIMEOUT = 30L;
    /**
     * default read timeout
     */
    private static final long READ_TIMEOUT = 30L;
    /**
     * default write timeout
     */
    private static final long WRITE_TIMEOUT = 120L;

    /**
     * Retrofit base url
     */
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    private static class Holder {
        private static HttpRetrofitManager INSTANCE = new HttpRetrofitManager();
    }

    public static HttpRetrofitManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 初始化设置base url
     *
     * @param baseUrl Retrofit builder base url
     */
    public void init(@NonNull String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public OkHttpClient.Builder buildDefaultClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier());
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder;
    }

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory factory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            factory = sc.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return factory;
    }

    public OkHttpClient buildHttpClient() {
        return buildHttpClient(buildDefaultClientBuilder());
    }

    public OkHttpClient buildHttpClient(OkHttpClient.Builder builder) {
        return builder.build();
    }

    public Retrofit buildRetrofit() {
        return buildRetrofit(mBaseUrl, getOkHttpClient());
    }

    public Retrofit buildRetrofit(String baseUrl) {
        return buildRetrofit(baseUrl, getOkHttpClient());
    }

    public Retrofit buildRetrofit(OkHttpClient client) {
        return buildRetrofit(mBaseUrl, client);
    }

    public Retrofit buildRetrofit(String baseUrl, OkHttpClient client) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("The base url is null!");
        }
        if (client == null) {
            throw new NullPointerException("The OkHttpclient is null!");
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = buildHttpClient();
        }
        return mOkHttpClient;
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = buildRetrofit();
        }
        return mRetrofit;
    }

    public <T> T build(Class<T> cls) {
        return build(getRetrofit(), cls);
    }

    public <T> T build(Retrofit retrofit, Class<T> cls) {
        if (retrofit == null) {
            throw new NullPointerException("The retrofit is null!");
        }
        return retrofit.create(cls);
    }
}
