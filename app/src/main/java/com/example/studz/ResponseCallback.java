package com.example.studz;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}
