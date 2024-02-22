package com.example.buzzwire;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    SoundManager soundManager = SoundManager.getSoundManager();
    Button musicButton, soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        musicButton = findViewById(R.id.musicButton);
        musicButton.setText(soundManager.buttonMusicText);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundManager.playButtonSound();
                if (soundManager.isMusicOn) {
                    soundManager.turnOffMusic();
                }
                else {
                    soundManager.turnOnMusic();
                    soundManager.playBackgroundMusic();
                }
                musicButton.setText(soundManager.buttonMusicText);
            }
        });

        soundButton = findViewById(R.id.soundButton);
        soundButton.setText(soundManager.buttonSoundText);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundManager.isSoundOn) {
                    soundManager.turnOffSound();
                }
                else {
                    soundManager.turnOnSound();
                    soundManager.playButtonSound();
                }
                soundButton.setText(soundManager.buttonSoundText);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(soundManager.isMusicOn) {
            if(soundManager.backgroundMusicPlayer.isPlaying()) {
                soundManager.backgroundMusicPlayer.pause();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(soundManager.isMusicOn) {
            if(!soundManager.backgroundMusicPlayer.isPlaying()) {
                soundManager.turnOnMusic();
                soundManager.playBackgroundMusic();
            }
        }
    }
}
