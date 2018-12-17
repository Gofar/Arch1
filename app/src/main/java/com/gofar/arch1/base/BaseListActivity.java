package com.gofar.arch1.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gofar.arch1.R;
import com.gofar.arch1.entity.BaseEntity;
import com.gofar.arch1.widget.RefreshRecyclerView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 13/12/2018 下午 6:04
 * @since 1.0
 */
public abstract class BaseListActivity<T> extends BaseActivity implements IListLoader<T> {
    protected RefreshRecyclerView mRefreshRecyclerView;
    protected BaseListLoader<T> mListLoader;

    @Override
    protected void initialize() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        initRecycler();
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
        return new LinearLayoutManager(this);
    }

    protected void initRecycler() {
        mRefreshRecyclerView = findViewById(R.id.refresh_recycler_view);
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
