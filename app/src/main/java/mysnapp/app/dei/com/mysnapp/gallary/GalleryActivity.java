package mysnapp.app.dei.com.mysnapp.gallary;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.BR;

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;

import mysnapp.app.dei.com.mysnapp.databinding.ActivityGalleryBinding;


public class GalleryActivity extends BindingActivity<ActivityGalleryBinding, GalleryActivityVM> {

    @Override
    public GalleryActivityVM onCreate() {
        return new GalleryActivityVM(this);
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gallery;
    }

}