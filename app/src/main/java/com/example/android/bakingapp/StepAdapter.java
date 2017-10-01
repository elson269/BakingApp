package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{
    Context mContext;
    ArrayList<Step> mSteps;
    OnStepDescriptionClickListener mListener;

    public StepAdapter (Context context, ArrayList<Step> steps, OnStepDescriptionClickListener listener) {
        mContext = context;
        mSteps = steps;
        mListener = listener;
    }
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.step_item, parent, false);
        final StepViewHolder stepViewHolder = new StepViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDescriptionItemClick(view, stepViewHolder.getAdapterPosition() );
            }
        });

        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.mStepTextView.setText(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps != null) {
            return mSteps.size();
        } else {
            return 0;
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        TextView mStepTextView;
        public StepViewHolder(View itemView) {
            super(itemView);
            mStepTextView = (TextView)itemView.findViewById(R.id.stepTextView);
        }
    }

    //This helper method clears all the items in the adapter.
    public void clearSteps() {
        int size = mSteps.size();
        mSteps.clear();
        notifyItemRangeRemoved(0, size);
    }

    //This helper method adds all the items to the adapter.
    public void addSteps(ArrayList<Step> steps) {
        mSteps.addAll(steps);
        notifyItemRangeInserted(0, steps.size() - 1);
    }

    public interface OnStepDescriptionClickListener {
        void onDescriptionItemClick(View view, int position);
    }
}
