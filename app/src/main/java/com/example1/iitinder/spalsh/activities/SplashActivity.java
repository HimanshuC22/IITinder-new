package com.example1.iitinder.spalsh.activities;

/**
 * This is the splash screen
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example1.iitinder.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    TextView appName, appTitle;
    LottieAnimationView lottieAnimationView;
    Timer timer;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /*
     * Shows the system bars by removing all the flags
     * except for the ones that make the content appear under the system bars.
     */
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("DETAILS", MODE_PRIVATE);
        String EMAIL, PASSWORD;
        EMAIL = sharedPreferences.getString("EMAIL", "LDAP_NOT_FOUND_IN_PREFERENCES");
        PASSWORD = sharedPreferences.getString("PASSWORD", "PASSWORD_NOT_FOUND_IN_PREFERENCES");

        appName = findViewById(R.id.app_name);
        appTitle = findViewById(R.id.app_title);
        lottieAnimationView = findViewById(R.id.lottie);
//        lottieAnimationView.animate().translationY(-500).setDuration(500).setStartDelay(2000);
        appName.animate().translationY(-600).setDuration(500).setStartDelay(2700);
//        fade();
//        appTitle.animate().translationY(-800).setDuration(500).setStartDelay(2000);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                Log.d("EMAIL/", EMAIL);
                Log.d("PASSWORD/", PASSWORD);
                mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnSuccessListener(authResult -> {

                    // TODO : Add function for handling successful signin

                    finish();
                }).addOnFailureListener(e -> {

                    // TODO : Add failure listener

                    finish();
                });
            }
        }, 3000);
    }

    public void fade() {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        appName.startAnimation((animation1));
        appTitle.startAnimation((animation1));
    }
}