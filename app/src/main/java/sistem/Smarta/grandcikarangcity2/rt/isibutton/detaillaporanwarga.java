package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AdapterLaporanrt;
import sistem.Smarta.grandcikarangcity2.model.adapterimagelaporanwarga;
import sistem.Smarta.grandcikarangcity2.model.datalaporanwarga;
import sistem.Smarta.grandcikarangcity2.model.imagelapwargadarirt;

public class detaillaporanwarga extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapFragment;
    GoogleMap map;
    double lat,lang;
    String namalokasi,id,image;
    List<imagelapwargadarirt> list;
    TextView nama,detail,pasti,userlok,tgl,namalaporan;
    LatLng latLng;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        id = i.getStringExtra("id");
        setContentView(R.layout.detailactivity);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        mapFragment =  findViewById(R.id.maps);
        mapFragment.onCreate(savedInstanceState);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getdata(id);

    }

    private void getdata(final String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/datalaporanid/"+id;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject data=jsonObject.getJSONObject("data");

                            if (status.equals("true")) {
                                if (message.equals("rtselesai")) {
                                    nama = findViewById(R.id.textView31);
                                    detail=findViewById(R.id.textView33);
                                    userlok = findViewById(R.id.textView34);
                                    pasti = findViewById(R.id.textView35);
                                    tgl = findViewById(R.id.textView36);
                                    namalaporan= findViewById(R.id.textView37);

                                    nama.setText(data.getString("nama_pelapor"));
                                    detail.setText(data.getString("deskripsi"));
                                    pasti.setText(data.getString("gps"));
                                    userlok.setText(data.getString("lokasiinputuser"));
                                    tgl.setText(data.getString("created_at"));
                                    namalokasi = data.getString("gps");
                                    namalaporan.setText(data.getString( "namalaporan"));
                                    lat = Double.parseDouble(data.getString("latituded"));
                                    lang = Double.parseDouble(data.getString("longituded"));
                                    checkPermission();
                                    getimage(id);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private  void  getimage(String image){
        list = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/lapimage/"+image;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject json = new JSONObject(response);
                            JSONArray jsonarray = json.getJSONArray("data");

                            if (status.equals("get")) {
                                if (message.equals("Get data")) {
                                    int oke = jsonarray.length();
                                    if (oke==0){
                                        RecyclerView recyclerView= findViewById(R.id.recyclerView);
                                        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
                                        params.height=1;
                                        recyclerView.setLayoutParams(params);
                                    }else {
                                    for(int i=0; i < jsonarray.length(); i++) {
                                        JSONObject jonobject = jsonarray.getJSONObject(i);
                                        imagelapwargadarirt adapterimagelaporanwargas = new imagelapwargadarirt ();
                                      String  path=jonobject.getString("path").trim();
                                      adapterimagelaporanwargas.setPath(path);
                                      list.add(adapterimagelaporanwargas);
                                    }
                                        RecyclerView recyclerView= findViewById(R.id.recyclerView);
                                        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(detaillaporanwarga.this,LinearLayoutManager.HORIZONTAL,false);
                                        RecyclerView.Adapter adapter = new adapterimagelaporanwarga(detaillaporanwarga.this,(ArrayList<imagelapwargadarirt>)list);
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setLayoutManager(layoutManager);


                                }
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(detaillaporanwarga.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(detaillaporanwarga.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                      latLng = new LatLng(lat,lang);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(namalokasi);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.addMarker(markerOptions);
                    }
                });
        }else {
            ActivityCompat.requestPermissions(detaillaporanwarga.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            Toast.makeText(detaillaporanwarga.this,"error sistem",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    protected void onStop() {
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
    protected void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapFragment.onSaveInstanceState(outState);
    }
}
