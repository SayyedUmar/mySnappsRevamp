package mysnapp.app.dei.com.mysnapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class MyApp extends Application {

    public static MyApp self;

    public static Context getAppContext() {
        return self;
    }



   /* @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;*/

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        Stetho.initializeWithDefaults(this);
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
