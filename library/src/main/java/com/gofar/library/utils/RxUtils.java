package com.gofar.library.utils;

import com.gofar.library.entity.BaseEntity;
import com.gofar.library.network.ServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 13/12/2018 下午 4:55
 * @since 1.0
 */
public class RxUtils {
    public static <T> ObservableTransformer<T, T> rxScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<BaseEntity<T>, T> handleResult() {
        return upstream -> upstream.flatMap((Function<BaseEntity<T>, ObservableSource<T>>) baseEntity -> {
            if (baseEntity.isSuccess()) {
                return Observable.just(baseEntity.getData());
            } else {
                return Observable.error(new ServerException(baseEntity.getMsg(), baseEntity.getCode()));
            }
        });
    }
}
