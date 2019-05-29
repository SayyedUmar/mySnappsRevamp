package mysnapp.app.dei.com.mysnapp.photodetail;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.listeners.ImageSaveListener;
import mysnapp.app.dei.com.mysnapp.utils.SingleLiveData;
import mysnapp.app.dei.com.mysnapp.utils.Utils;

public class PhotoDetailVM extends ViewModel {

    private SingleLiveData imagesave = new SingleLiveData<String>();
    private Image model;
    private int imagePosition;
    private List<Image> imageList;
    private SlidePagerAdapter pagerAdapter;
    private ImageLoader imageLoader;
    private CompositeDisposable compositeDisposable;
    private Consumer<Image> consumer;

    public Image getModel() {
        return model;
    }

    public void setModel(int pos) {
        imagePosition = pos;
        this.model = imageList.get(pos);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public SlidePagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public Consumer<Image> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<Image> consumer) {
        this.consumer = consumer;
    }

    public void init (FragmentActivity ctx, int position, Image image, List<Image> list) {
        compositeDisposable = new CompositeDisposable();
        imageLoader = MyApp.getImageLoader();
        imagePosition = position;
        model = image;
        imageList = list;
        pagerAdapter = new SlidePagerAdapter(list, ctx.getSupportFragmentManager());
    }


    public SingleLiveData<String> getImageSaveListener() {
        return imagesave;
    }

    public void saveToGallery(int currentItem, Bitmap bitmap) {

        Utils.saveImage(model.getImageName(), bitmap, new ImageSaveListener() {
            @Override
            public void onImageSave(Uri uri, String path) {
                imagesave.postValue("Image has been saved to "+path);
            }

            @Override
            public void onError(String error) {
                imagesave.postValue(error);
            }
        });

    }

    public void saveShareImage(int currentItem, Bitmap bitmap, Activity activity) {
        WeakReference<Activity> weakActivity = new WeakReference(activity);
        Utils.saveImage(model.getImageName(), bitmap, new ImageSaveListener() {
            @Override
            public void onImageSave(Uri uri, String path) {
                if (weakActivity.get() != null) {
                    shareImage(uri, weakActivity.get());
                }
                imagesave.postValue("");
            }

            @Override
            public void onError(String error) {
                imagesave.postValue(error);
            }
        });
    }

    private void shareImage(Uri uri, Activity activity) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        activity.startActivity(Intent.createChooser(share, "Share Image"));
    }

    public void enableBackground(TextView selected, TextView...unselected) {
        selected.setBackground(ContextCompat.getDrawable(selected.getContext(), R.drawable.bg_rect_round_red));
        for (TextView item : unselected) {
            item.setBackground(ContextCompat.getDrawable(selected.getContext(), R.drawable.bg_rect_round_gray));
        }
    }

    public BitmapDrawable getImageBitmap(int pos) {
        SlidePageFragment frag = (SlidePageFragment) pagerAdapter.mPageReferenceMap.get(pos);
       return (BitmapDrawable) frag.imageView.getDrawable();
    }

}
