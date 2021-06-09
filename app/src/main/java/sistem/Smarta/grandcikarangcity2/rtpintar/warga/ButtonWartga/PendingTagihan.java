package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.kasbutton.Cekbayar;

public class PendingTagihan extends AppCompatActivity {
String eko,id;
TextView tanggalan,namaiur,nominaliuran,statusiuran;
Button cek,bayarlagi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        eko = i.getStringExtra("id");
        setContentView(R.layout.activity_cekbayar);
        tanggalan = findViewById(R.id.date);
        namaiur   = findViewById(R.id.nomame);
        nominaliuran= findViewById(R.id.nomin);
        statusiuran = findViewById(R.id.statuskelar);
        cek = findViewById(R.id.cek);
        bayarlagi = findViewById(R.id.cekdua);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        getdata(eko);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdataupdate(eko);
            }
        });
        bayarlagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getupdate(eko);
            }
        });
    }

    private void getupdate(String eko) {
        final Dialog dialogok;
        dialogok= new Dialog(PendingTagihan.this);
        dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogok.setContentView(R.layout.loading);
        dialogok.setCancelable(true);
        dialogok.setCanceledOnTouchOutside(false);
        dialogok.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargatrans/"+eko;
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
                                    id = data.getString("idorder");
                                    midtrasnsget(id);
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

    private void midtrasnsget(String id) {
        final Dialog dialogok;
        dialogok= new Dialog(PendingTagihan.this);
        dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogok.setCancelable(false);
        dialogok.setCanceledOnTouchOutside(false);
        String UrlLogin="https://api.sandbox.midtrans.com/v2/"+id+"/status";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("status_message");
                            String status  = jsonObject.getString("status_code");
                            JSONArray jsonArray =jsonObject.getJSONArray("va_numbers");
                            if (message.equals("Success, transaction is found"))
                            { dialogok.setContentView(R.layout.customdialogbayar);
                                TextView idku = dialogok.findViewById(R.id.isisatu);
                                final TextView makan = dialogok.findViewById(R.id.isidua);
                                TextView makanlagi = dialogok.findViewById(R.id.tigaisi);
                                makanlagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ClipboardManager clipboardManager=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("Bayar Dengan ",makan.getText().toString());
                                        clipboardManager.setPrimaryClip(clip);
                                        Toast.makeText(getApplicationContext(),"NO VA TERSALIN",Toast.LENGTH_LONG).show();
                                        dialogok.dismiss();
                                    }
                                });
                                String satus = null,dua = null;
                                for (int i =0;i<jsonArray.length();i++){
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    satus= data.getString("bank");
                                    dua = data.getString("va_number");
                                }


                                idku.setText(satus);
                                makan.setText(dua);
                                ImageView satu = dialogok.findViewById(R.id.imageView2);

                                satu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogok.dismiss();
                                    }
                                });
                                dialogok.show();

                            }else {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }

                            Log.d("id", "onResponse: "+status);

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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "SB-Mid-server-OjQPsBCUNzyH2ceKUJmNPOQz"+":"+"";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.DEFAULT);
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", auth);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getdataupdate(String eko) {
        final Dialog dialogok;
        dialogok= new Dialog(PendingTagihan.this);
        dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogok.setContentView(R.layout.loading);
        dialogok.setCancelable(true);
        dialogok.setCanceledOnTouchOutside(false);
        dialogok.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargatrans/"+eko;
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
                                   id = data.getString("idorder");
                                    midtrasns(id);
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

    private void midtrasns(final String id) {
        final Dialog dialogok;
        dialogok= new Dialog(PendingTagihan.this);
        dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialogok.setCancelable(true);
        dialogok.setCanceledOnTouchOutside(true);
        dialogok.show();
        String UrlLogin="https://api.sandbox.midtrans.com/v2/"+id+"/status";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("status_message");
                            String status  = jsonObject.getString("status_code");
                           if (status.equals("200"))
                           {    updateiuran(eko);
                                bayarlagi.setVisibility(View.GONE);
                                updatetransaksi(id);
                               dialogok.setContentView(R.layout.popupgreat);
                               ImageView iga = dialogok.findViewById(R.id.imageView2);
                               iga.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       dialogok.dismiss();
                                   }
                               });
                               dialogok.show();
                           }else {
                               dialogok.setContentView(R.layout.popupsory);
                               ImageView iga = dialogok.findViewById(R.id.imageView2);
                               iga.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       dialogok.dismiss();
                                   }
                               });
                               dialogok.show();
                           }

                            Log.d("id", "onResponse: "+status);

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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "SB-Mid-server-OjQPsBCUNzyH2ceKUJmNPOQz"+":"+"";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.DEFAULT);
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", auth);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void updatetransaksi(final String id) {
        String e = "http://gccestatemanagement.online/public/api/updatetransaksi";
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, e,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("update success")) {
                                if (success.equals("true")) {
                                    statusiuran.setText("STATUS_SUCCESS");
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Jaringan Bermasalah Mohon Periksa Jaringan Ana", Toast.LENGTH_SHORT).show();
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
                params.put("status","SUCCESS");
                params.put("idorder",id);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(PendingTagihan.this);
        requestQueue.add(stringRequest);

    }

    private void updateiuran(String eko) {
        String e = "http://gccestatemanagement.online/public/api/iuranupdate/"+eko;
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, e,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("Data berhasil diubah")) {
                                if (success.equals("true")) {
                                    statusiuran.setText("STATUS_SUCCESS");
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Jaringan Bermasalah Mohon Periksa Jaringan Ana", Toast.LENGTH_SHORT).show();
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
                params.put("statu","STATUS_SUCCESS");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(PendingTagihan.this);
        requestQueue.add(stringRequest);

    }

    private void getdata(String eko) {
        final Dialog dialogok;
        dialogok= new Dialog(PendingTagihan.this);
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