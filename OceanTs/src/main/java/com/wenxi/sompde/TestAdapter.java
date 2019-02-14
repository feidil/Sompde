package com.wenxi.sompde;

import android.content.Context;

import com.mine.xrecyclerview.rcvadapter.BaseRcvAdapter;
import com.mine.xrecyclerview.rcvadapter.ViewHolder;

import java.util.List;

/**
 * Created by Terry on 2019/2/12.
 */

public class TestAdapter extends BaseRcvAdapter<TestBean.DataBean.ListBean>{

    public TestAdapter(Context context, List<TestBean.DataBean.ListBean> datas, int... resid) {
        super(context, datas, resid);
    }

    @Override
    public void convert(ViewHolder holder, TestBean.DataBean.ListBean data, int itemViewType) {
        // itemViewType = -1表示显示空布局
        if (itemViewType != -1){
            holder.setText(R.id.tv_name,data.tip);
        }
    }
}
