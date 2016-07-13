package mvp.com.mvpsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mvp.com.mvpsample.R;
import mvp.com.mvpsample.presenter.MvpPresenter;

public class MvpActivity extends AppCompatActivity implements IMvpUi{

    private TextView mNameTv;
    private Button mStartBtn;
    private ProgressBar mLoadPb;
    private MvpPresenter mvpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mvpPresenter=new MvpPresenter(this);
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
                    mvpPresenter.load();
                    break;
            }

        }
    };

    @Override
    public void onProgressUpdate(int progress) {
        mLoadPb.setProgress(progress);
    }

    @Override
    public void loadCompleted(String result) {
        mNameTv.setText(result);
    }
}
