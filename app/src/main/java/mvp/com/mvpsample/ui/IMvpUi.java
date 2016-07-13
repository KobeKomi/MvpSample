package mvp.com.mvpsample.ui;

/**
 * Created by Komi on 2016/7/13.
 */
public interface IMvpUi {

    void onProgressUpdate(int progress);

    void loadCompleted(String result);
}
