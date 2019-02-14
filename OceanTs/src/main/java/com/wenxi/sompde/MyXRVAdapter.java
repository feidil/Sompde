package com.wenxi.sompde;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mine.xrecyclerview.rcvadapter.BaseRcvAdapter;
import com.mine.xrecyclerview.rcvadapter.ViewHolder;

import java.util.List;

/**
 * $DESCRIPTION
 *
 * @author dawenxi
 * @email feidil@163.com
 * @creat 2019 - 02 - 12 13:55
 */
public class MyXRVAdapter extends BaseRcvAdapter{
    public MyXRVAdapter(Context context, List datas, int... resid) {
        super(context, datas, resid);
    }

    @Override
    public void convert(ViewHolder holder, Object o, int itemViewType) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}

