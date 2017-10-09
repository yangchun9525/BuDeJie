package com.yc.baisibudejie.http;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.logger.Logger;
import com.yc.baisibudejie.GlobalApp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by leo on 16/10/22.
 */

public class WBHttpClient {

    public static String mToken;

    public static OkHttpClient getHttpClient() {


        /**
         * 缓存
         */
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                Request request = chain.request();
                if (GlobalApp.isShowNetworkError) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
                            .build();
                } else {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                            .build();
                }
                Response response = chain.proceed(request);
                if (GlobalApp.isShowNetworkError) {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };


//        File cacheFile = new File(Environment.getExternalStorageDirectory(), Constans.CACHE_PAHT);
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);//缓存文件为10MB、
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(GlobalApp.getInstance()));

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(new LogInterceptor())
//                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
//                .addInterceptor(cacheInterceptor)
                .build();
        return client;
    }


    /**
     * LOG
     */
    public static class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String paramsStr = "";
            if (request.body() != null) {
                RequestBody requestBody = request.body();
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                paramsStr = buffer.readString(charset);
            }
            long t1 = System.nanoTime();
            Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
//            if (AppUtils.isAppDebug())
                Logger.e(String.format(Locale.getDefault(), "Received response for %s%n%s in %.1fms%n%s%n%s", response.request().url(), paramsStr, (t2 - t1) / 1e6d, response.headers(), content));
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}