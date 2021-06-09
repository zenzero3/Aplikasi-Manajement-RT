package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.rt.Login;

public class Panic extends AppCompatActivity {
    int counter = 0;
    String lokasi,name,ide,longitude,latitude;
    String s = "tel:+628129885679";
    TextView satu,dua,tiga,empat;
    ProgressDialog dialog;
    Button hilangkan;
    AnimationDrawable soses;
    FusedLocationProviderClient fusedLocationProviderClient;
    ImageView panikjalan;
    ImageButton panikc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formpanic);
        SharedPreferences preferences = Panic.this.getSharedPreferences("blood", Context.MODE_PRIVATE);
       ide=preferences.getString("ide","Empty");
       name = preferences.getString("noh","Empty");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setTitle("Customer Call");
        dialog.setProgress(10);
        dialog.setMax(100);
        dialog.setMessage("Loading...");
        satu = findViewById(R.id.satuperingatan);
        dua = findViewById(R.id.satuisiperingatan);
        tiga = findViewById(R.id.duaperingatan);
        empat= findViewById(R.id.duaisiperingatan);
        panikc = (ImageButton) findViewById(R.id.kanic);
        panikjalan = findViewById(R.id.kanicdua);
        hilangkan= findViewById(R.id.tutupsalahkan);
        panikjalan.setBackgroundResource(R.drawable.sos);
        panikjalan.setScaleType(ImageView.ScaleType.FIT_CENTER);
        soses = (AnimationDrawable) panikjalan.getBackground();

        panikc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lokasi =addresses.get(0).getAddressLine(0);
                                    longitude = String.valueOf(addresses.get(0).getLongitude());
                                    latitude = String.valueOf(addresses.get(0).getLatitude());
                                    Log.d("lokasi",lokasi);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }else {
                    ActivityCompat.requestPermissions(Panic.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
                counter++;

                pacik();
            }
        });
        hilangkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void senddata() {
        String UrlLogin="http://gccestatemanagement.online/public/api/panicbtn";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("Post Created")) {
                                if (success.equals("true")) {
                                    if (ContextCompat.checkSelfPermission(Panic.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                                            ActivityCompat.requestPermissions(Panic.this,new String[] {Manifest.permission.CALL_PHONE},1);
                                        Toast.makeText(Panic.this, "Izinkan Aplikasi Untuk Melakukan Telp", Toast.LENGTH_SHORT).show();
                                    }else {
                                      startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(s
                                      )));
                                    }
                                }
                            }else {
                                Toast.makeText(Panic.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Panic.this, "Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Panic.this, "Peiksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", ide);
                params.put("nohp",name);
                params.put("longitude", longitude);
                params.put("latitude", latitude);
                params.put("lokasi", lokasi);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }else {
                Toast.makeText(Panic.this,"Izinkan Aplikasi melakukan Panggilan",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        soses.start();
    }

    private void pacik() {

        if (counter == 2) {
            Toast.makeText(getApplicationContext(), "Silah klik 1 kali lagi untuk Menghubungi Estate Management", Toast.LENGTH_LONG).show();
        }
        if (counter >= 3) {
            senddata();
            panikjalan.setVisibility(View.VISIBLE);
            panikc.setVisibility(View.GONE);
            satu.setVisibility(View.GONE);
            dua.setVisibility(View.GONE);
            tiga.setVisibility(View.VISIBLE);
            empat.setVisibility(View.VISIBLE);
            hilangkan.setText("Batalkan");


        }

    }

}