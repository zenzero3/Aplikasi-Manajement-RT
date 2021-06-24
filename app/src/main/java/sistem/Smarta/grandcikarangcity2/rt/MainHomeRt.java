package sistem.Smarta.grandcikarangcity2.rt;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AdapterTransaksi;
import sistem.Smarta.grandcikarangcity2.model.Sessionrt;
import sistem.Smarta.grandcikarangcity2.model.Transaksi;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.DaftarLaporan;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Historysurat;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.KasRT;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtpintar_RT;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.HomeWarga;

public class MainHomeRt extends AppCompatActivity {
    String nilai,level,nik,id_rt,desa,laporan,title,idrt;
    ArrayList<String>agus,ferdian;
    Handler handler;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    int satu, dua;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtmain);
        SharedPreferences sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahres.getString("ide","empty");
        id_rt=isiid;
        handler = new Handler();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homebar,R.id.pembayaran)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        getdatartoke(id_rt);
        onResume();
    }
    public void onResume() {

        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getdatartoke(id_rt);
            }
        }, 10000);

    }

    private void getlaporanwarga() {
        ferdian = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/datalaporan/"+idrt;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray data=jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("rtselesai")) {
                                    int ek = data.length();
                                    satu = ek;
                                    if (ek==0){

                                    }else {
                                        for (int i=0;i<data.length();i++)
                                        {    JSONObject he = data.getJSONObject(i);
                                            ferdian.add(he.getString("id"));
                                        }

                                        getnotiflaporan();
                                    }
                                }
                            }else {
                                Toast.makeText(MainHomeRt.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainHomeRt.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),"Maaf Jaringan sibuk mohon menunggu beberapa saat ",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                }
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainHomeRt.this);
        requestQueue.add(stringRequest);
    }

    private void getpengajuawarga() {
        agus = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/datapengajuanid/"+idrt;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray data=jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("rtselesai")) {
                                    int ek = data.length();
                                    dua = ek;
                                    if (ek==0){

                                    }else {
                                        for (int i=0;i<data.length();i++)
                                        {    JSONObject he = data.getJSONObject(i);
                                            agus.add(he.getString("id"));
                                        }

                                       getnotif();
                                    }
                                }
                            }else {
                                Toast.makeText(MainHomeRt.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainHomeRt.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),"Maaf Jaringan sibuk mohon menunggu beberapa saat ",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                }
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainHomeRt.this);
        requestQueue.add(stringRequest);
    }

    private void getnotiflaporan() {
        String message ="laporan";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),message);
        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentText(satu+" Laporan Warga Belum di tanggapi");
        builder.setContentTitle("Laporan Warga");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.default_icon);
        builder.setSound(alarmSound);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(message,"Laporan Warga",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(message);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            builder.setChannelId(message);
            channel.setSound(alarmSound, attributes);
            if (manager!=null){
                manager.createNotificationChannel(channel);
            }

        }
        Intent intent = new Intent(MainHomeRt.this, DaftarLaporan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainHomeRt.this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification nf = builder.build();
        if (manager!=null){
            manager.notify(2,nf);
        }
    }

    private void getnotif() {
        String message ="warga";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),message);
        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentText("Pak RT Ada "+dua+" Surat Pengajuan Baru Masuk");
        builder.setContentTitle("Surat Pengajuan");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.default_icon);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(message,"Warga",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(message);
            builder.setChannelId(message);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(alarmSound, attributes);
            if (manager!=null){
                manager.createNotificationChannel(channel);
            }

        }
        Intent intent = new Intent(MainHomeRt.this, Historysurat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainHomeRt.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification nf = builder.build();
        if (manager!=null){
            manager.notify(1,nf);
        }


    }


    private void getdatartoke(final String isiid) {
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
                                    String isi = data.getString("id").trim();
                                    nik = data.getString("nik").trim();
                                    desa = data.getString("desa").trim();
                                    idrt = isi;
                                    getlaporanwarga();
                                    getpengajuawarga();
                                    ceklevel(isiid);
                                    updateuser(isi);
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
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),"Maaf Jaringan sibuk mohon menunggu beberapa saat ",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                }
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void ceklevel(String isi) {
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+isi;
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
                                level   = data.getString("id_level").trim();
                                    if (level.equals("3")){
                                        level = "RT";
                                    }else if (level.equals("4")){
                                        level = "Kepala Keluarga";
                                    }else if (level.equals("2")){
                                        level = "Warga";
                                    }else {
                                        level = "Admin Web";
                                    }
                                }
                            }else {
                                Toast.makeText(MainHomeRt.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainHomeRt.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),"Maaf Jaringan sibuk mohon menunggu beberapa saat ",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                }
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void updateuser(final String isi) {
        String Ur="http://gccestatemanagement.online/public/api/getrt/"+isi;
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
                                    nilai = isi;

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
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),"Maaf Jaringan sibuk mohon menunggu beberapa saat ",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                }
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("is_active","1");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert= new AlertDialog.Builder(MainHomeRt.this);
        alert.setTitle("Anda Yakin Ingin Kembali Ke Aplikasi Kabupaten Kebumen ?");
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

    private void off(String nilai) {
        String Ur="http://gccestatemanagement.online/public/api/getrt/"+nilai;
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
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
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
                params.put("is_active","0");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);

    }
}
