package com.example.an.pnaandroid.ex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.an.pnaandroid.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ExYoutubeActivity extends YouTubeBaseActivity implements  YouTubePlayer.OnInitializedListener {
    String API_KEY = "AIzaSyC-REUhiOIQQylc8sfYjvkFoEm0qVXpYpU";
    YouTubePlayerView youTubePlayerView;
    int REQUEST_VIDEO = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_youtube);
        youTubePlayerView = findViewById(R.id.youtube);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo("hKOLILJYArw");
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(ExYoutubeActivity.this, REQUEST_VIDEO);
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==requestCode){
            youTubePlayerView.initialize(API_KEY, ExYoutubeActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}