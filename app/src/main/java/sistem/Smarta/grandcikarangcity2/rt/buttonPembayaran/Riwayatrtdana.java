package sistem.Smarta.grandcikarangcity2.rt.buttonPembayaran;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import sistem.Smarta.grandcikarangcity2.R;

public class Riwayatrtdana extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riwayatactivity);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}