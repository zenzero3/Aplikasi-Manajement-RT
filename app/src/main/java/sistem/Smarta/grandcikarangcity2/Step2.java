package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    SupportMapFragment mapFragment;
    EditText satu;
    ImageButton search;
    GoogleMap map;
    String isi;
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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        lokasi = LocationServices.getFusedLocationProviderClient(getActivity());
        dapat = getActivity().getSharedPreferences("laporan", Context.MODE_PRIVATE);
        getlokasi();
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

    private void getlokasi() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                           @Override
                           public void onMapReady(GoogleMap googleMap) {
                                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                               MarkerOptions markerOptions = new MarkerOptions();
                               markerOptions.position(latLng);
                               mycoordinat=latLng;
                               markerOptions.title(namalokasi);
                               googleMap.clear();
                               googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                               googleMap.addMarker(markerOptions);
                           }
                       });
                    }
                }
            });
        }else {
             ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},44);

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
        progressBar.show();
        progressBar.setTitle("Loading");
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

}
