package com.example.android.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.bakingapp.network.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class IngredientLoader extends AsyncTaskLoader<ArrayList<Ingredient>> {
    Context context;
    String url;
    int id;
    ArrayList<Ingredient> mIngredients;

    public IngredientLoader(Context context, String url, int id) {
        super(context);
        this.context = context;
        this.url = url;
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Ingredient> loadInBackground() {
        URL recipesUrl = NetworkUtils.createUrl(url);
        String jsonResponse = " ";
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(recipesUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (url == null) {
            return null;
        }
        mIngredients = NetworkUtils.extractIngredientsFromJson(jsonResponse, id);
        return mIngredients;
    }
}
