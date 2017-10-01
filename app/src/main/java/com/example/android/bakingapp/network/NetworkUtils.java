package com.example.android.bakingapp.network;


import android.util.Log;

import com.example.android.bakingapp.Ingredient;
import com.example.android.bakingapp.Recipe;
import com.example.android.bakingapp.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.android.bakingapp.RecipesActivity.LOG_TAG;

public class NetworkUtils {

    public static ArrayList<Recipe> extractRecipesFromJson(String jsonRequestResults) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try{
            JSONArray rootJsonResponseArray = new JSONArray(jsonRequestResults);
            for (int i = 0; i < rootJsonResponseArray.length(); i++) {
                JSONObject recipeObject = rootJsonResponseArray.getJSONObject(i);
                int id = recipeObject.getInt("id");
                String name = recipeObject.getString("name");
                recipes.add(new Recipe(id, name));
            }
            return recipes;
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the recipe JSON results", e);
        }
        return null;
    }

    public static ArrayList<Ingredient> extractIngredientsFromJson(String jsonRequestResults, int recipeId) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try{
            JSONArray rootJsonResponseArray = new JSONArray(jsonRequestResults);
            JSONObject recipeDetailsObject = rootJsonResponseArray.getJSONObject(recipeId - 1);
            JSONArray ingredientsArray = recipeDetailsObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                JSONObject ingredientObject = ingredientsArray.getJSONObject(i);
                double quantity = ingredientObject.getDouble("quantity");
                String measure = ingredientObject.getString("measure");
                String ingredient = ingredientObject.getString("ingredient");
                ingredients.add(new Ingredient(quantity, measure, ingredient));
            }
            return ingredients;
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the ingredients JSON results", e);
        }
        return null;
    }

    public static ArrayList<Step> extractStepsFromJson (String jsonRequestResults, int recipeId) {
        ArrayList<Step> steps = new ArrayList<>();
        try{
            JSONArray rootJsonResponseArray = new JSONArray(jsonRequestResults);
            JSONObject recipeDetailsObject = rootJsonResponseArray.getJSONObject(recipeId - 1);
            JSONArray stepsArray = recipeDetailsObject.getJSONArray("steps");
            for (int i = 0; i < stepsArray.length(); i++) {
                JSONObject stepObject = stepsArray.getJSONObject(i);
                String shortDescription = stepObject.getString("shortDescription");
                String description = stepObject.getString("description");
                String videoUrl = stepObject.getString("videoURL");
                steps.add(new Step(shortDescription, description, videoUrl));
            }
            return steps;
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the steps JSON results", e);
        }
        return null;
    }

    public static URL createUrl(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
