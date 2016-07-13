package mvp.com.mvpsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mvp.com.mvpsample.model.MvpModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mNameTv;
    private Button mStartBtn;
    private ProgressBar mLoadPb;
    private MvpModel mvpModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameTv = (TextView) findViewById(R.id.tv_name);
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mLoadPb = (ProgressBar) findViewById(R.id.pb_load_progress);
        mStartBtn.setOnClickListener(listener);
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                    mLoadPb.setProgress(0);
                    mNameTv.setText("正在获取数据...");
                    LoaderAsyncTask task = new LoaderAsyncTask();
                    task.execute();
                    break;
            }

        }
    };


    private class LoaderAsyncTask extends AsyncTask<String, Integer, String> {


        public LoaderAsyncTask() {
            super();
            mvpModel = new MvpModel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mLoadPb.setProgress(values[0]);
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
            mNameTv.setText(s);
        }
    }


}
