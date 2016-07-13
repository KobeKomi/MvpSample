
说明
===================================
关于Android mvp模式的简单例子与抽象实例<br/>

## 关于MVP模式的个人见解

###1.什么是MVP模式？

  MVP早期是由MVC模式（在此暂不讨论）演变过来的一套框架，就是对项目代码模块化，区分各类职责而诞生的。<br/>

  Model 业务逻辑和实体模型类<br/>
  View 对应于Activity/Fragment，负责View的绘制以及与用户交互<br/>
  Presenter 负责完成View于Model间交互的类<br/>

###2.为什么需要MVP模式？

  因为一般情况下，现有项目的Activity/Fragment职责过于繁杂，几乎把逻辑代码和界面交互代码都混合在了Activity/Fragment里面来处理。随着功能的更新迭代，不仅不利于日后代码的维护，也不利于代码模块的测试。我们需要MVP做的事情就是把Activity/Fragment恢复原本View层最基本简单的职责，只处理界面的交互与变化。<br/>

###3.怎么使用MVP模式？

  我们来看看此项目一个简单的界面与数据交互的实例。<br/>
  

```java
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
```
   不使用MVP模式看上去也没什么大问题。但就像我在上面所说的那样，随着Activity功能的增加，它们需要处理业务逻辑的代码量也会迅速膨胀，所以我们首先就需要从简单的情况入手，一步步拆解Activity现有所承担的职责。<br/>

   Activity的功能很简单，只是模拟从网络加载数据，其中MvpModel类代表Model层，只是模拟填充数据。点击开始按钮后，ProgressBar显示加载进度,最后TextView显示加载完成。来看看Activity除了要直接和Model层直接交互，还要处理加载的逻辑代码。在此MVP模式就有大显身手的地方啦！<br/>

   我们只需要恢复Activity原本View的职责：只处理界面的交互与变化。然后Activity精简后干净如下：<br/>

```java
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
```
    看到没有，在Activity里面，我们既去除了直接与MvpModel的耦合，也把逻辑代码放到了MvpPresenter来处理，而只需要持有一个
    MvpPresenter实例，通过回调方法处理进度条与TextView的变化！
    
    我们再来看看MvpPresenter做了些什么：

```java
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
            mvpModel = new MvpModel();
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
```
    MvpPresenter代码很简单，就是实现了之前Activity里的逻辑代码，但是彻底实现了View层与Model层的解耦，只是通过
    Presenter来处理逻辑，而Presenter在获得结果后，回调结果引起的View的变化。
    
### 为什么需要一个iMvpUi接口，而不是直接传入Activity本身？
    
    既然我们的MVP模式是为了让Activity恢复单一的职责，我们也希望Activity与Presenter弱耦合，iMvpUi接口的存在
    既方便了日后的模块测试（我们不需要实例一个Activity，而只需要实例一个实现接口的类），更符合了我们Java的基本原则：
    依赖倒置原则（面向接口编程）。所以我们只需在Activity实现iMvpUi接口，然后通过Presenter传入，当Activity需要变化，
    Presenter只需通过接口回调即可。
    
    当然更深层次的情况我们在这不讨论，我们通过MVP模式可以大大精简Activity的逻辑代码和让Activity处于View职责的位置。



## 扩展
    
    在项目代码里，extend包下，我结合之前在Google项目源码看到的，抽象了Activity、IMvpUi和Presenter的代码。
    通过Activity/fragment的生命周期来绑定Presenter，所以当Activity/fragment的界面销毁后，并不影响Presenter的
    逻辑代码的继续处理。
    
    因为在实际情况中，我们的Activity/fragment基本是与UI接口和Presenter一一对应，所以使用了泛型参数，这样在实现了
    BaseActivity/BaseFragment和Presenter的子类中，可以相互更简单地获取实例类。具体请参考代码！


## 作者
 **[KobeKomi](https://github.com/KobeKomi)** 
