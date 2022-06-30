package com.huawei.audiodevicekit.mvp.view.support;

import com.huawei.audiodevicekit.mvp.presenter.IPresenter;
import com.huawei.audiodevicekit.mvp.view.Ui;
import com.huawei.audiodevicekit.mvp.view.UiCreator;
import com.huawei.audiodevicekit.mvp.view.UiCreatorAdapter;
import com.huawei.audiodevicekit.mvp.view.UiFactory;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Felix on 2015/3/26.
 */
public abstract class BaseAppCompatActivity<P extends IPresenter, U extends Ui> extends
    AppCompatActivity implements UiFactory<P, U> {

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
        setContentView(getResId());
        initView();
        initData();
        setOnclick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUiAdapter.unbindPresenter();
    }

    /**
     * 获取资源id模板
     *
     * @return 返回资源id
     */
    protected abstract int getResId();

    /**
     * 初始化UI模板
     */
    protected abstract void initView();

    /**
     * 初始化数据模板
     */
    protected void initData() {
    }

    /**
     * 点击方法设置模板
     */
    protected void setOnclick() {
    }
}
