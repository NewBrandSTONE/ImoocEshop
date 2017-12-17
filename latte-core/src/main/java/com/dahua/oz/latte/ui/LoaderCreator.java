package com.dahua.oz.latte.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * 加载动画
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/29
 * @desc ${CURSOR}
 */

public class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context) {
        final AVLoadingIndicatorView loadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            if (indicator != null) {
                LOADING_MAP.put(type, indicator);
            }
        }
        loadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return loadingIndicatorView;
    }

    /**
     * 传入Type返回对应的Loader
     *
     * @param name 传入LoaderType
     * @return 对应Type的Loader
     */
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            // 这一个库是通过反射类名来进行获取实例的
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
