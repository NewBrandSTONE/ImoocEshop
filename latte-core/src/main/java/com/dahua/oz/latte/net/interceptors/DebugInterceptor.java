package com.dahua.oz.latte.net.interceptors;

import android.support.annotation.RawRes;

import com.dahua.oz.latte.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 方便调试的时候用的
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/1/18
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(chain.request())
                .build();
    }

    /**
     * @param chain
     * @param rawId @RawRes 确保是在/res/raw 下的资源文件，其他也有类似的例如@color
     * @return
     */
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        } else {
            return chain.proceed(chain.request());
        }
    }
}
