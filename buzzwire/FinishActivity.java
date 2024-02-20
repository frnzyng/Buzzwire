package com.example.buzzwire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {
    Button playAgainButton, exitButton;
    TextView scoreTextView;
    Indicator indicator = Indicator.getIndicator();
    String flag = indicator.getFlag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.transitionNone;
        setContentView(R.layout.activity_finish);

        User user = User.getUser();
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(user.getScoreMedium());

        playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect();
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

    // Game will restart if back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        redirect();
    }

    public void redirect() {
        switch (flag) {
            case "Easy": {
                Intent intent = new Intent(getApplicationContext(), EasyActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case "Medium": {
                Intent intent = new Intent(getApplicationContext(), MediumActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case "Hard": {
                Intent intent = new Intent(getApplicationContext(), HardActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}
