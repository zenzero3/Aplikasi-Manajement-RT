package sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmentlistpolling;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

import sistem.Smarta.grandcikarangcity2.R;

public class Polling_Button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling__button);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}