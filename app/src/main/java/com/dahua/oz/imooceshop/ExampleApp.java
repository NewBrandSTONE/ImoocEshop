package com.dahua.oz.imooceshop;

import android.app.Application;

import com.dahua.oz.latte.app.Latte;
import com.dahua.oz.latte.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/5
 * @desc ${CURSOR}
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://127.0.0.1")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
    }
}
