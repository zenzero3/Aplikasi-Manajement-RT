package sistem.Smarta.grandcikarangcity2.rtpintar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kotlin.text.Regex;
import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.Usereditclass;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.Provinsi;
import sistem.Smarta.grandcikarangcity2.model.SessionManager;
import sistem.Smarta.grandcikarangcity2.model.Sessionrt;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.model.kabupaten;
import sistem.Smarta.grandcikarangcity2.model.kecamatan;
import sistem.Smarta.grandcikarangcity2.model.kelurahan;
import sistem.Smarta.grandcikarangcity2.model.kerjaan;
import sistem.Smarta.grandcikarangcity2.model.postal;
import sistem.Smarta.grandcikarangcity2.rt.Login;
import sistem.Smarta.grandcikarangcity2.rt.MainHomeRt;
import sistem.Smarta.grandcikarangcity2.rt.formregister1;

public class  Registerrtformpintar  extends AppCompatActivity {
    RadioGroup grup;
    RadioButton satubutton;
    ImageButton back;
    ImageView buktikk,buktiktp,buktinpwp;
    String ide,stipbuktikk="kosong",stripbuktiktp="kosong",stripbuktinpwp="belum ada",idnp;
    TextView lahi;
    EditText rwx,rtx,desah,nixe,nkkx,namalengkp,nohpx,nemail,alamatleng,norumx,noblox;
    SharedPreferences preferences;
    ArrayList<Provinsi>provinsi;
    ArrayAdapter<String> adapters;
    Dialog dialosg;
    ArrayList<sistem.Smarta.grandcikarangcity2.model.kabupaten> kabupatens;
    ArrayList<sistem.Smarta.grandcikarangcity2.model.kecamatan>kecamatans;
    ArrayList<sistem.Smarta.grandcikarangcity2.model.kelurahan>kelurahans;
  ArrayList<kerjaan>kerjaan;
    DatePickerDialog.OnDateSetListener datell;
   BottomSheetDialog dialog;
    ArrayList<postal>postals;
    TextView date;
    ProgressBar ekoki;
    ArrayList<String>proving,kabupaten,kecamatan,kelurahan,kodepos,ronda;
    LinearLayout kab,kec,kel,des,rtrw,titlert,isidiri,pos;
    Spinner spinprov,spinkab,spinkec,spinkel,pose,agam,lahir,kawin,kerja,kk,pendik,statusomah;
    String  id,provinsx,xabupaten,xecamatan,xelurahan,desa,xodepos,nokk,noktp,namalengkap,jk,
            nohp,email,agama,tempatlahir,tanggla,statusnikah,kerjaa,statuskk,pendix,tinggal,alam,rumah,blokir;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formpintarketuartregister);
        ArrayList<postal>postals=new ArrayList<postal>();
        ekoki= findViewById(R.id.progressBar2);
        provinsi= new ArrayList<Provinsi>();
        proving= new ArrayList<>();
        preferences = this.getSharedPreferences("blood", Context.MODE_PRIVATE);
        id = preferences.getString("ide","Empty");
        String namax = preferences.getString("namalengakp","null");
        String mailx = preferences.getString("email",null);
        String hpku = preferences.getString("noh",null);
        ceklevel(id);
        date = findViewById(R.id.tanggallahir);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year,mont,day;
                year=cal.get(Calendar.YEAR);
                mont=cal.get(Calendar.MONTH);
                day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Registerrtformpintar.this, datell,year,mont,day);

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
               tanggla = data;
                Log.d("isi lahir", "onDateSet: "+tanggla);
            }
        };
        getdata();
        Button daftar = findViewById(R.id.registerrt);
        titlert=findViewById(R.id.datart);
        isidiri=findViewById(R.id.datartdua);
        grup = findViewById(R.id.grupgender);
        back = findViewById(R.id.backbutt);
        pos=findViewById(R.id.kodepos);
        pose=findViewById(R.id.pos);
        kab= findViewById(R.id.kabupatenttampil);
        kec=findViewById(R.id.kecamatantampil);
        kel=findViewById(R.id.kelurahantampil);
        des=findViewById(R.id.desatampil);
        rtrw=findViewById(R.id.pilihrtrrwtampil);
        spinprov=findViewById(R.id.provinsi1);
        spinkab =findViewById(R.id.kabupaten1);
        spinkec =findViewById(R.id.Kecamatan1);
        spinkel=findViewById(R.id.kelurahan1);
        agam = findViewById(R.id.agam);
        kawin = findViewById(R.id.kawin);
        kerja = findViewById(R.id.kerjasono);
        kk= findViewById(R.id.kkstat);
        pendik = findViewById(R.id.pendik);
        statusomah= findViewById(R.id.statusrumah);
        desah = findViewById(R.id.desax);
        rtx = findViewById(R.id.rtx);
       rwx = findViewById(R.id.rwx);
       nixe = findViewById(R.id.nik);
       nkkx = findViewById(R.id.nokk);
       namalengkp = findViewById(R.id.lengkapnama);
       nohpx = findViewById(R.id.hanphone);
       nemail = findViewById(R.id.mail);
       alamatleng = findViewById(R.id.lamat);
       norumx = findViewById(R.id.rumahno);
       noblox = findViewById(R.id.blok);
        lahi= findViewById(R.id.lahiran);
        nohpx.setText(hpku);
        namalengkp.setText(namax);
        nemail.setText(mailx);
        buktikk = findViewById(R.id.imageView6);
        buktiktp = findViewById(R.id.imageView7);
        buktinpwp = findViewById(R.id.imageView8);

        lahi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlahir();
            }
        });
       dialog = new BottomSheetDialog(Registerrtformpintar.this, R.style.CustomBottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottomsheettiga);

        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Agama, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pernikahan, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> ada = ArrayAdapter.createFromResource(getApplicationContext(), R.array.statuskk, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pendidikan, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> omah = ArrayAdapter.createFromResource(getApplicationContext(), R.array.statustempattinggal, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agam.setAdapter(adapter);
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
        daftar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Regex regex =new Regex("^08[0-9]{9,}$");
        int ix =14;
        namalengkap = namalengkp.getText().toString();
        desa = desah.getText().toString();
        nokk = nkkx.getText().toString();
        noktp=nixe.getText().toString();
        alam = alamatleng.getText().toString();
        rumah= norumx.getText().toString();
        nohp = nohpx.getText().toString();
        email = nemail.getText().toString();
        blokir=noblox.getText().toString();


        if (rumah.equals("")){
            Toast.makeText(getApplicationContext(),"No Rumah  tidak boleh kosong",Toast.LENGTH_LONG).show();
        }
        else if (nokk.equals("")){
            Toast.makeText(getApplicationContext(),"No KK  tidak boleh kosong",Toast.LENGTH_LONG).show();
        }else if (noktp.equals("")){
            Toast.makeText(getApplicationContext(),"NO Ktp tidak boleh kosong",Toast.LENGTH_LONG).show();
        }else if (alam.equals("")){
            Toast.makeText(getApplicationContext(),"Alamat tidak boleh kosong",Toast.LENGTH_LONG).show();
        }else if (namalengkap.equals("")){
            Toast.makeText(getApplicationContext(),"Nama Lengkap tidak boleh kosong",Toast.LENGTH_LONG).show();
        }else if (rtx.getText().toString().isEmpty()&&rwx.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Data tidak boleh kosong",Toast.LENGTH_LONG).show();}
        else if (tanggla.equals("")){
            Toast.makeText(getApplicationContext(),"Tangggal Lahir tidak boleh kosong",Toast.LENGTH_LONG).show();
        }
        else if (statuskk.equals("Masukan Status Dalam Kartu Keluarga")){
            Toast.makeText(getApplicationContext(),"Masukan Status Kartu Keluarga",Toast.LENGTH_LONG).show();
    }else if (noktp.length()<ix){
            Toast.makeText(getApplicationContext(),"Nomor Ktp Tidak Valid"+noktp.length(),Toast.LENGTH_LONG).show();
        }
        else if (nokk.length()<ix){
            Toast.makeText(getApplicationContext(),"Nomor KK Tidak Valid",Toast.LENGTH_LONG).show();
        }else if (tempatlahir.equals("")){
            Toast.makeText(getApplicationContext(),"Pilih Tempat Lahir",Toast.LENGTH_LONG).show();
        }else if (stipbuktikk.equals("kosong")){
            Toast.makeText(getApplicationContext(),"Masukan Bukti Kartu Keluarga",Toast.LENGTH_LONG).show();
        }else if (stripbuktiktp.equals("kosong")){
            Toast.makeText(getApplicationContext(),"Masukan Bukti Ktp",Toast.LENGTH_LONG).show();
        }
        else {
            kirimdata();
        }
    }
});

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (data!=null){
        if(resultCode != RESULT_CANCELED){
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if (photo!=null){
                    stipbuktikk ="ada";
                Bitmap.createScaledBitmap(photo,60,800,true);
                buktikk.setImageBitmap(photo);
                }else {
                    Toast.makeText(getApplicationContext(),"Batal Ambil Gambar Kartu keluarga",Toast.LENGTH_LONG).show();
                }
            }else if (requestCode == 3) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if (photo!=null){
                    stripbuktiktp = "ada";
                buktiktp.setImageBitmap(photo);}
                else {
                    Toast.makeText(getApplicationContext(),"Batal Ambil Gambar KTP",Toast.LENGTH_LONG).show();
                }
            }else if (requestCode == 4) {

                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if (photo!=null){
                buktinpwp.setImageBitmap(photo);
                dialosg = new BottomSheetDialog(Registerrtformpintar.this,R.style.CustomBottomSheetDialogTheme);
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
            }
    }

    private void ceklevel(String id) {
            String UrlLogin="http://gccestatemanagement.online/public/api/register/"+id;
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
                                    if (message.equals("Data user")) {
                                        String id =data.getString("id").trim();
                                        String user = data.getString("username").trim();
                                        String fulluser=data.getString("nama").trim();
                                        String level   = data.getString("id_level").trim();
                                        String hp   = data.getString("nohp").trim();
                                        String email = data.getString("email").trim();
                                        if (level.equals("3")){
                                          Toast.makeText(getApplicationContext(),"Akun Sudah Registrasi",Toast.LENGTH_LONG).show();
                                          finish();
                                        }else {
                                            ;
                                        }


                                        ekoki.setVisibility(View.GONE);
                                    }
                                }else {
                                    Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(Registerrtformpintar.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registerrtformpintar.this, "Username Belum Terdaftar" , Toast.LENGTH_SHORT).show();
                }
            }){
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    private void kirimdata() {
    sendrt();

    }

    private void updateuser() {
        String Ur="http://gccestatemanagement.online/public/api/rtupdate/"+id;
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
                                    SharedPreferences.Editor editor = preferences.edit();
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
                Toast.makeText(getApplicationContext(), "Username Atau Password Salah" , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_level","3");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }

    private void sendrt() {
        if (desa.equals("")){
            desa = "Tidak Ada Pedukuhan";
        }
        String UrlLogin="http://gccestatemanagement.online/public/api/rt";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (success.equals("true")) {
                                if (message.equals("Post Created")) {
                                    ide = data.getString("id");
                                    sendnik();
                                }
                            }else {
                                Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Registerrtformpintar.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registerrtformpintar.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put( "nama_lengkap",namalengkap);
                params.put("id_user",id);
                params.put("provinsi",provinsx);
                params.put("kecamatan",xecamatan);
                params.put("kabupaten",xabupaten);
                params.put("kelurahan",xelurahan);
                params.put("desa",desa);
                params.put("kodepos",xodepos);
                params.put("rt",rtx.getText().toString());
                params.put("rw",rwx.getText().toString());
                params.put("nik",noktp);
                params.put("nk",nokk);
                params.put("email",email);
                params.put("nohp",nohp);
                params.put("is_active","0");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
                                    updateuser();
                                    dialog.show();
                                    final Button selesai =dialog.findViewById(R.id.selesai);
                                    selesai.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                           sendimage(noktp,nokk,stripbuktinpwp);
                                        }
                                    });
                                }
                            }else {
                                Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Registerrtformpintar.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registerrtformpintar.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama",namalengkap);
                params.put("nkk",nokk);
                params.put("status_kk",statuskk);
                params.put("nik",noktp);
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
                                    sendkk();

                                }
                            }else {
                                Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Registerrtformpintar.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registerrtformpintar.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",noktp);
                params.put("nkk",nokk);
                params.put("nama_lengkap",namalengkap);
                params.put("jenis_kelamin",jk);
                params.put("id_kerja",kerjaa);
                params.put("status_pernikahan",statusnikah);
                params.put("tempatlahir",tempatlahir);
                params.put("alamat",desa+" no "+rumah+" Blok "+blokir);
                params.put("agama",agama);
                params.put("status_tempat",tinggal);
                params.put("id_rt", ide );
                params.put("tanggallahir",tanggla);
                params.put("pendidikan",pendix);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void sendimage(final String noktp, final String nokk, final String nik) {
        if (nik.equals("belum ada")){
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
                                            sendimagektp(noktp,nokk,nik);

                                    }
                                }else {
                                    Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(Registerrtformpintar.this, "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registerrtformpintar.this, "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("ktp",noktp);
                    params.put("kk",nokk);
                    params.put("npwp",idnp);
                    return params;
                }};
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    private void sendimagenpwp(final String stripbuktinpwp) {
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
                                ekoki.setVisibility(View.GONE);
                                Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            ekoki.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            Toast.makeText(Registerrtformpintar.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ekoki.setVisibility(View.GONE);
                Toast.makeText(Registerrtformpintar.this, "Gagal Memasukan data Periksa Koneksi Internet Anda" , Toast.LENGTH_SHORT).show();
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

    private void sendimagektp(final String s, final String nokk, final String noktp) {
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
                                    if (noktp.equals("belum ada")){
                                        sendimagekk(nokk);
                                    }else {
                                        sendimagenpwp(noktp);
                                    }

                                }
                            }else {
                                ekoki.setVisibility(View.GONE);
                                Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            ekoki.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            Toast.makeText(Registerrtformpintar.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ekoki.setVisibility(View.GONE);
                Toast.makeText(Registerrtformpintar.this, "Gagal Memasukan data Periksa Koneksi Internet Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",s);
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
                                ekoki.setVisibility(View.GONE);
                                Toast.makeText(Registerrtformpintar.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            ekoki.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            Toast.makeText(Registerrtformpintar.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ekoki.setVisibility(View.GONE);
                Toast.makeText(Registerrtformpintar.this, "Gagal Memasukan data Periksa Koneksi Internet Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("kkid",nokk);
                params.put("nama",namalengkap);
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
                                    ekoki.setVisibility(View.GONE);
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String profid=data.getString( "city_id");
                                        String lahir = data.getString(  "city_name");
                                        lahiran.add(lahir);

                                    }
                                    final ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,lahiran);
                                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    final Dialog dialog = new Dialog(Registerrtformpintar.this);
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
                                            lahi.setText(adapters.getItem(position));
                                            tempatlahir = lahi.getText().toString();
                                            lahi.setTextColor(Color.parseColor("#000000"));
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
                                    ekoki.setVisibility(View.GONE);
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


    private void getdata() {

        String url = "http://gccestatemanagement.online/public/api/posing";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("List Data User")) {
                                ekoki.setVisibility(View.GONE);
                                kab.setVisibility(View.VISIBLE);
                                if (status.equals("true")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String profid=data.getString("prov_id");
                                        String prof = data.getString("prov_name");
                                        provinsi.add(new Provinsi(profid,prof));
                                    }
                                  for (int i =0;i<provinsi.size();i++){
                                    proving.add(provinsi.get(i).pname);
                                  }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,proving);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinprov.setAdapter(adapter);
                                    spinprov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            spinprov.getSelectedItem().toString();
                                            kabupaten=new ArrayList<>();
                                          String id =  provinsi.get(spinprov.getSelectedItemPosition()).pid;
                                          provinsx = provinsi.get(spinprov.getSelectedItemPosition()).pname;
                                          datakab(id);
                                            Log.d("isi id ",id);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                    getkerja();

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
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                ekoki.setVisibility(View.GONE);
                kab.setVisibility(View.GONE);
                kec.setVisibility(View.GONE);
                kel.setVisibility(View.GONE);
                des.setVisibility(View.GONE);
                rtrw.setVisibility(View.GONE);
                titlert.setVisibility(View.GONE);
                isidiri.setVisibility(View.GONE);
                pos.setVisibility(View.GONE);
                backe();

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

    private void backe() {
        finish();
    }

    private void datakab(String id) {
        kabupatens  = new ArrayList<kabupaten>();
        String url = "http://gccestatemanagement.online/public/api/pos/"+id;
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
                                    kab.setVisibility(View.VISIBLE);
                                    kec.setVisibility(View.VISIBLE);
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String kabid=data.getString("city_id");
                                        String kabar= data.getString("city_name");
                                       kabupatens.add(new kabupaten(kabid,kabar));
                                       kabupaten.add(kabar);
                                    }

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,kabupaten);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinkab.setAdapter(adapter);
                                spinkab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        spinkab.getSelectedItem().toString();
                                        String id =  kabupatens.get(spinkab.getSelectedItemPosition()).getCityid();
                                        xabupaten = kabupatens.get(spinkab.getSelectedItemPosition()).getCityname();
                                        getkecamatan(id);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                ekoki.setVisibility(View.GONE);
                kab.setVisibility(View.GONE);
                kec.setVisibility(View.GONE);
                kel.setVisibility(View.GONE);
                des.setVisibility(View.GONE);
                rtrw.setVisibility(View.GONE);
                titlert.setVisibility(View.GONE);
                isidiri.setVisibility(View.GONE);
                pos.setVisibility(View.GONE);
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

    private void getkecamatan(String id) {
    kecamatans = new ArrayList<kecamatan>();
    kecamatan = new ArrayList<>();
        String url = "http://gccestatemanagement.online/public/api/poskec/"+id;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("data kec")) {
                                kel.setVisibility(View.VISIBLE);
                                if (status.equals("get")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String kabid=data.getString("dis_id");
                                        String kabar= data.getString("dis_name");
                                        kecamatans.add(new kecamatan(kabid,kabar));
                                        kecamatan.add(kabar);
                                    }

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,kecamatan);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinkec.setAdapter(adapter);
                                spinkec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        spinkab.getSelectedItem().toString();
                                        String id =  kecamatans.get(spinkec.getSelectedItemPosition()).getKecid();
                                        xecamatan = kecamatans.get(spinkec.getSelectedItemPosition()).getKecname();
                                        getkeluraha(id);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                            backe();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                ekoki.setVisibility(View.GONE);
                kab.setVisibility(View.GONE);
                kec.setVisibility(View.GONE);
                kel.setVisibility(View.GONE);
                des.setVisibility(View.GONE);
                rtrw.setVisibility(View.GONE);
                titlert.setVisibility(View.GONE);
                isidiri.setVisibility(View.GONE);
                pos.setVisibility(View.GONE);
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

    private void getkeluraha(String id) {
    kelurahans = new ArrayList<kelurahan>();
    kelurahan = new ArrayList<>();
        String url = "http://gccestatemanagement.online/public/api/poskel/"+id;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("data kel")) {
                                if (status.equals("get")) {
                                    des.setVisibility(View.VISIBLE);
                                    pos.setVisibility(View.VISIBLE);
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String kabid=data.getString("subdis_id");
                                        String kabar= data.getString("subdis_name");
                                        kelurahans.add(new kelurahan(kabid,kabar));

                                        kelurahan.add(kabar);
                                    }

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,kelurahan);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinkel.setAdapter(adapter);
                                spinkel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        spinkel.getSelectedItem().toString();
                                        String id =  kelurahans.get(spinkel.getSelectedItemPosition()).getKelurid();
                                        xelurahan = kelurahans.get(spinkel.getSelectedItemPosition()).getKelurname();
                                        getpostal(id);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Mohon Tunggu Sebentar Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                ekoki.setVisibility(View.GONE);
                kab.setVisibility(View.GONE);
                kec.setVisibility(View.GONE);
                kel.setVisibility(View.GONE);
                des.setVisibility(View.GONE);
                rtrw.setVisibility(View.GONE);
                titlert.setVisibility(View.GONE);
                isidiri.setVisibility(View.GONE);
                pos.setVisibility(View.GONE);
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

    private void getpostal(String id) {
   postals = new ArrayList<postal>();
   kodepos = new ArrayList<>();
   kodepos.add(0,"Tentukan KodePos");
        String url = "http://gccestatemanagement.online/public/api/pospos/"+id;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("datapos")) {
                                if (status.equals("get")) {
                                    rtrw.setVisibility(View.VISIBLE);
                                    titlert.setVisibility(View.VISIBLE);
                                    isidiri.setVisibility(View.VISIBLE);
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String kabid=data.getString("postal_id");
                                        String kabar= data.getString("postal_code");
                                        postals.add(new postal(kabid,kabar));
                                        kodepos.add(kabar);
                                    }

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,kodepos);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                pose.setAdapter(adapter);
                                pose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        pose.getSelectedItem().toString();
                                        xodepos = kodepos.get(pose.getSelectedItemPosition());

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Mohon Tunggu Sebentar Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah harap Cek Koneksi Anda" , Toast.LENGTH_SHORT).show();
                ekoki.setVisibility(View.GONE);
                kab.setVisibility(View.GONE);
                kec.setVisibility(View.GONE);
                kel.setVisibility(View.GONE);
                des.setVisibility(View.GONE);
                rtrw.setVisibility(View.GONE);
                titlert.setVisibility(View.GONE);
                isidiri.setVisibility(View.GONE);
                pos.setVisibility(View.GONE);
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
}
