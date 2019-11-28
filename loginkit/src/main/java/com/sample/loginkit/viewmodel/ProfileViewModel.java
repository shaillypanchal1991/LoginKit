package com.sample.loginkit.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.loginkit.analytics.AnalyticsServiceManager;
import com.sample.loginkit.interactor.ProfileInteractor;
import com.sample.loginkit.interactor.RefreshTokenInteractor;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.DataWrapper;
import com.sample.loginkit.network.generic.GenericRefreshTokenHandler;
import com.sample.loginkit.network.generic.GenericRequestHandler;

import java.util.HashMap;

public class ProfileViewModel extends ViewModel implements GenericRequestHandler.IResponseStatus, GenericRefreshTokenHandler.ITokenResponseStatus {
    public MutableLiveData livedata;

    public void getProfilesOfUser() {

        if (DataRepository.getInstance().fetchUserSessionDetails() != null) {
            String token = DataRepository.getInstance().getInstance().fetchUserSessionDetails().getAccessToken();

            ProfileInteractor.createInstance("bearer " + token).onProfilesRequest(ProfileViewModel.this,DataRepository.getInstance().getApiRequest());

        }

    }


    @Override
    public void onResponseReceived(DataWrapper liveData) {
        if (liveData.getData() != null && liveData.getApiException() == null) {


            livedata.setValue(liveData);
            DataRepository.getInstance().cacheProfileDetails(liveData);

        } else if (liveData.getApiException() != null) {
            if (liveData.getApiException().getErrorCode() == 401) {
                refreshToken();
            }
        }
        livedata.setValue(liveData);
    }

    private void refreshToken() {
        try {
            Login login = DataRepository.getInstance().fetchUserSessionDetails();
            String expiredtoken = login.getRefreshToken();

            RefreshTokenInteractor.createInstance(expiredtoken, "refresh_token").onRefreshTokenRequest(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MutableLiveData getLivedata() {

        if (livedata == null) {
            livedata = new MutableLiveData<MutableLiveData>();
        }
        return livedata;
    }

    @Override
    public void refreshTokenResponse(DataWrapper liveData) {
        if (liveData.getData() != null && liveData.getApiException() == null) {


            HashMap<String,String> eventParams = new HashMap<String,String>();
            eventParams.put("username", DataRepository.getInstance().fetchUserName());
            AnalyticsServiceManager.getInstance().pushAnalyticsEvent("refresh_session_success",eventParams);

            DataRepository.getInstance().storeUserSessionDetails((Login) liveData.getData());
            getProfilesOfUser();
        }
        else{
            HashMap<String,String> eventParams = new HashMap<String,String>();
            eventParams.put("username", DataRepository.getInstance().fetchUserName());
            eventParams.put("error", liveData.getApiException().getErrorMessage(liveData.getApiException().getErrorCode()));

            AnalyticsServiceManager.getInstance().pushAnalyticsEvent("refresh_session_failure",eventParams);

        }


    }
}
