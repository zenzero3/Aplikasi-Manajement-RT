package sistem.Smarta.grandcikarangcity2.rtpintar.warga;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.model.kerjaan;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtformpintar;

public class Wargaregisterone extends AppCompatActivity {
    Button masuk;
    RadioGroup grup;
    RadioButton satubutton;
    Spinner agama,nikah,kerja,sekolah,tinggal;
    ArrayList<String>ronda;
    Dialog dialosg;
    ImageView buktikk,buktiktp,buktinpwp;
    String id,hainun,jk, tempatlahir,nik,nokk,ide,bojo,skul,religion,manggrok,tanggallahir,idxx,stipbuktikk="kosong",stripbuktiktp="kosong",stripbuktinpwp="belum ada",idnp;
    TextView lahir,tmlahir;
    EditText nk,ni,name,hp,ema,al,nono,blok;
    DatePickerDialog.OnDateSetListener datell;
    SharedPreferences ok,preferences;
    ArrayList<kerjaan>kerjaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wargaregisterone);
        grup = findViewById(R.id.grupgender);
        lahir = findViewById(R.id.tanggallahir);
        SharedPreferences preferences = getSharedPreferences("blood",MODE_PRIVATE);
        idxx = preferences.getString("ide","Empty");
        String namax = preferences.getString("namalengakp","null");
        String mailx = preferences.getString("email",null);
        String hpku = preferences.getString("noh",null);
        buktikk = findViewById(R.id.imageView6);
        buktiktp = findViewById(R.id.imageView7);
        buktinpwp = findViewById(R.id.imageView8);
        lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year,mont,day;
                year=cal.get(Calendar.YEAR);
                mont=cal.get(Calendar.MONTH);
                day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Wargaregisterone.this, datell,year,mont,day);

                dialog.show();
            }
        });
        datell = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int bulan = month+1;
                String data = year+"-"+bulan+"-"+dayOfMonth;
                TextView iu = findViewById(R.id.tanggallahi);
                iu.setText(data);
                iu.setVisibility(View.VISIBLE);
                tanggallahir = data;
            }
        };

        ema = findViewById(R.id.mail);
        ni=findViewById(R.id.nik);
        nk = findViewById(R.id.nokk);
        name = findViewById(R.id.lengkapnama);
        hp = findViewById(R.id.hanphone);
        al = findViewById(R.id.lamat);
        nono = findViewById(R.id.rumahno);
        blok = findViewById(R.id.blok);
        ok = getSharedPreferences("rtcoy", Context.MODE_PRIVATE);
        id = ok.getString("rt","kosong");
        preferences = this.getSharedPreferences("blood", Context.MODE_PRIVATE);
        hainun = preferences.getString("ide","kosong");
        masuk = findViewById(R.id.regiskepalawarga);
        ImageView kelua= findViewById(R.id.backbuttwarga);
        agama = findViewById(R.id.agam);
        tmlahir = findViewById(R.id.lahiran);
        nikah = findViewById(R.id.kawin);
        kerja = findViewById(R.id.kerjasono);
        sekolah = findViewById(R.id.pendik);
        tinggal = findViewById(R.id.statusrumah);
        name.setText(namax);
        ema.setText(mailx);
        hp.setText(hpku);
        tmlahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlahir();
            }
        });
        buktikk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=2;

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);

            }
        });
        buktiktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 3);
            }
        });
        buktinpwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 4);
            }
        });
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendkeluarga();
            }
        });

        kelua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Wargaregisterone.this, R.array.Agama,R.layout.itemoke);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agama.setAdapter(adapter);
        agama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                religion= agama.getSelectedItem().toString();
                Log.d("agama", "onItemSelected: "+religion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> nikahan = ArrayAdapter.createFromResource(Wargaregisterone.this, R.array.pernikahan,R.layout.itemoke);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nikah.setAdapter(nikahan);
        nikah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bojo=nikah.getSelectedItem().toString();
                Log.d("nikah", "onItemSelected: "+bojo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> didik = ArrayAdapter.createFromResource(Wargaregisterone.this, R.array.pendidikan,R.layout.itemoke);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sekolah.setAdapter(didik);
        sekolah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            skul = sekolah.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> tinggalku = ArrayAdapter.createFromResource(Wargaregisterone.this, R.array.statustempattinggal,R.layout.itemoke);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tinggal.setAdapter(tinggalku);
        tinggal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manggrok = tinggal.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getkerja();
    }

    private void sendkeluarga() {
        if (ni.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"nik tidak boleh kosong",Toast.LENGTH_LONG).show();
            ni.setError("NIK kosong");
        }else if(nk.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Nomor KK tidak boleh kosong",Toast.LENGTH_LONG).show();
            nk.setError("NKK kosong");
        }else if (name.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Nama tidak boleh kosong",Toast.LENGTH_LONG).show();
            name.setError("Nama kosong");
        }else if (ema.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Email tidak boleh kosong",Toast.LENGTH_LONG).show();
            ema.setError("Email kosong");
        }else if (al.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Alamat tidak boleh kosong",Toast.LENGTH_LONG).show();
            al.setError("Alamat kosong");
        }else if (nono.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Nomor Rumah tidak boleh kosong",Toast.LENGTH_LONG).show();
            ni.setError("Nomor Rumah kosong");
        }else if (blok.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"blok tidak boleh kosong",Toast.LENGTH_LONG).show();
            ni.setError("Blok kosong");
        }else if (stipbuktikk.equals("kosong")){
            Toast.makeText(getApplicationContext(),"Masukan Bukti Kartu Keluarga",Toast.LENGTH_LONG).show();
        }else if (stripbuktiktp.equals("kosong")){
            Toast.makeText(getApplicationContext(),"Masukan Bukti Ktp",Toast.LENGTH_LONG).show();
        }else {
            nokk = nk.getText().toString();
            nik = ni.getText().toString();
            getrt();
            userkeluarga();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            if (requestCode == 2) {
                stipbuktikk ="ada";
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                Bitmap.createScaledBitmap(photo,60,800,true);
                buktikk.setImageBitmap(photo);
            }else if (requestCode == 3) {
                stripbuktiktp = "ada";
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                buktiktp.setImageBitmap(photo);
            }else if (requestCode == 4) {

                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                buktinpwp.setImageBitmap(photo);
                dialosg = new BottomSheetDialog(Wargaregisterone.this,R.style.CustomBottomSheetDialogTheme);
                dialosg.setContentView(R.layout.bottomsheetnpwp);
                final EditText isiidnpwp= dialosg.findViewById(R.id.editTextTextPersonName);
                Button apply      = dialosg.findViewById(R.id.button6);
                dialosg.setCancelable(false);
                dialosg.setCanceledOnTouchOutside(false);
                dialosg.show();
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = isiidnpwp.getText().length();
                        if (isiidnpwp.getText().toString().equals("")||i<14){
                            Toast.makeText(getApplicationContext(),"No Npwp Salah",Toast.LENGTH_LONG).show();
                            isiidnpwp.setError("Harap Masukan No NPWP yang benar");
                        }else {
                            stripbuktinpwp = "ada";
                            idnp = isiidnpwp.getText().toString();
                            dialosg.dismiss();
                        }
                    }
                });
            }
        }
    }
    private void userkeluarga() {
        String UrlLogin="http://gccestatemanagement.online/public/api/warga";
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
                                    updateuser();
                                    sendkk();
                                    sendnik();
                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Wargaregisterone.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama_lengkap",name.getText().toString());
                params.put("norumah",nono.getText().toString());
                params.put("blok",blok.getText().toString());
                params.put("alamat",al.getText().toString());
                params.put("id_rt",id);
                params.put("idone",idxx);
                params.put("nik",nik);
                params.put("nkk",nokk);
                params.put("nohp",hp.getText().toString());
                params.put("mail",ema.getText().toString());
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getrt() {
        String Ur="http://gccestatemanagement.online/public/api/getrt2/"+id;
        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.GET, Ur,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject result = new JSONObject(response);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data=result.getJSONObject("data");

                            if (message.equals("get rt")) {
                                if (success.equals("true")) {
                                    String nama = data.getString("nama_lengkap").trim();
                                    String rtku = data.getString("rt").trim();
                                    String rw   = data.getString("rw").trim();
                                    SharedPreferences.Editor editor = ok.edit();
                                    editor.putString("namapakrt",nama);
                                    editor.putString("rtku",rtku);
                                    editor.putString("rwku",rw);
                                    editor.apply();
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
                Toast.makeText(getApplicationContext(), "Jaringan Error" , Toast.LENGTH_SHORT).show();
            }
        }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }
    private void getlahir() {
        String url = "http://gccestatemanagement.online/public/api/kab";
        final ArrayList<String>lahiran = new ArrayList<>();
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
                                        String profid=data.getString( "city_id");
                                        String lahir = data.getString(  "city_name");
                                        lahiran.add(lahir);

                                    }
                                    final ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,lahiran);
                                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    final Dialog dialog = new Dialog(Wargaregisterone.this);
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
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            tmlahir.setText(adapters.getItem(position));
                                            tempatlahir =tmlahir.getText().toString();
                                            tmlahir.setTextColor(Color.parseColor("#000000"));
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
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    private void updateuser() {
        String Ur="http://gccestatemanagement.online/public/api/rtupdate/"+hainun;
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

                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Kirim Data Gagal Ulangi beberapa saat lagi" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Kirim Data Gagal Ulangi beberapa saat lagi" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_level","4");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
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
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.itemoke,ronda);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    kerja.setAdapter(adapter);
                                    kerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            ide = kerjaan.get(kerja.getSelectedItemPosition()).getId();
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    public void checkButton(View view) {
        int id = grup.getCheckedRadioButtonId();
        satubutton = (RadioButton) findViewById(id);
        if (satubutton.getText().equals("Laki-Laki")){
            jk="Laki-laki";
        }else {
            jk="Perempuan";
        }
        Log.d("jenis kelamin ", jk);


    }
    private void sendkk() {
        String UrlLogin="http://gccestatemanagement.online/public/api/rtkk";
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

                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Wargaregisterone.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nkk",nokk);
                params.put("nik",nik);
                params.put("nama",name.getText().toString());
                params.put("status_kk","Kepala Keluarga");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void sendnik() {
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
                                    Intent intent = new Intent(Wargaregisterone.this,HomeWarga.class);
                                    SharedPreferences jos = Wargaregisterone.this.getSharedPreferences("loss", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = jos.edit();
                                    editor.putString("nik",data.getString("nik"));
                                    editor.putString("fulluname",name.getText().toString());
                                    editor.apply();
                                    sendimage(nik,nokk,stripbuktinpwp);
                                    finish();
                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Wargaregisterone.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",nik);
                params.put("nama_lengkap",name.getText().toString());
                params.put("jenis_kelamin",jk);
                params.put("id_kerja",ide);
                params.put("status_pernikahan",bojo);
                params.put("tempatlahir",tempatlahir);
                params.put("alamat",al.getText().toString()+" no rumah "+nono.getText().toString()+" Blok  "+blok.getText().toString());
                params.put("agama",religion);
                params.put("status_tempat",manggrok);
                params.put("id_rt", id );
                params.put("nkk",nokk);
                params.put("tanggallahir",tanggallahir);
                params.put("pendidikan",skul);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }

    private void sendimage(final String nik, final String nokk, final String stripbuktinpwp) {
        if (stripbuktinpwp.equals("belum ada")){
            idnp="belum ada";
        }else {

        }
        String UrlLogin="http://gccestatemanagement.online/public/api/datatunjang";
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
                                    sendimagektp(nik,nokk,stripbuktinpwp);

                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Wargaregisterone.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ktp",nik);
                params.put("kk",nokk);
                params.put("npwp",idnp);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendimagektp(final String nik, final String nokk, final String nik1) {
        String Ur="http://gccestatemanagement.online/public/api/ktpimage";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Ur,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");

                            if (message.equals("upload ktp")) {
                                if (success.equals("true")) {
                                    if ( nik1.equals("belum ada")){
                                        sendimagekk(nokk);
                                    }else {
                                        sendimagenpwp(nik1);
                                    }

                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            Toast.makeText(Wargaregisterone.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Gagal Memasukan data Periksa Koneksi Internet Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",nik);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), buktiktp.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private void sendimagenpwp(String nik1) {
        String Ur="http://gccestatemanagement.online/public/api/npimage";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Ur,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");

                            if (message.equals("upload npwp")) {
                                if (success.equals("true")) {
                                    sendimagekk(nokk);

                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            Toast.makeText(Wargaregisterone.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Gagal Memasukan data Periksa Koneksi Internet Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("npwpid",idnp);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), buktinpwp.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);

    }

    private void sendimagekk(final String nokk) {
        String Ur="http://gccestatemanagement.online/public/api/kkimage";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Ur,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");

                            if (message.equals("upload kk")) {
                                if (success.equals("true")) {
                                    finish();
                                    Toast.makeText(getApplicationContext(),"Registrasi Berhasil",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(Wargaregisterone.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            Toast.makeText(Wargaregisterone.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wargaregisterone.this, "Gagal Memasukan data Periksa Koneksi Internet Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("kkid",nokk);
                params.put("nama",name.getText().toString());
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), buktikk.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }
}