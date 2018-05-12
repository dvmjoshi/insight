package com.insight.divyamjoshi.insight;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.insight.divyamjoshi.insight.adapter.AdaptorSource;
import com.insight.divyamjoshi.insight.db.InsightContract;
import com.insight.divyamjoshi.insight.network.UtilsNetwork;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfSource extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.recyclerViewSources)
    RecyclerView recyclerViewSources;
    String categoryString = null;

    AdaptorSource mAdapterSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_source);
        ButterKnife.bind(this);

        //getting all the category form news api


        mAdapterSource = new AdaptorSource(getApplicationContext());
        recyclerViewSources.setHasFixedSize(true);

        recyclerViewSources.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManger = new GridLayoutManager(getApplicationContext(), 2);

        recyclerViewSources.setLayoutManager(mLayoutManger);

        categoryString = " null ";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String bundleString = bundle.getString("LIST");
            if (bundleString != null) {
                categoryString = UtilsNetwork.category.get(bundleString);
                getSupportLoaderManager().initLoader(4, null, this);
                Log.i("bundle String", bundleString + categoryString);
            }
        }
        recyclerViewSources.setAdapter(mAdapterSource);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String whereCondition = InsightContract.Category.COLUMN_CATEGORY + " = ? ";

        return new CursorLoader(getApplicationContext(), InsightContract.Category.CONTENT_URI, new String[]{InsightContract.Category.COLUMN_ID, InsightContract.Category.COLUMN_CATEGORY, InsightContract.Category.COLUMN_ARTICLE_ID, InsightContract.Category.COLUMN_SOURCE_NAME, InsightContract.Category.COLUMN_SOURCE_URL, InsightContract.Category.COLUMN_SORT_BYS_AVAILABLE}, whereCondition, new String[]{categoryString}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            mAdapterSource.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapterSource.swapCursor(null);
    }
}
