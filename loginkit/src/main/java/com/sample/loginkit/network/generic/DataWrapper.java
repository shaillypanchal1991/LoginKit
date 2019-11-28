package com.sample.loginkit.network.generic;


import com.sample.loginkit.network.error.CustomException;

public class DataWrapper<T> {
    private CustomException apiException;
    private T data;

    public CustomException getApiException() {
        return apiException;
    }

    public void setApiException(CustomException apiException) {
        this.apiException = apiException;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}