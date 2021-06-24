package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AdapterLaporanrt;
import sistem.Smarta.grandcikarangcity2.model.Adapteriuran;
import sistem.Smarta.grandcikarangcity2.model.datalaporanwarga;
import sistem.Smarta.grandcikarangcity2.model.iurannamadesa;

public class DaftarLaporan extends AppCompatActivity {
    List<datalaporanwarga> datalaporanwargas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarlaporan);
        LottieAnimationView lottieAnimationView= findViewById(R.id.lotielose);
        lottieAnimationView.setVisibility(View.GONE);
        ImageButton imageButton = findViewById(R.id.backbutt);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahres.getString("ide","empty");
getdatartoke(isiid);
    }

    private void getdatartoke(final String isiid) {
        String UrlLogin="http://gccestatemanagement.online/public/api/getrt/"+isiid;
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
                                if (message.equals("get rt")) {
                                    String isi = data.getString("id").trim();
                                  getdatawarga(isi);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Username Belum Terdaftar" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void getdatawarga(String isi) {
        datalaporanwargas = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/datalaporandua/"+isi;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray data=jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("rtselesai")) {
                                    int ek = data.length();
                                    if (ek==0){

                                    }else {
                                        for (int i=0;i<data.length();i++)
                                        {   datalaporanwarga dd= new datalaporanwarga();
                                            JSONObject he = data.getJSONObject(i);
                                            dd.setId(he.getString("id"));
                                            dd.setDate(he.getString("created_at"));
                                            dd.setDeskripsi(he.getString("lokasiinputuser"));
                                            dd.setStatu(he.getString("deskripsi"));
                                            dd.setStatus(he.getString("sta_laporan"));
                                            dd.setJudul(he.getString("namalaporan"));
                                            datalaporanwargas.add(dd);
                                        }
                                        RecyclerView recyclerView= findViewById(R.id.framelaporan);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DaftarLaporan.this);
                                        RecyclerView.Adapter adapter = new AdapterLaporanrt(DaftarLaporan.this,(ArrayList<datalaporanwarga>) datalaporanwargas);
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setLayoutManager(layoutManager);


                                    }
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Username Belum Terdaftar" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}