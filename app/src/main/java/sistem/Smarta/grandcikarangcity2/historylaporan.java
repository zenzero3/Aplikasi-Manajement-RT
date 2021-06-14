package sistem.Smarta.grandcikarangcity2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.model.datahistory;

public class historylaporan extends AppCompatActivity {

    SharedPreferences preferences;
    RecyclerView recyclerView;
    ImageView hlang;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<datahistory>history;
    ImageButton balik;
    LinearLayout saru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historylaporan);
     history = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.dataisi);
        hlang = findViewById(R.id.imageView13);
        recyclerView.setLayoutManager(layoutManager);
        saru = findViewById(R.id.dwiko);
        balik = findViewById(R.id.backbutt);
         preferences= this.getSharedPreferences("share", Context.MODE_PRIVATE);
        getdata();
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getdata() {
        String url = "http://gccestatemanagement.online/public/api/rusak/"+  preferences.getString("id","Empty");;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("Get data")) {
                                if (status.equals("get")) {
                                    if (jsonArray.length()==0){
                                        recyclerView.setVisibility(View.GONE);
                                        hlang.setVisibility(View.VISIBLE);
                                        saru.setVisibility(View.GONE);
                                    }
                                    for (int i = 0;i<jsonArray.length();i++){
                                        saru.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        hlang.setVisibility(View.GONE);
                                        datahistory datahis=new datahistory();
                                        JSONObject data = jsonArray.getJSONObject(i);
                                       datahis.setDeskripsi(data.getString("nama_masalah"));
                                       String statuse =data.getString("is_active");
                                       if (statuse.equals("1")){
                                           datahis.setStatus("Dalam Proses");
                                       }
                                       else {
                                           datahis.setStatus("Selesai");
                                       }
                                       datahis.setTanggal(data.getString("updated_at"));
                                       history.add(datahis);
                                    }
                                    mAdapter = new Adapter_history(historylaporan.this, (ArrayList<datahistory>) history);
                                    recyclerView.setAdapter(mAdapter);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Data Kosong" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}