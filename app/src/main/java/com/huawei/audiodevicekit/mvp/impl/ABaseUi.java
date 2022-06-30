package com.huawei.audiodevicekit.mvp.impl;

import android.content.Context;

import com.huawei.audiodevicekit.mvp.view.Ui;


/**
 * Created by Felix on 2017/3/10.
 * <p>
 * 返回Context对象的Ui封装
 */
public interface ABaseUi extends Ui {
    Context getContext();
}
