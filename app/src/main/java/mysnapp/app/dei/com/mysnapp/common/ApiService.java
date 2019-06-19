package mysnapp.app.dei.com.mysnapp.common;


import java.util.List;

import io.reactivex.Observable;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Border;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;
import mysnapp.app.dei.com.mysnapp.data.remote.RequestModel;
import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.model.Data;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("UploadFile")
    Call<ResponseBody> uploadFile(@Header("Content-Type") String type,
                                  @Part MultipartBody.Part file,
                                  @Part("ImageId") RequestBody description
    );

    @POST("UploadFile")
    Call<ResponseBody> uploadFile1(@Header("Content-Type") String type,
                                   @Body RequestModel model
    );

    @POST("Restore")
    Call<ResponseBody> restoreImage (@Header("Content-Type") String type,
                                     @Body RequestModel model
    );
}
