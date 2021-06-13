package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.rt.Login;

import static android.app.Activity.RESULT_CANCELED;

public class stepone extends Fragment implements Step, BlockingStep, AdapterView.OnItemSelectedListener {
    EditText rum,bb,nm;
    SharedPreferences laporan;
    /*ImageButton poto;*/
    String rumah,fasilitas,noblok,norumah,lokal;
    public int one;
    String namalokasi;
    FusedLocationProviderClient lokasi;
    TextView ambilgaleri, ambilkamera,peruma;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    Bitmap ekoa;
    Uri dwi;
    private ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentstepone, container, false);

        rum = v.findViewById(R.id.deskripsi);
        bb=v.findViewById(R.id.alamatBlok);
        peruma=v.findViewById(R.id.Perumahan);
        nm= v.findViewById(R.id.norumah);
        laporan= this.getActivity().getSharedPreferences("laporan", Context.MODE_PRIVATE);
        bb.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
        bb.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

       /* poto = v.findViewById(R.id.buttonambil);*/
        final Spinner spinner = v.findViewById(R.id.spinner);
        lokasi= LocationServices.getFusedLocationProviderClient(requireContext());
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.kerusakan_array,R.layout.itemoke2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            fasilitas = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



      /*  lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lokasi.setText(addresses.get(0).getAddressLine(0));
                                    lokasi.setTextColor(getResources().getColor(R.color.colorbackld));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }else {
                    ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }

            }
        });*/



        return v;

    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getalert();
            return;
        }else
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = lokasi.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            one =2;
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            namalokasi = addresses.get(0).getAddressLine(0);
                            peruma.setText(namalokasi);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();

                        }
                    }else {
                        one =1;
                        getalert();
                    }
                }
            });
              }else    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},44);
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},44);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Task<Location> task = lokasi.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        if (location != null){
                            Geocoder geocoder = new Geocoder(getActivity(),Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                namalokasi=addresses.get(0).getAddressLine(0);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            else {
                    getalert();
            }
        }
    }

    private void getalert() {
        if (one == 1){
        new AlertDialog.Builder(requireContext())
                .setTitle("Permission needed")
                .setCancelable(false)
                .setMessage("Pilih Akses Lokasi Sepanjang Waktu \nUntuk Menggunakan Fitur Ini")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
                        one = 2;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getActivity().getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);
                        getActivity().finish();
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
    }else {
            getlocation();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                ekoa=photo;
                img.setImageBitmap(ekoa);
                    ambilkamera.setText("Batal");
                    ambilgaleri.setText("Simpan");
                    imageButton2.setVisibility(View.GONE);
                    imageButton1.setVisibility(View.GONE);
                    imageButton3.setVisibility(View.VISIBLE);
                    imageButton4.setVisibility(View.VISIBLE);

            }
            if (requestCode == 1)
            {
                Uri selectedImage = data.getData();
                dwi=selectedImage;
                img.setImageURI(dwi);
                ambilkamera.setText("Batal");
                ambilgaleri.setText("Simpan");
                imageButton2.setVisibility(View.GONE);
                imageButton1.setVisibility(View.GONE);
                imageButton3.setVisibility(View.VISIBLE);
                imageButton4.setVisibility(View.VISIBLE);

            }
        }
    }
    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }


    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setMessage("Loading...."); rumah = rum.getText().toString();
        noblok= bb.getText().toString();
        norumah=nm.getText().toString();
        callback.getStepperLayout().showProgress("Memproses data.....");
            progressBar.show();
            if (rumah.equals("")){
                callback.getStepperLayout().hideProgress();
                progressBar.dismiss();
                Toast.makeText(requireContext(),"Nama Masalah tidak boleh kosong",Toast.LENGTH_LONG).show();
            } else if (noblok.equals("")){
                callback.getStepperLayout().hideProgress();
                progressBar.dismiss();
                Toast.makeText(requireContext(),"Masukan NO Blok ",Toast.LENGTH_LONG).show();
            }else if (norumah.equals("")){
                callback.getStepperLayout().hideProgress();
                progressBar.dismiss();
                Toast.makeText(requireContext(),"Masukan No Rumah",Toast.LENGTH_LONG).show();
            }else {
                rumah = rum.getText().toString();
                noblok= bb.getText().toString();
                norumah=nm.getText().toString();
                String perumahan=peruma.getText().toString();
                SharedPreferences.Editor edit = laporan.edit();
                    edit.putString("deskripsi",rumah);
                    edit.putString("fasilitas",fasilitas);
                    edit.putString("perumaha",perumahan);
                    edit.putString("blok",noblok);
                    edit.putString("norumah",norumah);
                    edit.putString("lokasi",namalokasi);
                    edit.apply();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        callback.getStepperLayout().hideProgress();
                        callback.goToNextStep();
                    }
                }, 2000L);
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
        }, 2000L);
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onSelected() {
       getlocation();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
