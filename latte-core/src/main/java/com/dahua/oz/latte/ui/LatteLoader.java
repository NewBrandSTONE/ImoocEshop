package com.dahua.oz.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dahua.oz.latte.R;
import com.dahua.oz.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * 在GitHub的例子上，Loader是直接在View上显示的，我们这里
 * 应该是增加一个Dialog，将Loader放在Dialog上面显示
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/12/16
 * @desc 用来控制Loader是否显示的类
 */

public class LatteLoader {

    /**
     * 屏幕的缩放比
     */
    private static final int LOADER_SIZE_SCALE = 8;

    /**
     * 宽度屏幕偏移量
     */
    public static final int LOADER_OFFSET_SCALE = 10;

    /**
     * 存储所有Loader的集合，方便统一管理
     */
    public static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    public static final String DEFAULT_LOADER = LoaderStyle.BallZigZagDeflectIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);

        dialog.setContentView(avLoadingIndicatorView);

        int screenHeight = DimenUtil.getScreenHeight();
        int screenWidth = DimenUtil.getScreenWidth();

        // 获取Window对象
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            // 增加缩放比
            lp.width = screenWidth / LOADER_SIZE_SCALE;
            lp.height = screenHeight / LOADER_SIZE_SCALE;
            // 增加高度的屏幕偏移量
            lp.height = lp.height + screenHeight / LOADER_OFFSET_SCALE;
            // 居中显示
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
        dialog.show();
    }

    /**
     * 展示Loader
     *
     * @param context 这个Context最好是Activity或者Fragment.getActivity的Loader
     */
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                // 这里还能调用一些onCancel的回调
                dialog.cancel();
                // dismiss就不会执行回调
            }
        }
    }

}
