package mysnapp.app.dei.com.mysnapp.editphoto;

import android.util.Log;

import java.util.concurrent.Executor;
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
import mysnapp.app.dei.com.mysnapp.data.local.entity.User;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.model.Data;
import mysnapp.app.dei.com.mysnapp.repos.HomeRepo;

public class EditPhotoRepo {

    private static EditPhotoRepo instance;
    private AppDatabase db;
    private ApiService apiService;
    private Executor executor;
    private CompositeDisposable disposable;
    private User user;

    private static final String TAG = "EditPhotoRepo";

    public static EditPhotoRepo getInstance() {
        if (instance == null) {
            synchronized (HomeRepo.class) {
                if (instance == null)
                    instance = new EditPhotoRepo();
            }
        }
        return instance;
    }

    public EditPhotoRepo () {
        disposable = new CompositeDisposable();
        executor = Executors.newSingleThreadExecutor();
        apiService = APIClient.getApiService();
        db = AppDatabase.getAppDatabase(MyApp.getAppContext());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                user = db.userDao().getSimpleUser();
                fetchAllBorders();
                fetchAllGraphics();
            }
        });
    }

    public void fetchAllBorders() {
        RequestModel requestModel = new RequestModel(null);
        requestModel.PartnerUserId = user.getPartnerUserID();
        apiService.fetchAllBorders("application/json", requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseModel<Data>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }
                    @Override
                    public void onNext(ResponseModel<Data> dataResponseModel) {
                        Log.e(TAG, "onNext: ");
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
                .subscribeWith(new Observer<ResponseModel<Data>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }
                    @Override
                    public void onNext(ResponseModel<Data> dataResponseModel) {
                        Log.e(TAG, "onNext: ");
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

    private static class ReqData {

    }
}
