package mysnapp.app.dei.com.mysnapp.photodetail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.thirdparty.TouchImageView;


public class SlidePageFragment extends Fragment {
    private static final String PIC_URL = "slidepagefragment.picurl";
    private static ImageLoader imageLoader = MyApp.getImageLoader();
    private Image image;
    public TouchImageView imageView;


    public static SlidePageFragment newInstance(@NonNull final Image image) {
        Bundle arguments = new Bundle();
        arguments.putString(PIC_URL, image.getImageUrl());

        SlidePageFragment fragment = new SlidePageFragment();
        fragment.image = image;
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_page, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        imageView.setMaxZoom(10);
        if (!imageLoader.isInited()) {
            imageLoader.init(MyApp.getImageConfig(getActivity()));
        }

        Bundle arguments = getArguments();
        if (arguments != null) {
            String url = arguments.getString(PIC_URL);
            imageLoader.displayImage(url, imageView, MyApp.getDisplayImageOptions());
        }

        return rootView;
    }
}

