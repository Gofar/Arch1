package com.gofar.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gofar.library.R;
import com.gofar.library.load.ILoadingListener;
import com.gofar.library.utils.ToastUtils;
import com.gofar.library.widget.LoadingDialog;
import com.gofar.titlebar.TitleBar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author lcf
 * @date 18/12/2018 下午 3:49
 * @since 1.0
 */
public abstract class BaseActivity extends SupportActivity implements ILoadingListener {
    protected Context mContext;
    protected CompositeDisposable mCompositeDisposable;
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityStashManager.onCreate(this);
        initialize();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initBundle(bundle);
        }
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
        }
        View titleView = findViewById(R.id.title_bar);
        if (titleView != null) {
            if (titleView instanceof TitleBar) {
                initTitleBar((TitleBar) titleView);
            } else if (titleView instanceof Toolbar) {
                initToolbar((Toolbar) titleView);
            }
        }
        initView();
        initData();
    }

    /**
     * 设置布局前的一些初始化操作
     */
    protected void initialize() {

    }

    /**
     * 获取Bundle中携带的参数
     *
     * @param bundle bundle参数
     */
    protected void initBundle(Bundle bundle) {

    }

    /**
     * 设置布局文件
     *
     * @return LayoutRes
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化Toolbar
     *
     * @param toolbar Toolbar
     */
    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化TitleBar
     *
     * @param titleBar TitleBar
     */
    protected void initTitleBar(TitleBar titleBar) {
        setSupportActionBar(titleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        ActivityStashManager.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStashManager.onDestroy(this);
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
