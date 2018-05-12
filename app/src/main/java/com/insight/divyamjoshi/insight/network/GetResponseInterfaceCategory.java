package com.insight.divyamjoshi.insight.network;

import com.insight.divyamjoshi.insight.model.CategoryNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GetResponseInterfaceCategory {

    @GET("sources")
    Call<CategoryNews> getCategory(@Query("category") String category);

}
