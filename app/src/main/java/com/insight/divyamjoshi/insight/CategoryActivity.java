package com.insight.divyamjoshi.insight;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.insight.divyamjoshi.insight.adapter.AdapterNews;
import com.insight.divyamjoshi.insight.db.InsightContract;
import com.insight.divyamjoshi.insight.network.UtilsNetwork;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    static final String EXTRA = "extra";
    static final String ARRAY = "Array";
    @BindView(R.id.recyclerViewCategorySelection)
    RecyclerView recyclerViewCategorySelection;
    String name, sortBy, source;
    Bundle bundle;

    AdapterNews adapterNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        recyclerViewCategorySelection.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewCategorySelection.setLayoutManager(layoutManager);

        recyclerViewCategorySelection.setItemAnimator(new DefaultItemAnimator());

        adapterNews = new AdapterNews(getApplicationContext());

        if (savedInstanceState == null) {
            bundle = getIntent().getExtras();
            setAll(bundle);
            new UtilsNetwork().getNewsBySource(source, sortBy, getApplicationContext());
        } else if (savedInstanceState != null && bundle == null) {
            bundle = savedInstanceState.getBundle(EXTRA);
            setAll(bundle);
        }

        recyclerViewCategorySelection.setAdapter(adapterNews);

        if (source != null && sortBy != null) {
            Bundle bundle = new Bundle();
            bundle.putString("SOURCE", source);
            bundle.putString("SORTBY", sortBy);
            getSupportLoaderManager().initLoader(0, bundle, this);
            getSupportActionBar().setTitle(name);
        }

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(EXTRA, bundle);

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(), InsightContract.Article.CONTENT_URI, new String[]{InsightContract.Article.COLUMN_TITLE, InsightContract.Article.COLUMN_URL_TO_IMAGE, InsightContract.Article.COLUMN_URL,}, InsightContract.Article.COLUMN_SOURCE + " = ? ", new String[]{(args.getString("SOURCE"))}, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        adapterNews.swapCursor(data);
        adapterNews.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapterNews.swapCursor(null);
        adapterNews.notifyDataSetChanged();

    }

    public void setAll(Bundle bundle) {
        name = bundle.getString(InsightContract.Category.COLUMN_SOURCE_NAME);
        sortBy = bundle.getString(InsightContract.Category.COLUMN_SORT_BYS_AVAILABLE);
        source = bundle.getString(InsightContract.Category.COLUMN_ARTICLE_ID);
    }
}
