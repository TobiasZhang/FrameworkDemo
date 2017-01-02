package com.tt.frameworkdemo.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater inflater;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        if (datas == null) datas = new ArrayList<>();
        this.mContext = context;
        this.mDatas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, T[] datas) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Collections.addAll(mDatas, datas);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /** 更新数据，替换原有数据 */
    public void updateItems(List<T> items) {
        mDatas = items;
        notifyDataSetChanged();
    }

    /** 插入一条数据 */
    public void addItem(T item) {
        mDatas.add(0, item);
        notifyItemInserted(0);
    }

    /** 插入一条数据 */
    public void addItem(T item, int position) {
        position = Math.min(position, mDatas.size());
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    /** 在列表尾添加一串数据 */
    public void addItems(List<T> items) {
        int start = mDatas.size();
        mDatas.addAll(items);
        notifyItemRangeChanged(start, items.size());
    }

    /** 移除一条数据 */
    public void removeItem(int position) {
        if (position > mDatas.size() - 1) {
            return;
        }
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /** 移除一条数据 */
    public void removeItem(T item) {
        int position = 0;
        ListIterator<T> iterator = mDatas.listIterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == item) {
                iterator.remove();
                notifyItemRemoved(position);
            }
            position++;
        }
    }

    /** 清除所有数据 */
    public void removeAllItems() {
        mDatas.clear();
        notifyDataSetChanged();
    }


    /**
     * 点击事件
     */
    private OnItemClickListener clickListener;
    private RecyclerView mRecyclerView;

    public interface OnItemClickListener<T>{
        void setItemClickListener(int position, T data, int viewType);
    }
    public void setItemClickLietener(OnItemClickListener<T> clickLietener){
        this.clickListener=clickLietener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }
    @Override
    public void onClick(View v) {
        int childAdapterPosition = mRecyclerView.getChildAdapterPosition(v);
        if (clickListener!=null) {
            clickListener.setItemClickListener(childAdapterPosition,mDatas.get(childAdapterPosition),getItemViewType(childAdapterPosition));
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent,viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setOnClickListener(this);
        onBind(holder,position,getItemViewType(position));
    }

    abstract VH onCreate(ViewGroup parent, int viewType);
    abstract void onBind(VH holder, int position ,int viewType);
}
