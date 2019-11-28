package com.sample.loginkit.network.generic;


import com.sample.loginkit.network.error.CustomException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


abstract public class ApiCallback<T> implements Callback<T> {
    private int totalRetries = 2;
    private int retryCount = 0;


    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.body() != null) {
            handleResponseData(response.body());
        } else {
            retryCount++;
            if (retryCount < totalRetries) {
                call.clone().enqueue(this);
            } else {

                handleError(response);
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof Exception) {
            handleException((CustomException) t);
        } else {

        }
    }

    abstract protected void handleResponseData(T data);

    abstract protected void handleError(Response<T> response);

    abstract protected void handleException(CustomException t);


}





