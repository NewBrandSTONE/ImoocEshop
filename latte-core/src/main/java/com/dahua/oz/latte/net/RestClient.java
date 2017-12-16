package com.dahua.oz.latte.net;

import android.content.Context;

import com.dahua.oz.latte.net.callback.IError;
import com.dahua.oz.latte.net.callback.IFailure;
import com.dahua.oz.latte.net.callback.IReqeust;
import com.dahua.oz.latte.net.callback.ISuces;
import com.dahua.oz.latte.net.callback.RequestCallbacks;
import com.dahua.oz.latte.ui.LatteLoader;
import com.dahua.oz.latte.ui.LoaderStyle;

import java.util.WeakHashMap;

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
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final ISuces ISUCCESS;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IReqeust irequest,
                      IError ierror,
                      IFailure ifailure,
                      ISuces isuccess,
                      RequestBody body,
                      LoaderStyle loaderStyle,
                      Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.IREQUEST = irequest;
        this.IERROR = ierror;
        this.IFAILURE = ifailure;
        this.ISUCCESS = isuccess;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
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
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
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
            case PUT: {
                call = service.put(URL, PARAMS);
                break;
            }
            case DELETE: {
                call = service.delete(URL, PARAMS);
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
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

}
