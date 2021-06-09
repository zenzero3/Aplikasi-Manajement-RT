package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.Adapter;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Adapteriuran;
import sistem.Smarta.grandcikarangcity2.model.Adapterwargaindividu;
import sistem.Smarta.grandcikarangcity2.model.iurannamadesa;
import sistem.Smarta.grandcikarangcity2.model.iuranwargapost;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Iuran;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton.Adapterkeluarga;

public class iuranwarga extends AppCompatActivity {
    SharedPreferences kep;
    List<iurannamadesa> iuran;
    List<iuranwargapost>iuranwargaposts;
    TextView notfound,notfoundtwo;
    RecyclerView satu,dua;
    String iuranid,rt;
    RecyclerView.Adapter mdapter,adapter;
    LinearLayout pat;
    RecyclerView.LayoutManager layoutManager,getLayoutManager;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iuranwarga);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutManager = new LinearLayoutManager(this);
        getLayoutManager = new LinearLayoutManager(this);
        kep=this.getSharedPreferences("Kepala",MODE_PRIVATE);
        iuranid = kep.getString("nik",null);
        pat= findViewById(R.id.pathone);
        String id = kep.getString("id_rt",null);
        rt = id;
        notfound = findViewById(R.id.wargaiurannofound);
        notfoundtwo= findViewById(R.id.warganotfoundloan);
        satu = findViewById(R.id.rtiuran);
        dua = findViewById(R.id.iuranwargaok);
        getdata(id);
        this.handler = new Handler();
        this.handler.postDelayed(m_Runnable,10000);
    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            getdata(rt);
            handler.postDelayed(m_Runnable, 10000);
        }

    };

    private void getdata(String id) {
        iuran = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaalliuranrt/"+id;
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
                                        notfound.setVisibility(View.VISIBLE);
                                        notfoundtwo.setVisibility(View.VISIBLE);
                                        satu.setVisibility(View.GONE);
                                        dua.setVisibility(View.GONE);
                                        pat.setVisibility(View.GONE);
                                    }else {
                                        pat.setVisibility(View.VISIBLE);
                                        notfoundtwo.setVisibility(View.GONE);
                                        notfound.setVisibility(View.GONE);
                                        satu.setVisibility(View.VISIBLE);
                                        for (int i = 0;i<jsonArray.length();i++){
                                            iurannamadesa iurandesa = new iurannamadesa();
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            iurandesa.setNama(data.getString("nama_iuran"));
                                            iurandesa.setNominal(data.getString("nominal"));
                                            iurandesa.setIdiuran(data.getString("id"));
                                            iurandesa.setTanggalstart(data.getString("tangga_mulai"));
                                            iuran.add(iurandesa);
                                        }
                                        satu.setLayoutManager(layoutManager);
                                        mdapter = new Adapteriuran(iuranwarga.this,(ArrayList<iurannamadesa>) iuran);
                                        satu.setAdapter(mdapter);
                                        getiuran(iuranid);
                                    }


                                }

                            }else {
                                Toast.makeText(iuranwarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(iuranwarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(iuranwarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(iuranwarga.this);
        requestQueue.add(stringRequest);
    }

    private void getiuran(String iuranid) {
        iuranwargaposts = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaspesifik/"+iuranid;
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
                                        notfoundtwo.setVisibility(View.VISIBLE);
                                    }else {
                                        notfoundtwo.setVisibility(View.GONE);
                                        dua.setVisibility(View.VISIBLE);
                                        for (int i = 0;i<jsonArray.length();i++){
                                            iuranwargapost iurandesax = new iuranwargapost();
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            iurandesax.setId(data.getString("id"));
                                            iurandesax.setNamaiuran(data.getString("namaiuran"));
                                            iurandesax.setStatus(data.getString("statu"));
                                            iurandesax.setNominal(data.getString("nominaliuran"));
                                            iuranwargaposts.add(iurandesax);
                                        }

                                        dua.setLayoutManager(getLayoutManager);
                                        adapter = new Adapterwargaindividu(iuranwarga.this,(ArrayList<iuranwargapost>) iuranwargaposts);
                                        dua.setAdapter(adapter);

                                    }


                                }

                            }else {
                                Toast.makeText(iuranwarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(iuranwarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(iuranwarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(iuranwarga.this);
        requestQueue.add(stringRequest);
    }
}