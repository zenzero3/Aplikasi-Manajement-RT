package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import sistem.Smarta.grandcikarangcity2.R;

public class Datatagihan extends AppCompatActivity {
String eko;
Button wee,falling;
    TextView tanggalan,namaiur,nominaliuran,statusiuran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        eko = i.getStringExtra("id");
        setContentView(R.layout.activity_cekbayar);
        tanggalan = findViewById(R.id.date);
        TextView up = findViewById(R.id.header);
        up.setText("Pembayaran Iuran Warga");
        namaiur   = findViewById(R.id.nomame);
        nominaliuran= findViewById(R.id.nomin);
        statusiuran = findViewById(R.id.statuskelar);
        wee= findViewById(R.id.cek);
        falling = findViewById(R.id.cekdua);
        falling.setVisibility(View.GONE);
        wee.setVisibility(View.GONE);
        getdata(eko);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getdata(String eko) {
        final Dialog dialogok;
        dialogok= new Dialog(Datatagihan.this);
        dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogok.setContentView(R.layout.loading);
        dialogok.setCancelable(true);
        dialogok.setCanceledOnTouchOutside(false);
        dialogok.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaspes/"+eko;
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
                                if (message.equals("get data iuran")) {
                                    String id = data.getString("id");
                                    tanggalan.setText(data.getString("updated_at"));
                                    namaiur.setText(data.getString("namaiuran"));
                                    nominaliuran.setText(data.getString("nominaliuran"));
                                    statusiuran.setText(data.getString("statu"));
                                    dialogok.dismiss();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}