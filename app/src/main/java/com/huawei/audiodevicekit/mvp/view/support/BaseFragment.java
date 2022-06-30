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

package com.huawei.audiodevicekit.mvp.view.support;

import com.huawei.audiodevicekit.mvp.presenter.IPresenter;
import com.huawei.audiodevicekit.mvp.view.Ui;
import com.huawei.audiodevicekit.mvp.view.UiCreator;
import com.huawei.audiodevicekit.mvp.view.UiCreatorAdapter;
import com.huawei.audiodevicekit.mvp.view.UiFactory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

/**
 * Created by Komi on 2016/10/9.
 */
public abstract class BaseFragment<P extends IPresenter, U extends Ui> extends Fragment
    implements UiFactory<P, U> {

    private P mPresenter;
    protected UiCreator<U> mUiAdapter;

    protected BaseFragment() {
        mPresenter = createPresenter();
        mUiAdapter = new UiCreatorAdapter<>(mPresenter, getUiImplement());
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
