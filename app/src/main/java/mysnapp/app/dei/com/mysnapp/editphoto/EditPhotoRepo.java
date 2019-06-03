package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.common.APIClient;
import mysnapp.app.dei.com.mysnapp.common.ApiService;
import mysnapp.app.dei.com.mysnapp.data.local.AppDatabase;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Border;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;
import mysnapp.app.dei.com.mysnapp.data.local.entity.User;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;

public class EditPhotoRepo {

    private AppDatabase db;
    private ApiService apiService;
    private CompositeDisposable disposable;
    private User user;
    private Executor executor;
    private ExecutorService executorService;
    private LiveData<List<Border>> liveBorders;
    private LiveData<List<Graphic>> liveGraphics;

    private static final String TAG = "EditPhotoRepo";

    public LiveData<List<Border>> getLiveBorders() {
        return liveBorders;
    }

    public LiveData<List<Graphic>> getLiveGraphics() {
        return liveGraphics;
    }

    public EditPhotoRepo () {
        disposable = new CompositeDisposable();
        executor = Executors.newSingleThreadExecutor();
        apiService = APIClient.getApiService();
        db = AppDatabase.getAppDatabase(MyApp.getAppContext());
        liveBorders = db.borderDao().getAllBorders();
        liveGraphics = db.graphicsDao().getAllGraphics();
        executor.execute(() -> {
            user = db.userDao().getSimpleUser();
        });

    }

    public void fetchAllBorders() {
        RequestModel requestModel = new RequestModel(null);
        requestModel.PartnerUserId = user.getPartnerUserID();
        apiService.fetchAllBorders("application/json", requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseModel<List<Border>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }
                    @Override
                    public void onNext(ResponseModel<List<Border>> res) {
                        Log.e(TAG, "onNext: ");
                        executor.execute(() -> {
                            for (Border b: res.Data) {
                                db.borderDao().insert(b);
                            }
                            liveBorders = db.borderDao().getAllBorders();
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }

    public void fetchAllGraphics() {
        RequestModel requestModel = new RequestModel(null);
        requestModel.PartnerUserId = user.getPartnerUserID();
        apiService.fetchAllGraphics("application/json", requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseModel<List<Graphic>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }
                    @Override
                    public void onNext(ResponseModel<List<Graphic>> res) {
                        Log.e(TAG, "onNext: ");
                        executor.execute(() -> {
                            for (Graphic b: res.Data) {
                                db.graphicsDao().insert(b);
                            }
                            liveGraphics = db.graphicsDao().getAllGraphics();
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }

    public void uploadImageBitmap(Bitmap bitmap) {
        File file = null;
        FileOutputStream out = null;
        File t_file = null;
        FileOutputStream t_out = null;

       /* file = FileUtils.getDownloadFilePath(url, ImageBeanContainer.getImageBeanContainer().getImageDetailBean(alumId)
                .get(Const.POSITION).getImageId(), BorderAndFontActivity.this);*/
    }

    private static class ReqData {

    }
}
