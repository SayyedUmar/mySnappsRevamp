package mysnapp.app.dei.com.mysnapp.common;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("Authenticate")
    Call<ResponseModel> performLogin(RequestModel model);
}
