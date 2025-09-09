package br.ifamjogo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

@SuppressLint("CustomSplashScreen")
    public class SplashScreenActivity extends AppCompatActivity implements Runnable {

        private final int DELAY = 60000;
        private MediaPlayer mediaPlayer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.splashscreen);
            iniciarSom();

            Handler handler = new Handler();
            handler.postDelayed(this, DELAY);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }, 6000);
        }

    public void iniciarSom(){
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }
    public void pararSom(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void run() {
        Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
        startActivity(intent);
        Log.e("test","a");
        SplashScreenActivity.this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pararSom();
    }

}