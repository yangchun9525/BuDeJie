package com.yc.baisibudejie.module;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.baisibudejie.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 22:31
 */

public class RecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public RecyclerAdapter(int layoutResId,Context context, List<String> datas) {
        super(layoutResId, datas);
        mContext = context;
        mDatas = datas;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        AutoUtils.autoSize(helper.itemView);
        helper.setText(R.id.tv_num, item);
    }
}