package com.example.filip.zdravahrana;



import java.util.ArrayList;

import hu.supercluster.overpasser.library.query.OverpassQuery;
import info.metadude.java.library.overpass.models.OverpassResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;



public interface RetrofitAPI {

    @POST(ApiUrls.OVERPASS_API)
    public Call<OverpassResponse> getOverpassElements(@Body RequestBody data);

}
