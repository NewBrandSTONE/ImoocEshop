package com.dahua.oz.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.dahua.oz.latte.app.Latte;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/12/16
 * @desc 用来测量的工具方法
 */

public class DimenUtil {
    /**
     * 获取屏幕的宽
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


}
