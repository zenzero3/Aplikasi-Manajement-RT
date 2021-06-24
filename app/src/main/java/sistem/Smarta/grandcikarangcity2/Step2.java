package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class Step2 extends Fragment implements Step, BlockingStep, AdapterView.OnItemSelectedListener {
    MapView mapFragment;
    EditText satu;
    ImageButton search;
    GoogleMap map;
    ImageView isis;
    public int one;
    String isi;
    LatLng latLng;
    SharedPreferences dapat;
    double lat, longi;
    String namalokasi = "";
    FusedLocationProviderClient lokasi;
    LatLng mycoordinat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step2, container, false);
        /* search = (ImageButton) v.findViewById(R.id.carialamat);*/
        /*satu = (EditText) v.findViewById(R.id.dapetama);*/
        mapFragment=v.findViewById(R.id.maps);
        lokasi = LocationServices.getFusedLocationProviderClient(requireActivity());
        dapat = requireActivity().getSharedPreferences("laporan", Context.MODE_PRIVATE);
        mapFragment.onCreate(savedInstanceState);
        isis = v.findViewById(R.id.location);
        isis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlocation();
            }
        });
/*        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                map = googleMap;
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    LatLng latLng = new LatLng(lat, longi);

                    @Override
                    public void onMapClick(LatLng latLng) {
                        Toast.makeText(requireContext(), "Lokasi berhasil Terambil", Toast.LENGTH_LONG).show();
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.addMarker(markerOptions);

                    }
                });
            }
        });*/


        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (satu.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Fieald Tidak boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    searchLocation();
                    satu.setText("");
                }
            }
        });*/


        return v;
    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getalert();
            return;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            one = 2;
            Task<Location> task = lokasi.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {

                            final List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            namalokasi = addresses.get(0).getAddressLine(0);
                            lat = location.getLatitude();
                            longi = location.getLongitude();
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    latLng = new LatLng(lat,longi);
                                    mycoordinat = latLng;
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(latLng);
                                    markerOptions.title(namalokasi);
                                    googleMap.clear();
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                    googleMap.addMarker(markerOptions);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();

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

    public void searchLocation()  {
        String locatione = satu.getText().toString();
        List <Address> addresses=null;

        try {
            Geocoder geocoder =new Geocoder(requireContext());
            addresses = geocoder.getFromLocationName(locatione,1);
            Toast.makeText(requireContext(),"Alamat Tersimpan",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
        mycoordinat = latLng;
        namalokasi = locatione;
        map.clear();
        map.addMarker(new MarkerOptions().position(latLng).title(locatione));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }



    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        lokasi = LocationServices.getFusedLocationProviderClient(getActivity());
        dapat = getActivity().getSharedPreferences("laporan", Context.MODE_PRIVATE);
        getlocation();

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setTitle("Memuat Data");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setCancelable(false);
        progressBar.setMessage("Mohon Tunggu Sejenak");
        progressBar.show();
        callback.getStepperLayout().showProgress("Memproses data.....");
            SharedPreferences.Editor edit = dapat.edit();
            edit.putString("lokasi",namalokasi);
            edit.putString("lat", String.valueOf(mycoordinat.latitude));
            edit.putString("long", String.valueOf(mycoordinat.longitude));
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


    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapFragment.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapFragment.onSaveInstanceState(outState);
    }
}
