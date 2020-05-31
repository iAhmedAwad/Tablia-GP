package iti.team.tablia.Authentication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import iti.team.tablia.R;


public class SplashScreen extends AppCompatActivity {

  private static final long SPLASH_TIME_OUT = 4000;
  private Animation mTop, mBottom;
  private ImageView xSplashImage;
  private TextView xSlogan, xAppName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    xSplashImage = findViewById(R.id.xSplashImage);
    xAppName = findViewById(R.id.xAppName);
    xSlogan = findViewById(R.id.xSlogan);

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    mTop = AnimationUtils.loadAnimation(this, R.anim.top_animation);
    mBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

    xSplashImage.setAnimation(mTop);

    xAppName.setAnimation(mBottom);
    xSlogan.setAnimation(mBottom);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(SplashScreen.this,
            LoginActivity.class);
        //This is commented out because it takes you ou of the app then goes to
        //login screen again
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
          Pair[] pairs = new Pair[2];
          pairs[0] = new Pair<View, String>(xSplashImage, "logo_image");
          pairs[1] = new Pair<View, String>(xAppName, "logo_text");
          ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
          startActivity(intent, options.toBundle());
        } else {

          startActivity(intent);
          finish();
        }
      }
    }, SPLASH_TIME_OUT);

  }
}
