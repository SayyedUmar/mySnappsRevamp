package mysnapp.app.dei.com.mysnapp.common;


import io.reactivex.Observable;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.model.Data;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService<T> {

    @POST("Authenticate")
    Observable<ResponseModel<Data>> performLogin(@Header("Content-Type") String type,
                                                 @Body RequestModel model);

    @POST("Authenticate")
    Observable<Response<ResponseBody>> performLogin1(@Header("Content-Type") String type,
                                                     @Body RequestModel model);

}
