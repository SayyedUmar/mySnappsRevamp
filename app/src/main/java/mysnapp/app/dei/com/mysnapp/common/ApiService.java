package mysnapp.app.dei.com.mysnapp.common;


import java.util.List;

import io.reactivex.Observable;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Border;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.model.Data;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService<T> {

    @POST("Authenticate")
    Observable<ResponseModel<Data>> performLogin(@Header("Content-Type") String type,
                                                 @Body RequestModel model);

    @POST("Authenticate")
    Observable<ResponseModel<Data>> performLogin1(@Header("Content-Type") String type,
                                                     @Body RequestModel model);

    @POST("ClaimQRCode")
    Observable<ResponseModel<Data>> claimQRCode(@Header("Content-Type") String type,
                                   @Body RequestModel model);

    @POST("GetPartnerBorders_V1")
    Observable<ResponseModel<List<Border>>> fetchAllBorders(@Header("Content-Type") String type,
                                                            @Body RequestModel model);

    @POST("GetPartnerGraphics_V1")
    Observable<ResponseModel<List<Graphic>>> fetchAllGraphics(@Header("Content-Type") String type,
                                                              @Body RequestModel model);
}
