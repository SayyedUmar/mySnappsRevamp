package mysnapp.app.dei.com.mysnapp.preview;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.FragmentActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.photodetail.SlidePagerAdapter;
import mysnapp.app.dei.com.mysnapp.utils.SingleLiveData;

public class PreviewVM extends ViewModel {

    private SingleLiveData imagesave = new SingleLiveData<String>();
    private Image model;
    private int imagePosition;
    private List<Image> imageList;
    private SlidePagerAdapter pagerAdapter;

    public Image getModel() {
        return model;
    }


    public int getImagePosition() {
        return imagePosition;
    }

    public SlidePagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void init (FragmentActivity ctx, int position, Image image, List<Image> list) {
        imagePosition = position;
        model = image;
        imageList = list;
        pagerAdapter = new SlidePagerAdapter(list, ctx.getSupportFragmentManager());
    }




}
