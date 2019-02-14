package com.mine.xrecyclerview;


import java.util.List;

/**
 * Created by chipiao on 2016/12/22.
 */
public class XRecyclerUtils {
    /**
     * 请求成功recyclerview的回调
     * */
    public static void setRefreshAndLoad(boolean isLoadMore, List mList, int meiYeShuLiang, XRecyclerView mXRecyclerView){
        if (isLoadMore){
            if (mList.size()<meiYeShuLiang){
                mXRecyclerView.setNoMore(true,true,"没有更多");
            }else {
                mXRecyclerView.loadMoreComplete();
            }
        }else {
            mXRecyclerView.refreshComplete();
            if (mList.size()<meiYeShuLiang){
                mXRecyclerView.setNoMore(true,true,"没有更多");
            }else {
                mXRecyclerView.setNoMore(false);
            }
        }
    }
    /**
     * 请求失败recyclerview的回调
     * 页面减1
     * */
    public static void fallStopRefresh(boolean isLoadMore, XRecyclerView mXRecyclerView, int dangQianYeMa){
        if (isLoadMore){
            mXRecyclerView.loadMoreComplete();
            dangQianYeMa--;
        }else {
            mXRecyclerView.refreshComplete();
        }
    }
    /**当普通recyclerview使用*/
    public static void commonStopRefresh(XRecyclerView mXRecyclerView){
        mXRecyclerView.setNoMore(true,false,"没有更多");
        mXRecyclerView.setPullRefreshEnabled(false);
    }
}
