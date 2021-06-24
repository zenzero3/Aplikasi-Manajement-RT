package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.stepper;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.laporanwarga;

public class fragmentone extends Fragment implements Step, BlockingStep, AdapterView.OnItemSelectedListener{
    FusedLocationProviderClient lokasi;
    SharedPreferences laporan;
    String namalokasi;
    String namas,ceknama;
    String detais;
    String lokasiss;
    double longitude;
    double latitude;
    EditText nama,detai,lokasis;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentlaporanwargasatu, container, false);
        lokasi= LocationServices.getFusedLocationProviderClient(requireContext());
        laporan= this.getActivity().getSharedPreferences("laporanwarga", Context.MODE_PRIVATE);
        nama = v.findViewById(R.id.namainfo);
        detai = v.findViewById(R.id.deskripsi);
        lokasis = v.findViewById(R.id.lokasi);
        checkdata();
        getlocation();
        return v;
    }

    private void checkdata() {
    ceknama=laporan.getString("nama_info",null);
    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = lokasi.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    if (location != null){
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            namalokasi=addresses.get(0).getAddressLine(0);
                            longitude = addresses.get(0).getLongitude();
                            latitude  = addresses.get(0).getLatitude();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else {
           getalert();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        namas = nama.getText().toString();
        detais= detai.getText().toString();
        lokasiss=lokasis.getText().toString();
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setMessage("Loading....");
        callback.getStepperLayout().showProgress("Memproses data.....");
        progressBar.show();
        if (namas.equals("")){
            callback.getStepperLayout().hideProgress();
            progressBar.dismiss();
            Toast.makeText(requireContext(),"Nama Informasi Tidak boleh Kosong",Toast.LENGTH_LONG).show();
        } else if (detais.equals("")){
            callback.getStepperLayout().hideProgress();
            progressBar.dismiss();
            Toast.makeText(requireContext(),"Deskripsi Tidak boleh Kosong ",Toast.LENGTH_LONG).show();
        }else if (lokasiss.equals("")){
            callback.getStepperLayout().hideProgress();
            progressBar.dismiss();
            Toast.makeText(requireContext(),"Lokasi Tidak boleh Kosong",Toast.LENGTH_LONG).show();
        }else {
            namas = nama.getText().toString();
            detais= detai.getText().toString();
            lokasiss=lokasis.getText().toString();
            SharedPreferences.Editor edit = laporan.edit();
            edit.putString("nama_info",namas);
            edit.putString("detail",detais);
            edit.putString("lokasiok",lokasiss);
            edit.putString("lokasi",namalokasi);
            edit.putString("longitude", String.valueOf(longitude));
            edit.putString("latitude",String.valueOf(latitude));
            edit.apply();
            edit.commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String iki = laporan.getString("nama_info",null);
                    if (iki.equals(namas)){
                        progressBar.dismiss();
                        callback.goToNextStep();

                    }

                }
            }, 1000);
        }
    }

    @Override
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.complete();
                Toast.makeText(requireContext(), "onCompleted!", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }
    private void getalert() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Permission needed")
                .setMessage("Aplikasi Memerlukan Perizinan Lokasi ")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
                        getlocation();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                })
                .create().show();
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
