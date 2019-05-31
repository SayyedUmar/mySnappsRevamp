package mysnapp.app.dei.com.mysnapp.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
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
import mysnapp.app.dei.com.mysnapp.data.local.dao.ImageDao;
import mysnapp.app.dei.com.mysnapp.data.local.dao.SubstoreDao;
import mysnapp.app.dei.com.mysnapp.data.local.dao.UserDao;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Substore;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.model.Data;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.MyPreferences;

public class HomeRepo {

    private ApiService apiService;

    private SubstoreDao substoreDao;
    private UserDao userDao;
    private ImageDao imageDao;
    private Executor executor;

    private CompositeDisposable disposable;
    MutableLiveData<ResponseModel> claimCodeResponse = new MutableLiveData<>();
    MutableLiveData<Throwable> claimCodeError = new MutableLiveData<>();

    public HomeRepo() {
        disposable = new CompositeDisposable();
        executor = Executors.newSingleThreadExecutor();
        apiService = APIClient.getApiService();
        this.userDao = AppDatabase.getAppDatabase(MyApp.getAppContext()).userDao();
        this.imageDao = AppDatabase.getAppDatabase(MyApp.getAppContext()).imageDao();
        this.substoreDao = AppDatabase.getAppDatabase(MyApp.getAppContext()).substoreDao();
    }


    public LiveData<ResponseModel> getClaimCodeResponse() {
        return claimCodeResponse;
    }
    public LiveData<Throwable> getClaimCodeError() {
        return claimCodeError;
    }


    static class ClaimQRCode {
        String QRCode;
        User User;
        public ClaimQRCode(String qrCode) {
            this.QRCode = qrCode;
            this.User = new User(MyPreferences.getStringValue(MyApp.getAppContext(), Const.USERNAME));
        }
        class User {
            String EmailId;
            User(String emailId) {
                this.EmailId = emailId;
            }
        }
    }

    public void claimQRCode(String qrCode) {

        apiService.claimQRCode("application/json", new RequestModel(new ClaimQRCode(qrCode)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseModel<Data>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ResponseModel<Data> responseModel) {
                        Log.e("onNext","onNext");
                        claimCodeResponse.postValue(responseModel);
                        if (responseModel.Data != null && responseModel.Data.User != null) {
                            executor.execute(() -> {
                                MyPreferences.setStringValue(MyApp.getAppContext(), Const.USER_MODEL, new Gson().toJson(responseModel.Data.User));
                                userDao.insert(responseModel.Data.User);
                                for (Substore store: responseModel.Data.SubStoreDetails) {
                                    substoreDao.insert(store);
                                    for (Image image: store.ImageDetailList) {
                                        imageDao.insert(image);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("onError","onError");
                        claimCodeError.postValue(t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","onComplete");
                    }
                });
    }

    public void unsubcribe () {
        if (disposable != null){
            disposable.clear();
        }
    }

    public LiveData<List<Image>> getAllImages() {
        return imageDao.getImages();
    }

}
