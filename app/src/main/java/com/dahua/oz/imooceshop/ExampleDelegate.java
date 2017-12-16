package com.dahua.oz.imooceshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dahua.oz.latte.delegates.LatteDelegate;
import com.dahua.oz.latte.net.RestClient;
import com.dahua.oz.latte.net.callback.IError;
import com.dahua.oz.latte.net.callback.IFailure;
import com.dahua.oz.latte.net.callback.ISuces;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/6
 * @desc ${CURSOR}
 */

public class ExampleDelegate extends LatteDelegate {

    private String TAG = this.getClass().getName();

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 对每一个控件做的操作
        testRestClient();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    private void testRestClient() {
        RestClient.builder()
                .loader(getContext())
                .url("http://news.baidu.com")
                .success(new ISuces() {
                    @Override
                    public void onSuccess(String response) {
                       // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailue(String errorMsg) {
                        Log.d(TAG, "onFailue: " + errorMsg);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.d(TAG, "onError: " + code + "|" + msg);
                    }
                })
                .build()
                .get();
    }
}
