package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Adapterwargaindividu;
import sistem.Smarta.grandcikarangcity2.model.Keluargakk;
import sistem.Smarta.grandcikarangcity2.model.iuranwargapost;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton.Adapterkeluarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.HomeWarga;

public class KartuKeluargaWarga  extends AppCompatActivity {
ArrayList<Keluargakk>keluargakks;
RecyclerView recyclerView;
    String idkk;
    ProgressDialog progressBar;
    RecyclerView.Adapter adapter;
TextView one;
int eko=0;
int detik=60000,detiks;
RecyclerView.LayoutManager layoutManager;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kartukeluarga);
        one = findViewById(R.id.jumlah);
        progressBar = new ProgressDialog(KartuKeluargaWarga.this);
        progressBar.getWindow();
        progressBar.show();
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setMessage("Prosess");
                progressBar.dismiss();
                finish();
                handler.removeCallbacksAndMessages(null);
            }
        });
        ImageView tambah = findViewById(R.id.tambahkkoke);
        recyclerView = findViewById(R.id.keluargaberancana);
        layoutManager = new LinearLayoutManager(this);
        SharedPreferences wau= getSharedPreferences("lengkap diri",MODE_PRIVATE);
        idkk = wau.getString("idnkk",null);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ButtontambahKK.class);
                startActivity(intent);
                finish();
            }
        });
        getdata(idkk);
        detiks = detik;
        this.handler = new Handler();
        this.handler.postDelayed(m_Runnable,detiks);
    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            eko++;
            if (eko >= 5){
                eko = 0;
                detiks=detik+10000;
                getdata(idkk);
                handler.postDelayed(m_Runnable, detiks);
            }else {
                getdata(idkk);
                handler.postDelayed(m_Runnable, detiks);
            }

        }

    };

    private void getdata(String idkk) {
        keluargakks = new ArrayList<>();
        final ArrayList<String>dd= new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/family/"+idkk;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("get data warga")) {
                                    if (jsonArray.length()==0){

                                    }else {
                                        for (int i = 0;i<jsonArray.length();i++){
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            Keluargakk keluargakk = new Keluargakk();
                                            keluargakk.setId(data.getString("id"));
                                            keluargakk.setNik(data.getString("nama"));
                                            keluargakk.setStatuskk(data.getString("status_kk"));
                                            keluargakks.add(keluargakk);
                                        }


                                    }
                                    recyclerView.setLayoutManager(layoutManager);
                                    adapter = new Adapterkeluarga(KartuKeluargaWarga.this,(ArrayList<Keluargakk>)keluargakks);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.dismiss();
                                    one.setText(Integer.toString(keluargakks.size()));


                                }

                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KartuKeluargaWarga.this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
      progressBar.dismiss();
      finish();
    }



}