package com.gofar.arch1.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.gofar.arch1.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import ezy.ui.layout.LoadingLayout;

/**
 * @author lcf
 * @date 13/12/2018 下午 3:32
 * @since 1.0
 */
public class RefreshRecyclerView extends FrameLayout {
    private RefreshLayout mRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private RecyclerView mRecyclerView;

    public RefreshRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_recycler_view,this);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mLoadingLayout = findViewById(R.id.loadingLayout);
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

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

}
