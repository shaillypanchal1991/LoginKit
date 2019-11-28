package com.sample.loginkit.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.loginkit.analytics.AnalyticsServiceManager;
import com.sample.loginkit.interactor.ProfileLoginInteractor;
import com.sample.loginkit.models.Profile;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.DataWrapper;
import com.sample.loginkit.network.generic.GenericRequestHandler;
import com.sample.loginkit.utils.LogUtils;
import com.sample.loginkit.utils.StringConstants;

import java.util.HashMap;


public class ProfileLoginViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus {
    private static final String TAG = ProfileLoginViewModel.class.getName();
    public MutableLiveData<String> strUserName = new MutableLiveData<>();
    public MutableLiveData<String> strPassword = new MutableLiveData<>();


    public MutableLiveData livedata;


    public void loginwithProfileID(Profile profile, int pin) {
        LogUtils.debug(TAG, "Profile Nick Name : " + profile.getNickname());


        if (pin != 0) {
            ProfileLoginInteractor.createInstance("password", DataRepository.getInstance().fetchUserName(), DataRepository.getInstance().fetchPassword(), profile.getId(), pin).onProfileLoginRequest(this, DataRepository.getInstance().getApiRequest());
        } else {
            ProfileLoginInteractor.createInstance("password", DataRepository.getInstance().fetchUserName(), DataRepository.getInstance().fetchPassword(), profile.getId()).onProfileLoginRequest(this, DataRepository.getInstance().getApiRequest());

        }
    }


    @Override
    public void onResponseReceived(DataWrapper dataWrapper) {


        if (dataWrapper.getData() != null && dataWrapper.getApiException() == null) {
            livedata.postValue(dataWrapper);

            HashMap<String,String> eventParams = new HashMap<String,String>();
            eventParams.put("username", DataRepository.getInstance().fetchUserName());
            eventParams.put("isRememberMe",String.valueOf(DataRepository.getInstance().fetchRememberMe()));
            eventParams.put("isProfile","true");
            eventParams.put("responseObject",dataWrapper.toString());
            AnalyticsServiceManager.getInstance().pushAnalyticsEvent("login_success",eventParams);
        }
        else{
            HashMap<String,String> eventParams = new HashMap<String,String>();
            eventParams.put("username", DataRepository.getInstance().fetchUserName());
            eventParams.put("isRememberMe",String.valueOf(DataRepository.getInstance().fetchRememberMe()));
            eventParams.put("isProfile","true");
            eventParams.put("errorResponse",dataWrapper.getApiException().getErrorCode() +"");
            AnalyticsServiceManager.getInstance().pushAnalyticsEvent("login_failure",eventParams);

        }
    }


    public MutableLiveData getLivedata() {

        if (livedata == null) {
            livedata = new MutableLiveData<MutableLiveData>();
        }
        return livedata;
    }


}
