package sistem.Smarta.grandcikarangcity2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);

    }

    @Override
    public Step createStep(int position) {
        Step step = null;
        switch (position) {
            case 0:
                step = new stepone();

                break;
            case 1:
                step = new Step2();
                break;
            case 2:
                step = new Step3();
                break;
            default:
                break;
        }

        return step;
    }



    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 2) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        switch (position) {
            case 0:
                builder.setTitle("Detail Masalah ");

                break;
            case 1:
                builder.setTitle("Lokasi");
                break;
            case 2:
                builder.setTitle("Review");
                break;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();

    }
}