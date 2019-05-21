package mysnapp.app.dei.com.mysnapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import mysnapp.app.dei.com.mysnapp.data.local.AppDatabase;
import mysnapp.app.dei.com.mysnapp.utils.RxBus;

public class MyApp extends Application {

    private static MyApp self;
    private static RxBus bus;

    public static Context getAppContext() {
        return self;
    }
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static RxBus getBus() {
        return bus;
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
        bus = new RxBus();
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

    @Override
    public void onTerminate() {
        super.onTerminate();
        imageLoader = null;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static ImageLoaderConfiguration getImageConfig (Context ctx) {
        File CACHE_DIR = StorageUtils.getCacheDirectory(ctx);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                //.taskExecutor(runnable -> {})
                //.taskExecutorForCachedImages(runnable -> {})
                .threadPoolSize(5) // default
                .threadPriority(Thread.NORM_PRIORITY) // default
                .tasksProcessingOrder(QueueProcessingType.LIFO) // default if FIFO
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(25) // default
                .diskCache(new UnlimitedDiskCache(CACHE_DIR)) // default
                .diskCacheSize(100 * 1024 * 1024)
//                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(ctx)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        return config;
    }

    public static DisplayImageOptions getDisplayImageOptions () {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_placeholder_image)
                .showImageOnFail(R.drawable.ic_placeholder_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.ic_placeholder_image).build();

        return options;
    }

}
