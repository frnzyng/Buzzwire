package com.example.buzzwire;

import android.os.Handler;
import android.widget.TextView;

public class TimerManager {
    private long startTime = 0;
    private long elapsedTime = 0;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private String time;
    private TextView timerTextView;

    public TimerManager(TextView textView) {
        timerTextView = textView;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                startTimer();
            }
        };
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void pauseTimer() {
        // Pause the timer
        elapsedTime = System.currentTimeMillis() - startTime;
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void resumeTimer() {
        // Resume the timer
        startTime = System.currentTimeMillis() - elapsedTime;
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void stopTimer() {
        // Stop the timer
        timerHandler.removeCallbacks(timerRunnable);
    }

    public String getTime() {
        return time;
    }

    private void setTime(String time) {
        this.time = time;
    }

    public void startTimer() {
        long milliseconds = System.currentTimeMillis() - startTime;
        int seconds = (int) (milliseconds / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        setTime(formattedTime);

        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));

        timerHandler.postDelayed(timerRunnable, 500); // Update every 0.5 seconds
    }
}
