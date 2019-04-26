package mysnapp.app.dei.com.mysnapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import mysnapp.app.dei.com.mysnapp.data.local.AppDatabase;

public class MyApp extends Application {

    private static MyApp self;

    public static Context getAppContext() {
        return self;
    }



   /* @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;*/

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        self = this;
        AppDatabase.getAppDatabase(this);
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
