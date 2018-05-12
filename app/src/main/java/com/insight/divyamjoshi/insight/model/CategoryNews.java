package com.insight.divyamjoshi.insight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CategoryNews {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("sources")
    @Expose
    public List<SourceNews> sources = null;
}
