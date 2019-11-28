package com.sample.loginkit.interactor;

import com.sample.loginkit.BuildConfig;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.network.apiUtils.APIRequestInterface;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.generic.GenericRefreshTokenHandler;
import com.sample.loginkit.network.generic.GenericRequestHandler;
import com.sample.loginkit.utils.StringConstants;


import retrofit2.Call;

public class RefreshTokenInteractor extends GenericRefreshTokenHandler<Login> {

    private APIRequestInterface refreshTokenService = DataRepository.getInstance().getApiRequest();
    private String refreshToken, granttype;

    public static RefreshTokenInteractor createInstance(String token, String granttype) {

        RefreshTokenInteractor refreshTokenInteractor = new RefreshTokenInteractor();
        refreshTokenInteractor.refreshToken = token;
        refreshTokenInteractor.granttype = granttype;

        return refreshTokenInteractor;
    }

    public void onRefreshTokenRequest(GenericRefreshTokenHandler.ITokenResponseStatus status) {

        doRequest(status);
    }


    @Override
    protected Call<Login> makeRequest() {
        return refreshTokenService.refreshToken(StringConstants.BASIC_PREFIX + BuildConfig.APIKEY, granttype, refreshToken);
    }
}
