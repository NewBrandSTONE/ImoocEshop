package com.dahua.oz.latte.app;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/5
 * @desc 唯一的单例模式，并且只能初始化一次
 */

public enum ConfigType {
    // 网络请求的域名
    API_HOST,
    APPLICATION_CONTEXT,
    // 初始化是否完成
    CONFIG_READY,
    ICON

}
