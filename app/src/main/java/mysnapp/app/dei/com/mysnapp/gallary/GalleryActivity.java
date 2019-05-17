package mysnapp.app.dei.com.mysnapp.gallary;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.databinding.ActivityGalleryBinding;
import mysnapp.app.dei.com.mysnapp.thirdparty.FitGridAdapterNew;
import mysnapp.app.dei.com.mysnapp.thirdparty.FitGridView;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;


public class GalleryActivity  extends BaseActivity<ActivityGalleryBinding> {

    private static final String TAG = "GalleryActivity";

    @BindView(R.id.gridView)
    FitGridView gridView;
    private GalleryActivityVM viewModel;
    private AlertDialog progress;
    private List<Image> list = new ArrayList<>();
    private ImageLoader imageLoader;
    private FitGridAdapterNew adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(GalleryActivityVM.class);

        initialize();
        setEventListeners();
    }

    static class Holder {
        ImageView imageView;
        public Holder (View v) {
            imageView = v.findViewById(R.id.imageView);
        }
    }
//https://res.cloudinary.com/www-commdel-net/image/upload/w_150,h_100,c_fit/OrderImages/MALAYSIA/MapSnaps%20Staging%20Venue/MapSnaps%20Staging%20Site/20190307/DG-1862106299/MAPSNAP1/731_4.jpg
    private void initialize() {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
        progress = new SpotsDialog(this, R.style.DilaogStyle);
        adapter = new FitGridAdapterNew(this, R.layout.row_gallery_gridview, list) {
            @Override
            public void onCreateView(int position, View view) {
                view.setTag(new Holder(view));
            }

            @Override
            public void onBindView(int position, View view) {
                Holder holder = (Holder) view.getTag();
                imageLoader.displayImage(list.get(position).getImageThumbnailUrl(), holder.imageView, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        Log.e(TAG, "onLoadingStarted: "+ imageUri);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        Log.e(TAG, "onLoadingFailed: "+ imageUri);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.e(TAG, "onLoadingComplete: "+ imageUri);
                        holder.imageView.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        Log.e(TAG, "onLoadingCancelled: "+ imageUri);
                    }
                });
            }
        };
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);

    }

    private void setEventListeners() {

        viewModel.getAllImages().observe(this, images -> {
            if (images != null) {
                this.list.clear();
                this.list.addAll(images);
                //gridView.setAdapter(adapter);
                gridView.setNumColumns(3);
                gridView.setNumRows(list.size()/3);
                gridView.update();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private ImageLoaderConfiguration setConfig () {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                //.taskExecutor(runnable -> {})
                //.taskExecutorForCachedImages(runnable -> {})
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.LIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                //.diskCache(new UnlimitedDiskCache(CACHE_DIR)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        return config;
    }

    private DisplayImageOptions displayImageOptions () {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_placeholder_image)
                .showImageOnFail(R.drawable.ic_placeholder_image)
                .showImageOnLoading(R.drawable.ic_placeholder_image).build();

        return options;
    }

}