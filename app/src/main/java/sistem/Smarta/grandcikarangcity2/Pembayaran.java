package sistem.Smarta.grandcikarangcity2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pembayaran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup);
        ImageButton keluar = findViewById(R.id.backbutt);
        final ImageButton mores = findViewById(R.id.app);
        final TextView moress = findViewById(R.id.more);
        final TextView less = findViewById(R.id.less_text);
        final ImageButton lesss = findViewById(R.id.less);
        final LinearLayout panel = findViewById(R.id.panelview1);
        mores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mores.setVisibility(View.GONE);
                moress.setVisibility(View.GONE);
                less.setVisibility(View.VISIBLE);
                lesss.setVisibility(View.VISIBLE);
                panel.setVisibility(View.VISIBLE);
            }
        });

        lesss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                less.setVisibility(View.GONE);
                lesss.setVisibility(View.GONE);
                mores.setVisibility(View.VISIBLE);
                moress.setVisibility(View.VISIBLE);
                panel.setVisibility(View.GONE);
            }
        });


        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
