package mvp.com.mvpsample.extend.base;

/**
 * Created by Komi on 2015/3/26.
 *
 * 负责初始化一些activity/fragment的一些共同方法
 */
 public interface UiIml<T extends Presenter<U>, U extends Ui> {

    T createPresenter();

   /**
    * @return 获取实现了Ui接口的activity/fragment
    */
    U getUiRealize();

    T getPresenter();

    void onPresenterUiReady(U ui);

    void onPresenterUiDestroy(U ui);

}
