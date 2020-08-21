package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * This is a class that is the Splash Screen window of our application. Here I create the animations I want to appear in UI when we run application. It is the first Activity of our Application
 */
public class LoceatSplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loceat_splash_screen);
        StartAnimations();
    }

    private void StartAnimations() {
        /**
         * Here I set the image and the animations of the image
         */
        final ImageView image = findViewById(R.id.imageView);
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        image.setAnimation(topAnim);
        topAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * This is a listener that helps me to define to start the second activity when the animation finish
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                image.clearAnimation();
                Intent intent = new Intent(LoceatSplashScreen.this,MapsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });
    }

}

