package mvp.com.mvpsample.extend.base;

import android.content.Context;

/**
 * Presenter类 高层抽象,View(Ui)层和Model(数据)层通过该类实现交互
 * @param <U>
 */
public abstract class Presenter<U extends Ui>  {

    private U mUi;
    private Context context;

    public void onUiReady(U ui) {
        mUi = ui;
    }

    public final void onUiDestroy(U ui) {
        onUiUnready(ui);
        mUi = null;
    }

    public void onUiUnready(U ui) {
    }

    public U getUi()
    {
        return mUi;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    
}
