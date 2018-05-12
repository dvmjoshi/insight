package com.insight.divyamjoshi.insight;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insight.divyamjoshi.insight.adapter.AdapterNews;
import com.insight.divyamjoshi.insight.db.InsightContract;
import com.insight.divyamjoshi.insight.syncadapter.SyncUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.recyclerViewNews)
    RecyclerView recyclerViewNews;

    private AdapterNews mAdapterNews;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        recyclerViewNews.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewNews.setLayoutManager(mLayoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());

        mAdapterNews = new AdapterNews(getContext());

        recyclerViewNews.setAdapter(mAdapterNews);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SyncUtils.CreateSyncAccount(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = InsightContract.Article.CONTENT_URI;

        return new CursorLoader(getActivity(), uri, new String[]{InsightContract.Article.COLUMN_ID, InsightContract.Article.COLUMN_TITLE, InsightContract.Article.COLUMN_URL, InsightContract.Article.COLUMN_URL_TO_IMAGE}, InsightContract.Article.COLUMN_SOURCE + " = ? ", new String[]{"google-news"}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null) {
            mAdapterNews.swapCursor(data);
            Log.d("Cursor COUNT ADAPTER", Integer.toString(data.getCount()));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapterNews.swapCursor(null);

    }
}
