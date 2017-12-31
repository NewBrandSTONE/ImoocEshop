package com.dahua.oz.latte.net;

import android.content.Context;

import com.dahua.oz.latte.net.callback.IError;
import com.dahua.oz.latte.net.callback.IFailure;
import com.dahua.oz.latte.net.callback.IReqeust;
import com.dahua.oz.latte.net.callback.ISuces;
import com.dahua.oz.latte.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/11
 * @desc ${CURSOR}
 */

public class RestClientBuilder {

    private String mUrl;
    private static WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IReqeust mIRequest;
    private IError mIError;
    private IFailure mIFailure;
    private ISuces mISuccess;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;

    private String mDownloadDir;
    private String mExtension;
    private String mName;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuces iSuces) {
        this.mISuccess = iSuces;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder onRequest(IReqeust iReqeust) {
        this.mIRequest = iReqeust;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }


    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,
                mIRequest, mIError, mIFailure,
                mISuccess, mBody, mLoaderStyle,
                mContext, mFile, mDownloadDir,
                mExtension, mName);
    }

}
