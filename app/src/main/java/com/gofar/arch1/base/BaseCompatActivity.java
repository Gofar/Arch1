package com.gofar.arch1.base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.gofar.arch1.R;
import com.gofar.titlebar.TitleBar;

import butterknife.BindView;

/**
 * @author lcf
 * @date 17/12/2018 下午 4:51
 * @since 1.0
 */
public abstract class BaseCompatActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    @Override
    protected int getLayoutId() {
        return R.layout.base_compat;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initToolbar(mTitleBar);
        int customLayoutId = getCustomLayoutId();
        if (customLayoutId != 0) {
            LayoutInflater.from(this).inflate(customLayoutId, mFlContent);
        }
    }

    protected void initToolbar(TitleBar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    /**
     * 内容View layout
     *
     * @return LayoutRes
     */
    protected abstract @LayoutRes int getCustomLayoutId();
}
