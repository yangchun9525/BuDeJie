package com.yc.baisibudejie.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 14340 on 2017/10/9.
 */

public class SingleDataEntity implements Serializable{
    //所有的num
    public int allNum;
    //所有的page
    public int allPages;
    // 当前page
    public int currentPage;
    //具体结果
    public ArrayList<ContentEntity> contentlist;
}
