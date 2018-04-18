package com.example.hp.echoapp.merchant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 12/7/2017.
 */

public class AppConfig {
    private static String BASE_URL = "http://media3.co.in/backend/";
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
