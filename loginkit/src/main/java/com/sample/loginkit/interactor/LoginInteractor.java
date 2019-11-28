package com.sample.loginkit.interactor;

import com.sample.loginkit.models.Login;
import com.sample.loginkit.network.apiUtils.APIRequestInterface;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.GenericRequestHandler;
import com.sample.loginkit.utils.StringConstants;

import retrofit2.Call;

public class LoginInteractor extends GenericRequestHandler<Login> {
    private APIRequestInterface apiRequestInterface;

    private String token, granttype, username, password;

    public static LoginInteractor createInstance(String token, String granttype, String username, String password)
    {
        LoginInteractor loginInteractor = new LoginInteractor();
        loginInteractor.token = token;
        loginInteractor.granttype = granttype;
        loginInteractor.username = username;
        loginInteractor.password = password;

        return loginInteractor;
    }

    public void onAuthRequest(GenericRequestHandler.IResponseStatus status, APIRequestInterface apirequestinterface) {
        apiRequestInterface = apirequestinterface;
        doRequest(status);
    }

    @Override
    protected Call<Login> makeRequest() {
        return apiRequestInterface.login(StringConstants.BASIC_PREFIX+ token, granttype, username, password);
    }
}
