package com.sample.loginkit.network.generic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sample.loginkit.network.error.CustomException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class GenericRefreshTokenHandler<R> {
    abstract protected Call<R> makeRequest();


    public final LiveData<DataWrapper<R>> doRequest(final ITokenResponseStatus status) {

        final MutableLiveData<DataWrapper<R>> liveDataResponse = new MutableLiveData<>();
        final DataWrapper<R> dataWrapper = new DataWrapper<R>();
        makeRequest().enqueue((Callback<R>) new ApiCallback<R>() {
            @Override
            protected void handleResponseData(R data)
            {
                dataWrapper.setData(data);
                liveDataResponse.setValue(dataWrapper);
                status.refreshTokenResponse(dataWrapper);
            }

            @Override
            protected void handleError(Response<R> response) {
                dataWrapper.setApiException(new CustomException(response.message(),response.code()));
                liveDataResponse.setValue(dataWrapper);
                status.refreshTokenResponse(dataWrapper);
            }


            @Override
            protected void handleException(CustomException t) {

                dataWrapper.setApiException(t);
                liveDataResponse.setValue(dataWrapper);
                status.refreshTokenResponse(dataWrapper);
            }
        });


        return liveDataResponse;
    }



    public interface ITokenResponseStatus<T> {

        void refreshTokenResponse(DataWrapper<T> liveData);


    }


}
