package com.dahua.oz.imooceshop;

import com.dahua.oz.latte.activities.ProxyActivity;
import com.dahua.oz.latte.delegates.LatteDelegate;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/5
 */
public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
