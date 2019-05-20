package mysnapp.app.dei.com.mysnapp.gallary;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
import mysnapp.app.dei.com.mysnapp.photodetail.PhotoDetailActivity;
import mysnapp.app.dei.com.mysnapp.utils.Utils;
import mysnapp.app.dei.com.mysnapp.view.adapters.GenericAdapter;
import mysnapp.app.dei.com.mysnapp.view.base.BaseActivity;


public class GalleryActivity  extends BaseActivity<ActivityGalleryBinding> {

    private static final String TAG = "GalleryActivity";

    private GalleryActivityVM viewModel;
    private AlertDialog progress;
    private List<Image> list = new ArrayList<>();
    private ImageLoader imageLoader;
    private GenericAdapter adapter;

    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.tvHeading)
    TextView tvHeading;
    @BindView(R.id.img_back)
    ImageView img_back;

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

        gridView.setColumnWidth(GridView.AUTO_FIT);
        adapter = getAdapter();
        gridView.setAdapter(adapter);
    }

    private void setEventListeners() {

        img_back.setOnClickListener(v -> onBackPressed());

        viewModel.getAllImages().observe(this, images -> {
            if (images != null) {
                this.list.clear();
                this.list.addAll(images);
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

    private GenericAdapter getAdapter() {
        return new GenericAdapter<Image>(list) {
            @Override
            public View onCreateView(View v, int pos, ViewGroup parent) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gallery_gridview, parent, false);
                ImageView imageView = v.findViewById(R.id.imageView);
                v.setTag(imageView);
                DisplayMetrics metrics = Utils.getDisplayMetrics((Activity) v.getContext());
                int width = metrics.widthPixels /3 - 30;
                imageView.setLayoutParams(new GridView.LayoutParams(width, width));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(0, 0, 0, 0);
                return v;
            }

            @Override
            public void onBindView(View v, int pos, Image item) {
                ImageView imageView = (ImageView) v.getTag();
                imageLoader.displayImage(list.get(pos).getImageThumbnailUrl(), imageView, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        Log.e(TAG, "onLoadingStarted: " + imageUri);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        Log.e(TAG, "onLoadingFailed: " + imageUri);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.e(TAG, "onLoadingComplete: " + imageUri);
                        imageView.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        Log.e(TAG, "onLoadingCancelled: " + imageUri);
                    }
                });

                v.setOnClickListener(v1 -> {
                    startActivity(new Intent(v.getContext(), PhotoDetailActivity.class)
                            .putExtra("ITEM", item));
                });

            }
        };
    }

}