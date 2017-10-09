package com.yc.baisibudejie.http;

import com.yc.baisibudejie.constant.HttpURL;
import com.yc.baisibudejie.entity.ShowApiEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by leo on 16/10/22.
 */

public interface Wbm {
    @GET(HttpURL.BASEURL)
    Observable<ShowApiEntity> getMainDatas(@Query("showapi_appid") String appid, @Query("showapi_sign") String sign, @Query("type") String type);
}
