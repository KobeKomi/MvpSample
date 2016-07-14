package mvp.com.mvpsample.extend.presenter;

import android.os.AsyncTask;
import android.util.Log;

import mvp.com.mvpsample.extend.base.Presenter;
import mvp.com.mvpsample.extend.base.Ui;
import mvp.com.mvpsample.model.MvpModel;

/**
 * Created by Komi on 2016/7/13.
 */
public class MvpXPresenter extends Presenter<MvpXPresenter.IMvpXUi>{

    private static final String TAG = "MvpXPresenter";
    private MvpModel mvpModel;


    public interface IMvpXUi extends Ui{

        void onProgressUpdate(int progress);

        void loadCompleted(String result);
    }

    public MvpXPresenter()
    {
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
            getUi().onProgressUpdate(values[0]);
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
            getUi().loadCompleted(s);
        }
    }


}
