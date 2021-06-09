package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Provinsi;
import sistem.Smarta.grandcikarangcity2.model.ViewPagerAdapter;
import sistem.Smarta.grandcikarangcity2.model.kabupaten;
import sistem.Smarta.grandcikarangcity2.model.suratwarga;
import sistem.Smarta.grandcikarangcity2.rt.Login;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmenthistorysurat.Fragmenall;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmenthistorysurat.Fragmenunsed;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtformpintar;

public class SuratRT extends AppCompatActivity {
    String eko,dwi;
    TextView date;
    ArrayList<suratwarga> surat;
    ArrayList<String> proving;
    String rtid,nikoko,data,ajukan,niname;
    SharedPreferences sahres;

    DatePickerDialog.OnDateSetListener datell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suratrt);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        date = findViewById(R.id.te2);
        data ="kosong";
        getwarga();
        Button lup = findViewById(R.id.ajukanoke);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year,mont,day;
                year=cal.get(Calendar.YEAR);
                mont=cal.get(Calendar.MONTH);
                day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SuratRT.this, datell,year,mont,day);

                dialog.show();
            }
        });
        datell = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int bulan = month+1;
               String datas = year+"-"+bulan+"-"+dayOfMonth;
               data=datas;
                date.setText("Tanggal Pemakaian \n"+data);
            }
        };
        final Spinner spinner = findViewById(R.id.spinner2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pengantar,R.layout.itemoke);
        adapter.setDropDownViewResource(R.layout.itemoke);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dwi = spinner.getSelectedItem().toString();
                ajukan = dwi;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", data);
                if (nikoko==null){
                    nikoko ="";
                    Toast.makeText(getApplicationContext(),"Anda Belum Memiliki Warga",Toast.LENGTH_LONG).show();
                }else if (ajukan.equals("Silahkan Pilih Surat Pengantar")){
                    Toast.makeText(getApplicationContext(),"Silahkan Pilih Surat Pengantar",Toast.LENGTH_LONG).show();
                }
                else if (nikoko.equals("")||nikoko.equals(null)){
                    Toast.makeText(getApplicationContext(),"Warga belum terpilih",Toast.LENGTH_LONG).show();
                }else if (data.equals("kosong")){
                    Toast.makeText(getApplicationContext(),"tanggal belum terpilih",Toast.LENGTH_LONG).show();
                }else {
                    senddatapengajuanrt();

                }

            }
        });

    }



    private void senddatapengajuanrt() {
        String UrlLogin="http://gccestatemanagement.online/public/api/suratrt";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("data tersimpan")) {
                                if (success.equals("true")) {
                                    Toast.makeText(getApplicationContext(),"Data terkirim",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }else {
                                Toast.makeText(SuratRT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(SuratRT.this, "Jaringan Bermasalah Mohon Periksa Jaringan Ana", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuratRT.this, "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nikoko);
                params.put("id_rt", rtid);
                params.put("tanggal_pakai",data);
                params.put("namapengajuan",ajukan);
                params.put("nama_warga",niname);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getwarga() {
        sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahres.getString("ide","empty");

        getdatart1(isiid);
    }
    private void getdatart1(String isiid) {
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
                                    datawarga(isi);
                                     rtid = isi;
                                }
                            }else {
                                Toast.makeText(SuratRT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(SuratRT.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuratRT.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SuratRT.this);
        requestQueue.add(stringRequest);

    }

    private void datawarga(String isi) {
        String url = "http://gccestatemanagement.online/public/api/wargadarirt/"+isi;
        surat =  new ArrayList<suratwarga>();
        proving = new ArrayList<String>();

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("get data warga")) {
                                if (status.equals("true")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String id = data.getString("id").trim();
                                        String warganeme= data.getString("nama_lengkap").trim();
                                        String nice = data.getString("nik").trim();
                                        surat.add(new suratwarga(id,warganeme,nice));
                                    }
                                    for (int i =0;i<surat.size();i++){
                                        proving.add(surat.get(i).pname);
                                    }

                                    final Spinner sit = findViewById(R.id.spinners);
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(),R.layout.itemoke,proving);
                                    adapters.setDropDownViewResource(R.layout.itemoke);
                                    sit.setAdapter(adapters);
                                    sit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            sit.getSelectedItem().toString();
                                            String idm =  surat.get(sit.getSelectedItemPosition()).pid;
                                            nikoko =surat.get(sit.getSelectedItemPosition()).niko;
                                            niname = surat.get(sit.getSelectedItemPosition()).pname;
                                            Log.d("TAG", nikoko+niname);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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