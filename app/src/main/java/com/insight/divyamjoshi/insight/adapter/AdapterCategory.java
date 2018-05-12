package com.insight.divyamjoshi.insight.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.insight.divyamjoshi.insight.ListOfSource;
import com.insight.divyamjoshi.insight.R;
import com.insight.divyamjoshi.insight.network.UtilsNetwork;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    List<String> category = new ArrayList<>();
    FirebaseAnalytics mFirebaseAnalytics;
    private Context mContext;


    public AdapterCategory(ArrayList<String> category, Context context) {
        this.category = category;
        mContext = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewCategory.setText(category.get(position));

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.textViewCategory)
        TextView textViewCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int possition = getAdapterPosition();
            String categoryString = category.get(possition);
            //firebase analytics
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, categoryString);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Category selected");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            Intent intent = new Intent(mContext, ListOfSource.class);
            intent.putExtra("LIST", categoryString);

            if (new UtilsNetwork().checkInternet(mContext)) {
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, R.string.internet_msg, Toast.LENGTH_LONG).show();
            }

        }
    }


}
