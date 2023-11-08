package com.example.filip.zdravahrana;



public interface ApiResponseHandler {
    void onResponse(Object response);
    void onFailure(Throwable t);
    void onError(int code);
}
