package com.wenxi.sompde;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mine.xrecyclerview.XRecyclerView;
import com.mine.xrecyclerview.rcvadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Terry on 2019/2/12.
 */

public class XRecyclerViewActivity extends Activity {
//    @BindView(R.id.tv_title)
//    TextView mTv_top_title;
//    @BindView(R.id.tv_right)
//    TextView mTv_right;
//    @BindView(R.id.fl_right)
//    FrameLayout mFl_right;


    XRecyclerView mXrcv;

    List<TestBean.DataBean.ListBean> mDatas = new ArrayList<>();
    private TestAdapter mAdapter;

    private boolean isRefresh = true; // 记录用户是否是刷新数据
    private int pageSize = 15;
    private int currentPage = 1;
    private int totalPage = 10;
    private XRecyclerViewActivity mSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xrview_activity);
        initViews();
    }


    protected void initViews() {

        mXrcv = findViewById(R.id.xrv);
        mSelf = this;
        // 基本步骤和RecyclerView是一样的
        // 1.指定子布局的样式
        LinearLayoutManager manager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager manager1=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mXrcv.setLayoutManager(manager1);

        // 2.设置是否可以上拉和下拉
        mXrcv.setPullRefreshEnabled(true);// 刷新
        mXrcv.setLoadingMoreEnabled(true);// 加载

        // 3.添加Adapter -- R.layout.empty_view 是必须传入的一个没有数据时显示的布局
        mAdapter = new TestAdapter(this, mDatas, R.layout.empty_view, R.layout.item_playlist);
        mXrcv.setAdapter(mAdapter);

        // 4.设置各种监听
        // 刷新和加载监听
        mXrcv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                // 上拉回调
                isRefresh = true;
                currentPage = 1;
                getList(false);
            }

            @Override
            public void onLoadMore() {
                // 下拉回调
                isRefresh = false;
                if (currentPage >= totalPage) {
                    mXrcv.setNoMore(true, true, "没有更多数据了");
                } else {
                    currentPage++;
                    getList(false);
                }
            }
        });
        // item的点击监听
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                showMsg("点击了第" + position + "个,msg = " + mDatas.get(position));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, int position) {
                return false;
            }}
        );

        // 5.设置数据源
        getList(false);
    }

    private void getList(boolean isEmpty) {

        // 模拟网络请求获取数据
        List<TestBean.DataBean.ListBean> data = new ArrayList<>();
        if (!isEmpty) {
            currentPage++;
            for (int i = 0; i < pageSize; i++) {
                data.add(new TestBean.DataBean.ListBean("刷新" + i));
            }
        } else {
            currentPage = 1;
        }


        if (data != null && data.size() > 0) {
            // 如果刷新时,有数据就开启加载更多
            mXrcv.setLoadingMoreEnabled(true);

            if (isRefresh) {
                // 上拉刷新时
                mDatas.clear();
                mDatas = data;
                // 结束刷新
                mXrcv.refreshComplete();
            } else {
                // 下拉加载时
                mDatas.addAll(data);
                // 结束加载
                mXrcv.loadMoreComplete();
            }
        } else {
            mDatas.clear();
            // 创建一个layout_type = -1;的数据源
            TestBean.DataBean.ListBean empty = new TestBean.DataBean.ListBean("");
            empty.layout_type = -1;
            mDatas.add(empty);
            // 显示空布局时,禁止上拉加载
            mXrcv.setLoadingMoreEnabled(false);
            // 结束刷新
            mXrcv.refreshComplete();
        }

        // 最后刷新适配器
        mAdapter.setDatas(mDatas);

    }

    //    @OnClick(R.id.fl_right)
//    public void onClick(View view) {
//        getList(true);
//    }
    public void showMsg(String str) {
        Toast.makeText(mSelf, str, Toast.LENGTH_SHORT).show();
    }
}
