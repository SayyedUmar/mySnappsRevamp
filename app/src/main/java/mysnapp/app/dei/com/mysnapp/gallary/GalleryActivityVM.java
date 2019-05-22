package mysnapp.app.dei.com.mysnapp.gallary;


import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.photodetail.PhotoDetailActivity;
import mysnapp.app.dei.com.mysnapp.repos.HomeRepo;
import mysnapp.app.dei.com.mysnapp.utils.Utils;
import mysnapp.app.dei.com.mysnapp.view.adapters.GenericAdapter;

public class GalleryActivityVM extends ViewModel {

    private static final String TAG = "GalleryActivityVM";
    private ImageLoader imageLoader;

    void init(Context context) {
        imageLoader = MyApp.getImageLoader();
        if (!imageLoader.isInited())
            imageLoader.init(MyApp.getImageConfig(context));
    }


    LiveData<List<Image>> getAllImages() {
        return HomeRepo.getInstance().getAllImages();
    }

    GenericAdapter getAdapter(List<Image> list) {
        return new GenericAdapter<Image>(list) {
            @Override
            public View onCreateView(View v, int pos, ViewGroup parent) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gallery_gridview, parent, false);
                ImageView imageView = v.findViewById(R.id.imageView);
                v.setTag(imageView);
                DisplayMetrics metrics = Utils.getDisplayMetrics((Activity) v.getContext());
                int width = metrics.widthPixels /3 - 24;
                imageView.setLayoutParams(new GridView.LayoutParams(width, width));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(0, 0, 0, 0);
                return v;
            }

            @Override
            public void onBindView(View v, int pos, Image item) {
                ImageView imageView = (ImageView) v.getTag();
                imageLoader.displayImage(list.get(pos).getImageThumbnailUrl(), imageView, MyApp.getDisplayImageOptions());

                v.setOnClickListener(v1 -> {
                    v.getContext().startActivity(new Intent(v.getContext(), PhotoDetailActivity.class)
                            .putParcelableArrayListExtra("ITEM_LIST", (ArrayList<? extends Parcelable>) list)
                            .putExtra("ITEM", item)
                            .putExtra("POSITION", pos)
                    );
                });

            }
        };
    }



}