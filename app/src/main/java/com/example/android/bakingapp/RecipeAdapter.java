package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mRecipes;
    private OnItemClickListener mListener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes, OnItemClickListener listener) {
        mContext = context;
        mRecipes = recipes;
        mListener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout itemView = (FrameLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_item, parent, false);
        final RecipeViewHolder recipeViewHolder = new RecipeViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, recipeViewHolder.getAdapterPosition());
            }
        });
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.recipeNameTextView.setText(mRecipes.get(position).getRecipeName());
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        } else {
            return 0;
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView)itemView.findViewById(R.id.recipeNameTextView);
        }
    }

    //This helper method clears all the items in the adapter.
    public void clearRecipes() {
        int size = mRecipes.size();
        mRecipes.clear();
        notifyItemRangeRemoved(0, size);
    }

    //This helper method adds all the items to the adapter.
    public void addRecipes(ArrayList<Recipe> recipes) {
        mRecipes.addAll(recipes);
        notifyItemRangeInserted(0, recipes.size() - 1);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}

