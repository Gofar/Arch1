package com.gofar.library.image;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

/**
 * @author lcf
 * @date 2018/4/19 11:19
 * @since 1.0
 */
public class GlideImageLoader {
    public static void load(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).into(imageView);
    }

    public static void load(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity).load(url).into(imageView);
    }

    public static void load(FragmentActivity activity, String url, ImageView imageView) {
        GlideApp.with(activity).load(url).into(imageView);
    }

    public static void load(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment).load(url).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int placeHolder) {
        GlideApp.with(context).load(url).placeholder(placeHolder).into(imageView);
    }

    public static void load(Activity activity, String url, ImageView imageView, int placeHolder) {
        GlideApp.with(activity).load(url).placeholder(placeHolder).into(imageView);
    }

    public static void load(FragmentActivity activity, String url, ImageView imageView, int placeHolder) {
        GlideApp.with(activity).load(url).placeholder(placeHolder).into(imageView);
    }

    public static void load(Fragment fragment, String url, ImageView imageView, int placeHolder) {
        GlideApp.with(fragment).load(url).placeholder(placeHolder).into(imageView);
    }
}
