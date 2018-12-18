package com.gofar.library.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.gofar.library.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import ezy.ui.layout.LoadingLayout;

/**
 * @author lcf
 * @date 13/12/2018 下午 3:32
 * @since 1.0
 */
public class RefreshRecyclerView extends FrameLayout {
    private LoadingLayout mLoadingLayout;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    public RefreshRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_recycler_view, this);
        mLoadingLayout = findViewById(R.id.loadingLayout);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLoadingLayout.showContent();
    }

    public RefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public LoadingLayout getLoadingLayout() {
        return mLoadingLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void autoRefresh() {
        mRefreshLayout.autoRefresh();
    }

    public void setEnableRefresh(boolean enabled) {
        mRefreshLayout.setEnableRefresh(enabled);
    }

    public void setEnableLoadMore(boolean enabled) {
        mRefreshLayout.setEnableLoadMore(enabled);
    }

    public void setEnableAutoLoadMore(boolean enabled) {
        mRefreshLayout.setEnableAutoLoadMore(enabled);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshLayout.setOnRefreshListener(listener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mRefreshLayout.setOnLoadMoreListener(listener);
    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        mRefreshLayout.setOnRefreshLoadMoreListener(listener);
    }

    public void setOnRetryListener(OnClickListener listener) {
        mLoadingLayout.setRetryListener(listener);
    }

    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    public void finishLoadMore(boolean success) {
        mRefreshLayout.finishLoadMore(success);
    }

    public void finishLoadMoreEnd() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void showLoading() {
        mLoadingLayout.showLoading();
    }

    public void showContent() {
        mLoadingLayout.showContent();
    }

    public void showEmpty() {
        mLoadingLayout.showEmpty();
    }

    public void showEmptyText(String msg) {
        mLoadingLayout.setEmptyText(msg).showEmpty();
    }

    public void showError() {
        mLoadingLayout.showError();
    }

    public void showError(String errMsg) {
        mLoadingLayout.setErrorText(errMsg).showError();
    }
}