package com.insight.divyamjoshi.insight.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class InsightProvider extends ContentProvider {

    private static final int ARTICLES = 100;
    private static final int CATEGORY = 200;
    private static final int ARTICLES_WITH_ID = 300;
    private static final int CATEGORY_WITH_ID = 400;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder mSQLiteQueryBuilder = new SQLiteQueryBuilder();
    private InsightDb mDbHelper;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = InsightContract.AUTHORITY;

        uriMatcher.addURI(authority, InsightContract.PATH_ARTICLE, ARTICLES);
        uriMatcher.addURI(authority, InsightContract.PATH_CATEGORY, CATEGORY);
        uriMatcher.addURI(authority, InsightContract.PATH_ARTICLE + "/#", ARTICLES_WITH_ID);
        uriMatcher.addURI(authority, InsightContract.PATH_CATEGORY + "/#", CATEGORY_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new InsightDb(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ARTICLES: {
                cursor = mDbHelper.getReadableDatabase().query(InsightContract.Article.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }

            case ARTICLES_WITH_ID: {
                String id = InsightContract.Article.getArticlesId(uri);

                selection = InsightContract.Article.COLUMN_ID + " = ?";

                selectionArgs = new String[]{id};

                cursor = mDbHelper.getReadableDatabase().query(InsightContract.Article.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }

            case CATEGORY: {
                cursor = mDbHelper.getReadableDatabase().query(InsightContract.Category.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder

                );
                break;
            }

            case CATEGORY_WITH_ID: {

                String id = InsightContract.Category.getCategoryId(uri);
                selection = InsightContract.Category.COLUMN_ID + " = ?";
                selectionArgs = new String[]{id};
                cursor = mDbHelper.getReadableDatabase().query(InsightContract.Category.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown Uri :" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    /**
     * inserting only in bulk so no need.
     * *
     *
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }


    /**
     * delete is used every time user change the category.
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int deleteRow;

        switch (match) {
            case ARTICLES: {
                deleteRow = db.delete(InsightContract.Article.TABLE_NAME, selection, selectionArgs);
            }
            break;
            case CATEGORY: {
                deleteRow = db.delete(InsightContract.Category.TABLE_NAME, selection, selectionArgs);
            }
            break;

            default:
                throw new UnsupportedOperationException("UnSupported Operation " + uri);
        }

        if (deleteRow != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteRow;
    }

    /**
     * not using update any where
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        String rawQuery = "DELETE  FROM " + InsightContract.Article.TABLE_NAME;

        Cursor cursor;

        int returnCount = 0;

        switch (match) {
            case ARTICLES: {
                db.beginTransaction();
                try {
                    db.rawQuery(rawQuery, null);
                    for (ContentValues value : values) {
                        long _id = db.insert(InsightContract.Article.TABLE_NAME, null, value);

                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            case CATEGORY: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(InsightContract.Category.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            default:
                return super.bulkInsert(uri, values);

        }


    }
}


