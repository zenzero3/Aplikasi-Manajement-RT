package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.rt.Login;
import sistem.Smarta.grandcikarangcity2.rt.formregister1;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.HomeWarga;

public class Usereditclass  extends AppCompatActivity {
    TextInputLayout user,email,pase,noh,namalengkapo;
    CircleImageView aku;
    int i = 0;
    private int camera = 1;
    String path,idem,gambarpasang,id_image;
    SharedPreferences preferences,leo;
    String ide,usernames , pass, namas ,emails,nohps,linkid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituser);
        aku = findViewById(R.id.proiles);
        ImageButton comeback= findViewById(R.id.backbutt);
        user= findViewById(R.id.usernames);
        final ImageButton tambahgambaruser = findViewById(R.id.tambahgambar);
        email= findViewById(R.id.useredit);
        Button ek = findViewById(R.id.simpen);
        pase = findViewById(R.id.userpassword);
        noh = findViewById(R.id.usertelp);
        namalengkapo = findViewById(R.id.usernama);
         preferences = getSharedPreferences("blood",MODE_PRIVATE);
         leo         = getSharedPreferences("wooo",MODE_PRIVATE);
         String id = preferences.getString("ide","Empty");
            getdatauser(id);

        ek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = user.getEditText().getText().toString();
                pass = pase.getEditText().getText().toString();
                namas =namalengkapo.getEditText().getText().toString();
                emails=email.getEditText().getText().toString();
                nohps=noh.getEditText().getText().toString();
                int ik = pass.length();
                if (usernames.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    user.setError("Username Kosong");
                }else if (pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    pase.setError("password Kosong");
                }else if (namas.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    namalengkapo.setError("Nama lengkap Tidak Boleh Kosong");
                }else if (emails.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    email.setError("Email Kosong");
                }else if (nohps.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    noh.setError("NO hp Kosong");
                }else if (emails.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    noh.setError("NO hp Kosong");
                }else if (nohps.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    noh.setError("NO hp Kosong");
                }else if (Patterns.EMAIL_ADDRESS.matcher(email.getEditText().getText().toString()).matches()) {
                    if (Patterns.PHONE.matcher(noh.getEditText().getText().toString()).matches()) {

                        if (ik<8){
                            Toast.makeText(Usereditclass.this,"Password Harus Lebih  Dari 8 Karakter",Toast.LENGTH_LONG).show();
                            pase.setErrorEnabled(true);
                            pase.setError("Password Salah");
                        }else {
                            uploademage();
                        }
                    }else {
                        Toast.makeText(Usereditclass.this,"Format Nomor Salah",Toast.LENGTH_LONG).show();
                        noh.setErrorEnabled(true);
                        noh.setError("Format  salah");
                    }
                }
                else {
                    Toast.makeText(Usereditclass.this,"Format Email Salah",Toast.LENGTH_LONG).show();
                    email.setErrorEnabled(true);
                    email.setError("Format Email salah");
                }

            }
        });
    comeback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder alert= new AlertDialog.Builder(Usereditclass.this);
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
    tambahgambaruser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tambahgambar();
        }
    });

    }

    private void getdatauser(String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+id;
        final ProgressDialog progressBar = new ProgressDialog(Usereditclass.this);
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
                                    ide = id;
                                    usernames=usere;
                                    pass = passw;
                                    namas = fulluser;
                                    emails=emailss;
                                    nohps=hp;
                                    linkid=emage;
                                    user.getEditText().setText(usernames);
                                    email.getEditText().setText(emails);
                                    noh.getEditText().setText(nohps);
                                    namalengkapo.getEditText().setText(namas);
                                    pase.getEditText().setText(pass);
                                    getimageurl(emage);
                                    Log.d("isi",emage);
                                    progressBar.dismiss();

                                }
                            }else {
                                Toast.makeText(Usereditclass.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Usereditclass.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Usereditclass.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Usereditclass.this);
        requestQueue.add(stringRequest);

    }

    private void uploademage() {
        final ProgressDialog progressBar = new ProgressDialog(Usereditclass.this);
        progressBar.setMessage("Prosess");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/image/"+idem;
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
                                           idem=data.getString("id").trim();
                                            path=data.getString("path").trim();
                                    id_image= data.getString("nama");
                                        SharedPreferences.Editor edit = preferences.edit();
                                        edit.putString("id_image",id_image);
                                        edit.apply();
                                        SharedPreferences.Editor orak = leo.edit();
                                        orak.putString("id_image",id_image);
                                        orak.apply();
                                        progressBar.dismiss();
                                        linkid=id_image;
                                        uploaddatauser(ide);
                                }
                            }else {
                                Toast.makeText(Usereditclass.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Usereditclass.this, "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Usereditclass.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), aku.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private void uploaddatauser(final String ide) {
        String Ur="http://gccestatemanagement.online/public/api/updateuser/"+ide;
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
                                    String id =data.getString("id").trim();
                                    String user = data.getString("username").trim();
                                    String fulluser=data.getString("nama").trim();
                                    String level   = data.getString("id_level").trim();
                                    String hp   = data.getString("nohp").trim();
                                    String email = data.getString("email").trim();
                                    String emage = data.getString("id_image").trim();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id",id);
                                    editor.putString("nama",fulluser);
                                    editor.putString("username",user);
                                    editor.putString("nohp",hp);
                                    editor.putString("password",pass);
                                    editor.putString("email",email);
                                    editor.putString("id_image",emage);
                                    editor.commit();
                                    editor.apply();
                                    Toast.makeText(getApplicationContext(),"Berhasil Update data",Toast.LENGTH_LONG).show();
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
                params.put("username",usernames);
                params.put("password",pass);
                params.put("nama",namas);
                params.put("email",emails);
                params.put("nohp",nohps);
                params.put("id_image",linkid);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);
    }



    private void getimageurl(final String id) {
        final ProgressDialog progressBar = new ProgressDialog(Usereditclass.this);
        progressBar.setMessage("Loadding....");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/image/"+id;
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
                                        path=jonobject.getString("path").trim();
                                        SharedPreferences.Editor edit = preferences.edit();
                                        edit.putString("id_image",idem);
                                        edit.apply();
                                        gambarpasang = "http://gccestatemanagement.online/public/gallery/"+path;
                                        Picasso.get()
                                                .load(gambarpasang)
                                                .into(aku);
                                    }
                                    progressBar.dismiss();

                                }
                            }else {
                                Toast.makeText(Usereditclass.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Usereditclass.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Usereditclass.this, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void tambahgambar() {
        getcamera();
    }

    private void getcamera() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(Usereditclass.this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                final BottomSheetDialog dialog = new BottomSheetDialog(Usereditclass.this, R.style.CustomBottomSheetDialogTheme);
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
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, 2);
                        }else {
                            dialog.dismiss();
                            getalert();
                        }


                        dialog.dismiss();
                    }
                });
            }
            else {
               getalert();
            }

        }else {

        }
    }

    private void getalert() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Usereditclass.this,
                                    new String[] {Manifest.permission.CAMERA}, camera);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                aku.setImageBitmap(photo);
            }
            if (requestCode == 1)
            {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                    aku.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void onBackPressed() {
        final AlertDialog.Builder alert= new AlertDialog.Builder(Usereditclass.this);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==camera)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 2);
                }else {
                    getalert();
                }
            } else {
                getalert();
            }
        }
    }

}
