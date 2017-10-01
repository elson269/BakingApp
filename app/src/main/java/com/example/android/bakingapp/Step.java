package com.example.android.bakingapp;

public class Step {
    private String mShortDescription, mDescription, mVideoUrl;

    public Step(String shortDescription, String description, String videoUrl) {

        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }
}
