package com.insight.divyamjoshi.insight.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class InsightDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "insight.db";

    private static final int DATA_BASE_VERSION = 5;


    public InsightDb(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE_ARTICLES = " CREATE TABLE " + InsightContract.Article.TABLE_NAME + " ( " + InsightContract.Article.COLUMN_ID + " TEXT PRIMARY KEY, " + InsightContract.Article.COLUMN_SOURCE + " TEXT , " + InsightContract.Article.COLUMN_TITLE + " TEXT NOT NULL, " + InsightContract.Article.COLUMN_DESCRIPTION + " TEXT NOT NULL, " + InsightContract.Article.COLUMN_URL + " TEXT NOT NULL, " + InsightContract.Article.COLUMN_URL_TO_IMAGE + " TEXT NOT NULL, " + InsightContract.Article.COLUMN_PUBLISHED_AT + " TEXT " + " );";

        Log.i("ARTICLE TABLE", CREATE_TABLE_ARTICLES);

        final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + InsightContract.Category.TABLE_NAME + " ( " + InsightContract.Category.COLUMN_ID + " TEXT PRIMARY KEY, " + InsightContract.Category.COLUMN_CATEGORY + " TEXT NOT NULL, " + InsightContract.Category.COLUMN_ARTICLE_ID + " TEXT NOT NULL, " + InsightContract.Category.COLUMN_SOURCE_NAME + " TEXT NOT NULL, " + InsightContract.Category.COLUMN_SOURCE_URL + " TEXT NOT NULL, " + InsightContract.Category.COLUMN_SORT_BYS_AVAILABLE + " TEXT NOT NULL " + " );";

        Log.i("CATEGORY TABLE", CREATE_TABLE_CATEGORY);


        db.execSQL(CREATE_TABLE_ARTICLES);
        db.execSQL(CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + InsightContract.Article.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InsightContract.Category.TABLE_NAME);
        onCreate(db);
    }
}
