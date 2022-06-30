package com.huawei.audiodevicekit.mvp.view;

import android.app.Activity;
import android.os.Bundle;

import com.huawei.audiodevicekit.mvp.presenter.IPresenter;


/**
 * Created by Felix on 2015/3/26.
 * 封装重复操作绑定{@link Ui}的步骤，基础类Activity
 * <p>
 * 继承此类泛型参数传入实现对应接口的类。泛型类声明是为了显示类型，不传参数也行，但获取的类型为父类型。
 */

public abstract class BaseModelActivity<P extends IPresenter, U extends Ui> extends Activity implements UiFactory<P, U> {

    protected UiCreator<U> mUiAdapter;

    private P mPresenter;

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mUiAdapter = new UiCreatorAdapter<>(mPresenter, getUiImplement());
        mUiAdapter.bindPresenterModel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUiAdapter.unbindPresenter();
    }


}
