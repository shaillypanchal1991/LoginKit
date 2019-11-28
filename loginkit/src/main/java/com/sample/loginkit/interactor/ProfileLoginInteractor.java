package com.sample.loginkit.interactor;


import com.sample.loginkit.BuildConfig;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.network.apiUtils.APIRequestInterface;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.GenericRequestHandler;
import com.sample.loginkit.utils.StringConstants;

import retrofit2.Call;

public class ProfileLoginInteractor extends GenericRequestHandler<Login> {

    private APIRequestInterface apiRequestInterface;
    private String granttype, username, password, profileid;
    int profilepin;

    public static ProfileLoginInteractor createInstance(String granttype, String username, String password, String profileid, int profilepin) {
        ProfileLoginInteractor profileLoginInteractor = new ProfileLoginInteractor();

        profileLoginInteractor.granttype = granttype;
        profileLoginInteractor.username = username;
        profileLoginInteractor.password = password;
        profileLoginInteractor.profileid = profileid;
        profileLoginInteractor.profilepin = profilepin;
        return profileLoginInteractor;
    }

    public static ProfileLoginInteractor createInstance(String granttype, String username, String password, String profileid) {
        ProfileLoginInteractor profileLoginInteractor = new ProfileLoginInteractor();

        profileLoginInteractor.granttype = granttype;
        profileLoginInteractor.username = username;
        profileLoginInteractor.password = password;
        profileLoginInteractor.profileid = profileid;

        return profileLoginInteractor;
    }

    public void onProfileLoginRequest(IResponseStatus status, APIRequestInterface apirequestinterface) {
        apiRequestInterface = apirequestinterface;
        doRequest(status);
    }

    @Override
    protected Call<Login> makeRequest() {
        if (profilepin != 0) {
            return apiRequestInterface.loginwithProfileID(StringConstants.BASIC_PREFIX + BuildConfig.APIKEY, granttype, username, password, profileid, profilepin);
        } else {
            return apiRequestInterface.loginwithProfileIDwithoutPIN(StringConstants.BASIC_PREFIX + BuildConfig.APIKEY, granttype, username, password, profileid);
        }
    }
}



