package mvp.com.mvpsample.extend.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mvp.com.mvpsample.R;
import mvp.com.mvpsample.extend.base.BaseActivity;
import mvp.com.mvpsample.extend.presenter.MvpXPresenter;

public class MvpXActivity extends BaseActivity<MvpXPresenter,MvpXPresenter.IMvpXUi> implements MvpXPresenter.IMvpXUi{

    private TextView mNameTv;
    private Button mStartBtn;
    private ProgressBar mLoadPb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameTv = (TextView) findViewById(R.id.tv_name);
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mLoadPb = (ProgressBar) findViewById(R.id.pb_load_progress);
        mStartBtn.setOnClickListener(listener);
    }

    @Override
    public MvpXPresenter createPresenter() {
        return new MvpXPresenter();
    }

    @Override
    public MvpXPresenter.IMvpXUi getUiRealize() {
        return this;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                    mLoadPb.setProgress(0);
                    mNameTv.setText("正在获取数据...");
                    getPresenter().load();
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
