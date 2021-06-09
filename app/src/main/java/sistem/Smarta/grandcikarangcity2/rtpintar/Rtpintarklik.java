package sistem.Smarta.grandcikarangcity2.rtpintar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;

public class Rtpintarklik extends AppCompatActivity {
    Button wargaklik,ketuartklik;
    ImageButton balik;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   setContentView(R.layout.rtpintarklik);
        wargaklik=findViewById(R.id.wargaklik);
        ketuartklik=findViewById(R.id.rtklik);
        balik = findViewById(R.id.backbutt);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ketuartklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registerrtpintar_RT.class);
                startActivity(intent);
                finish();
            }
        });
        wargaklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registerrtpintar_warga.class);
                startActivity(intent);
                finish();

            }
        });
    }



    @Override
    public void onBackPressed() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
    }

}
