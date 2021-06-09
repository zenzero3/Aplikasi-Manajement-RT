package sistem.Smarta.grandcikarangcity2.rtpintar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.Wargaregisterone;

public class Registerrtpintar_warga extends AppCompatActivity {
    Integer code =111444;
     ProgressBar ss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wargaform);
        ImageView buttonkeluar = findViewById(R.id.backbutt);
        final TextInputLayout ambilcode= findViewById(R.id.emailss);
            ss   = findViewById(R.id.progressBar4);
        Button register = findViewById(R.id.rt_pintarwargaregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ss.setVisibility(View.VISIBLE);
                String masuk = ambilcode.getEditText().getText().toString();
                if (masuk.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Silahkan Masukan Kode aktivasi terlebih dahulu",Toast.LENGTH_LONG).show();
                    ss.setVisibility(View.GONE);
                }
                else{
                    getcode(masuk);
                }

            }
        });

        buttonkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getcode(String masuk) {
        String UrlLogin="http://gccestatemanagement.online/public/api/getkode/"+masuk;
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
                                if (message.equals("get kodewarga")) {
                                    String id = data.getString("id_rt").trim();
                                      SharedPreferences eko = getSharedPreferences("rtcoy", Context.MODE_PRIVATE);
                                      SharedPreferences.Editor editor = eko.edit();
                                      editor.putString("rt",id);
                                      editor.apply();
                                      Intent intent = new Intent(Registerrtpintar_warga.this,Wargaregisterone.class);
                                      startActivity(intent);
                                      finish();
                                      ss.setVisibility(View.GONE);
                                }
                            }else {
                                Toast.makeText(Registerrtpintar_warga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            ss.setVisibility(View.GONE);
                            Toast.makeText(Registerrtpintar_warga.this, "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ss.setVisibility(View.GONE);
                Toast.makeText(Registerrtpintar_warga.this, "Kode Tidak valid Mohon Periksa Kode Anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        finish();
    }
}