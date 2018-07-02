package co.za.bluemarble;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;



public class SplashScreen extends AppCompatActivity {

    public LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("loading.json");
        animationView.loop(true);
        animationView.playAnimation();

            int INTERNET_TIME_OUT = 3000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent inent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(inent);
                    animationView.cancelAnimation();
                    finish();
                }
            }, INTERNET_TIME_OUT);

    }
}
