package mysnapp.app.dei.com.mysnapp.repos;

import io.reactivex.Observable;
import mysnapp.app.dei.com.mysnapp.common.APIClient;
import mysnapp.app.dei.com.mysnapp.common.ApiService;
import mysnapp.app.dei.com.mysnapp.common.RequestModel;
import mysnapp.app.dei.com.mysnapp.common.ResponseModel;

public class LoginRepo<T, R> {

    private static LoginRepo instance;
    private ApiService apiService;

    public static LoginRepo getInstance() {
        if (instance == null) {
            instance = new LoginRepo();
        }
        return instance;
    }

    public LoginRepo () {
        apiService = APIClient.getApiService();
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
