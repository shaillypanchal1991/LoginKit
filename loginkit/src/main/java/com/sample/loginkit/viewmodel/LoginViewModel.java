package com.sample.loginkit.viewmodel;

import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.loginkit.BuildConfig;
import com.sample.loginkit.interactor.LoginInteractor;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.DataWrapper;
import com.sample.loginkit.network.generic.GenericRequestHandler;
import com.sample.loginkit.utils.StringConstants;

public class LoginViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus {
   String strUsername =  "";
   String strPassword="";
   boolean chkIsRemember;

    public MutableLiveData livedata;


    public void loginWithCredentials(String username,String password,boolean isRemember) {
        strUsername=username;
        strPassword=password;
        chkIsRemember=isRemember;
        LoginInteractor.createInstance(BuildConfig.APIKEY, StringConstants.PASSWORD, username, password).onAuthRequest(LoginViewModel.this,DataRepository.getInstance().getApiRequest());


    }

    @Override
    public void onResponseReceived(DataWrapper liveData)
    {
        if (liveData.getData() != null && liveData.getApiException() == null) {
            DataRepository.getInstance().storeUserSessionDetails((Login) liveData.getData());

            DataRepository.getInstance().storeUserName(strUsername);
            DataRepository.getInstance().storePassword(strPassword);
            DataRepository.getInstance().storeRememberMeStatus(chkIsRemember);

        }

        livedata.setValue(liveData);
    }

    public MutableLiveData getLivedata() {

        if (livedata == null) {
            livedata = new MutableLiveData<MutableLiveData>();
        }
        return livedata;
    }
}
