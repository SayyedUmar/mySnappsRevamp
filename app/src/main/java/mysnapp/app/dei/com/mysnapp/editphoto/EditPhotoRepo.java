package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.data.local.entity.User;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.Logs;
import mysnapp.app.dei.com.mysnapp.utils.SingleLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPhotoRepo {

    private AppDatabase db;
    private ApiService apiService;
    private CompositeDisposable disposable;
    private User user;
    private Executor executor;
    private ExecutorService executorService;
    private LiveData<List<Border>> liveBorders;
    private LiveData<List<Graphic>> liveGraphics;
    public SingleLiveData<Boolean> imageObserver = new SingleLiveData<>(true);

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

    public void uploadImageBitmap(Bitmap bitmap, Image image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        ReqData req = new ReqData();
        req.ImageId = image.getImageId();
        req.IsStockShot = image.getIsStockShot();
        req.ImageData = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

        Call<ResponseBody> call = APIClient.getApiService(Const.ROOT_URL_FILE_UPLOAD).uploadFile1("application/json", new RequestModel(req));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()) {//{"PartnerUserID":0,"ResponseCode":"000","ResponseMessage":"Action completed successfully.","Data":null}
                        JSONObject res = new JSONObject(response.body().string());
                        if (res.getString("ResponseCode").equals("000")) {
                            Logs.shortToast(MyApp.getAppContext(), "Image uploaded successfully.");
                            DiskCacheUtils.removeFromCache(image.getImageUrl(), MyApp.getImageLoader().getDiskCache());
                            DiskCacheUtils.removeFromCache(image.getImageThumbnailUrl(), MyApp.getImageLoader().getDiskCache());
                            MyApp.getImageLoader().getDiskCache().save(image.getImageThumbnailUrl(), Bitmap.createBitmap(bitmap));
                            MyApp.getImageLoader().getDiskCache().save(image.getImageUrl(), bitmap);
                            imageObserver.setValue(true);
                        }
                    } else {
                        JSONObject res = new JSONObject(response.errorBody().string());
                        Logs.shortToast(MyApp.getAppContext(), res.getString("ResponseMessage"));
                    }
                }catch(Exception e) { e.printStackTrac˛e(); }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logs.shortToast(MyApp.getAppContext(), t.getMessage());
            }
        });
    }

    public void restoreImage(Image image) {
        ReqData data = new ReqData();
        data.ImageId = image.getImageId();
        data.IsStockShot = image.getIsStockShot();
        apiService.restoreImage("application/json", new RequestModel(data)).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Logs.shortToast(MyApp.getAppContext(),"success");
                } else {
                    Logs.shortToast(MyApp.getAppContext(),"failure");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Logs.shortToast(MyApp.getAppContext(),"Error");
            }
        });
    }


    private static class ReqData {
        String ImageId;
        int IsStockShot;
        String ImageData;
    }

}
