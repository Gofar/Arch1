package com.gofar.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gofar.library.R;
import com.gofar.library.load.ILoadingListener;
import com.gofar.library.utils.ToastUtils;
import com.gofar.library.widget.LoadingDialog;
import com.gofar.titlebar.TitleBar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author lcf
 * @date 18/12/2018 下午 4:25
 * @since 1.0
 */
public abstract class BaseFragment extends SupportFragment implements ILoadingListener {
    protected Context mContext;
    protected CompositeDisposable mCompositeDisposable;
    protected LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        initialize();
        Bundle bundle = getArguments();
        if (bundle != null) {
            initBundle(bundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View titleView = view.findViewById(R.id.title_bar);
        if (titleView != null) {
            if (titleView instanceof TitleBar) {
                initTitleBar((TitleBar) titleView);
            } else if (titleView instanceof Toolbar) {
                initToolbar((Toolbar) titleView);
            }
        }
        initView(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
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
     *
     * @param rootView Fragment root view
     */
    protected abstract void initView(View rootView);

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

    }

    /**
     * 初始化TitleBar
     *
     * @param titleBar TitleBar
     */
    protected void initTitleBar(TitleBar titleBar) {

    }

    /**
     * Toast文本
     *
     * @param msg 文本
     */
    protected void showToast(String msg) {
        if (getActivity() == null || !isAdded() || getActivity().isFinishing()) {
            return;
        }
        ToastUtils.showShort(getContext(), msg);
    }

    /**
     * Show loading dialog fragment.
     */
    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.newInstance();
        }
        mLoadingDialog.show(getFragmentManager());
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
