package com.huawei.audiodevicekit.mvp.impl;

import com.huawei.audiodevicekit.mvp.presenter.BasePresenter;
import com.huawei.audiodevicekit.mvp.utils.LifeCycleUtil;

/**
 * Created by Felix on 2016/11/21.
 * <p>
 * 封装相同函数实现的Presenter
 */
public abstract class ABasePresenter<U extends ABaseUi> extends BasePresenter<U> {

    protected boolean isUiDestroy() {
        return LifeCycleUtil.isUiDestroyed(getUi());
    }

}
