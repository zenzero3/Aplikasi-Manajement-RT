package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.kerjaan;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtformpintar;

public class ButtontambahKK extends AppCompatActivity {
    RadioGroup grup;
    RadioButton satubutton;
    LinearLayout kks;
    ArrayList<String>ronda;
    Spinner agam,umuranggota,kawin,kerja,kk,pendik,statusomah;
    String namalengkap,jk="",
            nohp,email,agama,tempatlahir="",tanggla="",statusnikah,kerjaa,statuskk,pendix,tinggal,alam,nikor="",nikkk,notsame;
    TextView lahi, date;
    ArrayList<kerjaan>kerjaan;
    EditText nixe,namalengkp;
    DatePickerDialog.OnDateSetListener datell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tamnbahkk);
        lahi= findViewById(R.id.lahiran);
        grup = findViewById(R.id.grupgender);
        agam = findViewById(R.id.agam);
        kawin = findViewById(R.id.kawin);
        nixe = findViewById(R.id.nik);
        namalengkp= findViewById(R.id.lengkapnama);
        kerja = findViewById(R.id.kerjasono);
        kk= findViewById(R.id.kkstat);
        kks = findViewById(R.id.nikelodion);
        umuranggota = findViewById(R.id.umuranggota);
        pendik = findViewById(R.id.pendik);
        statusomah= findViewById(R.id.statusrumah);
        date =findViewById(R.id.tanggallahir);
        Button simpan= findViewById(R.id.button3);
        SharedPreferences wau= getSharedPreferences("lengkap diri",MODE_PRIVATE);
        final String idkk = wau.getString("idnkk",null);
        final String idrt = wau.getString( "id_rt",null);
        final String alaamat = wau.getString("alamlengkap",null);
        notsame = wau.getString("nik",null);
        lahi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlahir();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year,mont,day;
                year=cal.get(Calendar.YEAR);
                mont=cal.get(Calendar.MONTH);
                day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ButtontambahKK.this, datell,year,mont,day);

                dialog.show();
            }
        });
        getkerja();
        datell = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int bulan = month+1;
                String data = year+"-"+bulan+"-"+dayOfMonth;
                TextView iu = findViewById(R.id.tanggallahi);
                iu.setText(data);
                iu.setVisibility(View.VISIBLE);
                tanggla = data;
                Log.d("isi lahir", "onDateSet: "+tanggla);
            }
        };
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayAdapter<CharSequence> adapterss = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Umur,R.layout.itemoke);
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Agama, R.layout.itemoke);
     ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pernikahan, R.layout.itemoke);
     ArrayAdapter<CharSequence> ada = ArrayAdapter.createFromResource(getApplicationContext(), R.array.newanggotakk, R.layout.itemoke);
     ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pendidikan, R.layout.itemoke);
     ArrayAdapter<CharSequence> omah = ArrayAdapter.createFromResource(getApplicationContext(), R.array.statustempattinggal, R.layout.itemoke);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        umuranggota.setAdapter(adapterss);
        agam.setAdapter(adapter);
        umuranggota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ih=umuranggota.getSelectedItem().toString();
                if (ih.equals("Masukan Umur Anggota Keluarga")){
                    nikor = "Masukan Umur Anggota Keluarga";
                    kks.setVisibility(View.GONE);
                }
                else if (ih.equals("17 Tahun Keatas")){
                    kks.setVisibility(View.VISIBLE);
                    nikor = "17 Tahun Keatas";
                }else if (ih.equals("0-5 Tahun")||ih.equals("5-12 Tahun")||ih.equals("12-16 Tahun")){
                    nikor="Belum Memiliki Ktp";
                    nikkk="Belum Memiliki Ktp";
                   kks.setVisibility(View.GONE);
                }else {
                    nikor="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        agam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                agama = agam.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        omah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namalengkap = namalengkp.getText().toString();
                if (nikor.equals("")){
                    Toast.makeText(getApplicationContext(),"Tentukan Umur Anggota Keluarga",Toast.LENGTH_LONG).show();
                }else
                 if ( nikor.equals("Masukan Umur Anggota Keluarga")){
                    Toast.makeText(getApplicationContext(),"Tentukan Umur Anggota Keluarga",Toast.LENGTH_LONG).show();
                }
                else  if (nikor.equals("Belum Memiliki Ktp")){
                    nikkk="Belum Memiliki Ktp";
                     if (namalengkap.equals("")){
                         Toast.makeText(getApplicationContext(),"Nama Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                         namalengkp.setError("Nama Kosong");
                     }else if (jk.equals("")){
                         Toast.makeText(getApplicationContext(),"Pilih Jenis Kelamin",Toast.LENGTH_LONG).show();
                     }else if (tempatlahir.equals("")){
                         Toast.makeText(getApplicationContext(),"Tempat Lahir Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                     }else if (tanggla.equals("")){
                         Toast.makeText(getApplicationContext(),"Tanggal Lahir Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                     }else {
                         sendnik(idrt,idkk,alaamat);
                     }
                } else if (statuskk.equals("Masukan Status Dalam Kartu Keluarga")) {
                     Toast.makeText(getApplicationContext(), "Belum Memilih Status Dalam Kartu Keluarga", Toast.LENGTH_LONG).show();
                 }else if (nikor.equals("17 Tahun Keatas")){
                    nikkk = nixe.getText().toString();
                    if (nikkk.equals("")){
                        Toast.makeText(getApplicationContext(),"NIK Kosong",Toast.LENGTH_LONG).show();
                        nixe.setError("Nik Salah");
                    }
                   else if (nikkk.length()<15){
                        Toast.makeText(getApplicationContext(),"Nik Salah",Toast.LENGTH_LONG).show();
                        nixe.setError("Nik Salah");
                    }
                    else if (namalengkap.equals("")){
                        Toast.makeText(getApplicationContext(),"Nama Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                        namalengkp.setError("Nama Kosong");
                    }else if (jk.equals("")){
                        Toast.makeText(getApplicationContext(),"Pilih Jenis Kelamin",Toast.LENGTH_LONG).show();
                    }else if (tempatlahir.equals("")){
                        Toast.makeText(getApplicationContext(),"Tempat Lahir Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                    }else if (tanggla.equals("")){
                        Toast.makeText(getApplicationContext(),"Tanggal Lahir Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    }else {
                        sendnik(idrt,idkk,alaamat);
                    }

                }
                else if (namalengkap.equals("")){
                    Toast.makeText(getApplicationContext(),"Nama Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                    namalengkp.setError("Nama Kosong");
                 }else if (jk.equals("")){
                    Toast.makeText(getApplicationContext(),"Pilih Jenis Kelamin",Toast.LENGTH_LONG).show();
                 }else if (tempatlahir.equals("")){
                    Toast.makeText(getApplicationContext(),"Tempat Lahir Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                 }else if (tanggla.equals("")){
                    Toast.makeText(getApplicationContext(),"Tanggal Lahir Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                 }else {
                     sendnik(idrt,idkk,alaamat);
                 }
            }
        });

        kawin.setAdapter(adap);
        kk.setAdapter(ada);
        pendik.setAdapter(ad);
        statusomah.setAdapter(omah);

        kawin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusnikah = kawin.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        kk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statuskk = kk.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pendik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pendix = pendik.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        statusomah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tinggal = statusomah.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void sendnik(final String idrt, final String idkk, final String alamatlengkap) {
        String UrlLogin="http://gccestatemanagement.online/public/api/rtktp";
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
                                    sendkk(idkk);
                                }
                            }else {
                                Toast.makeText(ButtontambahKK.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ButtontambahKK.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButtontambahKK.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",nikkk);
                params.put("nkk",idkk);
                params.put("nama_lengkap",namalengkap);
                params.put("jenis_kelamin",jk);
                params.put("id_kerja",kerjaa);
                params.put("status_pernikahan",statusnikah);
                params.put("tempatlahir",tempatlahir);
                params.put("alamat",alamatlengkap);
                params.put("agama",agama);
                params.put("status_tempat",tinggal);
                params.put("id_rt",idrt);
                params.put("tanggallahir",tanggla);
                params.put("pendidikan",pendix);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendkk(final String idkk) {
        String UrlLogin="http://gccestatemanagement.online/public/api/rtkk";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");

                            if (message.equals("Post Created")) {
                                if (success.equals("true")) {
                                    Toast.makeText(getApplicationContext(),"Data Berhasil Terkirim Mohon Tunggu beberapa saat lagi",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }else {
                                Toast.makeText(ButtontambahKK.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ButtontambahKK.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButtontambahKK.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama",namalengkap);
                params.put("nkk",idkk);
                params.put("status_kk",statuskk);
                params.put("nik",nikkk);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getkerja() {
        kerjaan = new ArrayList<kerjaan>();
        ronda = new ArrayList<>();
        String url = "http://gccestatemanagement.online/public/api/kerja";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("List kerja")) {
                                if (status.equals("true")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String profid=data.getString("id");
                                        String prof = data.getString("nama_pekerjaan");
                                        kerjaan.add(new kerjaan(profid,prof));
                                        ronda.add(prof);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,ronda);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    kerja.setAdapter(adapter);
                                    kerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            kerjaa = kerjaan.get(kerja.getSelectedItemPosition()).getId();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

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
                Toast.makeText(getApplicationContext(), "Mohon Tunggu Sebentar Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6ImZhZWE3Y2Q2YWFhYjM1YmIyYmE4MjE3ZTgyNWNkODE5I");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void getlahir() {
        String url = "http://gccestatemanagement.online/public/api/kab";
        final ArrayList<String> lahiran = new ArrayList<>();
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("data kab")) {
                                if (status.equals("get")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String lahir = data.getString(  "city_name");
                                        lahiran.add(lahir);

                                    }
                                    final ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,lahiran);
                                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    final Dialog dialog = new Dialog(ButtontambahKK.this);
                                    dialog.setContentView(R.layout.customlahiran);
                                    dialog.getWindow().setLayout(650,800);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();
                                    EditText lahirsearch = dialog.findViewById(R.id.searchad);
                                    ListView listView = dialog.findViewById(R.id.mooo);
                                    listView.setAdapter(adapters);
                                    lahirsearch.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            adapters.getFilter().filter(s);
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            lahi.setText(adapters.getItem(position));
                                            lahi.setTextColor(getColor(R.color.ms_black));
                                            lahi.setTextSize(12);
                                            tempatlahir = lahi.getText().toString();
                                            dialog.dismiss();
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
                backe();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    private void backe() {
        finish();
    }

    public void checkButton(View view) {
        jk = "";
        int id = grup.getCheckedRadioButtonId();
        satubutton = (RadioButton) findViewById(id);
        if (satubutton.getText().equals("Laki-Laki")){
            jk="Laki-laki";
        }else {
            jk="Perempuan";
        }
        Log.d("jenis kelamin ", jk);


    }
}