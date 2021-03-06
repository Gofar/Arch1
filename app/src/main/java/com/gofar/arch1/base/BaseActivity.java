package com.gofar.arch1.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.gofar.library.base.ActivityStashManager;
import com.gofar.library.entity.BaseEntity;
import com.gofar.library.utils.RxUtils;
import com.gofar.library.utils.ToastUtils;
import com.gofar.library.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author lcf
 * @date 10/12/2018 下午 2:54
 * @since 1.0
 */
public abstract class BaseActivity extends SupportActivity implements ILoader {
    protected Context mContext;
    protected CompositeDisposable mCompositeDisposable;
    private Unbinder mUnBinder;
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityStashManager.onCreate(this);
        initialize();
        setContentView(getLayoutId());
        initView();
        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0) {
            super.setContentView(layoutResID);
            mUnBinder = ButterKnife.bind(this);
        }
    }

    /**
     * 设置布局前的一些初始化操作
     */
    protected abstract void initialize();

    /**
     * 设置布局文件
     *
     * @return LayoutRes
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * Toast文本
     *
     * @param msg 文本
     */
    protected void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }

    /**
     * Show loading dialog fragment.
     */
    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.newInstance();
        }
        mLoadingDialog.show(getSupportFragmentManager());
    }

    /**
     * Hide loading dialog fragment.
     */
    protected void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected <T> void addSubscribe(Observable<BaseEntity<T>> observable, Consumer<T> result) {
        addSubscribe(observable.compose(RxUtils.handleResult())
                .compose(RxUtils.rxScheduler())
                .subscribe(result, Throwable::printStackTrace));
    }

    protected <T> void addSubscribe(Observable<BaseEntity<T>> observable, Consumer<T> result, Consumer<Throwable> throwable) {
        addSubscribe(observable.compose(RxUtils.handleResult())
                .compose(RxUtils.rxScheduler())
                .subscribe(result, throwable));
    }

    protected <T> void addSubscribe(Observable<BaseEntity<T>> observable, BaseObserver<T> baseObserver) {
        showLoading();
        addSubscribe(observable.compose(RxUtils.handleResult())
                .compose(RxUtils.rxScheduler())
                .subscribeWith(baseObserver));
    }

    protected <T extends BaseEntity> void addSubscribeDefault(Observable<T> observable, BaseObserver<T> baseObserver) {
        showLoading();
        addSubscribe(observable.compose(RxUtils.handleDefault())
                .compose(RxUtils.rxScheduler())
                .subscribeWith(baseObserver));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityStashManager.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStashManager.onDestroy(this);
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
