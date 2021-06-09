package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import sistem.Smarta.grandcikarangcity2.Step2;
import sistem.Smarta.grandcikarangcity2.Step3;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.stepper.fragmentone;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.stepper.fragmenttwo;
import sistem.Smarta.grandcikarangcity2.stepone;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Step step=null;
        switch (position) {
            case 0:
                step = new fragmentone();
                break;
            case 1:
                step = new fragmenttwo();
                break;
            default:
                break;
        }

        return step;
    }



    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 2) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        switch (position) {
            case 0:
                builder.setTitle("Detail Informasi ");
                break;
            case 1:
                builder.setTitle("Submit");
                break;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();

    }
}
