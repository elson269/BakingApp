package com.example.android.bakingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipeAdapter.OnItemClickListener{

    public static final String LOG_TAG = RecipesActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 1;
    private static final String RECIPE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecipesRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    ArrayList<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mRecipesRecyclerView = (RecyclerView) findViewById(R.id.recipesRecyclerView);
        mRecipeAdapter = new RecipeAdapter(this, new ArrayList<Recipe>(), this);
        mRecipesRecyclerView.setAdapter(mRecipeAdapter);

        getLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecipesRecyclerView.setLayoutManager(mLinearLayoutManager);

    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle bundle) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            return new RecipeLoader(this, RECIPE_URL);
        } else {
            Toast.makeText(RecipesActivity.this, getString(R.string.network_connection_error_message),
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        mRecipeAdapter.clearRecipes();
        if (recipes != null && !recipes.isEmpty()) {
            mRecipeAdapter.addRecipes(recipes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {
        mRecipeAdapter.clearRecipes();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent recipeDetailsIntent = new Intent(this, RecipeDetailsActivity.class);
        recipeDetailsIntent.putExtra("recipeId", mRecipes.get(position).getRecipeId());
        startActivity(recipeDetailsIntent);
    }
}
