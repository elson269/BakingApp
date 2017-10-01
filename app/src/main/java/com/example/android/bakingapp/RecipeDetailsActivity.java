package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;


public class RecipeDetailsActivity extends AppCompatActivity
        implements MasterListFragment.OnListItemClickListener {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (findViewById(R.id.videoContainer) != null) {
            mTwoPane = true;
//            String videoUrl = getIntent().getStringExtra("videoUrl");
//            String description = getIntent().getStringExtra("description");

//            Bundle bundle = new Bundle();
//            bundle.putString("videoUrl", videoUrl);
//            bundle.putString("description", description);
//            VideoFragment videoFragment = new VideoFragment();
//            Bundle bundle = videoFragment.getArguments();
//            if (bundle != null) {
//                String string = bundle.getString("description");
//                TextView descriptionTextView = (TextView)findViewById(R.id.stepDescriptionTextView);
//                descriptionTextView.setText(string);
//                //mBinding.stepDescriptionTextView.setText(string);
//            }
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.videoContainer, videoFragment)
//                    .commit();
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onStepItemClick(int position, ArrayList<Step> steps) {
        if (mTwoPane) {
//            String videoUrl = getIntent().getStringExtra("videoUrl");
//            String description = getIntent().getStringExtra("description");
            Bundle bundle = new Bundle();
            bundle.putString("videoUrl", steps.get(position).getVideoUrl());
            bundle.putString("description", steps.get(position).getDescription());
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.videoContainer, videoFragment)
                    .commit();

        } else {
            Intent videoIntent = new Intent(this, VideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("videoUrl", steps.get(position).getVideoUrl());
            bundle.putString("description", steps.get(position).getDescription());
            videoIntent.putExtras(bundle);
            startActivity(videoIntent);
        }
    }
}
