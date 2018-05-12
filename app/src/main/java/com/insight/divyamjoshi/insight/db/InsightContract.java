package com.insight.divyamjoshi.insight.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class InsightContract {

    public static final String AUTHORITY = "com.insight.divyamjoshi.insight";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_ARTICLE = "article";

    public static final String PATH_CATEGORY = "category";

    public static final class Article implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ARTICLE;

        public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ARTICLE;

        public static final String TABLE_NAME = "article";

        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_SOURCE = "source";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_URL = "url";

        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";

        public static final String COLUMN_PUBLISHED_AT = "publishedAt";

        public static Uri buildArticleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final Uri buildArticleId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String getArticlesId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class Category implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_CATEGORY;

        public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_CATEGORY;

        public static final String TABLE_NAME = "category";

        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_CATEGORY = "category";

        public static final String COLUMN_ARTICLE_ID = "article_id";

        public static final String COLUMN_SOURCE_NAME = "source_name";

        public static final String COLUMN_SOURCE_URL = "url";

        public static final String COLUMN_SORT_BYS_AVAILABLE = "sort_bys_available";

        public static Uri buildCategory(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final Uri buildCategoryId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static final String getCategoryId(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }


}