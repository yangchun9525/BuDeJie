package com.yc.baisibudejie.http;

import com.yc.baisibudejie.constant.HttpURL;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leo on 16/10/22.
 */

public class WBService {

    public static Retrofit getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpURL.BASEURL)
                .client(WBHttpClient.getHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
