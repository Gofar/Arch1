package com.gofar.arch1.base;

/**
 * Loader interface
 *
 * @author lcf
 * @date 13/12/2018 下午 2:53
 * @since 1.0
 */
public interface ILoader {
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
