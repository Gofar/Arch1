package com.gofar.arch1.base;

import com.gofar.arch1.App;
import com.gofar.arch1.R;
import com.gofar.library.network.exceptions.ServerException;
import com.gofar.library.network.exceptions.TokenExpiredException;

import io.reactivex.observers.ResourceObserver;

/**
 * @author lcf
 * @date 13/12/2018 下午 2:39
 * @since 1.0
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {
    private ILoader mILoader;

    public BaseObserver(ILoader iLoader) {
        mILoader = iLoader;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mILoader == null) {
            return;
        }
        if (e instanceof ServerException) {
            mILoader.showError(e.getMessage());
        } else if (e instanceof TokenExpiredException) {
            mILoader.showError("登录失效");
        } else {
            mILoader.showError(App.getApp().getString(R.string.http_error));
        }
    }
}
