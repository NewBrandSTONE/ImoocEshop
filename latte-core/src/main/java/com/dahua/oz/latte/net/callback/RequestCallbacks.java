package com.dahua.oz.latte.net.callback;

import com.dahua.oz.latte.ui.LatteLoader;
import com.dahua.oz.latte.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/14
 * @desc ${CURSOR}
 */

public class RequestCallbacks implements Callback<String> {
    private final IReqeust IREQUEST;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final ISuces ISUCCESS;
    private final LoaderStyle LOADER_STYLE;

    private static final android.os.Handler HANDLER = new android.os.Handler();

    public RequestCallbacks(IReqeust reqeust, IError error,
                            IFailure failure, ISuces suces, LoaderStyle loaderStyle) {
        this.IREQUEST = reqeust;
        this.IERROR = error;
        this.IFAILURE = failure;
        this.ISUCCESS = suces;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                // 执行回调
                if (ISUCCESS != null) {
                    ISUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (IERROR != null) {
                IERROR.onError(response.code(), response.message());
            }
        }

        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (IFAILURE != null) {
            IFAILURE.onFailue();
        }

        if (IREQUEST != null) {
            IREQUEST.onRequestEnd();
        }

        stopLoading();
    }

    /**
     * 停止Loading
     */
    private void stopLoading() {
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            }, 1000);
        }
    }
}
