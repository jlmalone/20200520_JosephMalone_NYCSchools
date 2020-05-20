package com.jpmorgan.nycschools.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiManager {

    private static ApiManager instance;

    private SchoolService service;

    private ApiManager()
    {
       service =  new Retrofit.Builder()
                .baseUrl("https://data.cityofnewyork.us/resource/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SchoolService.class);
    }

    public static ApiManager getInstance()
    {
        //could use double locking
        if(instance == null)
        {
            instance = new ApiManager();
        }
        return instance;
    }

    public SchoolService getService()
    {
        return service;
    }
}
