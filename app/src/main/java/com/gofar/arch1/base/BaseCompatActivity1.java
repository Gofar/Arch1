package com.gofar.arch1.base;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.gofar.arch1.R;
import com.gofar.library.base.BaseActivity;

/**
 * @author lcf
 * @date 18/12/2018 下午 2:13
 * @since 1.0
 */
public abstract class BaseCompatActivity1 extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.base_compat;
    }

    @Override
    protected void initView() {
        FrameLayout flContent = findViewById(R.id.fl_content);
        int contentLayoutId = getContentLayoutId();
        if (contentLayoutId > 0) {
            LayoutInflater.from(this).inflate(contentLayoutId, flContent);
        }

    }

    /**
     * 内容布局id
     *
     * @return layoutRes
     */
    protected abstract int getContentLayoutId();
}
