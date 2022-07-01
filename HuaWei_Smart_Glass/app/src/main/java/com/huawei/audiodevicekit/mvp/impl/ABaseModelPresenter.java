package com.huawei.audiodevicekit.mvp.impl;

import com.huawei.audiodevicekit.mvp.model.Model;
import com.huawei.audiodevicekit.mvp.presenter.BaseModelPresenter;
import com.huawei.audiodevicekit.mvp.utils.LifeCycleUtil;

/**
 * Created by Felix on 2016/11/21.
 * <p>
 * 封装相同函数实现的Presenter
 */
public abstract class ABaseModelPresenter<U extends ABaseUi, M extends Model> extends BaseModelPresenter<U, M> {

    protected boolean isUiDestroy() {
        return LifeCycleUtil.isUiDestroyed(getUi());
    }

}
