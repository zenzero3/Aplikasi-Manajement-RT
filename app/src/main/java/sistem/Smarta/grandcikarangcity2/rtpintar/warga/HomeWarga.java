package sistem.Smarta.grandcikarangcity2.rtpintar.warga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.reciver;
import sistem.Smarta.grandcikarangcity2.rt.MainHomeRt;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.DaftarLaporan;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtpintar_RT;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.SuratWarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.kontenwarga.akunwarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.kontenwarga.pesanwarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.kontenwarga.wargahome;

public class HomeWarga extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBar actionBar;
    private Toolbar toolbar;
    int laporan,surat;
    List <String>ferdian,agus;
    String idrt;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    SharedPreferences sharedPreferences;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    DrawerLayout drawer;
    String nik ;
    TextView title;
    NavigationView nav_view;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_warga);
        initToolbar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        title = findViewById(R.id.koptitle);
        sharedPreferences=getSharedPreferences("blood", MODE_PRIVATE);
        nik=sharedPreferences.getString("ide","null");
        onResume();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.bb,R.string.cc);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
        fragmentManager =getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenwarga,new wargahome());
        fragmentTransaction.commit();

        
    }

   public void onResume(){
        super.onResume();
       wargadata(nik);
   }
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        Tools.setSystemBarColor(this, R.color.grey_3);
    }

    private void close(DrawerLayout drawer) {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert= new AlertDialog.Builder(HomeWarga.this);
        alert.setTitle("Anda Yakin Ingin Kembali Ke Menu RT Manajement?");
        alert.setCancelable(false);

        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.homebar){
            fragmentManager =getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenwarga,new wargahome());
            fragmentTransaction.commit();
            title.setText("Menu Utama");
            close(drawer);
        }
        if (item.getItemId()==R.id.pesanwarga){
            fragmentManager =getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenwarga,new pesanwarga());
            fragmentTransaction.commit();
            title.setText("Menu Pesan");
            close(drawer);
        }
        if (item.getItemId()==R.id.akunwarga){
            fragmentManager =getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenwarga,new akunwarga());
            title.setText("Menu Profil");
            fragmentTransaction.commit();
            close(drawer);
        }
        if (item.getItemId()==R.id.back){
            final AlertDialog.Builder alert= new AlertDialog.Builder(HomeWarga.this);
            alert.setTitle("Anda Yakin Ingin Kembali Ke Menu RT Manajement");
            alert.setCancelable(false);

            alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    close(drawer);
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
        return true;
    }
    private void getkk(final String kk) {
        final List<String> nik=new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/family/"+kk;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            if (status.equals("true")) {
                                if (message.equals("get data warga")) {
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        nik.add(data.getString("nik"));

                                    }
                                    for (int i = 0;i<nik.size();i++){
                                        String nike = nik.get(i);

                                    }
                                  getpengajuawarga(idrt,kk);

                                }
                            }else {
                                Toast.makeText(HomeWarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(HomeWarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeWarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeWarga.this);
        requestQueue.add(stringRequest);
    }
    private void wargadata(String nik) {
        String UrlLogin="http://gccestatemanagement.online/public/api/wargabisa/"+nik;
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
                                    idrt = data.getString("id_rt");
                                    getkk(data.getString("nkk"));
                                    getlaporan(idrt,data.getString("nik"));


                                }
                            }else {
                                Toast.makeText(HomeWarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(HomeWarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeWarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeWarga.this);
        requestQueue.add(stringRequest);
}
    private void getnotifsurat(int ek) {
        laporan = ek;
        String message ="laporan";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),message);
        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentText(laporan+" Surat Anda Disetujui Cek Riwayat Surat");
        builder.setContentTitle("Surat Warga");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.default_icon);
        builder.setSound(alarmSound);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(message,"Terima surat Warga",NotificationManager.IMPORTANCE_DEFAULT);
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
        Intent intent = new Intent(HomeWarga.this, SuratWarga.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(HomeWarga.this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification nf = builder.build();
        if (manager!=null){
            manager.notify(2,nf);
        }
    }

    private void getnotilaporan(int ek) {
        surat = ek;
        String message ="surat";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),message);
        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentText(surat+" Laporan Anda Telah Di tindak lanjuti Ketua RT");
        builder.setContentTitle("Laporan Warga");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.default_icon);
        builder.setSound(alarmSound);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(message,"Laporan Acc",NotificationManager.IMPORTANCE_DEFAULT);
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
        Intent intent = new Intent(HomeWarga.this, reciver.class);
        intent.putStringArrayListExtra("isi", (ArrayList<String>) agus);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeWarga.this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification nf = builder.build();
        if (manager!=null){
            manager.notify(2,nf);

        }
    }

    private void getpengajuawarga(final String idrt,final String kk) {
      ferdian = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/datapengajuanid2/"+idrt+"/"+kk;
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
                                    if (ek==0){
                                        ek = 0;
                                    }else {
                                        for (int i=0;i<data.length();i++)
                                        {    JSONObject he = data.getJSONObject(i);
                                            ferdian.add(he.getString("id"));
                                        }
                                            getnotifsurat(ek);
                                                                 }
                                }
                            }else {
                                Toast.makeText(HomeWarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(HomeWarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeWarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeWarga.this);
        requestQueue.add(stringRequest);
    }
    private void getlaporan(final String idrt,final String kk) {
        agus = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/datalaporanambil/"+idrt+"/"+kk;
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
                                    if (ek==0){
                                        ek = 0;
                                    }else {
                                        for (int i=0;i<data.length();i++)
                                        {    JSONObject he = data.getJSONObject(i);
                                           agus.add(he.getString("id"));
                                        }
                                        getnotilaporan(ek);
                                    }
                                }
                            }else {
                                Toast.makeText(HomeWarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(HomeWarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeWarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeWarga.this);
        requestQueue.add(stringRequest);
    }
}