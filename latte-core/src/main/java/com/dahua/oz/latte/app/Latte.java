package com.dahua.oz.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * 项目配置初始化
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/5
 * @desc ${CURSOR}
 */

public final class Latte {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

}
