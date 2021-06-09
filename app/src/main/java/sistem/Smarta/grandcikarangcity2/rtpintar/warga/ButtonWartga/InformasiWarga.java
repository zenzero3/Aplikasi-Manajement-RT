package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.adapter.StepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import sistem.Smarta.grandcikarangcity2.MyStepperAdapter;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton.StepperAdapter;

public class InformasiWarga extends AppCompatActivity {
    private StepperLayout mStepperLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
    }
}
