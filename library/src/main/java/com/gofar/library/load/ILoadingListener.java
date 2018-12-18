package com.gofar.library.load;

/**
 * @author lcf
 * @date 18/12/2018 下午 4:23
 * @since 1.0
 */
public interface ILoadingListener {
    /**
     * Show normal content.
     */
    void showNormal();

    /**
     * Show no data.
     */
    void showEmpty();

    /**
     * Show error view.
     * @param errMsg error message
     */
    void showError(String errMsg);

    /**
     * Show loading view.
     */
    void showLoading();
}
