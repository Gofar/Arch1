package com.gofar.arch1;

import com.gofar.arch1.base.BaseActivity1;
import com.gofar.titlebar.TitleBar;

/**
 * @author lcf
 * @date 18/12/2018 上午 11:54
 * @since 1.0
 */
public class SimpleActivity extends BaseActivity1 {

    @Override
    protected int getLayoutId() {
        return R.layout.base_compat;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        super.initTitleBar(titleBar);
        titleBar.setCenterTitle("Simple");
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
}
