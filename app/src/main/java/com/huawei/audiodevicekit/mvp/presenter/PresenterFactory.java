package com.huawei.audiodevicekit.mvp.presenter;


import com.huawei.audiodevicekit.mvp.view.Ui;
import com.huawei.audiodevicekit.mvp.view.UiFactory;

/**
 * Created by Felix on 2016/8/19.
 * <p>
 * 封装重复操作绑定View层的步骤,由Presenter{@link BasePresenter}{@link BaseModelPresenter}层继承
 * <p>
 * <p>
 * 泛型参数可不传，返回原始类型
 * 泛型参数传入当前View层实现的接口类型
 * <p>
 * <p>
 * 为什么{@link PresenterFactory}不直接继承{@link IPresenter}？
 * <p>
 * 与MVP模式无直接关联，与{@link UiFactory}原理相同，控制Presenter与View关联周期。
 * 且对于Presenter可扩展性更好，此接口的方法可以自定义实现与否。
 */

public interface PresenterFactory<U extends Ui> {

    /**
     * 绑定View层
     */
    void onUiReady(U ui);

    /**
     * 解绑View层前的回调
     */
    void onUiUnready(U ui);

    /**
     * 解绑View层
     */
    void onUiDestroy(U ui);

    /**
     * @return 返回View层实现
     */
    U getUi();
}