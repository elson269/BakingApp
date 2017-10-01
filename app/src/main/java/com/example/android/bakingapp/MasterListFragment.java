package com.example.android.bakingapp;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MasterListFragment extends Fragment implements LoaderManager.LoaderCallbacks{

    private OnListItemClickListener mListener;

    private static final String RECIPE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int INGREDIENT_LOADER_ID = 1;
    private static final int STEP_LOADER_ID = 2;
    private Intent mIntentFromRecipesActivity;
    private int mRecipeId;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private RecyclerView mIngredientsRecyclerView, mStepRecyclerView;
    private LinearLayoutManager mIngredientLayoutManager, mStepLayoutManager;

    public MasterListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container,false);

        mIntentFromRecipesActivity = getActivity().getIntent();
        if (mIntentFromRecipesActivity.hasExtra("recipeId")) {
            mRecipeId = mIntentFromRecipesActivity.getIntExtra("recipeId", -2);

            SharedPreferences sharedPref = getActivity().getSharedPreferences("recipeDetails",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("recipeDetailId", mRecipeId);
            editor.apply();

            mIngredientsRecyclerView = (RecyclerView)rootView.findViewById(R.id.ingredientsRecyclerView);
            mStepRecyclerView = (RecyclerView)rootView.findViewById(R.id.stepsRecyclerView);

            mIngredientAdapter = new IngredientAdapter(getActivity(), new ArrayList<Ingredient>());
            mStepAdapter = new StepAdapter(getActivity(), new ArrayList<Step>(),
                    new StepAdapter.OnStepDescriptionClickListener() {
                @Override
                public void onDescriptionItemClick(View view, int position) {
                    mListener.onStepItemClick(position, mSteps);
                }
            });

            mIngredientsRecyclerView.setAdapter(mIngredientAdapter);
            mStepRecyclerView.setAdapter(mStepAdapter);

            mIngredientLayoutManager = new LinearLayoutManager(getActivity());
            mStepLayoutManager = new LinearLayoutManager(getActivity());

            mIngredientsRecyclerView.setLayoutManager(mIngredientLayoutManager);
            mStepRecyclerView.setLayoutManager(mStepLayoutManager);

            getActivity().getLoaderManager().initLoader(INGREDIENT_LOADER_ID, null, this);
            getActivity().getLoaderManager().initLoader(STEP_LOADER_ID, null, this);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListItemClickListener) {
            mListener = (OnListItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListItemClickListener");
        }
    }

    public interface OnListItemClickListener {
        void onStepItemClick(int position, ArrayList<Step> steps);
    }

    @Override
    public android.content.Loader onCreateLoader(int id, Bundle bundle) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            switch (id) {
                case INGREDIENT_LOADER_ID:
                    return new IngredientLoader(getActivity(), RECIPE_URL, mRecipeId);
                case STEP_LOADER_ID:
                    return new StepLoader(getActivity(), RECIPE_URL, mRecipeId);
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.network_connection_error_message),
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader loader, Object data) {
        int loaderId = loader.getId();
        switch (loaderId) {
            case INGREDIENT_LOADER_ID:
                try {
                    if (data instanceof ArrayList<?>) {
                        if (((ArrayList<?>) data).get(0) instanceof Ingredient) {
                            mIngredients = (ArrayList<Ingredient>) data;
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                mIngredientAdapter.clearIngredients();
                if (mIngredients != null && !mIngredients.isEmpty()) {
                    mIngredientAdapter.addIngredients(mIngredients);
                }
                break;

            case STEP_LOADER_ID:
                try {
                    if (data instanceof ArrayList<?>) {
                        if (((ArrayList<?>) data).get(0) instanceof Step) {
                            mSteps = (ArrayList<Step>) data;
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                mStepAdapter.clearSteps();
                if (mSteps != null && !mSteps.isEmpty()) {
                    mStepAdapter.addSteps(mSteps);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader loader) {
        mIngredientAdapter.clearIngredients();
        mStepAdapter.clearSteps();
    }



//    @Override
//    public void onLoadFinished(Loader loader, Object data) {
//        int loaderId = loader.getId();
//        switch (loaderId) {
//            case INGREDIENT_LOADER_ID:
//                try {
//                    if (data instanceof ArrayList<?>) {
//                        if (((ArrayList<?>) data).get(0) instanceof Ingredient) {
//                            mIngredients = (ArrayList<Ingredient>) data;
//                        }
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//                mIngredientAdapter.clearIngredients();
//                if (mIngredients != null && !mIngredients.isEmpty()) {
//                    mIngredientAdapter.addIngredients(mIngredients);
//                }
//                break;
//
//            case STEP_LOADER_ID:
//                try {
//                    if (data instanceof ArrayList<?>) {
//                        if (((ArrayList<?>) data).get(0) instanceof Step) {
//                            mSteps = (ArrayList<Step>) data;
//                        }
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//                mStepAdapter.clearSteps();
//                if (mSteps != null && !mSteps.isEmpty()) {
//                    mStepAdapter.addSteps(mSteps);
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader loader) {
//        mIngredientAdapter.clearIngredients();
//        mStepAdapter.clearSteps();
//    }
}

