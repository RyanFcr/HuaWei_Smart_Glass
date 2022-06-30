/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2021. All rights reserved.
 */

package com.huawei.audiodevicekit.bluetoothsample.view.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * description
 *
 * @since 2021/02/1
 */
public abstract class CustomAdapter<T> extends RecyclerView.Adapter<CustomAdapter.BaseViewHolder> {
    protected List<T> mData;
    private Context context;

    public CustomAdapter(Context context, List<T> data) {
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getItemLayoutId(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.BaseViewHolder holder, int position) {
        if (position < mData.size()) {
            T t = mData.get(position);
            bindData(holder, t);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract int getItemLayoutId();

    public void refresh(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    protected abstract void bindData(BaseViewHolder holder, T t);

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        // item 中的控件缓存,key:控件id
        private HashMap<Integer, View> viewMap;

        BaseViewHolder(View itemView) {
            super(itemView);
            viewMap = new HashMap<>();
        }

        /**
         * 获取 itemView 中的控件
         */
        public View getView(int id) {
            View view = viewMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewMap.put(id, view);
            }
            return view;
        }
    }
}
