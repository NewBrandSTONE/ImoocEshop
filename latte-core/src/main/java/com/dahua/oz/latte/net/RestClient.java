package com.dahua.oz.latte.net;

import android.content.Context;

import com.dahua.oz.latte.net.callback.IError;
import com.dahua.oz.latte.net.callback.IFailure;
import com.dahua.oz.latte.net.callback.IReqeust;
import com.dahua.oz.latte.net.callback.ISuces;
import com.dahua.oz.latte.net.callback.RequestCallbacks;
import com.dahua.oz.latte.net.download.DownloadHandler;
import com.dahua.oz.latte.ui.LatteLoader;
import com.dahua.oz.latte.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/11/6
 */

public class RestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IReqeust IREQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final ISuces ISUCCESS;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IReqeust irequest,
                      IError ierror,
                      IFailure ifailure,
                      ISuces isuccess,
                      RequestBody body,
                      LoaderStyle loaderStyle,
                      Context context,
                      File file,
                      String downloadDir,
                      String extension,
                      String name) {
        this.URL = url;
        PARAMS.putAll(params);
        this.IREQUEST = irequest;
        this.IERROR = ierror;
        this.IFAILURE = ifailure;
        this.ISUCCESS = isuccess;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }

        // 在这里调用Loader
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT);
        }


        switch (method) {
            case GET: {
                call = service.get(URL, PARAMS);
                break;
            }
            case POST: {
                call = service.post(URL, PARAMS);
                break;
            }
            case POST_RAW: {
                // POST原始数据
                call = service.postRaw(URL, BODY);
                break;
            }
            case PUT: {
                call = service.put(URL, PARAMS);
                break;
            }
            case PUT_RAW: {
                call = service.putRaw(URL, BODY);
                break;
            }
            case DELETE: {
                call = service.delete(URL, PARAMS);
                break;
            }
            case UPLOAD: {
                // 记得去OkHttp官网查看
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = RestCreator.getRestService().upload(URL, body);
                break;
            }
            default: {
                break;
            }
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                IREQUEST,
                IERROR,
                IFAILURE,
                ISUCCESS,
                LOADER_STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must not be null");
            } else {
                request(HttpMethod.POST_RAW);
            }
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must not be null");
            } else {
                request(HttpMethod.PUT_RAW);
            }
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, IREQUEST, DOWNLOAD_DIR, EXTENSION,
                NAME, IERROR, IFAILURE, ISUCCESS).handleDownload();
    }
}
