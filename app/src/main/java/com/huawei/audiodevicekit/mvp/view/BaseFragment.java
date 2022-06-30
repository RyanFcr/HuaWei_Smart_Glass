/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.huawei.audiodevicekit.mvp.view;

import android.os.Bundle;

import com.huawei.audiodevicekit.mvp.presenter.BasePresenter;
import com.huawei.audiodevicekit.mvp.presenter.IPresenter;
import com.huawei.audiodevicekit.mvp.presenter.PresenterFactory;


/**
 * Created by Felix on 2015/3/26.
 * 封装重复操作绑定Ui的步骤，基础类Fragment
 *
 *  注意：继承此类的子类对应的Presenter需继承 {@link BasePresenter} 或者 手动实现 {@link PresenterFactory}接口,否则返回空}
 */
public abstract class BaseFragment<P extends IPresenter, U extends Ui> extends android.app.Fragment implements UiFactory<P, U> {

    private P mPresenter;
    protected UiCreator<U> mUiAdapter;


    protected BaseFragment() {
        mPresenter = createPresenter();
        mUiAdapter =new UiCreatorAdapter<>(mPresenter, getUiImplement());
    }


    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUiAdapter.bindPresenterModel();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当在后台被销毁时需重新绑定
        mUiAdapter.rebindIfNeed();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUiAdapter.unbindPresenter();
    }
}
