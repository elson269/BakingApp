package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mIngredients;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredient_item, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(itemView);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.mIngredientTextView.setText(mIngredients.get(position).getIngredient());
        holder.mQuantity.setText(String.valueOf(mIngredients.get(position).getQuantity()));
        holder.mMeasure.setText(mIngredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        } else {
            return 0;
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView mIngredientTextView, mQuantity, mMeasure;
        public IngredientViewHolder(View itemView) {
            super(itemView);
            mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredientTextView);
            mQuantity = (TextView) itemView.findViewById(R.id.quantityTextView);
            mMeasure = (TextView) itemView.findViewById(R.id.measureTextView);
        }
    }

    //This helper method clears all the items in the adapter.
    public void clearIngredients() {
        int size = mIngredients.size();
        mIngredients.clear();
        notifyItemRangeRemoved(0, size);
    }

    //This helper method adds all the items to the adapter.
    public void addIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients.addAll(ingredients);
        notifyItemRangeInserted(0, ingredients.size() - 1);
    }
}
