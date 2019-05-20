package mysnapp.app.dei.com.mysnapp.listeners;

import android.net.Uri;

public interface ImageSaveListener {
    void onImageSave(Uri uri, String path);
    void onError(String error);
}
