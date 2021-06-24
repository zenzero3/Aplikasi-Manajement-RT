package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.text.Regex;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.Wargaregisterone;

public class ButtonEditakun extends AppCompatActivity {
    SharedPreferences sahre,ok;
    List<String>ronda;
    CircleImageView lion;
    ImageButton yo;
    ProgressBar progressBar;
    DatePickerDialog.OnDateSetListener datell;
    int i = 0;
    String idem;
    Regex regex;
    Spinner pendikkk,pernikk,tempatlahir,statustempat,jke,ker;
    String nmdesa,nix,namaleng,mailx,jk,hp,agam,lahir,tgllhir,statusper,kerja,pendik,tinggal,almleng,
            idkk,noblok,desaku, gambarpasang,id,idkepalaoke;
    TextInputLayout editnic,edtnamle,editmail,edithp,editlahireditalamatleng,lahirjalan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editakun);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert= new AlertDialog.Builder(ButtonEditakun.this);
                alert.setTitle("Data belum tersimpan yakin ingin kembali?");
                alert.setCancelable(false);

                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
        Button button = findViewById(R.id.button);
        lion= findViewById(R.id.profile_image3);
        yo = findViewById(R.id.ganti);
        progressBar = findViewById(R.id.loadding);

        SharedPreferences wau= ButtonEditakun.this.getSharedPreferences("lengkap diri",MODE_PRIVATE);
        idkk            = wau.getString("idnkk",null);
        idkepalaoke     = wau.getString("idkepala",null);
        nmdesa          = wau.getString("namadesa",null);
        nix             = wau.getString("nik",null);
        namaleng        = wau.getString("namalengkap",null);
        mailx           = wau.getString("mail",null);
        jk              = wau.getString("jk",null);
        hp              = wau.getString("hp",null);
        agam            = wau.getString("agama",null);
        lahir           = wau.getString("tempatlahir",null);
        statusper       = wau.getString("statuspernikahan",null);
        kerja           = wau.getString("pekerjaan",null);
        pendik          = wau.getString("pendidikan",null);
        tinggal         = wau.getString("tinggalstatus",null);
        almleng         = wau.getString("alamlengkap",null);
        noblok          = wau.getString("noblok",null);
        gambarpasang    = wau.getString("gambar",null);
        tgllhir         = wau.getString("tanggallahir",null);

        edtnamle = findViewById(R.id.editnamalengkap);
        editmail = findViewById(R.id.editemail);
        edithp   = findViewById(R.id.editnomerhp);
        lahirjalan = findViewById(R.id.edittanggallahir);
        editlahireditalamatleng = findViewById(R.id.editalamatlengkap);

        editlahireditalamatleng.getEditText().setText(almleng);
        edtnamle.getEditText().setText(namaleng);
        editmail.getEditText().setText(mailx);
        edithp.getEditText().setText(hp);
        lahirjalan.getEditText().setText(tgllhir);

    lahirjalan.getEditText().setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Calendar cal = Calendar.getInstance();
        int year,mont,day;
        year=cal.get(Calendar.YEAR);
        mont=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(ButtonEditakun.this, datell,year,mont,day);

        dialog.show();

    }
});
        datell = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int bulan = month+1;
                String data = year+"-"+bulan+"-"+dayOfMonth;
                lahirjalan.getEditText().setText(data);
                tgllhir = data;

            }
        };

        id= wau.getString("iduserone",null);
        getkerja();
        getlahir();
        final ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pernikahan, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pendidikan, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> omah = ArrayAdapter.createFromResource(getApplicationContext(), R.array.statustempattinggal, android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> omahxx = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Kelamin, android.R.layout.simple_spinner_dropdown_item);
        pendikkk = findViewById(R.id.editpendidikanterakhir);
        pernikk  = findViewById(R.id.editstatuspernikahan);
        statustempat = findViewById(R.id.statustempattinggal);
        jke          = findViewById(R.id.jeniskelamin);
        omahxx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jke.setAdapter(omahxx);
        if (jk!=null){
            int uk = omahxx.getPosition(jk);
            jke.setSelection(uk);
            jke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    jk= jke.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        pernikk.setAdapter(adap);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (statusper!=null);{
            int spinpos = adap.getPosition(statusper);
            pernikk.setSelection(spinpos);
        }
        pernikk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusper = pernikk.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pendikkk.setAdapter(ad);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (pendik!= null){
            int hot= ad.getPosition(pendik);
            pendikkk.setSelection(hot);
            pendikkk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pendik = pendikkk.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        statustempat.setAdapter(omah);
        omah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (tinggal!=null){
            int om = omah.getPosition(tinggal);
            statustempat.setSelection(om);
            statustempat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tinggal = statustempat.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        final Spinner mSpinner = findViewById(R.id.agama);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                regex =new Regex("^08[0-9]{9,}$");
               SharedPreferences preferences = getSharedPreferences("blood",MODE_PRIVATE);
                String id = preferences.getString("ide","Empty");
                almleng =editlahireditalamatleng.getEditText().getText().toString();
                namaleng=edtnamle.getEditText().getText().toString();
                mailx =editmail.getEditText().getText().toString();
                hp = edithp.getEditText().getText().toString();
                if (regex.toPattern().matcher(hp).matches()){
                    if (Patterns.EMAIL_ADDRESS.matcher(mailx).matches()){
                        if (almleng.equals("")){
                            Toast.makeText(getApplicationContext(),"Alamat Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                        }else if (namaleng.equals("")){
                            Toast.makeText(getApplicationContext(),"Nama Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                        }
                        getdata(id);
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Format No Telp salah",Toast.LENGTH_LONG).show();
                }


            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Agama, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        if (agam != null) {
            int spinnerPosition = adapter.getPosition(agam);
            mSpinner.setSelection(spinnerPosition);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    agam= mSpinner.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        Picasso.get()
                .load(gambarpasang)
                .into(lion);
        yo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahgambar();
            }
        });
    }

    private void getdata(String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+id;
        final ProgressDialog progressBar = new ProgressDialog(ButtonEditakun.this);
        progressBar.setMessage("Prosess");
        progressBar.show();
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
                                    String usere = data.getString("username").trim();
                                    String fulluser=data.getString("nama").trim();
                                    String level   = data.getString("id_level").trim();
                                    String hp   = data.getString("nohp").trim();
                                    String passw = data.getString("password").trim();
                                    String emailss = data.getString("email").trim();
                                    String emage = data.getString("id_image").trim();
                                    getimageurl(emage);
                                    progressBar.dismiss();

                                }
                            }else {
                                Toast.makeText(ButtonEditakun.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ButtonEditakun.this);
        requestQueue.add(stringRequest);

    }

    private void getimageurl(String emage) {

        final ProgressDialog progressBar = new ProgressDialog(ButtonEditakun.this);
        progressBar.setMessage("Loadding....");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/image/"+emage;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject json = new JSONObject(response);
                            JSONArray jsonarray = json.getJSONArray("data");

                            if (status.equals("get")) {
                                if (message.equals("Get data")) {
                                    for(int i=0; i < jsonarray.length(); i++) {
                                        JSONObject jonobject = jsonarray.getJSONObject(i);
                                         idem=jonobject.getString("id").trim();
                                        String path=jonobject.getString("path").trim();
                                    }
                                    progressBar.dismiss();
                                    uploademage(idem);


                                }
                            }else {
                                Toast.makeText(ButtonEditakun.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void uploademage(String emage) {
        String UrlLogin="http://gccestatemanagement.online/public/api/image/"+emage;
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data = result.getJSONObject("data");
                            if (message.equals("Post Created")) {
                                if (success.equals("true")) {
                                   String idem=data.getString("id").trim();
                                    String path=data.getString("path").trim();
                                    String id_image= data.getString("nama");
                                    gambarpasang = id_image;
                                    uploaduser();
                                }
                            }else {
                                Toast.makeText(ButtonEditakun.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ButtonEditakun.this, "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), lion.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private void uploaduser() {
        String Ur="http://gccestatemanagement.online/public/api/updatekepalakeluarga";
        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.PUT, Ur,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject result = new JSONObject(response);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data=result.getJSONObject("data");

                            if (success.equals("true")) {
                                if (message.equals("ubah data")) {
                                   updatenik(nix);
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
                params.put("id",id);
                params.put("id_image",gambarpasang);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);
    }

    private void updatenik(String nix) {
        String Ur="http://gccestatemanagement.online/public/api/uid/"+nix;
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
                                      updatekepala();
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
                params.put("nama_lengkap",namaleng);
                params.put("jenis_kelamin",jk);
                params.put("status_pernikahan",statusper);
                params.put("alamat",almleng);
                params.put("id_kerja",desaku);
                params.put("agama",agam);
                params.put("status_tempat",tinggal);
                params.put("tanggallahir",tgllhir);
                params.put("tempatlahir",lahir);
                params.put("pendidikan",pendik);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);


    }
    private void updatekepala() {
        final ProgressDialog progressBar = new ProgressDialog(ButtonEditakun.this);
        progressBar.setMessage("Loadding....");
        progressBar.show();
        String Ur="http://gccestatemanagement.online/public/api/kepala";
        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.PUT, Ur,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject result = new JSONObject(response);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data=result.getJSONObject("data");

                            if (message.equals("get data iuran")) {
                                if (success.equals("true")) {
                                    progressBar.dismiss();
                                    updatekk(idkk);

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
                params.put("id",idkepalaoke);
                params.put("mail",mailx);
                params.put("nama_lengkap",namaleng);
                params.put("alamat",almleng);
                params.put("nohp",hp);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);


    }

    private void updatekk(String idkk) {
        String UrlLogin="http://gccestatemanagement.online/public/api/family2/"+idkk;
        final ProgressDialog progressBar = new ProgressDialog(ButtonEditakun.this);
        progressBar.setMessage("Prosess");
        progressBar.show();
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
                                    String id = data.getString("id");
                                    progressBar.dismiss();
                                    updatekkoke(id);

                                }
                            }else {
                                Toast.makeText(ButtonEditakun.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButtonEditakun.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ButtonEditakun.this);
        requestQueue.add(stringRequest);
    }

    private void updatekkoke(String id) {
        final ProgressDialog progressBar = new ProgressDialog(ButtonEditakun.this);
        progressBar.setMessage("Loadding....");
        progressBar.show();
        String Ur="http://gccestatemanagement.online/public/api/kkubahoke/"+id;
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
                                    progressBar.dismiss();
                                    Toast.makeText(getApplicationContext(),"Berhasil Update Data",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }else {
                                progressBar.dismiss();
                                Toast.makeText(getApplicationContext(),"Gagal Update Warga", Toast.LENGTH_SHORT).show();
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
                params.put("nik",nix);
                params.put("nama",namaleng);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }

    private void tambahgambar() {

        final BottomSheetDialog dialog = new BottomSheetDialog(ButtonEditakun.this, R.style.CustomBottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottomsheetone);
        final LinearLayout foto = dialog.findViewById(R.id.kamera);
        final TextView back   = dialog.findViewById(R.id.cancel);
        final LinearLayout galeri = dialog.findViewById(R.id.ambilhp);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=1;
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

                dialog.dismiss();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=2;
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);

                dialog.dismiss();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
        if(resultCode != RESULT_CANCELED){
            if (requestCode == 1)
            {
                Uri selectedImage = data.getData();
                if (selectedImage!=null){
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    lion.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            }else {
                Toast.makeText(getApplicationContext(),"Batal Ambil Gambar dari Galerry",Toast.LENGTH_LONG).show();
            }
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if (photo!=null){
                lion.setImageBitmap(photo);
                }else {
                    Toast.makeText(getApplicationContext(),"Batal Ambil Gambar dari Kamera",Toast.LENGTH_LONG).show();
                }
            }

        }
        }else {
        }
    }
    private void getkerja() {
        ronda = new ArrayList<>();
        final List<String>mam = new ArrayList<>();
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
                                        ronda.add(prof);
                                        mam.add(profid);
                                    }
                                    ker = findViewById(R.id.editpekerjaan);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.itemoke,ronda);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    ker.setAdapter(adapter);
                                    if (kerja!=null){
                                        int ex = adapter.getPosition(kerja);
                                        ker.setSelection(ex);

                                        ker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                   desaku= mam.get(ker.getSelectedItemPosition());

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }

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
    private void getlahir() {
        String url = "http://gccestatemanagement.online/public/api/posing";
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
                            if (message.equals("List Data User")) {
                                if (status.equals("true")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        String profid=data.getString("prov_id");
                                        String lahir = data.getString("prov_name");
                                        lahiran.add(lahir);

                                    }
                                    tempatlahir= findViewById(R.id.edttempatlahir);
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(),R.layout.itemoke,lahiran);
                                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    tempatlahir.setAdapter(adapters);
                                   if (lahir!=null){
                                       int ye = adapters.getPosition(lahir);
                                       tempatlahir.setSelection(ye);
                                       tempatlahir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                           @Override
                                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                               lahir = tempatlahir.getSelectedItem().toString();
                                           }

                                           @Override
                                           public void onNothingSelected(AdapterView<?> adapterView) {

                                           }
                                       });
                                   }
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
    public void onBackPressed() {
        final AlertDialog.Builder alert= new AlertDialog.Builder(ButtonEditakun.this);
        alert.setTitle("Data belum tersimpan yakin ingin kembali?");
        alert.setCancelable(false);

        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
