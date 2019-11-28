package com.sample.loginkit.interactor;

import com.sample.loginkit.models.Profile;
import com.sample.loginkit.network.apiUtils.APIRequestInterface;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.GenericRequestHandler;

import java.util.List;

import retrofit2.Call;

public class ProfileInteractor extends GenericRequestHandler<List<Profile>> {



    private String authorizationToken;
    private APIRequestInterface apiRequestInterface;

    public static ProfileInteractor createInstance(String token) {
        ProfileInteractor profilesInteractor = new ProfileInteractor();
        profilesInteractor.authorizationToken = token;

        return profilesInteractor;
    }

    public void onProfilesRequest(IResponseStatus status, APIRequestInterface apirequestinterface) {
        apiRequestInterface = apirequestinterface;
        doRequest(status);

    }

    @Override
    protected Call<List<Profile>> makeRequest() {
        return apiRequestInterface.getUserProfiles(authorizationToken);
    }


}