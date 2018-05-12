package com.insight.divyamjoshi.insight.network;

import com.insight.divyamjoshi.insight.model.GetNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GetResponseInterfaceArticle {

    @GET("articles")
    Call<GetNews> getNews(@Query("source") String source, @Query("sortBY") String sortBy, @Query("apiKey") String apiKey);


}
