package com.example.buzzwire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    ImageView wireLoopImageView, finishBoxImageView;
    ImageView[] hitBoxImageView;
    TextView timerTextView;
    Timer timer;
    static String time;
    private long startTime = 0;
    long elapsedTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            startTimer();
        }
    };
    Button pauseButton;
    boolean isTimerPaused = false;
    int prevX, prevY;
    int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Timer
        timerTextView = findViewById(R.id.timerTextView);
        timer = new Timer();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        hitBoxImageView = new ImageView[19];
        hitBoxImageView [0] = findViewById(R.id.imageView1);
        hitBoxImageView [1] = findViewById(R.id.imageView2);
        hitBoxImageView [2] = findViewById(R.id.imageView3);
        hitBoxImageView [3] = findViewById(R.id.imageView4);
        hitBoxImageView [4] = findViewById(R.id.imageView5);
        hitBoxImageView [5] = findViewById(R.id.imageView6);
        hitBoxImageView [6] = findViewById(R.id.imageView7);
        hitBoxImageView [7] = findViewById(R.id.imageView8);
        hitBoxImageView [8] = findViewById(R.id.imageView9);
        hitBoxImageView [9] = findViewById(R.id.imageView10);
        hitBoxImageView [10] = findViewById(R.id.imageView11);
        hitBoxImageView [11] = findViewById(R.id.imageView12);
        hitBoxImageView [12] = findViewById(R.id.imageView13);
        hitBoxImageView [13] = findViewById(R.id.imageView14);
        hitBoxImageView [14] = findViewById(R.id.imageView15);
        hitBoxImageView [15] = findViewById(R.id.imageView16);
        hitBoxImageView [16] = findViewById(R.id.imageView17);
        hitBoxImageView [17] = findViewById(R.id.imageView18);
        hitBoxImageView [18] = findViewById(R.id.imageView19);

        finishBoxImageView = findViewById(R.id.finishBox);

        // Set on touch listener to drag the loop
        wireLoopImageView = findViewById(R.id.wireLoopImageView);
        wireLoopImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isTimerPaused) {
            return false;
        }
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                prevX = x - lParams.leftMargin;
                prevY = y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                // Calculate the new position of the image
                int newX = x - prevX;
                int newY = y - prevY;

                // Limit the image to stay within the screen bounds
                newX = Math.max(0, Math.min(screenWidth - v.getWidth(), newX));
                newY = Math.max(0, Math.min(screenHeight - v.getHeight(), newY));

                // Update the layout parameters with the new position
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin = newX;
                layoutParams.topMargin = newY;

                v.setLayoutParams(layoutParams);

                // Check if the wire loop intersects with the finish box
                if (isIntersecting(wireLoopImageView, finishBoxImageView)) {
                    pauseTimer();
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    startActivity(intent);
                } else {
                    // Check if the wire loop intersects with any of the hit box
                    for (ImageView imageView : hitBoxImageView) {
                        if (isIntersecting(wireLoopImageView, imageView)) {
                            pauseTimer();
                            Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                break;
        }
        return true;
    }

    // Checks if the wire loop touches the hit box
    public boolean isIntersecting(ImageView wireLoopImageView, ImageView hitBoxImageView) {
        Rect rect1 = new Rect();
        wireLoopImageView.getHitRect(rect1);
        Rect rect2 = new Rect();
        hitBoxImageView.getHitRect(rect2);
        return rect1.intersect(rect2);
    }

    public void pauseTimer() {
        if (!isTimerPaused) {
            // Calculate elapsed time before pausing the timer
            elapsedTime = System.currentTimeMillis() - startTime;

            // Pause the timer
            timerHandler.removeCallbacks(timerRunnable);
            isTimerPaused = true;
            pauseButton.setText("Resume");
        } else {
            // Resume the timer
            startTime = System.currentTimeMillis() - elapsedTime;
            timerHandler.postDelayed(timerRunnable, 0);
            isTimerPaused = false;
            pauseButton.setText("Pause");
        }
    }

    public void startTimer() {
        long milliseconds = System.currentTimeMillis() - startTime;
        int seconds = (int) (milliseconds / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        time = timerTextView.getText().toString();

        timerHandler.postDelayed(timerRunnable, 500); // Update every 0.5 seconds
    }
}