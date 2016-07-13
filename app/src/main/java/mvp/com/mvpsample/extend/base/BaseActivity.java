package mvp.com.mvpsample.extend.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Komi on 2015/3/26.
 */
public abstract class BaseActivity <P extends Presenter<U>, U extends Ui> extends Activity implements UiIml<P,U>{

    private P mPresenter;

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        onPresenterUiReady(getUiRealize());
        getPresenter().setContext(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onPresenterUiDestroy(getUiRealize());
    }

    @Override
    public  void onPresenterUiReady(U ui) {
        getPresenter().onUiReady(ui);
    }

    @Override
    public void onPresenterUiDestroy(U ui) {
        getPresenter().onUiDestroy(ui);
    }

}
