package com.example.buzzwire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    private Button retryButton, exitButton;
    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.transitionNone;
        setContentView(R.layout.activity_game_over);

        User user = User.getUser();
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(user.getScoreMedium());

        retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert return to main menu code chuchu
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Restart the previous activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}
