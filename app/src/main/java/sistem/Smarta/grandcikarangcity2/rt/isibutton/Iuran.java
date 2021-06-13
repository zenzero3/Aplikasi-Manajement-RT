package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.Adapter_history;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.historylaporan;
import sistem.Smarta.grandcikarangcity2.model.Adapteriuran;
import sistem.Smarta.grandcikarangcity2.model.Adapternamaiuranwargart;
import sistem.Smarta.grandcikarangcity2.model.datahistory;
import sistem.Smarta.grandcikarangcity2.model.iurannamadesa;
import sistem.Smarta.grandcikarangcity2.model.iurannamawarga;
import sistem.Smarta.grandcikarangcity2.model.suratwarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtformpintar;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.Wargaregisterone;

public class Iuran extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener datell;
    String date="kosong",isiisd,iuranname,war,nikolo,nominal;
    ArrayList<String>dapat,isidapat,nominaldapt;
    EditText tiga;
    ArrayList<String> nama;
    Dialog dialogok;
    int dapt;
    ArrayList<String> nikelodion;
    BottomSheetDialog dialog;
     Spinner warga,aku;
     ArrayList<String> namawarga,nike,warganike;
     List<iurannamadesa> iuran;
     List<iurannamawarga> iurannamawargaList;
     RecyclerView.Adapter mdapter,adaptess;
     RecyclerView onerecyle,thworecylce;
     RecyclerView.LayoutManager layoutManager,layoutManager2;
    TextView kosongiuran,koteka;
    RecyclerView desaiuran;
    LinearLayout tampak,desatampak,onemild;
    SharedPreferences sahres;
    Handler handler = new Handler();
    int apiDelayed = 5*1000; //1 second=1000 milisecond, 5*1000=5seconds
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_iuranrt);
        layoutManager = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        onerecyle = findViewById(R.id.desaisi);
        thworecylce = findViewById(R.id.wargaisi);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView pluske=findViewById(R.id.tambahiuran);
        ImageView wargaiuran = findViewById(R.id.tambahwargaiuran);
        desatampak = findViewById(R.id.desalinear);
        koteka = findViewById(R.id.desaku);
        kosongiuran = findViewById(R.id.desakosong);
        desaiuran = findViewById(R.id.desaisi);
        onemild = findViewById(R.id.spinnerisiname);
        tampak = findViewById(R.id.iuranwargatampak);
        sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahres.getString("ide","empty");
        getdata(isiid);

        pluske.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog = new BottomSheetDialog(Iuran.this, R.style.CustomBottomSheetDialogTheme);
                dialog.setContentView(R.layout.bottomsheetiuran);
                warga = dialog.findViewById(R.id.spinner3);
                final TextView satu = dialog.findViewById(R.id.tanggaliuranoke);
                tiga = dialog.findViewById(R.id.iurannominal);
                final  Button empat = dialog.findViewById(R.id.butoniuran);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                getiuran();
                empat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dapt = Integer.parseInt(tiga.getText().toString());
                       if (tiga.getText().toString().isEmpty()){
                           Toast.makeText(Iuran.this,"Nominal Kosong",Toast.LENGTH_LONG).show();
                           tiga.setError("Nominal Tidak boleh Kosong");
                       }
                       else if  (date.equals("kosong")){
                           Toast.makeText(Iuran.this,"Tanggal Masih Kosong",Toast.LENGTH_LONG).show();
                       }else  if(dapt==0 ||dapt<=4000){
                           Toast.makeText(Iuran.this,"Minimal Nominal Rp5000",Toast.LENGTH_LONG).show();
                           tiga.setError("Minimal Nominal Rp5000");
                       } else {
                           nominal = tiga.getText().toString();
                           namawarga = new ArrayList<>();
                           nike= new ArrayList<>();
                           postdataiuran(isiisd);
                       }
                    }
                });

                satu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year,mont,day;
                        year=cal.get(Calendar.YEAR);
                        mont=cal.get(Calendar.MONTH);
                        day=cal.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dialoga = new DatePickerDialog(Iuran.this, datell,year,mont,day);
                        dialoga.show();
                        satu.setTextColor(getResources().getColor(R.color.ms_black));
                    }
                });


                datell = new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int bulan = month+1;
                        String datas = year+"-"+bulan+"-"+dayOfMonth;
                        date=datas;
                     satu.setText("Tanggal Mulai Iuran \n"+date);
                    }
                };



            }
        });

        wargaiuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogok= new Dialog(Iuran.this);
                dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogok.setContentView(R.layout.tambahwargaiuran);
                dialogok.setCancelable(true);
                dialogok.setCanceledOnTouchOutside(false);
                dialogok.show();
                getwargaok(isiisd);
                Button save = dialogok.findViewById(R.id.button4);
                save.setText("Tambah Warga");
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogok.dismiss();
                        newpost(war,nikolo);
                    }
                });
               

            }
        });


    }

    private void newpost(final String war, final String nikolo) {
        String UrlLogin="http://gccestatemanagement.online/public/api/dataiuranwarga";
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
                                  dialogok.dismiss();
                                }
                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",nikolo);
                params.put("namaiuran",iuranname);
                params.put("id_rt", isiisd);
                params.put("namawarga",war);
                params.put("nominaliuran",nominal);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getwargaok(String isiisd) {
       nama=new ArrayList<>();
       nikelodion = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/ktps/"+isiisd;
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
                                if (message.equals("get ktp")) {
                                    for (int i = 0 ;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String nik = data.getString("nik");
                                        String name = data.getString("nama_lengkap");
                                        nama.add(name);
                                        nikelodion.add(nik);
                                    }
                                    final Spinner kamu = dialogok.findViewById(R.id.spinner5);
                                    ArrayAdapter<String> adapte = new ArrayAdapter<>(getApplicationContext(),R.layout.itemoke,nama);
                                    adapte.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                    kamu.setAdapter(adapte);
                                    kamu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            war = kamu.getSelectedItem().toString();
                                            nikolo = nikelodion.get(kamu.getSelectedItemPosition());
                                            Log.d("isi nik ",nikelodion.get(kamu.getSelectedItemPosition()));
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });



                                }
                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Iuran.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        final String isiid=sahres.getString("ide","empty");
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
           /*     getdata(isiid);*/
                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void getiuran() {
        dapat = new ArrayList<String>();
        String UrlLogin="http://gccestatemanagement.online/public/api/dataiuran";
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
                                if (message.equals("data iuran")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String isian =data.getString("namaiuran");
                                        String idisian = data.getString("id");
                                        dapat.add(isian);
                                    }
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(),R.layout.itemoke,dapat);
                                    adapters.setDropDownViewResource(R.layout.itemoke);
                                    warga.setAdapter(adapters);
                                    warga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            iuranname=warga.getSelectedItem().toString();

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Iuran.this);
        requestQueue.add(stringRequest);

    }

    private void postdataiuran(final String isiisd) {
        String UrlLogin="http://gccestatemanagement.online/public/api/iurannama";
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
                                   dialog.dismiss();
                                   iuran(isiisd);
                                   getwarga(isiisd);
                                   Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan Mohon Menunggu sejenak",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama_iuran",iuranname);
                params.put("id_rt",isiisd);
                params.put("nominal",tiga.getText().toString());
                params.put("tangga_mulai",date);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getwarga(final String isiisd) {
        String UrlLogin="http://gccestatemanagement.online/public/api/ktps/"+isiisd;
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
                                if (message.equals("get ktp")) {
                                    for (int i = 0 ;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String nik = data.getString("nik");
                                        String name = data.getString("nama_lengkap");
                                        namawarga.add(name);
                                        nike.add(nik);

                                    }
                                    for (int i = 0; i<nike.size();i++){
                                        String warganame = namawarga.get(i);
                                        String nikename  = nike.get(i);
                                        postiuranwarga(warganame,nikename);
                                    }

                                }
                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Iuran.this);
        requestQueue.add(stringRequest);
    }

    private void postiuranwarga(final String warganame, final String nikename) {
        String UrlLogin="http://gccestatemanagement.online/public/api/dataiuranwarga";
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
                                    dialog.dismiss();
                                }
                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",nikename);
                params.put("namaiuran",iuranname);
                params.put("id_rt", isiisd);
                params.put("namawarga",warganame);
                params.put("nominaliuran",nominal);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
                                    String idesa= data.getString("desa").trim();
                                    iuran(isi);
                                    isiisd=isi;
                                    koteka.setText("List Iuran Desa "+idesa);
                                }
                            }else {
                                Toast.makeText(Iuran.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Iuran.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Iuran.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Iuran.this);
        requestQueue.add(stringRequest);

    }


    private void iuran(final String isi) {
        iuran = new ArrayList<>();
        isidapat = new ArrayList<>();
        nominaldapt = new ArrayList<>();
        aku = findViewById(R.id.spinner4);
        String url = "http://gccestatemanagement.online/public/api/iuran/"+isi;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("get iuran")) {
                                if (status.equals("true")) {
                                    int dataarray = jsonArray.length();
                                 if (dataarray==0){
                                     tampak.setVisibility(View.GONE);
                                     kosongiuran.setVisibility(View.VISIBLE);
                                     desatampak.setVisibility(View.GONE);
                                     aku.setVisibility(View.GONE);
                                 }else {
                                     aku.setVisibility(View.VISIBLE);
                                     onemild.setVisibility(View.VISIBLE);
                                     desatampak.setVisibility(View.VISIBLE);
                                     kosongiuran.setVisibility(View.GONE);
                                     for (int i = 0;i<jsonArray.length();i++){
                                         JSONObject data = jsonArray.getJSONObject(i);
                                         iurannamadesa iurandesa = new iurannamadesa();
                                         iurandesa.setNama(data.getString("nama_iuran"));
                                         iurandesa.setNominal(data.getString("nominal"));
                                         iurandesa.setIdiuran(data.getString("id"));
                                         iurandesa.setTanggalstart(data.getString("tangga_mulai"));
                                         iuran.add(iurandesa);
                                         isidapat.add(data.getString("nama_iuran"));
                                         nominaldapt.add(data.getString("nominal"));
                                     }

                                     ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(),R.layout.itemoke,isidapat);
                                     adapters.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                     aku.setAdapter(adapters);
                                     aku.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                         @Override
                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                             String nameisi= isidapat.get(aku.getSelectedItemPosition());
                                             iuranname     = isidapat.get(aku.getSelectedItemPosition());
                                             nominal       = nominaldapt.get(aku.getSelectedItemPosition());
                                          getwargaiuran(nameisi);
                                         }

                                         @Override
                                         public void onNothingSelected(AdapterView<?> parent) {

                                         }
                                     });
                                     onerecyle.setLayoutManager(layoutManager);
                                     mdapter = new Adapteriuran(Iuran.this,(ArrayList<iurannamadesa>) iuran);
                                     onerecyle.setAdapter(mdapter);
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

    private void getwargaiuran(String nameisi) {
        iurannamawargaList = new ArrayList<>();
        warganike = new ArrayList<>();
        String url = "http://gccestatemanagement.online/public/api/dataiuran/"+nameisi+"/"+isiisd;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("rtselesai")) {
                                if (status.equals("true")) {
                                    int dataarray = jsonArray.length();
                                    if (dataarray==0){
                                        tampak.setVisibility(View.GONE);
                                    }else {
                                       tampak.setVisibility(View.VISIBLE);
                                        for (int i = 0;i<jsonArray.length();i++){
                                            iurannamawarga iurannamawarga = new iurannamawarga();
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            iurannamawarga.setNamaiuran(data.getString("namaiuran"));
                                            iurannamawarga.setNamawarga(data.getString("namawarga"));
                                            iurannamawarga.setStatus(data.getString("statu"));
                                            iurannamawargaList.add(iurannamawarga);
                                            warganike.add(data.getString("namawarga").trim());
                                        }
                                        thworecylce.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                                        adaptess = new Adapternamaiuranwargart(Iuran.this,(ArrayList<iurannamawarga>)iurannamawargaList);
                                      thworecylce.setAdapter(adaptess);
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