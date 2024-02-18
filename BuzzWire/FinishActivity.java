package com.example.buzzwire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {
    Button playAgainButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.transitionNone;
        setContentView(R.layout.activity_finish);

        playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
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
}
