package com.gofar.library.network;

import android.content.Context;

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
 * @author lcf
 * @date 18/12/2018 下午 4:45
 * @since 1.0
 */
public class RetrofitManager {
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

    private Retrofit mRetrofit;

    private static class Holder {
        private static RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Holder.INSTANCE;
    }

    private OkHttpClient buildDefaultClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .build();
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

    public Retrofit getRetrofit(Context context, String baseUrl) {
        mRetrofit= new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildDefaultClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }

    public <T> T build(Class<T> cls){
        if (mRetrofit==null){
            throw new IllegalArgumentException("Retrofit is null!");
        }
        return mRetrofit.create(cls);
    }

    public <T> T build(Retrofit retrofit, Class<T> cls) {
        if (retrofit == null) {
            throw new IllegalArgumentException("Retrofit is null!");
        }
        this.mRetrofit = retrofit;
        return retrofit.create(cls);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
