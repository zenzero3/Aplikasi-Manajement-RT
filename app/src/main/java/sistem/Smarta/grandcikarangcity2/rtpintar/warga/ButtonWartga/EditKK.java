package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
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

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.kerjaan;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.SuratRT;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.HomeWarga;

public class EditKK extends AppCompatActivity {
        String kkid;
    RadioGroup grup;
    DatePickerDialog.OnDateSetListener datell;
    RadioButton satubutton,dua;
    LinearLayout kks;
    TextView iu;
    Spinner agam,umuranggota,kawin,kerja,kk,pendik,statusomah;
    String idniks,idrt,namalengkap,jk="kosong",
            agamax,tempatlahirx="",tanggla="",statusnikah,kerjaa,statuskk,pendix,tinggal,alam,nikor="",nikkk,notsame;
    TextView lahi, date;
    ArrayList<sistem.Smarta.grandcikarangcity2.model.kerjaan> kerjaan;
    EditText nixe,namalengkp;
    ArrayList<String> ronda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkkkeluarga);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        lahi= findViewById(R.id.lahiran);
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
       iu = findViewById(R.id.tanggallahi);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert= new AlertDialog.Builder(EditKK.this);
                alert.setTitle("Data Tidak Tersimpan Yakin Kembali?");
                alert.setCancelable(false);

                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),KartuKeluargaWarga.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        Intent i = getIntent();
        kkid = i.getStringExtra("id");
        getkk(kkid);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdata();
            }
        });
    }

    private void checkdata() {
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
            }else if (tempatlahirx.equals("")){
                Toast.makeText(getApplicationContext(),"Tempat Lahir Tidak boleh Kosong",Toast.LENGTH_LONG).show();
            }else if (tanggla.equals("")){
                Toast.makeText(getApplicationContext(),"Tanggal Lahir Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }else {
                sendnik(idrt,kkid,idniks);
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
            }else if (tempatlahirx.equals("")){
                Toast.makeText(getApplicationContext(),"Tempat Lahir Tidak boleh Kosong",Toast.LENGTH_LONG).show();
            }else if (tanggla.equals("")){
                Toast.makeText(getApplicationContext(),"Tanggal Lahir Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            }else {
                sendnik(idrt,kkid,idniks);
            }

        }
        else if (namalengkap.equals("")){
            Toast.makeText(getApplicationContext(),"Nama Tidak boleh Kosong",Toast.LENGTH_LONG).show();
            namalengkp.setError("Nama Kosong");
        }else if (jk.equals("")){
            Toast.makeText(getApplicationContext(),"Pilih Jenis Kelamin",Toast.LENGTH_LONG).show();
        }else if (tempatlahirx.equals("")){
            Toast.makeText(getApplicationContext(),"Tempat Lahir Tidak boleh Kosong",Toast.LENGTH_LONG).show();
        }else if (tanggla.equals("")){
            Toast.makeText(getApplicationContext(),"Tanggal Lahir Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
        }else {
            sendnik(idrt,kkid,idniks);
        }
    }

public void sendnik(String rt, String koka, final String nikofinger){
    String Ur="http://gccestatemanagement.online/public/api/kkubahoke/"+koka;
    StringRequest volleyMultipartRequest = new StringRequest(Request.Method.PUT, Ur,
            new Response.Listener<String> () {

                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject result = new JSONObject(response);
                        String message = result.getString("message");
                        String success  = result.getString("success");
                        JSONObject data=result.getJSONObject("data");

                        if (message.equals("Data berhasil diubah")) {
                            if (success.equals("true")) {
                               updatektp(nikofinger);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
        }
    })
    {

        @Override
        public String getBodyContentType() {
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("nama",namalengkap);
            params.put("nik",nikkk);
            params.put("Content-Type","application/x-www-form-urlencoded");
            return params;
        }

    };
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(volleyMultipartRequest);


}

    private void updatektp(String nikofinger) {
        String Ur="http://gccestatemanagement.online/public/api/ktpkeluarga/"+nikofinger;
        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.PUT, Ur,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject result = new JSONObject(response);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data=result.getJSONObject("data");

                            if (message.equals("Data berhasil diubah")) {
                                if (success.equals("true")) {
                                   Toast.makeText(getApplicationContext(),"Update Data Berhasil",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),KartuKeluargaWarga.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama_lengkap",namalengkap);
                params.put("jenis_kelamin",jk);
                params.put("id_kerja",kerjaa);
                params.put("status_pernikahan",statusnikah);
                params.put("tempatlahir",tempatlahirx);
                params.put("alamat",alam);
                params.put("agama",agamax);
                params.put("status_tempat",tinggal);
                params.put(" tanggallahir",tanggla);
                params.put("pendidikan",pendix);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }

    private void getkk(String kkid) {
        String UrlLogin="http://gccestatemanagement.online/public/api/idkk/"+kkid;
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
                                if (message.equals("get data warga")) {
                                   String isi = data.getString("nama");
                                   String idkk = data.getString("nkk");
                                   String idnik = data.getString("nik");
                                    getwarga(idnik,idkk,isi);
                                }
                            }else {
                                Toast.makeText(EditKK.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EditKK.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditKK.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditKK.this);
        requestQueue.add(stringRequest);
    }

    private void getwarga(final String idnik, String idkk, String isi) {
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaktp/"+idnik+"/"+isi+"/"+idkk;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject data=jsonObject.getJSONObject("data");

                            if (status.equals("true")) {
                                if (message.equals("get data warga")) {
                                    String nik= data.getString("nik");
                                    idniks = data.getString("id");
                                    String nama = data.getString("nama_lengkap");
                                    String jenis= data.getString("jenis_kelamin");
                                    String kerja = data.getString("id_kerja");
                                    String nikah= data.getString("status_pernikahan");
                                    String tempatlahir=data.getString("tempatlahir");
                                    String tanggal = data.getString("tanggallahir");
                                    String alamat = data.getString("alamat");
                                    String agama = data.getString("agama");
                                    String stattempat= data.getString("status_tempat");
                                    String rtid = data.getString("id_rt");
                                    String Pendidik = data.getString("pendidikan");
                                    setdatawarga(nik,nama,jenis,kerja,nikah,tempatlahir,tanggal,alamat,agama,stattempat,rtid,Pendidik);
                                }
                            }else {
                                Toast.makeText(EditKK.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EditKK.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditKK.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditKK.this);
        requestQueue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setdatawarga(String nik, String nama, String jenis, String kerja, String nikah, String tempatlahir, String tanggal, String alamat, String agama, String stattempat, String rtid, String pendidik) {
        idrt=rtid;
        iu.setVisibility(View.VISIBLE);
        alam = alamat;
        tanggla=tanggal;
        grup = findViewById(R.id.grupgender);
    if (nik.equals("Belum Memiliki Ktp")){
        nixe.setVisibility(View.GONE);
    }else {
        nixe.setVisibility(View.VISIBLE);
        nixe.setText(nik);
    }
     namalengkp.setText(nama);
    jk = jenis;
    if (jk.equals("Laki-laki")){
        grup.check(R.id.laki);
    }else if (jk.equals("Perempuan")){
        grup.check(R.id.perm);
    }else {
        int id = grup.getCheckedRadioButtonId();
        satubutton =(RadioButton) findViewById(id);
        if (satubutton != null){
            if (satubutton.equals("Laki-Laki")){
                jk="Laki-laki";
            }else {
                jk="Perempuan";
            }
            Log.d("jenis kelamin ", String.valueOf(satubutton.getText()));
        }

    }
    lahi.setText(tempatlahir);
        lahi.setTextColor(getColor(R.color.ms_black));
        lahi.setTextSize(12);
        tempatlahirx = tempatlahir;
    lahi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getlahir();
        }
    });

        datell = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int bulan = month+1;
                String data = year+"-"+bulan+"-"+dayOfMonth;
                iu.setText(data);
                iu.setVisibility(View.VISIBLE);
                tanggla = data;
                Log.d("isi lahir", "onDateSet: "+tanggla);
            }
        };

    iu.setText(tanggal);
    date.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar cal = Calendar.getInstance();
            int year,mont,day;
            year=cal.get(Calendar.YEAR);
            mont=cal.get(Calendar.MONTH);
            day=cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(EditKK.this, datell,year,mont,day);

            dialog.show();
        }
    });
    kerjaa=kerja;
    getkerja();
        ArrayAdapter<CharSequence> adapterss = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Umur,R.layout.itemoke);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Agama, R.layout.itemoke);
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pernikahan, R.layout.itemoke);
        ArrayAdapter<CharSequence> ada = ArrayAdapter.createFromResource(getApplicationContext(), R.array.newanggotakk, R.layout.itemoke);
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pendidikan, R.layout.itemoke);
        ArrayAdapter<CharSequence> omah = ArrayAdapter.createFromResource(getApplicationContext(), R.array.statustempattinggal, R.layout.itemoke);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        omah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agam.setAdapter(adapter);
        umuranggota.setAdapter(adapterss);
        pendik.setAdapter(ad);
        kawin.setAdapter(adap);
        kk.setAdapter(ada);
        statusomah.setAdapter(omah);
        if (nikah != null) {
            int spinnerPosition = adap.getPosition(nikah);
            kawin.setSelection(spinnerPosition);
        }
        if (pendidik != null) {
            int spinnerPosition = ad.getPosition(pendidik);
            pendik.setSelection(spinnerPosition);
        }
        if (agama != null) {
            int spinnerPosition = adapter.getPosition(agama);
            agam.setSelection(spinnerPosition);
        }
        if (stattempat!= null) {
            int spinnerPosition = omah.getPosition(stattempat);
            statusomah.setSelection(spinnerPosition);
        }
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
                    nixe.setVisibility(View.VISIBLE);
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
                agamax = agam.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                                    final Dialog dialog = new Dialog(EditKK.this);
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
                                            tempatlahirx = lahi.getText().toString();
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


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert= new AlertDialog.Builder(EditKK.this);
        alert.setTitle("Data Tidak Tersimpan Yakin Kembali?");
        alert.setCancelable(false);

        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),KartuKeluargaWarga.class);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

}