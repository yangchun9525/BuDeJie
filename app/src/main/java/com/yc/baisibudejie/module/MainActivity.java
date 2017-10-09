package com.yc.baisibudejie.module;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseActivity;
import com.yc.baisibudejie.constant.Const;
import com.yc.baisibudejie.entity.ShowApiEntity;
import com.yc.baisibudejie.http.WBService;
import com.yc.baisibudejie.http.Wbm;
import com.yc.baisibudejie.utils.ToastUtil;

import java.util.ArrayList;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 14340 on 2017/10/9.
 */

public class MainActivity extends BaseActivity {
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"Android", "iOS", "Web", "Other"};
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initFragments();
        initViewPager();
        mImageArray = new int[]{
                R.mipmap.bg_android,
                R.mipmap.bg_ios,
                R.mipmap.bg_js,
                R.mipmap.bg_other};
        mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light};

        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTransulcentStatusBar(this)
                .setTitle("Demo")
                .setBackEnable(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setViewSize() {

    }

    @Override
    protected void initValue() {
        WBService.getService()
                .create(Wbm.class)
                .getMainDatas(Const.SHOWAPI_APPID, Const.SHOWAPI_SIGN, Const.SHOWAPI_TYPE_IMAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ShowApiEntity>() {
                    @Override
                    public void onNext(ShowApiEntity text) {
                        ToastUtil.showLongToast(text.showapi_res_error + "," + text.showapi_res_code);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("yc-onError", e.toString());
                    }
                });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void addActivity() {
        GlobalApp.getInstance().addActivity(this);
    }

    @Override
    protected void removeActivity() {
        GlobalApp.getInstance().removeActivity(this);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(MainFragment.getInstance(title));
        }
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
