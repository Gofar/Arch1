package com.gofar.library.load;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.library.entity.BaseEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 18/12/2018 下午 6:18
 * @since 1.0
 */
public interface IListLoadingListener<T> {
    /**
     * 是否需要下拉刷新
     *
     * @return True if need.
     */
    boolean needRefresh();

    /**
     * 是否需要加载更多
     *
     * @return True if need.
     */
    boolean needLoadMore();

    /**
     * RecyclerView LayoutManager
     *
     * @return LayoutManager
     */
    RecyclerView.LayoutManager getLayoutManager();


    /**
     * 创建适配器
     *
     * @return adapter extends BaseQuickAdapter.
     */
    BaseQuickAdapter<T, ? extends BaseViewHolder> getAdapter();

    /**
     * 拿到网络请求的Observable
     *
     * @param page 页码
     * @return Observable
     */
    Observable<BaseEntity<List<T>>> getListObservable(int page);
}
