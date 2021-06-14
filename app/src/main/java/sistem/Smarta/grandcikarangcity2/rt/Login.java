package sistem.Smarta.grandcikarangcity2.rt;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.SessionManager;

public class Login  extends AppCompatActivity {

    SharedPreferences sahre,blodd;
    SessionManager sessionManager;
    private TextInputEditText username, password;
    private TextInputLayout email, passworde;
    private int STORAGE_PERMISSION_CODE = 1;
    private int camera = 1,iko=0;
    private int internal= 1;
    private int lokasion= 1;
    private int internet = 1;
    int login=0,register=0;
    String izin="gagal";
    private int Call= 1,masukke=1;
    SharedPreferences eko ;
    int wool;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        eko = getSharedPreferences("log",Context.MODE_PRIVATE);
        wool = eko.getInt("isi",0);
        izin = eko.getString("izin","gagal");
        if (masukke == 1){
            if (wool ==0){
                getpermission();
            }
            getpermission();
        }
        Button satu = (Button) findViewById(R.id.buttonlogin);
        Button dua = (Button) findViewById(R.id.button2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        passworde = findViewById(R.id.passworde);
        sessionManager = new SessionManager(this);
        sahre = getSharedPreferences("share", Context.MODE_PRIVATE);
        blodd = getSharedPreferences("blood",Context.MODE_PRIVATE);
        satu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpermission();
                if (izin.equals("gagal")){
                    getpermission();
                }else {
                login = 1;
                register =0;
               if (masukke == 1){
                   if (wool == 0){
                       getalertdialog();
                   }else {
                       if (username.getText().toString().isEmpty()){
                           Toast.makeText(getApplicationContext(), "Username Kosong",Toast.LENGTH_LONG).show();
                           username.setError("Email Tidak boleh kosong");
                       }else if (password.getText().toString().isEmpty()){
                           Toast.makeText(getApplicationContext(), "Password Kosong",Toast.LENGTH_LONG).show();
                           password.setError("Password Tidak boleh Kosong");
                       }else {
                           email.setErrorEnabled(false);
                           passworde.setErrorEnabled(false);
                        Logines();
                       }
                   }
                }else if (masukke==2){
                   if (wool == 0){
                       getalertdialog();
                   }else {
                       if (username.getText().toString().isEmpty()){
                           Toast.makeText(getApplicationContext(), "Username Kosong",Toast.LENGTH_LONG).show();
                           username.setError("Email Tidak boleh kosong");
                       }else if (password.getText().toString().isEmpty()){
                           Toast.makeText(getApplicationContext(), "Password Kosong",Toast.LENGTH_LONG).show();
                           password.setError("Password Tidak boleh Kosong");
                       }else {
                           email.setErrorEnabled(false);
                           passworde.setErrorEnabled(false);
                          Logines();
                       }
                   }
                }

                 }
            }
        });


        dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpermission();
                register=1;
                login = 0;
                if (izin.equals("gagal")){
                    getpermission();
                }else {
                if (masukke == 1){
                    if (wool == 0){
                        getalertdialog();
                    }else {
                        Intent regis = new Intent(Login.this, formregister1.class);
                        startActivity(regis);
                    }
                }else if (masukke==2){
                    if (wool == 0){
                        getalertdialog();
                    }else {
                        Intent regis = new Intent(Login.this, formregister1.class);
                        startActivity(regis);
                    }
                }
            }}
        });

    }

    private void getalertdialog() {
        if (wool==2){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    if (login==1 && register==0){
                        if (username.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Username Kosong",Toast.LENGTH_LONG).show();
                            username.setError("Email Tidak boleh kosong");
                        }else if (password.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Password Kosong",Toast.LENGTH_LONG).show();
                            password.setError("Password Tidak boleh Kosong");
                        }else {
                            email.setErrorEnabled(false);
                            passworde.setErrorEnabled(false);
                            Logines();
                        }
                    }else {
                        Intent regis = new Intent(Login.this, formregister1.class);
                        startActivity(regis);
                    }
                }
                if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){

                }


                if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED){


                }
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("Warning Permission Needed")
                            .setMessage("Membutuhkan perizinan aplikasi")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor edit = eko.edit();
                                    wool =2;
                                    edit.putInt("isi",2);
                                    edit.apply();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",getPackageName(),null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    dialog.dismiss();

                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor edit = eko.edit();
                                    wool =1;
                                    edit.putInt("isi",2);
                                    edit.apply();
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            }else {
                if (login==1 && register==0){
                    if (username.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Username Kosong",Toast.LENGTH_LONG).show();
                        username.setError("Email Tidak boleh kosong");
                    }else if (password.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Kosong",Toast.LENGTH_LONG).show();
                        password.setError("Password Tidak boleh Kosong");
                    }else {
                        email.setErrorEnabled(false);
                        passworde.setErrorEnabled(false);
                        Logines();
                    }
                }else {
                    Intent regis = new Intent(Login.this, formregister1.class);
                    startActivity(regis);
                }

            }
        } else {

        new AlertDialog.Builder(this)
                .setTitle("Warning Permission Needed")
                .setMessage("Membutuhkan perizinan aplikasi")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor edit = eko.edit();
                            wool =2;
                            edit.putInt("isi",2);
                            edit.apply();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getPackageName(),null);
                            intent.setData(uri);
                            startActivity(intent);
                            dialog.dismiss();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
        }
    }

    private void getinternalstorage() {
        if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            masukke=2;
        }
        else {
            requestinternal();
        }
    }

    private void requestinternal() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan internal")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, internal);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, internal);
        }
    }

    private void getcamera() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
            getpermissionstorage();
        }
        else {
            requestcamera();
        }

        }else {
            getpermissionstorage();
        }
    }

    private void requestcamera() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[] {Manifest.permission.CAMERA}, camera);
                            getpermissionstorage();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, camera);
        }
    }

    private void getpermission() {
        if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            getcall();

        }
        if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            getcall();

        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED){
               getcall();

            }
            else {
                requestloc();
            }
        }else {
            getcall();
        }

    }

    private void getpermissionstorage() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            getinternalstorage();
        }
        else {
            requestExternal();
        }
        }else {
            getinternalstorage();
        }
    }

    private void getcall() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        if (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            getcamera();
        }
        else {
            requestcall();
        }

        }else{
            getcamera();
        }
    }

    private void requestcall() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan Telp")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getpermissionstorage();
                            getinternalstorage();
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[] {Manifest.permission.CALL_PHONE}, Call);
                            getcamera();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CALL_PHONE}, Call);
        }
    }


    private void requestExternal() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan Galerry")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            getinternalstorage();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private void requestloc() {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("Aplikasi Membutuhkan perizinan Lokasi")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Login.this,
                                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, lokasion);
                                ActivityCompat.requestPermissions(Login.this,
                                        new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, lokasion);
                                ActivityCompat.requestPermissions(Login.this,
                                        new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, lokasion);
                                dialog.dismiss();
                                getcall();

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, lokasion);
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, lokasion);
                ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, lokasion);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE && requestCode == lokasion && requestCode == internal&& requestCode==internet && requestCode==camera
                && requestCode== Call)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                masukke=2;
                SharedPreferences.Editor editors = eko.edit();
                editors.putInt("isi", 1);
                editors.putString("izin","sukses");
                editors.apply();
                editors.commit();
                izin="sukses";
            } else {
                masukke=1;
                SharedPreferences.Editor editors = eko.edit();
                editors.putInt("isi", 0);
                editors.apply();
                editors.commit();
            }
        }else {
            masukke=1;
            SharedPreferences.Editor editors = eko.edit();
            editors.putInt("isi", 0);
            editors.apply();
            editors.commit();
        }
    }

    private void Logines(){
        getpermission();
        final String user = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
            String UrlLogin="http://gccestatemanagement.online/public/api/login";
       StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try{
                           JSONObject jsonObject = new JSONObject(response);
                           String message = jsonObject.getString("message");
                           String success  = jsonObject.getString("success");
                           JSONObject data    = jsonObject.getJSONObject("data");

                           if (message.equals("Login Success")) {
                               if (success.equals("true")) {
                                   String id =data.getString("id").trim();
                                   String hp   = data.getString("nohp").trim();
                                   String id_level   = data.getString("nohp").trim();
                                   String username = data.getString("username").trim();
                                   String lengkapi = data.getString("nama").trim();
                                   sessionManager.createSession(id);
                                   SharedPreferences.Editor Edis = blodd.edit();
                                   Edis.putString("ide",id);
                                   Edis.putString("noh",hp);
                                   Edis.putString("username",username);
                                   Edis.putString("id_level",id_level);
                                   Edis.putString("namalengakp",lengkapi);
                                   Edis.putString("email",data.getString("email"));
                                   Edis.apply();
                                   getdata(id);
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                               }
                           }else {
                               Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                           }
                       } catch (JSONException e){
                           e.printStackTrace();
                           Toast.makeText(Login.this, "Password atau username salah", Toast.LENGTH_SHORT).show();
                       }
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(Login.this, "Username Atau Password Salah" , Toast.LENGTH_SHORT).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() {
               Map<String, String> params = new HashMap<>();
               params.put("username", user);
               params.put("password", pass);
               return params;
           }};
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(stringRequest);
    }

    private void getdata(String id) {
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
                                        String passw = data.getString("password").trim();
                                        String email = data.getString("email").trim();
                                        String emage = data.getString("id_image").trim();
                                        SharedPreferences.Editor edit = sahre.edit();
                                        edit.putString("id",id);
                                        edit.putString("password",passw);
                                        edit.putString("username",user);
                                        edit.putString("fulluname",fulluser);
                                        edit.putString("userlevel",level);
                                        edit.putString("nohp",hp);
                                        edit.putString("email",email);
                                        edit.putString("id_image",emage);
                                        edit.apply();

                                }
                            }else {
                                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Username Atau Password Salah" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    }
