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

package mvp.com.mvpsample.extend.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Komi on 2015/3/26.
 */
public abstract class BaseFragment<P extends Presenter<U>, U extends Ui> extends Fragment implements UiIml<P,U>{

    private P mPresenter;

    public Context getContext()
    {
        return getActivity();
    }

    protected BaseFragment() {
        mPresenter = createPresenter();
    }


    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPresenterUiReady(getUiRealize());
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().setContext(getContext());
        //当在后台被销毁时需重新绑定
        if(getPresenter().getUi()==null) {
            onPresenterUiReady(getUiRealize());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onPresenterUiDestroy(getUiRealize());
    }


    @Override
    public  void onPresenterUiReady(U ui) {
        getPresenter().onUiReady(ui);

    }

    @Override
    public  void onPresenterUiDestroy(U ui) {
        getPresenter().onUiDestroy(ui);
    }
}
