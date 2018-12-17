package com.gofar.arch1.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gofar.arch1.R;

/**
 * @author lcf
 * @date 17/12/2018 下午 3:41
 * @since 1.0
 */
public class LoadingDialog extends DialogFragment {
    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.default_dialog_style);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null) {
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public void show(FragmentManager manager) {
        show(manager, "LoadingDialog");
    }
}
