package sistem.Smarta.grandcikarangcity2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.stepstone.stepper.StepperLayout;

import java.util.ArrayList;
import java.util.List;

public class Laporankerusakan extends AppCompatActivity {

    private StepperLayout mStepperLayout;
SharedPreferences laporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formlaporan);
        SharedPreferences preferences = Laporankerusakan.this.getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences maslaha = Laporankerusakan.this.getSharedPreferences("laporan", Context.MODE_PRIVATE);
        SharedPreferences isi = Laporankerusakan.this.getSharedPreferences("isi", Context.MODE_PRIVATE);
        String user = preferences.getString("username","Empty");
        String maslaah= maslaha.getString("deskripsi","Empty");
        String falislitas= maslaha.getString("fasilitas","Empty");
        String perum =maslaha.getString("perumaha","Empty");
        String blok =maslaha.getString("blok","Empty");
        String norumah =maslaha.getString("norumah","Empty");
        String lokasi =isi.getString("lokasi","empty");
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), this));
        laporan= this.getSharedPreferences("laporan", Context.MODE_PRIVATE);
        ImageButton imagebutt= (ImageButton)findViewById(R.id.backbutt);
        imagebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
