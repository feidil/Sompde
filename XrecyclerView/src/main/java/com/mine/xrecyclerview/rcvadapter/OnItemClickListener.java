package com.mine.xrecyclerview.rcvadapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2016/5/12.
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewGroup parent, View view, int position);
//    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
    boolean onItemLongClick(ViewGroup parent, View view, int position);
}
