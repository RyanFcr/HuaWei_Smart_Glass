package com.huawei.audiodevicekit.mvp.view;


import com.huawei.audiodevicekit.mvp.model.ModelFactory;
import com.huawei.audiodevicekit.mvp.presenter.PresenterFactory;

/**
 * Created by Felix on 2017/1/2.
 * <p>
 * 此接口是{@link UiCreatorAdapter 所要实现的接口}
 */

public interface UiCreator<U extends Ui> {

    /**
     * Presenter绑定回调
     */
    void bindPresenter(U ui);

    /**
     * Presenter解绑回调
     */
    void unbindPresenter(U ui);


    /**
     * ui绑定presenter,需要在实现此接口的类传入Ui实例
     */
    void bindPresenter();

    /**
     * 解绑Ui和Presenter
     */
    void unbindPresenter();

    /**
     * 获取实现PresenterFactory的类
     */
    PresenterFactory<U> getPresenterFactory();

    /**
     * Presenter是否与Ui已解绑
     *
     * @return
     */
    boolean isUnbind();


    /**
     * 如果没有绑定则重新绑定，需要在实现此接口的类传入Ui实例
     */
    void rebindIfNeed();


    void rebindIfNeed(U ui);

    /**
     * 如果Presenter实现了{@link ModelFactory},则返回该接口，说明可以设置model层，否则返回为空
     */
    ModelFactory getModelFactory();


    /**
     * 让Presenter创建Model层并绑定
     */
    void bindModel();


    void bindPresenterModel();

    /**
     * 在同一生命周期绑定Presenter和Model层（实际情况可以分开绑定）
     * <p>
     * Model层为空依然可以调用此方法，不影响Presenter的使用。因为Model层在Presenter层创建和使用。
     *
     * @param ui
     */
    void bindPresenterModel(U ui);


}
