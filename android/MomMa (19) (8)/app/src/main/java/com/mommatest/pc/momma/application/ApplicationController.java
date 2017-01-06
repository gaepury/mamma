package com.mommatest.pc.momma.application;

import android.app.Application;


import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApplicationController extends Application {

    private static ApplicationController instance;

    private static String baseUrl = "http://52.78.46.225:3000";
    private  final int PORT = 3000;

    private NetworkService networkService;

    public static ApplicationController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
        buildService();
    }



    public void buildService() {


        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
}
