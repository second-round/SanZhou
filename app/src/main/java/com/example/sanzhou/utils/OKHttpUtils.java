package com.example.sanzhou.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpUtils {
    private static OKHttpUtils instence;
    private OkHttpClient client;
    private Handler handler=new Handler(Looper.getMainLooper());
    public OKHttpUtils() {
        client=new OkHttpClient.Builder()
                .connectTimeout(0,TimeUnit.SECONDS)
                .writeTimeout(0,TimeUnit.SECONDS)
                .readTimeout(0,TimeUnit.SECONDS)
                .build();
    }

    public static OKHttpUtils getInstence() {
        if (instence==null){
            synchronized (OKHttpUtils.class){
                instence=new OKHttpUtils();
            }
        }
        return instence;
    }

    public void getData(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        FormBody.Builder builder=new FormBody.Builder();
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iCallBack.fail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final Object o = new Gson().fromJson(string, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallBack.success(o);
                    }
                });
            }
        });


    }

}
