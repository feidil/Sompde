package com.mine.xrecyclerview.rcvadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by admin on 2016/5/12.
 */
public abstract class BaseRcvAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    //前提条件 -- 强制要求数据源中有一个layout_type名称的属性，默认值设为0,当为-1时表示显示没有数据.通过反射得到该属性
    private static final String TYPENAME = "layout_type";
    private int[] mResid;//getItemViewType方法的返回值，即为resid数组的下标
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public BaseRcvAdapter(Context context, List<T> datas, int... resid) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResid = resid;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mResid[viewType+1], -1);
//        switch (viewType){
//            case -1: // 表示数据为空,显示无数据
//                viewHolder = ViewHolder.get(mContext, null, parent, mResid[viewType+1], -1);
//                break;
//            case 0: // 表示单布局
//                viewHolder = ViewHolder.get(mContext, null, parent, mResid[viewType+1], -1);
//                break;
//            case 1: // 表示双布局
//                break;
//        }
//        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        if(viewType != -1){
            setListener(parent, viewHolder, viewType);
        }
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        if(viewType == -1){
            return false;
        }
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, position - 1);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, v, position - 1);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        holder.updatePosition(position);
        convert(holder, mDatas.get(position),itemViewType);
        convert(position,holder,mDatas.get(position),itemViewType);
        if(position >0){
            convert(position,position-1,holder,mDatas.get(position),mDatas.get(position-1),itemViewType);
        }
    }

    public abstract void convert(ViewHolder holder, T t,int itemViewType);
    public  void convert(int position, ViewHolder holder, T t,int itemViewType){

    }

    public  void convert(int position,int positionPre, ViewHolder holder, T t,T t1,int itemViewType){

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setDatas(List<T> mDatas){
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<T> mDatas){
        this.mDatas.addAll(mDatas);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 默认返回是为0; 0表示为单布局
        if (position>=0 && position<mDatas.size()){
            T t = mDatas.get(position);
            Class cl = t.getClass();
            try {
                Field field = cl.getDeclaredField(TYPENAME);//为了实现此应用,修改一下
                field.setAccessible(true);//打开私有属性的使用权限
                return field.getInt(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.getItemViewType(position);
    }
}
