package com.huawei.audiodevicekit.mvp.view;


import com.huawei.audiodevicekit.mvp.presenter.IPresenter;

/**
 * Created by Felix on 2016/8/19.
 * <p>
 * 封装重复操作绑定Presenter的步骤,由View层(由BaseActivity/BaseFragment)实现
 * <p>
 * (此类和MVP模式无直接关联，只是一种处理View与Presenter生命周期的规范)
 * <p>
 * 泛型参数可不传，返回原始类型
 * 泛型参数：传入T类型即返回T类型，T类型有限定声明类型即是IPresenter的子类
 */
public interface UiFactory<P extends IPresenter, U extends Ui> {

    /**
     * @return 创建并返回Presenter
     */
    P createPresenter();

    /**
     * @return 获取实现了Ui接口的activity/fragment
     */
    U getUiImplement();

    /**
     * @return 返回Presenter
     */
    P getPresenter();


}

