package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String videoUrl = getIntent().getStringExtra("videoUrl");
        String description = getIntent().getStringExtra("description");

        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", videoUrl);
        bundle.putString("description", description);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.videoContainer, videoFragment)
                .commit();
    }
}
