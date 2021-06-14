package sistem.Smarta.grandcikarangcity2.rtpintar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Sessionrt;
import sistem.Smarta.grandcikarangcity2.rt.Login;
import sistem.Smarta.grandcikarangcity2.rt.MainHomeRt;
import sistem.Smarta.grandcikarangcity2.rt.formregister1;

public class Registerrtpintar_RT extends AppCompatActivity {
    Button regis,masuk;
    ImageButton back;
    SharedPreferences sahre;
    ProgressBar ss;
    Sessionrt sessionrt;
    String isi,isiid;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formpintarketuart);
        regis = findViewById(R.id.rt_pintarregister);
        back = findViewById(R.id.backbutt);
       ss = findViewById(R.id.progressBar3);
        masuk = findViewById(R.id.rt_pintarmasuk);
        sessionrt = new Sessionrt(this);
        sahre = getSharedPreferences("blood", Context.MODE_PRIVATE);
        isi=sahre.getString("username","Empty");
        isiid=sahre.getString("ide","empty");
        final TextInputLayout satu= findViewById(R.id.email);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registerrtpintar_RT.this, Registerrtformpintar.class);
                startActivity(intent);
            }
        });
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(intent);
                 finish();
             }
         });

         masuk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cek(satu);
             }
         });
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void cek(TextInputLayout satu) {
        ss.setVisibility(View.VISIBLE);
    String masuk = satu.getEditText().getText().toString();
    if (masuk.equals("")){
        Toast.makeText(getApplicationContext(),"Belum Memasukan Username",Toast.LENGTH_LONG).show();
        satu.setError("Username Kosong");
        ss.setVisibility(View.GONE);
    }else
    if (masuk.equals(isi)){
        ceklevel(isiid);
        ss.setVisibility(View.GONE);
    }else {
        ss.setVisibility(View.GONE);
        satu.setError("Username Tidak terdaftar");
        Toast.makeText(getApplicationContext(),"Username Salah",Toast.LENGTH_LONG).show();

    }
    }

    private void ceklevel(String isiid) {
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+isiid;
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
                                            Intent intent = new Intent(getApplicationContext(), MainHomeRt.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Anda belum Terdaftar Sebagai RT Harap mendaftar Atau Menunggu Konfirmasi Dari Admin",Toast.LENGTH_LONG).show();
                                        }


                                    ss.setVisibility(View.GONE);
                                }
                            }else {
                                Toast.makeText(Registerrtpintar_RT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Registerrtpintar_RT.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registerrtpintar_RT.this, "Username Belum Terdaftar" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
