package com.huawei.audiodevicekit.mvp.presenter;


import com.huawei.audiodevicekit.mvp.model.Model;
import com.huawei.audiodevicekit.mvp.model.ModelFactory;
import com.huawei.audiodevicekit.mvp.view.Ui;

/**
 * Created by Felix on 2016/10/19
 *
 * 实现{@link PresenterFactory}{@link ModelFactory}的基础类Presenter，有Model层
 *
 * M层的实现何时传入由自己控制
 */
public abstract class BaseModelPresenter<U extends Ui,M extends Model>  extends BasePresenter<U> implements ModelFactory<M> {

    private M mModel;

    /**
     * 传入model层绑定，目前只能由presenter传入，由{@link #createModel()}传入
     * @param model
     */
    @Override
    public void onModelReady(M model) {
        this.mModel=model;
    }

    @Override
    public M getModel() {
        return mModel;
    }
}
