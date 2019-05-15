package mysnapp.app.dei.com.mysnapp.gallary;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.repos.HomeRepo;

public class GalleryActivityVM extends ViewModel {


    public LiveData<List<Image>> getAllImages() {
        return HomeRepo.getInstance().getAllImages();
    }
}