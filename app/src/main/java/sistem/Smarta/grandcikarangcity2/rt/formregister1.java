package sistem.Smarta.grandcikarangcity2.rt;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import kotlin.text.Regex;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;

public class formregister1 extends AppCompatActivity {
    Button register;
    String namas,emails,nohps,pass,usernames;
    ProgressBar ekoki;
    Bitmap bitmap;
    String gambar;
    ImageView akor;
    ImageButton imagebutt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeropen);
        register= findViewById(R.id.daftar);
        akor = findViewById(R.id.imageView4);
        ekoki = findViewById(R.id.progressBar);
       imagebutt= (ImageButton)findViewById(R.id.backbutt);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imagebutt.getDrawable() ;
        bitmap  = bitmapDrawable.getBitmap();
        gambar = imagetoString(bitmap);
        imagebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        final TextInputLayout nama=findViewById(R.id.daftarnama);
        final TextInputLayout email=findViewById(R.id.daftaremail);
        final TextInputLayout user = findViewById(R.id.daftarusername);
        final TextInputLayout password = findViewById(R.id.daftarpassword);
        final TextInputLayout hp = findViewById(R.id.daftarnotelp);
        nama.getEditText().setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Regex regex =new Regex("^08[0-9]{9,}$");
                String isi =password.getEditText().getText().toString();
                int ik= isi.length();
                if (email.getEditText().getText().toString().isEmpty()){
                       Toast.makeText(formregister1.this,"Email tidak boleh kosong",Toast.LENGTH_LONG).show();
                       email.setErrorEnabled(true);
                       email.setError("Email Tidak boleh kosong");
                }else if (nama.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(formregister1.this,"Nama tidak boleh kosong",Toast.LENGTH_LONG).show();
                    nama.setErrorEnabled(true);
                    nama.setError("Nama Harus di isi");
                }else if (user.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(formregister1.this,"Username tidak boleh kosong",Toast.LENGTH_LONG).show();
                    user.setErrorEnabled(true);
                    user.setError("masukan Username");
                }else if (password.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(formregister1.this,"Password tidak boleh kosong",Toast.LENGTH_LONG).show();
                    password.setErrorEnabled(true);
                    password.setError("masukan Password");
                }else if (hp.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(formregister1.this,"Nomor Hp tidak boleh kosong",Toast.LENGTH_LONG).show();
                    hp.setErrorEnabled(true);
                    hp.setError("Masukan No HP");
                }else if (Patterns.EMAIL_ADDRESS.matcher(email.getEditText().getText().toString()).matches()) {
                    if (regex.toPattern().matcher(hp.getEditText().getText().toString()).matches()) {

                        if (ik<8){
                            Toast.makeText(formregister1.this,"Password Harus Lebih  Dari 8 Karakter",Toast.LENGTH_LONG).show();
                            password.setErrorEnabled(true);
                            password.setError("Passowrd Harus Lebih Dari 8 Karakter");
                        }else {
                            getuser(nama, email, user, password, hp);
                        }
                    }else {
                        Toast.makeText(formregister1.this,"Format Nomor Salah",Toast.LENGTH_LONG).show();
                       hp.setErrorEnabled(true);
                        hp.setError("Format Nomor Telp salah");
                    }
                }
                else {
                    Toast.makeText(formregister1.this,"Format Email Salah",Toast.LENGTH_LONG).show();
                    email.setErrorEnabled(true);
                    email.setError("Format Email salah");
                }

            }
        });
    }

    private void getuser(final TextInputLayout nama, final TextInputLayout email, final TextInputLayout user, final TextInputLayout password, final TextInputLayout hp){
            String usernam = user.getEditText().getText().toString();
            ekoki.setVisibility(View.VISIBLE);
        String UrlLogin="http://gccestatemanagement.online/public/api/valid/"+usernam;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            if (status.equals("true")) {
                                if (message.equals("get data warga")) {
                                    ekoki.setVisibility(View.GONE);
                                    Toast.makeText(formregister1.this, "Username Sudah Ada", Toast.LENGTH_SHORT).show();
                                    user.getEditText().setError("Username Sudah di Pakai");
                                }
                            }else {
                                Submit(nama,email,user,password,hp);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(formregister1.this, "Mohon maaf sistem sementara Offline" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ekoki.setVisibility(View.GONE);
                error.printStackTrace();
                Toast.makeText(formregister1.this, "Jaringan Error Periksa Jaringan Anda ", Toast.LENGTH_SHORT).show();

            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(formregister1.this);
        requestQueue.add(stringRequest);
    }


    private void Submit(TextInputLayout nama, TextInputLayout email, TextInputLayout user, TextInputLayout password, TextInputLayout hp) {
            namas=nama.getEditText().getText().toString();
            emails=email.getEditText().getText().toString();
            pass=password.getEditText().getText().toString();
            usernames=user.getEditText().getText().toString();
            nohps= hp.getEditText().getText().toString();
            ekoki.setVisibility(View.VISIBLE);
        String Ur="http://gccestatemanagement.online/public/api/register";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Ur,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");

                            if (message.equals("Post Created")) {
                                if (success.equals("true")) {
                                    ekoki.setVisibility(View.GONE);
                                    Toast.makeText(formregister1.this,"Success Register", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(formregister1.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }else {
                                ekoki.setVisibility(View.GONE);
                                Toast.makeText(formregister1.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            ekoki.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.d("isi error",e.toString());
                            finish();
                            Toast.makeText(formregister1.this, "Data Yang Anda Masukan Mohon Periksa Kembali" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ekoki.setVisibility(View.GONE);
                error.printStackTrace();
                Log.d("isi error",error.toString());
                Toast.makeText(formregister1.this, "Gagal Memasukan data Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username",usernames);
                params.put("password",pass);
                params.put("nama",namas);
                params.put("email",emails);
                params.put("nohp",nohps);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), imagebutt.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private String imagetoString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageType = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageType, Base64.DEFAULT);
    }
}
