package com.insight.divyamjoshi.insight.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsApiCall {
    public static final String BASE_URL = "https://newsapi.org/v1/";


    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    private static Retrofit retrofitArticle = null;
    private static Retrofit retrofitCategory = null;

    public static Retrofit getRetrofit() {
        if (retrofitArticle == null) {
            retrofitArticle = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }

        return retrofitArticle;
    }


}
