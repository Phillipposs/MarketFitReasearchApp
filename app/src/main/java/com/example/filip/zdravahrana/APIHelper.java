package com.example.filip.zdravahrana;

import hu.supercluster.overpasser.library.query.OverpassQuery;
import info.metadude.java.library.overpass.models.OverpassResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Filip on 1/3/2019.
 */

public class APIHelper {
    public static final int DEFAULT_RETRIES = 3;
    public static final int MY_RETRIES = 3;
    public static void getOverpassElementsCall(RequestBody data, final ApiResponseHandler handler) {
        Call<OverpassResponse> call = RetrofitApiHelper.getRetrofitAPI().getOverpassElements(data);
        APIHelper.enqueueWithRetry(call, MY_RETRIES, new Callback<OverpassResponse>() {
            @Override
            public void onResponse(Call<OverpassResponse> call, Response<OverpassResponse> response) {
                if (handler != null) {
                    if (response.body() != null)
                        handler.onResponse(response.body());
                    else if (response.errorBody() != null) {
                        handler.onError(response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<OverpassResponse> call, Throwable t) {
                handler.onFailure(t);
            }
        });
    }
    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final Callback<T> callback) {
        call.enqueue(new RetryableCallback<T>(call, retryCount) {

            @Override
            public void onFinalResponse(Call<T> call, Response<T> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFinalFailure(Call<T> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public static boolean isCallSuccess(Response response) {
        int code = response.code();
        return (code >= 200 && code < 400);
    }
}
