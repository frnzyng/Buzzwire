package com.example.buzzwire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ConstraintLayout congratulationLayout, gameLayout;
    private ImageView wireLoopImageView, finishBoxImageView;
    private ImageView[] hitBoxImageView;
    private int prevX, prevY;
    private int screenWidth, screenHeight;
    boolean gameOver = false;
    boolean finish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        congratulationLayout = findViewById(R.id.congratulationsLayout);
        gameLayout = findViewById(R.id.gameLayout);

        wireLoopImageView = findViewById(R.id.wireLoopImageView);
        wireLoopImageView.setOnTouchListener(this);

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

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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

                // Check if the loop image intersects with the finish box
                if (isIntersecting(wireLoopImageView, finishBoxImageView) && !finish) {
                    gameLayout.setVisibility(View.GONE);
                    congratulationLayout.setVisibility(View.VISIBLE);

                    //Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    //startActivity(intent);

                    Toast.makeText(MainActivity.this, "Yay", Toast.LENGTH_SHORT).show();
                    finish = true; // Set a flag to indicate that the toast message has been displayed
                } else {
                    // Check if the loop image intersects with any of the hit box images
                    boolean intersectionDetected = false;
                    for (int i = 0; i < hitBoxImageView.length; i++) {
                        if (isIntersecting(wireLoopImageView, hitBoxImageView[i])) {
                            intersectionDetected = true;
                            break; // Exit the loop once an intersection is detected
                        }
                    }

                    // Display "Game Over" message if intersection is detected
                    if (intersectionDetected && !gameOver) {
                        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                        gameOver = true; // Set a flag to indicate that the toast message has been displayed
                    } else if (!intersectionDetected) {
                        gameOver = false; // Reset the flag if loop is not intersecting with any hit box
                    }
                }
                break;
        }
        return true;
    }

    private boolean isIntersecting(ImageView wireLoopImageView, ImageView hitBoxImageView) {
        Rect rect1 = new Rect();
        wireLoopImageView.getHitRect(rect1);
        Rect rect2 = new Rect();
        hitBoxImageView.getHitRect(rect2);
        return rect1.intersect(rect2);
    }
}