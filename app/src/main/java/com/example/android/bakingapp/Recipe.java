package com.example.android.bakingapp;

public class Recipe {

    private int mRecipeId;
    private String mRecipeName;

    public Recipe(int id, String recipeName) {
        mRecipeId = id;
        mRecipeName = recipeName;
    }

    public int getRecipeId () {
        return mRecipeId;
    }

    public String getRecipeName () {
        return mRecipeName;
    }
}