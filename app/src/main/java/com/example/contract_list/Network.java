package com.example.contract_list;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static HttpLoggingInterceptor httpLoggingInterceptor= new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    public static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://parichit.co/").
                /****** convertor factory is responsible for converting  json to pojo and vice-versa********/
                        addConverterFactory(GsonConverterFactory.create())
                /*** client is responsible for observing logcat****/
                .client(new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
                .build();
        return  retrofit;
    }

}
