package com.gofar.arch1.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        mListLoader = new BaseListLoader<T>(mCompositeDisposable, mRefreshRecyclerView, getAdapter(), needRefresh(), needLoadMore()) {
            @Override
            protected Observable<BaseEntity<List<T>>> getObservable(int page) {
                return getListObservable(page);
            }
        };
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void showLoading() {

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

}
