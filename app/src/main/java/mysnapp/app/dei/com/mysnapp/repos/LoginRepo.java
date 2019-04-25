package mysnapp.app.dei.com.mysnapp.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.common.APIClient;
import mysnapp.app.dei.com.mysnapp.common.ApiService;
import mysnapp.app.dei.com.mysnapp.common.RequestModel;
import mysnapp.app.dei.com.mysnapp.common.ResponseModel;
import mysnapp.app.dei.com.mysnapp.data.local.AppDatabase;
import mysnapp.app.dei.com.mysnapp.data.local.dao.ImageDao;
import mysnapp.app.dei.com.mysnapp.data.local.dao.SubstoreDao;
import mysnapp.app.dei.com.mysnapp.data.local.dao.UserDao;
import mysnapp.app.dei.com.mysnapp.data.local.entity.ImageEntity;
import mysnapp.app.dei.com.mysnapp.data.local.entity.SubstoreEntity;
import mysnapp.app.dei.com.mysnapp.model.Data;
import mysnapp.app.dei.com.mysnapp.utils.RxUtils;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginRepo<T, R> {

    private static LoginRepo instance;
    private ApiService apiService;
    SubstoreDao substoreDao;
    UserDao userDao;
    ImageDao imageDao;
    Executor executor;

    private DisposableObserver disposableObserver;
    private Disposable disposable;
    MutableLiveData<ResponseModel> loginResponse = new MutableLiveData<>();

    public static LoginRepo getInstance() {
        if (instance == null) {
            instance = new LoginRepo();
        }
        return instance;
    }

    LoginRepo () {
        executor = Executors.newSingleThreadExecutor();
        apiService = APIClient.getApiService();
        this.userDao = AppDatabase.getAppDatabase(MyApp.getAppContext()).userDao();
        this.imageDao = AppDatabase.getAppDatabase(MyApp.getAppContext()).imageDao();
        this.substoreDao = AppDatabase.getAppDatabase(MyApp.getAppContext()).substoreDao();
    }

    class UserLogin {
        String EmailId;
        String UserName;
        String Password;
        UserLogin(String email, String pwd) {
            EmailId = email;
            UserName = email;
            Password = pwd;
        }
    }

    public Observable fetchUserData(String username, String pwd) {
        return apiService.performLogin("application/json", new RequestModel(new UserLogin(username, pwd)));
    }

    public void loginUser(String username, String pwd) {

        apiService.performLogin("application/json", new RequestModel(new UserLogin(username, pwd)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseModel<Data>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseModel<Data> responseModel) {
                        Log.e("onNext","onNext");
                        loginResponse.postValue(responseModel);
                        if (responseModel.Data != null && responseModel.Data.User != null) {
                            executor.execute(() -> {
                                userDao.insert(responseModel.Data.User);
                                for (SubstoreEntity store: responseModel.Data.SubStoreDetails) {
                                    substoreDao.insert(store);
                                    for (ImageEntity image: store.ImageDetailList) {
                                        imageDao.insert(image);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","onComplete");
                    }
                });
    }

    public LiveData<ResponseModel> getLoginReponse() {
        return loginResponse;
    }

    public void unsubcribe () {
        if (disposable != null){
            disposableObserver.dispose();
        }
    }


   /* public Observable<Response<ResponseBody>> fetchUserData1(String username, String pwd) {
        return apiService.performLogin1("application/json", new RequestModel(new UserLogin(username, pwd)));
    }*/

   /* public LiveData<Resource<T>> fetchUserData(String username, String pwd) {
        return new NetworkBoundResource<T, ResponseModel>() {

            @Override
            protected void saveCallResult(ResponseModel item) {
                if(null != item && item.ResponseCode.equalsIgnoreCase("000")) {
                    Log.e("TAG_RESPONSE", "success");
                } else {

                    Log.e("TAG_RESPONSE", "failed" );
                }
                //articleDao.saveArticles(item.getPopularArticles());
            }

            @NonNull
            @Override
            protected LiveData<T> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Call<ResponseModel> createCall() {
                return apiService.performLogin(new RequestModel(new UserLogin(username, pwd)));
            }
        }.getAsLiveData();
    }*/

    /*public LiveData<Resource<T>> fetchUserData(String username, String pwd) {
        return new NetworkBoundResource<T, R>() {

            @Override
            protected void saveCallResult(R item) {

            }

            @NonNull
            @Override
            protected LiveData<T> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Call<R> createCall() {
                return null;
            }
        }.getAsLiveData();
    }*/

}
