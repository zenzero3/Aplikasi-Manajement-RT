package sistem.Smarta.grandcikarangcity2.rt.isibutton.kasbutton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import sistem.Smarta.grandcikarangcity2.R;

public class Bayar extends AppCompatActivity {
    String eko;
    TextView tanggalan,namaiur,nominaliuran,statusiuran;
    Button cek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        eko = i.getStringExtra("id");
        setContentView(R.layout.activity_cekbayar);
        tanggalan = findViewById(R.id.date);
        namaiur   = findViewById(R.id.nomame);
        nominaliuran= findViewById(R.id.nomin);
        statusiuran = findViewById(R.id.statuskelar);
        cek = findViewById(R.id.cek);
        getdata(eko);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdata(String eko) {

    }
}
