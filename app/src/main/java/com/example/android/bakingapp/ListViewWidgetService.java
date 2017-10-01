package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.network.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.bakingapp.IngredientsWidgetProvider.EXTRA_RECIPE_ID;


public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(getApplicationContext());
    }
}

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String LOG_TAG = ListRemoteViewFactory.class.getSimpleName();
    private static final String RECIPE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Context mContext;
    private final int DEFAULT_VALUE = 1;
    private int mRecipeId;
    private SharedPreferences mSharedPref;
    private ArrayList<Ingredient> mIngredients;

    public ListRemoteViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {

            URL recipesUrl = NetworkUtils.createUrl(RECIPE_URL);
            String jsonResponse = " ";
            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(recipesUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mSharedPref = mContext.getSharedPreferences("recipeDetails", Context.MODE_PRIVATE);
            mRecipeId = mSharedPref.getInt("recipeDetailId", DEFAULT_VALUE);

            mIngredients = NetworkUtils.extractIngredientsFromJson(jsonResponse, mRecipeId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, IngredientsWidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        }
    }

    @Override
    public void onDestroy() {
        if (mIngredients != null)
            mIngredients.clear();
    }

    @Override
    public int getCount() {

        if (mIngredients == null) {
            return 0;
        } else {
            return mIngredients.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);
        if (mIngredients != null && mIngredients.size() > 0) {
            remoteViews.setTextViewText(R.id.ingredientTextView, mIngredients.get(position).getIngredient());
            remoteViews.setTextViewText(R.id.quantityTextView, String.valueOf(mIngredients.get(position).getQuantity()));
            remoteViews.setTextViewText(R.id.measureTextView, mIngredients.get(position).getMeasure());
        }
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_RECIPE_ID, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.ingredient_linear_layout, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
