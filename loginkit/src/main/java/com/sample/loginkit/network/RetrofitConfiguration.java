package com.sample.loginkit.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.loginkit.init.RootLoginController;
import com.sample.loginkit.network.apiUtils.HeaderInterceptor;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitConfiguration {






    public static Retrofit getRetrofitInstance() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
        okBuilder.addInterceptor(headerInterceptor);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RootLoginController.customConfiguration.domainURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okBuilder.build())
                .build();

        return retrofit;

    }


}
