package com.yc.baisibudejie.entity;

import java.io.Serializable;

/**
 * Created by 14340 on 2017/10/9.
 */

public class ShowApiEntity implements Serializable{
    public int showapi_res_code;
    public String showapi_res_error;
    public BuDeJieEntity showapi_res_body;

    @Override
    public String toString() {
        return "ShowApiEntity{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
