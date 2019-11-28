package com.sample.loginkit.network.apiUtils;



import com.sample.loginkit.models.Login;
import com.sample.loginkit.models.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIRequestInterface {


    @FormUrlEncoded
    @POST("api/login/v1.1")
    Call<Login> login(@Header("Authorization") String authorizationToken, @Field("grant_type") String granttype, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/login/v1.1")
    Call<Login> loginwithProfileID(@Header("Authorization") String apikey, @Field("grant_type") String granttype, @Field("username") String username, @Field("password") String password, @Field("profile_id") String profileid, @Field("profile_pin") int profilepin);

    @FormUrlEncoded
    @POST("api/login/v1.1")
    Call<Login> loginwithProfileIDwithoutPIN(@Header("Authorization") String apikey, @Field("grant_type") String granttype, @Field("username") String username, @Field("password") String password, @Field("profile_id") String profileid);


    @FormUrlEncoded
    @POST("api/login/v1.1")
    Call<Login> refreshToken(@Header("Authorization") String apikey, @Field("grant_type") String granttype, @Field("refresh_token") String refreshtoken);


    @GET("api/profile/v1.1")
    Call<List<Profile>> getUserProfiles(@Header("Authorization") String authorizationToken);
}
