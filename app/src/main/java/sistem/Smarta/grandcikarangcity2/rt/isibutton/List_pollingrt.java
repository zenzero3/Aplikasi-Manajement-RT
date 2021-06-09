package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.Adapter;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Adapteriuran;
import sistem.Smarta.grandcikarangcity2.model.Adapterlistpollingrt;
import sistem.Smarta.grandcikarangcity2.model.iurannamadesa;
import sistem.Smarta.grandcikarangcity2.model.pollingrtmodule;

public class List_pollingrt extends AppCompatActivity {
SharedPreferences sahres;
    RecyclerView onercyle;
    List<String>monster;
    RecyclerView.Adapter mdapter;
    List<pollingrtmodule>pollingrtmodules;
    pollingrtmodule polling;
    LinearLayout sock;
    RecyclerView.LayoutManager layoutManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pollingsurvey);
        sock = findViewById(R.id.josssip);
//        String valid_until = "1/4/2021";
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date strDate = null;
//        Date currentDate = new Date();
//        try {
//            strDate = sdf.parse(valid_until);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (new Date().after(strDate)) {
//            int catalog_outdated = 1;
//            long diff = currentDate.getTime() - strDate.getTime();
//            long diffMonths = diff / (24 * 60 * 60 * 1000)+1;
//            Toast.makeText(List_pollingrt.this,""+diffMonths,Toast.LENGTH_LONG).show();
//
//        }
        layoutManager = new LinearLayoutManager(this);
        onercyle= findViewById(R.id.wow);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahres.getString("ide","empty");
         getdata(isiid);
    }

    private void getdata(String isiid) {
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
                                    iuran(isi);
                                }
                            }else {
                                Toast.makeText(List_pollingrt.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(List_pollingrt.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(List_pollingrt.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(List_pollingrt.this);
        requestQueue.add(stringRequest);
    }

    private void iuran(String isi) {
        pollingrtmodules=new ArrayList<>();
        String url = "http://gccestatemanagement.online/public/api/polling/"+isi;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("getpolling")) {
                                if (status.equals("true")) {
                                    int dataarray = jsonArray.length();
                                    if (dataarray==0){
                                        sock.setVisibility(View.VISIBLE);
                                    }else {
                                        onercyle.setVisibility(View.VISIBLE);
                                        sock.setVisibility(View.GONE);
                                        for (int i = 0;i<jsonArray.length();i++){
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            polling = new pollingrtmodule();
                                            polling.setNamapolling(data.getString("nama_polling"));
                                            polling.setDeskripsipolling(data.getString("deskripsi"));
                                             polling.setImage(data.getString("id_image"));
                                            pollingrtmodules.add(polling);
                                        }
                                        onercyle.setLayoutManager(layoutManager);
                                        mdapter = new Adapterlistpollingrt(List_pollingrt.this,(ArrayList<pollingrtmodule>)pollingrtmodules);
                                        onercyle.setAdapter(mdapter);


                                    }
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Koneksi error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Koneksi Bermasalah Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();

            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}