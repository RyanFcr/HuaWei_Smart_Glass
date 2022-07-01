/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2021. All rights reserved.
 */

package com.huawei.audiodevicekit.bluetoothsample.view.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huawei.audiodevicekit.R;

import java.util.ArrayList;

/**
 * description
 *
 * @since 2021/02/1
 */
public class SingleChoiceAdapter extends CustomAdapter<String> {
    private static final String TAG = "SingleChoiceAdapter";

    private SaveOptionListener saveOptionListener;

    public SingleChoiceAdapter(Context context, ArrayList<String> data) {
        super(context, data);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LinearLayout choiceBlock = (LinearLayout) holder.getView(R.id.ll_choice_block);
        final TextView mTv = (TextView) holder.getView(R.id.tv_choice_text);
        if (position >= 0 && position < mData.size()) {
            mTv.setText(mData.get(position));
        }
        final int i = holder.getAdapterPosition();
        choiceBlock.setOnClickListener(v -> {
            if (i < 0 || i > mData.size() - 1) {
                return;
            }
            saveOptionListener.saveOption(mTv.getText().toString(), i);
        });
        choiceBlock.setOnLongClickListener(v -> {
            if (i < 0 || i > mData.size() - 1) {
                return false;
            }
            saveOptionListener.longClickOption(mTv.getText().toString(), i);
            return true;
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.view_rv_singlechoice;
    }

    @Override
    protected void bindData(BaseViewHolder holder, String s) {
    }

    public void pushData(String content) {
        mData.add(content);
        notifyDataSetChanged();
    }

    public void removeData(String content) {
        mData.remove(content);
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        mData = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setSaveOptionListener(SaveOptionListener listener) {
        this.saveOptionListener = listener;
    }

    public interface SaveOptionListener {
        void saveOption(String optionText, int pos);

        void longClickOption(String optionText, int pos);
    }
}
