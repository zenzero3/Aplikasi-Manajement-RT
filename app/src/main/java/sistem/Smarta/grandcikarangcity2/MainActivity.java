package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import sistem.Smarta.grandcikarangcity2.model.SessionManager;
import sistem.Smarta.grandcikarangcity2.rt.Login;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String getId;
    SharedPreferences sharedPreferences;
    String namalokasi;
    FusedLocationProviderClient lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getpermission();
        onResume();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homenavbar, R.id.info, R.id.usernavbar)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        lokasi= LocationServices.getFusedLocationProviderClient(MainActivity.this);
        getlocation();
    }
    public void onResume() {
        super.onResume();
        getdata();
        getPrefs();
    }
    private void getPrefs() {
        sharedPreferences = getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences preferencdes =getSharedPreferences("ghost", Context.MODE_PRIVATE);
        String id =  sharedPreferences.getString("id","Empty");
        String passw = sharedPreferences.getString("password","Empty");
        String name =  sharedPreferences.getString("fulluname","Empty");
        String username = sharedPreferences.getString("username","Empty");
        String nohp =  sharedPreferences.getString("nohp","Empty");
        String email=  sharedPreferences.getString("email","empty");
        String image=  sharedPreferences.getString("id_image","Empty");
        String level= sharedPreferences.getString("userlevel","Empty");
        SharedPreferences.Editor editor = preferencdes.edit();
        editor.putString("id",id);
        editor.putString("nama",name);
        editor.putString("username",username);
        editor.putString("password",passw);
        editor.putString("nohp",nohp);
        editor.putString("email",email);
        editor.putString("id_image",image);
        editor.apply();

    }
    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = lokasi.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    if (location != null){
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            namalokasi=addresses.get(0).getAddressLine(0);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},44);
            Toast.makeText(getApplicationContext(),"Membutuhkan Allow permission Untuk access Laporan",Toast.LENGTH_LONG).show();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Task<Location> task = lokasi.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        if (location != null){
                            Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
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
                Toast.makeText(getApplicationContext(),"Membutuhkan Allow permission Untuk access Laporan",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getdata() {
        sharedPreferences = getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences preferencdes =getSharedPreferences("ghost", Context.MODE_PRIVATE);
        String id =  sharedPreferences.getString("id","Empty");
        String passw = sharedPreferences.getString("password","Empty");
        String name =  sharedPreferences.getString("fulluname","Empty");
        String username = sharedPreferences.getString("username","Empty");
        String nohp =  sharedPreferences.getString("nohp","Empty");
        String email=  sharedPreferences.getString("email","empty");
        String image=  sharedPreferences.getString("id_image","Empty");
        String level= sharedPreferences.getString("userlevel","Empty");
        SharedPreferences.Editor editor = preferencdes.edit();
        editor.putString("id",id);
        editor.putString("nama",name);
        editor.putString("username",username);
        editor.putString("password",passw);
        editor.putString("nohp",nohp);
        editor.putString("email",email);
        editor.putString("id_image",image);
        editor.apply();

    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            System.exit(0);
        } else {
            Toast.makeText(this, "Tekan  Lagi Untuk Menutup Aplikasi",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    private void getpermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)+ContextCompat
                .checkSelfPermission(MainActivity.this,Manifest.permission.LOCATION_HARDWARE)+ContextCompat
                .checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)+ContextCompat
                .checkSelfPermission(MainActivity.this,Manifest.permission.INTERNET)+
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)+ContextCompat
                .checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)){

            }
        }else {

        }



    }

}