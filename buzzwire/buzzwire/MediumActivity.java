package com.example.buzzwire;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MediumActivity extends AppCompatActivity implements View.OnTouchListener {
    Button pauseButton;
    ImageView wireLoopImageView, finishBoxImageView;
    ImageView[] hitBoxImageView;
    TextView timerTextView;
    TimerManager timerManager;

    boolean isTimerPaused = false;
    int prevX, prevY;
    int screenWidth, screenHeight;
    User user = User.getUser();
    Indicator indicator = Indicator.getIndicator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Timer
        timerTextView = findViewById(R.id.timerTextView); // Get reference to timer TextView
        timerManager = new TimerManager(timerTextView);
        timerManager.startTimer();

        // Pause or resume button
        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerPaused) {
                    timerManager.resumeTimer();
                    isTimerPaused = false;
                    pauseButton.setText("Pause");
                } else {
                    timerManager.pauseTimer();
                    isTimerPaused = true;
                    pauseButton.setText("Resume");
                }
            }
        });

        // Hit and finish box
        hitBoxImageView = new ImageView[19];
        hitBoxImageView[0] = findViewById(R.id.imageView1);
        hitBoxImageView[1] = findViewById(R.id.imageView2);
        hitBoxImageView[2] = findViewById(R.id.imageView3);
        hitBoxImageView[3] = findViewById(R.id.imageView4);
        hitBoxImageView[4] = findViewById(R.id.imageView5);
        hitBoxImageView[5] = findViewById(R.id.imageView6);
        hitBoxImageView[6] = findViewById(R.id.imageView7);
        hitBoxImageView[7] = findViewById(R.id.imageView8);
        hitBoxImageView[8] = findViewById(R.id.imageView9);
        hitBoxImageView[9] = findViewById(R.id.imageView10);
        hitBoxImageView[10] = findViewById(R.id.imageView11);
        hitBoxImageView[11] = findViewById(R.id.imageView12);
        hitBoxImageView[12] = findViewById(R.id.imageView13);
        hitBoxImageView[13] = findViewById(R.id.imageView14);
        hitBoxImageView[14] = findViewById(R.id.imageView15);
        hitBoxImageView[15] = findViewById(R.id.imageView16);
        hitBoxImageView[16] = findViewById(R.id.imageView17);
        hitBoxImageView[17] = findViewById(R.id.imageView18);
        hitBoxImageView[18] = findViewById(R.id.imageView19);

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
                    timerManager.stopTimer();
                    user.setScoreMedium(timerManager.getTime());
                    indicator.setFlag("Medium");

                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    startActivity(intent);
                } else {
                    // Check if the wire loop intersects with any of the hit box
                    for (ImageView imageView : hitBoxImageView) {
                        if (isIntersecting(wireLoopImageView, imageView)) {
                            timerManager.stopTimer();
                            user.setScoreMedium(timerManager.getTime());
                            indicator.setFlag("Medium");

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
}
