package com.example.android.bakingapp;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.bakingapp.network.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecipeLoader extends AsyncTaskLoader<ArrayList<Recipe>> {
    Context context;
    String url;
    ArrayList<Recipe> recipes;

    public RecipeLoader(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {
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
        recipes = NetworkUtils.extractRecipesFromJson(jsonResponse);
        return recipes;
    }
}
