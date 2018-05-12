package com.insight.divyamjoshi.insight.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.insight.divyamjoshi.insight.R;
import com.insight.divyamjoshi.insight.db.InsightContract;


public class NewsWidgetRemoteViewService extends RemoteViewsService {

    //indices --> projection
    static final int INDEX_ID = 0;
    static final int INDEX_TITLE = 1;
    static final int INDEX_URL = 2;
    private static final String[] NEWS_COLUMNS = {InsightContract.Article.COLUMN_ID, InsightContract.Article.COLUMN_TITLE, InsightContract.Article.COLUMN_URL,

    };

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                data = getContentResolver().query(InsightContract.Article.CONTENT_URI, NEWS_COLUMNS, InsightContract.Article.COLUMN_SOURCE + " = ? ", new String[]{"google-news"}, null);

            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }

            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_items);

                String headline = data.getString(INDEX_TITLE);

                views.setTextViewText(R.id.textViewWidget, headline);

                final Intent intent = new Intent();

                return views;

            }

            @Override
            public RemoteViews getLoadingView() {

                return new RemoteViews(getPackageName(), R.layout.widget_items);

            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position)) return position;
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
