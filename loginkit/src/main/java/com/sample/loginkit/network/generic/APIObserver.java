package com.sample.loginkit.network.generic;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.sample.loginkit.network.error.CustomException;


public class APIObserver<T> implements Observer<DataWrapper<T>> {
    private ChangeListener<T> changeListener;
    private static final int ERROR_CODE = 0;

    public APIObserver(ChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void onChanged(@Nullable DataWrapper<T> tDataWrapper) {
        if (tDataWrapper != null) {
            if (tDataWrapper.getApiException() != null) {

                changeListener.onException(tDataWrapper.getApiException());
            } else {
                changeListener.onSuccess(tDataWrapper.getData());
            }
            return;
        }
        //custom exceptionn to suite my use case
        //changeListener.onException(new ValidationAPIException("Invalid Credentials"));
    }

    public interface ChangeListener<T> {
        void onSuccess(T dataWrapper);

        void onException(CustomException exception);
    }
}