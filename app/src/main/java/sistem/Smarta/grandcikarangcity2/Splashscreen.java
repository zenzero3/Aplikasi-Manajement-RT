package sistem.Smarta.grandcikarangcity2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import sistem.Smarta.grandcikarangcity2.model.SessionManager;

public class Splashscreen extends AppCompatActivity {
    int Splashscreentimeout=2000;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent = new Intent(Splashscreen.this, MainActivity.class);
              startActivity(intent);
              overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
              finish();
          }
      }, Splashscreentimeout);
    }
}