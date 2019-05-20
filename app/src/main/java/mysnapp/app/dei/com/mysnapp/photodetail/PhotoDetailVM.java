package mysnapp.app.dei.com.mysnapp.photodetail;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.net.Uri;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.listeners.ImageSaveListener;
import mysnapp.app.dei.com.mysnapp.utils.SingleLiveData;
import mysnapp.app.dei.com.mysnapp.utils.Utils;

public class PhotoDetailVM extends ViewModel {

    private SingleLiveData imagesave = new SingleLiveData<String>();
    private SingleLiveData imageshare = new SingleLiveData<Object[]>();

    private Image model;

    public Image getModel() {
        return model;
    }

    public void setModel(Image model) {
        this.model = model;
    }

    public SingleLiveData<String> getImageSaveListener() {
        return imagesave;
    }
    public SingleLiveData<Object[]> getImageShareListener() {
        return imageshare;
    }

    public void saveToGallery(Bitmap bitmap) {

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

    public void saveShareImage(Bitmap bitmap) {
        Utils.saveImage(model.getImageName(), bitmap, new ImageSaveListener() {
            @Override
            public void onImageSave(Uri uri, String path) {
                Object[] obj = {uri, path};
                imageshare.postValue(obj);
            }

            @Override
            public void onError(String error) {
                imagesave.postValue(error);
            }
        });
    }


}
