package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class Urusrt extends AppCompatActivity {
    TextView rt;
    SharedPreferences sahre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengurusketua);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        rt= findViewById(R.id.rrt);
        sahre = getApplication().getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahre.getString("ide","empty");
        getdatartsekarang(isiid);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getdatartsekarang(String isiid) {

        String UrlLogin="http://gccestatemanagement.online/public/api/getrt/"+isiid;
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
                                if (message.equals("get rt")) {
                                    String name = data.getString("nama_lengkap").trim();
                                    String desa = data.getString("desa").trim();
                                    if (desa.equals("Tidak Ada Pedukuhan")){
                                        String kelurah = data.getString("kelurahan");
                                        rt.setText(name);
                                    }else {
                                        rt.setText(name);
                                    }


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
