package com.gofar.arch1.base;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.arch1.App;
import com.gofar.arch1.R;
import com.gofar.arch1.entity.BaseEntity;
import com.gofar.arch1.utils.RxUtils;
import com.gofar.arch1.widget.RefreshRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.ResourceObserver;

/**
 * @author lcf
 * @date 13/12/2018 下午 4:09
 * @since 1.0
 */
public abstract class BaseListLoader<T> {

    public static final int START_PAGE = 0;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;

    private CompositeDisposable mCompositeDisposable;
    private RefreshRecyclerView mRefreshRecyclerView;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mQuickAdapter;
    private int mCurrentState = STATE_NORMAL;
    private int mCurrentPage = START_PAGE;
    private boolean mIsRefresh = false;

    public BaseListLoader(@NonNull CompositeDisposable compositeDisposable, @NonNull RefreshRecyclerView refreshRecyclerView, @NonNull BaseQuickAdapter<T, ? extends BaseViewHolder> quickAdapter) {
        this(compositeDisposable, refreshRecyclerView, quickAdapter, true, true);
    }

    public BaseListLoader(@NonNull CompositeDisposable compositeDisposable, @NonNull RefreshRecyclerView refreshRecyclerView, @NonNull BaseQuickAdapter<T, ? extends BaseViewHolder> quickAdapter,
                          boolean enableRefresh, boolean enableLoadMore) {
        mCompositeDisposable = compositeDisposable;
        mRefreshRecyclerView = refreshRecyclerView;
        mQuickAdapter = quickAdapter;
        init(enableRefresh, enableLoadMore);
    }

    private void init(boolean enableRefresh, boolean enableLoadMore) {
        mRefreshRecyclerView.setEnableRefresh(enableRefresh);
        mRefreshRecyclerView.setEnableLoadMore(enableLoadMore);
        mRefreshRecyclerView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                autoRefresh();
            }
        });
    }

    public void autoRefresh() {
        mIsRefresh = true;
        mCurrentPage = START_PAGE;
        load();
    }

    public void loadMore() {
        mIsRefresh = false;
        mCurrentPage++;
        load();
    }

    /**
     * 拿到网络请求的Observable
     *
     * @param page 页码
     * @return Observable
     */
    protected abstract Observable<BaseEntity<List<T>>> getObservable(int page);

    public void load() {
        mCompositeDisposable.add(getObservable(mCurrentPage)
                .compose(RxUtils.rxScheduler())
                .compose(RxUtils.handleResult())
                .subscribeWith(new ResourceObserver<List<T>>() {
                    @Override
                    public void onNext(List<T> ts) {
                        onSuccess(ts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void onSuccess(List<T> data) {
        if (mIsRefresh) {
            mRefreshRecyclerView.finishRefresh(true);
            mQuickAdapter.setNewData(data);
            if (mQuickAdapter.getData().isEmpty()) {
                mRefreshRecyclerView.showEmpty();
            }
        } else {
            mRefreshRecyclerView.showContent();
            if (data == null || data.isEmpty()) {
                if (mQuickAdapter.getData().isEmpty()) {
                    mRefreshRecyclerView.showEmpty();
                } else {
                    mRefreshRecyclerView.finishLoadMoreEnd();
                }
            } else {
                mQuickAdapter.addData(data);
                mRefreshRecyclerView.finishLoadMore(true);
            }
        }
    }

    private void onFailed(Throwable e) {
        e.printStackTrace();
        String errMsg;
        if (e instanceof ServerException) {
            errMsg = e.getMessage();
        } else {
            errMsg = App.getApp().getString(R.string.http_error);
        }
        if (mIsRefresh) {
            mRefreshRecyclerView.finishRefresh(false);
            if (mQuickAdapter.getData().isEmpty()) {
                mRefreshRecyclerView.showError(errMsg);
            }
        } else {
            mRefreshRecyclerView.showContent();
            if (mQuickAdapter.getData().isEmpty()) {
                mRefreshRecyclerView.showError(errMsg);
            } else {
                mRefreshRecyclerView.finishLoadMore(false);
            }
        }
    }
}
