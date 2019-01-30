package com.gofar.library.core;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

/**
 * @author lcf
 * @date 30/1/2019 上午 11:33
 * @since 1.0
 */
public class AccountLiveData<T> extends LiveData<T> {

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    @Override
    public void observeForever(@NonNull Observer<T> observer) {
        super.observeForever(observer);
    }

    @Override
    public void removeObserver(@NonNull Observer<T> observer) {
        super.removeObserver(observer);
    }
}
