package mysnapp.app.dei.com.mysnapp.common;


import org.json.JSONObject;

import io.reactivex.Observable;
import mysnapp.app.dei.com.mysnapp.repos.LoginRepo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService<T> {

    @POST("Authenticate")
    Observable<ResponseModel<String>> performLogin(@Header("Content-Type") String type,
                                                   @Body RequestModel model);

}
