package mysnapp.app.dei.com.mysnapp;

import android.app.Application;

public class MyApp extends Application {

    private static MyApp sInstance;


    public static MyApp getAppContext() {
        return sInstance;
    }



    private static synchronized void setInstance(MyApp app) {
        sInstance = app;
    }
   /* @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;*/

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
    }

    private void initializeComponent() {
        /*DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);*/
    }

   /* @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }*/
}
