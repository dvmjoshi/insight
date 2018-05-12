package com.insight.divyamjoshi.insight;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.insight.divyamjoshi.insight.adapter.AdapterCategory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryFragment extends Fragment {

    AdapterCategory mAdapter;

    @BindView(R.id.recyclerViewCategory)
    RecyclerView recyclerViewCategory;

    @BindView(R.id.adView)
    AdView mAdView;

    ArrayList<String> category;
    ArrayList<Integer> category1;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* category = new ArrayList<>();
        category.add(R.drawable.album1);
        category.add(R.drawable.album2);
        category.add(R.drawable.album3);
        category.add(R.drawable.album4);
        category.add(R.drawable.album5);
        category.add(R.drawable.album6);
        category.add(R.drawable.album7);
        category.add(R.drawable.album8);
        category.add(R.drawable.album9);*/

        category = new ArrayList<>();
        category.add("business");
        category.add("entertainment");
        category.add("gaming");
        category.add("general");
        category.add("music");
        category.add("politics");
        category.add("science and nature");
        category.add("sport");
        category.add("technology");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        Configuration configuration = getResources().getConfiguration();

        MobileAds.initialize(getContext(), "ca-app-pub-4276208530377668/9442445565");


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdapter = new AdapterCategory(category, getContext());
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewCategory.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setLayoutManager(layoutManager);


        recyclerViewCategory.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
