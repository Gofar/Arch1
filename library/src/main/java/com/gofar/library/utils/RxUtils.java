package com.gofar.library.utils;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.github.qingmei2.core.GlobalErrorTransformer;
import com.github.qingmei2.func.Suppiler;
import com.github.qingmei2.retry.RetryConfig;
import com.gofar.library.entity.BaseEntity;
import com.gofar.library.network.RxDialog;
import com.gofar.library.network.exceptions.ConnectFailedAlertDialogException;
import com.gofar.library.network.exceptions.ServerException;
import com.gofar.library.network.exceptions.TokenExpiredException;

import org.json.JSONException;

import java.net.ConnectException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 13/12/2018 下午 4:55
 * @since 1.0
 */
public class RxUtils {
    /**
     * 请求成功
     */
    private static final int STATUS_SUCCESS = 200;
    /**
     * token失效
     */
    private static final int STATUS_UNAUTHORIZED = 401;

    public static <T> ObservableTransformer<T, T> rxScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T extends BaseEntity> ObservableTransformer<T, T> handleDefault() {
        return upstream -> upstream.flatMap((Function<T, ObservableSource<T>>) t -> {
            int code = t.getCode();
            if (code == STATUS_SUCCESS) {
                return Observable.just(t);
            } else if (code == STATUS_UNAUTHORIZED) {
                return Observable.error(new TokenExpiredException());
            } else {
                return Observable.error(new ServerException(t.getMsg(), t.getCode()));
            }
        });
    }

    public static <T, R extends BaseEntity<T>> ObservableTransformer<R, T> handleResult() {
        return upstream -> upstream.flatMap((Function<R, ObservableSource<T>>) r -> {
            int code = r.getCode();
            if (code == STATUS_SUCCESS) {
                return Observable.just(r.getData());
            } else if (code == STATUS_UNAUTHORIZED) {
                return Observable.error(new TokenExpiredException());
            } else {
                return Observable.error(new ServerException(r.getMsg(), r.getCode()));
            }
        });
    }

    public static <T extends BaseEntity> GlobalErrorTransformer<T> handleGlobalError(FragmentActivity activity) {
        return new GlobalErrorTransformer<T>(new Function<T, Observable<T>>() {
            @Override
            public Observable<T> apply(T t) throws Exception {
                switch (t.getCode()) {
                    case STATUS_UNAUTHORIZED:
                        return Observable.error(new TokenExpiredException());
                    default:
                        break;
                }
                return Observable.just(t);
            }
        }, new Function<Throwable, Observable<T>>() {
            @Override
            public Observable<T> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException) {
                    return Observable.error(new ConnectFailedAlertDialogException());
                }
                return Observable.error(throwable);
            }
        }, new Function<Throwable, RetryConfig>() {
            @Override
            public RetryConfig apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectFailedAlertDialogException) {
                    return new RetryConfig(new Suppiler<Single<Boolean>>() {
                        @Override
                        public Single<Boolean> call() {
                            return RxDialog.showErrorDialog(activity, "ConnectException")
                                    .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                                        @Override
                                        public SingleSource<? extends Boolean> apply(Boolean retry) throws Exception {
                                            return Single.just(retry);
                                        }
                                    });
                        }
                    });
                }
                if (throwable instanceof TokenExpiredException) {
                    return new RetryConfig(1, 3000, new Suppiler<Single<Boolean>>() {
                        @Override
                        public Single<Boolean> call() {
                            Toast.makeText(activity, "Token失效，跳转到Login重新登录！", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                    });
                }
                return null;
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof JSONException) {
                    Toast.makeText(activity, "全局异常捕获-Json解析异常！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
