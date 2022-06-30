package com.huawei.audiodevicekit.mvp.view;


import com.huawei.audiodevicekit.mvp.model.Model;
import com.huawei.audiodevicekit.mvp.model.ModelFactory;
import com.huawei.audiodevicekit.mvp.presenter.IPresenter;
import com.huawei.audiodevicekit.mvp.presenter.PresenterFactory;

/**
 * Created by Felix on 2017/1/2.
 * <p>
 * 此类是封装绑定Ui和Presenter的操作类（从activity/fragment提取出来的重复操作的方法）
 */

public class UiCreatorAdapter<P extends IPresenter, U extends Ui, M extends Model> implements UiCreator<U> {

    private P mPresenter;

    private U mUi;

    private M mModel;

    /**
     * 使用此构造函数，则可以直接调用不用传参（U）的方法绑定Presenter
     *
     * @param presenter Presenter 实现 IPresenter
     * @param ui        fragment/activity 实现Ui
     */
    public UiCreatorAdapter(P presenter, U ui) {
        mPresenter = presenter;
        mUi = ui;
    }

    /**
     * 不直接传入ui的实现类，可以自由在实现类的不同生命周期下进行Presenter的绑定操作
     *
     * @param presenter
     */
    public UiCreatorAdapter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public void bindPresenter(U ui) {
        if (ui != null) {
            this.mUi = ui;
            if (getPresenterFactory() != null) {
                getPresenterFactory().onUiReady(ui);
            }
        }
    }

    @Override
    public void bindPresenter() {
        bindPresenter(mUi);
    }


    @Override
    public void unbindPresenter(U ui) {
        if (ui != null && ui == mUi) {
            if (getPresenterFactory() != null) {
                getPresenterFactory().onUiDestroy(ui);
            }
        }
    }

    @Override
    public void unbindPresenter() {
        unbindPresenter(mUi);
    }


    /**
     * 通过判断presenter是否实现{@link PresenterFactory}来控制Presenter层的生命周期
     *
     * @return
     */
    @Override
    public PresenterFactory<U> getPresenterFactory() {
        if (mPresenter instanceof PresenterFactory) {
            return (PresenterFactory) mPresenter;
        }
        return null;
    }

    /**
     * 通过判断presenter是否实现{@link ModelFactory}来控制Model层的生命周期
     *
     * @return
     */
    @Override
    public ModelFactory<M> getModelFactory() {
        if (mPresenter instanceof ModelFactory) {
            return (ModelFactory) mPresenter;
        } else {
            return null;
        }
    }

    @Override
    public boolean isUnbind() {
        return getPresenterFactory() == null || getPresenterFactory().getUi() == null;
    }

    @Override
    public void rebindIfNeed(U ui) {
        if (isUnbind()) {
            bindPresenter(ui);
        }
    }

    @Override
    public void rebindIfNeed() {
        rebindIfNeed(mUi);
    }


    @Override
    public void bindModel() {
        ModelFactory<M> factory = getModelFactory();
        if (factory != null) {
            if (mModel == null) {
                mModel = factory.createModel();
            }
            factory.onModelReady(mModel);
        }
    }

    @Override
    public void bindPresenterModel() {
        bindPresenter();
        bindModel();
    }

    @Override
    public void bindPresenterModel(U ui) {
        bindPresenter(ui);
        bindModel();
    }
}
