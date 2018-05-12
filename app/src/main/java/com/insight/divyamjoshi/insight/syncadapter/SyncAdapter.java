package com.insight.divyamjoshi.insight.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.insight.divyamjoshi.insight.db.InsightContract;
import com.insight.divyamjoshi.insight.model.ArticlesNews;
import com.insight.divyamjoshi.insight.model.GetNews;
import com.insight.divyamjoshi.insight.network.GetResponseInterfaceArticle;
import com.insight.divyamjoshi.insight.network.NewsApiCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = "SYncAdapter";

    private static final String APIKEY = "fb9c93887dd84a74aee92261b48c9ff1";

    private final ContentResolver mContentResolver;

    Call<GetNews> callArticles;


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {


        GetResponseInterfaceArticle getResponseInterfaceArticle = NewsApiCall.getRetrofit().create(GetResponseInterfaceArticle.class);

        callArticles = getResponseInterfaceArticle.getNews("google-news", "top", APIKEY);

        callArticles.enqueue(new Callback<GetNews>() {
            @Override
            public void onResponse(Call<GetNews> call, Response<GetNews> response) {

                GetNews getNews = response.body();

                String status = getNews.getStatus();

                Log.d("Status", status);

                List<ArticlesNews> articlesNewses = response.body().getArticles();

                List<ContentValues> contentValuesList = new ArrayList<ContentValues>();

                for (ArticlesNews news : articlesNewses) {
                    ContentValues values = new ContentValues();
                    values.put(InsightContract.Article.COLUMN_SOURCE, getNews.getSource());
                    values.put(InsightContract.Article.COLUMN_TITLE, news.getTitle());
                    values.put(InsightContract.Article.COLUMN_DESCRIPTION, news.getDescription());
                    values.put(InsightContract.Article.COLUMN_URL, news.getUrl());
                    values.put(InsightContract.Article.COLUMN_URL_TO_IMAGE, news.getUrlToImage());
                    values.put(InsightContract.Article.COLUMN_PUBLISHED_AT, news.getPublishedAt());

                    contentValuesList.add(values);
                }

                ContentValues[] news = new ContentValues[contentValuesList.size()];

                contentValuesList.toArray(news);

                if (!news.equals(null)) {
                    mContentResolver.delete(InsightContract.Article.CONTENT_URI, null, null);
                    mContentResolver.bulkInsert(InsightContract.Article.CONTENT_URI, news);
                }
            }

            @Override
            public void onFailure(Call<GetNews> call, Throwable t) {

            }
        });


    }
}
