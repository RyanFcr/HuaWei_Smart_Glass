package com.huawei.audiodevicekit.mvp.view.support;

import com.huawei.audiodevicekit.mvp.presenter.IPresenter;
import com.huawei.audiodevicekit.mvp.view.Ui;
import com.huawei.audiodevicekit.mvp.view.UiCreator;
import com.huawei.audiodevicekit.mvp.view.UiCreatorAdapter;
import com.huawei.audiodevicekit.mvp.view.UiFactory;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by Felix on 2015/3/26.
 */
public abstract class BaseFragmentActivity<P extends IPresenter, U extends Ui> extends
    FragmentActivity implements UiFactory<P, U> {

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
