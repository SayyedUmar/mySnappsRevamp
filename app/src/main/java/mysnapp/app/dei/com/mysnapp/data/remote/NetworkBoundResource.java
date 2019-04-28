package mysnapp.app.dei.com.mysnapp.data.remote;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import mysnapp.app.dei.com.mysnapp.BuildConfig;
import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.model.Data;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.JsonDateDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * This class act as the decider to cache the response/ fetch from the service always
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public abstract class NetworkBoundResource<T, V> {

    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(Resource.loading(null));

        // Always load the data from DB intially so that we have
        LiveData<T> dbSource = loadFromDb();

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch()) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> {
                    if(null != newData)
                        result.setValue(Resource.success(newData)) ;
                });
            }
        });
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     * @param dbSource - Database source
     */
    private void fetchFromNetwork(final LiveData<T> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<V>() {
            @Override
            public void onResponse(@NonNull Call<V> call, @NonNull Response<V> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<V> call, @NonNull Throwable t) {
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(getCustomErrorMessage(t), newData)));
            }
        });
    }

    private String getCustomErrorMessage(Throwable error){

        if (error instanceof SocketTimeoutException) {
            return MyApp.getAppContext().getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return  MyApp.getAppContext().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
             return  MyApp.getAppContext().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return MyApp.getAppContext().getString(R.string.unknownError);
        }

    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(V response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(V item);

    @MainThread
    private boolean shouldFetch() {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<V> createCall();

    public final LiveData<Resource<T>> getAsLiveData() {
        return result;
    }

    public static class APIClient {

        private static Retrofit retrofit = null;

        public static Retrofit getRetroClient(String url) {

            if (retrofit == null) {
                synchronized (APIClient.class) {
                    if (retrofit == null) {
                        retrofit = getClient(Const.ROOT_URL);
                    }
                }
            } else if (!retrofit.baseUrl().toString().equalsIgnoreCase(url)) {
                synchronized (APIClient.class) {
                    retrofit = getClient(url);
                }
            }
            return retrofit;
        }

    //    private static OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
    //        @Override
    //        public Response intercept(Chain chain) throws IOException {
    //
    //
    //            Request.Builder ongoing = chain.request().newBuilder();
    //
    //            //Log.d("printauthkeys","****   "+Idea_Urban.getInstance().get_Authkeyvalues());
    //
    //            ongoing.addHeader("auth_key","201905Awgvv9gYkN75aa34b9c");
    //            ongoing.addHeader("Content-Type", "application/xml");
    //
    //            return chain.proceed(ongoing.build());
    //        }
    //    }).build();


        private static Retrofit getClient(String url) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
    //                .cache(new Cache(new File(App.getContext().getCacheDir(), "http"), 1024 * 1024 * 10))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                okHttpBuilder.addInterceptor(loggingInterceptor);
            }

    //        GsonBuilder builder = new GsonBuilder();
    //        builder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());

            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();

            return new Retrofit.Builder()
                    .client(okHttpBuilder.build()).baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        public static ApiService getApiService(String url) {
            return getRetroClient(url).create(ApiService.class);
        }

        public static ApiService getApiService() {
            return getApiService(Const.ROOT_URL);
        }
    }

    public static interface ApiService<T> {

        @POST("Authenticate")
        Observable<ResponseModel<Data>> performLogin(@Header("Content-Type") String type,
                                                     @Body RequestModel model);

        @POST("Authenticate")
        Observable<Response<ResponseBody>> performLogin1(@Header("Content-Type") String type,
                                                         @Body RequestModel model);

    }
}
