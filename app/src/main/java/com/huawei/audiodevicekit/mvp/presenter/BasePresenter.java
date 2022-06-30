package com.huawei.audiodevicekit.mvp.presenter;


import com.huawei.audiodevicekit.mvp.view.Ui;

/**
 * Created by Felix on 2016/8/16.
 * 实现{@link PresenterFactory}的基础类Presenter
 */
public abstract class BasePresenter<U extends Ui> implements PresenterFactory<U> {

    private U mUi;

    @Override
    public void onUiReady(U ui) {
        mUi = ui;
    }

    @Override
    public void onUiDestroy(U ui) {
        onUiUnready(ui);
        mUi = null;
    }

    @Override
    public void onUiUnready(U ui) {
    }

    @Override
    public U getUi() {
        return mUi;
    }

}
