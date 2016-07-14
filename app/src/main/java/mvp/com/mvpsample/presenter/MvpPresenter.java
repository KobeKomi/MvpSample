package mvp.com.mvpsample.presenter;

import android.os.AsyncTask;
import android.util.Log;

import mvp.com.mvpsample.model.MvpModel;
import mvp.com.mvpsample.ui.IMvpUi;

/**
 * Created by Komi on 2016/7/13.
 */
public class MvpPresenter {

    private static final String TAG = "MvpPresenter";
    private IMvpUi iMvpUi;
    private MvpModel mvpModel;

    public MvpPresenter(IMvpUi iMvpUi)
    {
        this.iMvpUi=iMvpUi;
        mvpModel=new MvpModel();
    }

    public void load()
    {
        LoaderAsyncTask task = new LoaderAsyncTask();
        task.execute();
    }


    private class LoaderAsyncTask extends AsyncTask<String, Integer, String> {

        public LoaderAsyncTask() {
            super();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            iMvpUi.onProgressUpdate(values[0]);
        }

        @Override
        protected String doInBackground(String... params) {

            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
                Log.d(TAG, "进度：" + i);
                if (i == 100) {
                    mvpModel.setInfo("获得了数据，加载完成！");
                }
            }
            return mvpModel.getInfo();
        }

        @Override
        protected void onPostExecute(String s) {
            iMvpUi.loadCompleted(s);
        }
    }
}
