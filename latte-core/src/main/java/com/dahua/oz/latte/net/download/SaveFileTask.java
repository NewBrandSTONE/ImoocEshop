package com.dahua.oz.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.dahua.oz.latte.app.Latte;
import com.dahua.oz.latte.net.callback.IReqeust;
import com.dahua.oz.latte.net.callback.ISuces;
import com.dahua.oz.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * 下载的时候，需要使用一个异步任务去处理
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @date 2017/12/31
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IReqeust REQUEST;
    private final ISuces SUCCESS;
    public static final String APK_EXTENSION = "apk";


    public SaveFileTask(IReqeust reqeust, ISuces success) {
        this.REQUEST = reqeust;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final InputStream is = body.byteStream();
        final String name = (String) params[3];
        if (TextUtils.isEmpty(downloadDir)) {
            downloadDir = "down_loads";
        }
        if (TextUtils.isEmpty(extension)) {
            extension = "";
        }
        if (TextUtils.isEmpty(name)) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals(APK_EXTENSION)) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
