package com.sample.loginkit.network.generic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.sample.loginkit.network.apiUtils.APIRequestInterface;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.error.CustomException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class GenericRequestHandler<R> {
    abstract protected Call<R> makeRequest();


    public final LiveData<DataWrapper<R>> doRequest(final IResponseStatus status) {

        final MutableLiveData<DataWrapper<R>> liveData = new MutableLiveData<>();
        final DataWrapper<R> dataWrapper = new DataWrapper<R>();

        makeRequest().enqueue((Callback<R>) new ApiCallback<R>() {
            @Override
            protected void handleResponseData(R data) {
                dataWrapper.setData(data);
                liveData.setValue(dataWrapper);
                status.onResponseReceived(dataWrapper);
            }

            @Override
            protected void handleError(Response<R> response) {
                dataWrapper.setApiException(new CustomException(response.message(),response.code()));
                liveData.setValue(dataWrapper);
                status.onResponseReceived(dataWrapper);
            }


            @Override
            protected void handleException(CustomException t) {

                dataWrapper.setApiException(t);
                liveData.setValue(dataWrapper);
                status.onResponseReceived(dataWrapper);
            }
        });


        return liveData;
    }



    public interface IResponseStatus<T> {

        void onResponseReceived(DataWrapper<T> liveData);


    }


}
