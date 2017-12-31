package com.dahua.oz.latte.net.download;

import android.os.AsyncTask;

import com.dahua.oz.latte.net.RestCreator;
import com.dahua.oz.latte.net.callback.IError;
import com.dahua.oz.latte.net.callback.IFailure;
import com.dahua.oz.latte.net.callback.IReqeust;
import com.dahua.oz.latte.net.callback.ISuces;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/12/31
 * @desc ${CURSOR}
 */

public class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IReqeust REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final ISuces SUCCESS;

    public DownloadHandler(String url, IReqeust reqeust
            , String dowloadDir, String extension
            , String name, IError error
            , IFailure failure, ISuces success) {
        this.URL = url;
        this.REQUEST = reqeust;
        this.DOWNLOAD_DIR = dowloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.ERROR = error;
        this.FAILURE = failure;
        this.SUCCESS = success;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR
                                    , EXTENSION, responseBody, NAME);

                            // 这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailue();
                        }
                    }
                });
    }
}
