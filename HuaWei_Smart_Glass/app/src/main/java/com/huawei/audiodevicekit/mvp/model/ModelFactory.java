package com.huawei.audiodevicekit.mvp.model;


import com.huawei.audiodevicekit.mvp.presenter.BaseModelPresenter;
import com.huawei.audiodevicekit.mvp.presenter.PresenterFactory;

/**
 * Created by Felix on 2016/10/19.
 * <p>
 * 封装重复操作绑定Model层的步骤
 * <p>
 * 与{@link PresenterFactory 原理相同}，不同的是此类被需要有model层的Presenter类{@link BaseModelPresenter}实现
 */
public interface ModelFactory<M extends Model> {

    /**
     * model层传入绑定
     *
     * @param model
     */
    void onModelReady(M model);

    /**
     * 创建model层
     *
     * @return
     */
    M createModel();

    /**
     * 得到model层的实现
     *
     * @return
     */
    M getModel();

}