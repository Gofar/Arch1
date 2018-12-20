package com.gofar.library.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gofar.library.R;
import com.gofar.library.entity.BaseEntity;
import com.gofar.library.load.BaseListLoader;
import com.gofar.library.load.IListLoadingListener;
import com.gofar.library.widget.RefreshRecyclerView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 19/12/2018 上午 10:59
 * @since 1.0
 */
public abstract class BaseListFragment<T> extends BaseFragment implements IListLoadingListener<T> {
    protected RefreshRecyclerView mRefreshRecyclerView;
    protected BaseListLoader<T> mListLoader;

    @Override
    protected void initView(View rootView) {
        initRecyclerView(rootView);
        initListLoader();
    }

    @Override
    public void showNormal() {
        mRefreshRecyclerView.showContent();
    }

    @Override
    public void showEmpty() {
        mRefreshRecyclerView.showEmpty();
    }

    @Override
    public void showError(String errMsg) {
        mRefreshRecyclerView.showError(errMsg);
    }

    @Override
    public void showLoading() {
        mRefreshRecyclerView.showLoading();
    }

    @Override
    public boolean needRefresh() {
        return true;
    }

    @Override
    public boolean needLoadMore() {
        return true;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    protected void initRecyclerView(View rootView) {
        mRefreshRecyclerView = rootView.findViewById(R.id.refresh_recycler_view);
        if (mRefreshRecyclerView == null) {
            throw new IllegalStateException("this activity must contain a RefreshRecyclerView named 'refresh_recycler_view'");
        }
        mRefreshRecyclerView.setLayoutManager(getLayoutManager());
        mRefreshRecyclerView.setAdapter(getAdapter());
    }

    private void initListLoader() {
        mListLoader = new BaseListLoader<T>(mCompositeDisposable, mRefreshRecyclerView, getAdapter(), needRefresh(), needLoadMore()) {
            @Override
            protected Observable<BaseEntity<List<T>>> getObservable(int page) {
                return getListObservable(page);
            }
        };
    }
}
