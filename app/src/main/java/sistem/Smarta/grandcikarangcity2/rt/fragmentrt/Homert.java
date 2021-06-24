package sistem.Smarta.grandcikarangcity2.rt.fragmentrt;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.MainHomeRt;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.DaftarLaporan;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Historysurat;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Iuran;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.KasRT;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.LaporanWarga;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Pollingwarga;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.SuratRT;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Urusrt;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtpintar_RT;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Homert extends Fragment {
    RecyclerView mrecycleview;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter adapter;
    ImageView daflapor,hissurat,kk,kasrt;
    Button whats;
    TextView show,not,icname;
    SharedPreferences sahres;
    String code,isi,idrtoke,leveling,nike,status,ii;
    LinearLayout lay;
    CheckBox aku;
    ImageButton urusl,iur,surt,lawarga,polwarga,more,less,pullsa,paketdata,tagihanlis,tahiganbpjs,pam,telep,wifi,voucgame,emon,tokenlis,etoll,ff,playg,
                asuransi,zakat,multi,pubgiq,itune;
    SharedPreferences sahre;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.homert, container, false);
        mrecycleview= view.findViewById(R.id.onlinbro);
        mrecycleview.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        adapter = new adpteruser();
        icname = view.findViewById(R.id.namart);
        urusl = view.findViewById(R.id.pengu);
        iur = view.findViewById(R.id.iuran);
        surt=view.findViewById(R.id.suratrt);
        more= view.findViewById(R.id.app);
        lay = view.findViewById(R.id.panelview1);
        pullsa =view.findViewById(R.id.pullsa);
        paketdata = view.findViewById(R.id.paketdata);
        tagihanlis = view.findViewById(R.id.tagihanlistrisk);
        tahiganbpjs = view.findViewById(R.id.bpjs);
        telep = view.findViewById(R.id.telepon);
        pam = view.findViewById(R.id.pdam);
        wifi = view.findViewById(R.id.wifi);
        voucgame = view.findViewById(R.id.gamevoucher);
        emon = view.findViewById(R.id.emoney);
        tokenlis = view.findViewById(R.id.tokenlistrik);
        etoll = view.findViewById(R.id.etoll);
        ff = view.findViewById(R.id.garena);
        playg = view.findViewById(R.id.googleplay);
        asuransi = view.findViewById(R.id.asuransi);
        zakat = view.findViewById(R.id.zakat);
        multi = view.findViewById(R.id.multiance);
        itune = view.findViewById(R.id.itunes);
        pubgiq = view.findViewById(R.id.pubgi);
        show = view.findViewById(R.id.more);
        not  = view.findViewById(R.id.less_text);
        less = view.findViewById(R.id.less);
        lawarga =view.findViewById(R.id.laporanwarga);
        lawarga.setVisibility(View.GONE);
        polwarga=view.findViewById(R.id.pollingwarga);
        hissurat=view.findViewById(R.id.historysurrat);
        daflapor=view.findViewById(R.id.daftarlaporan);
        kasrt = view.findViewById(R.id.kasrt);
        whats = view.findViewById(R.id.wa);
        kk = view.findViewById(R.id.backing);
        sahre = getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
        sahres = getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahre.getString("ide","empty");
        nike =sahre.getString("nikkx",null);
        ii = isiid;
        getdatartsekarang(isiid);
        ceklevel(ii);

        aku = view.findViewById(R.id.checkBox);
        aku.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
               if (isChecked){
//                   cekadarumah(nike);
                   status ="Kosong";
                   SharedPreferences.Editor edit = sahre.edit();
                   edit.putString("ada","Kosong");
                   edit.apply();
               } else {
                   status ="ada";
                   SharedPreferences.Editor edit = sahre.edit();
                   edit.putString("ada","Ada");
                   edit.apply();
               }
            }

        });
        cek();
        mrecycleview.setLayoutManager(mLayout);
        mrecycleview.setAdapter(adapter);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setVisibility(View.GONE);
                not.setVisibility(View.VISIBLE);
                less.setVisibility(View.VISIBLE);
                more.setVisibility(View.GONE);
                lay.setVisibility(View.VISIBLE);
            }
        });
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setVisibility(View.VISIBLE);
                more.setVisibility(View.VISIBLE);
                less.setVisibility(View.GONE);
                lay.setVisibility(View.GONE);
                not.setVisibility(View.GONE);
            }
        });
    kk.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
        alert.setTitle("Anda Yakin Ingin Kembali Ke Aplikasi Kabupaten Kebumen");
        alert.setCancelable(false);

        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sahres = getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
                String isiid=sahre.getString("ide","empty");
                getdatart1(isiid);
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
    whats.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getCode();
    }
});

        urusl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Urusrt.class);
                startActivity(intent);
            }
        });
        surt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuratRT.class);
                startActivity(intent);
            }
        });

iur.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Iuran.class);
        startActivity(intent);
    }
});
lawarga.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), LaporanWarga.class);
        startActivity(intent);
    }
});
polwarga.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Pollingwarga.class);
        startActivity(intent);
    }
});
hissurat.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Historysurat.class);
        startActivity(intent);
    }
});
daflapor.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DaftarLaporan.class);
        startActivity(intent);
    }
});

kasrt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), KasRT.class);
        startActivity(intent);
    }
});

pullsa.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

paketdata.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

tagihanlis.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.badjoemu.apps");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

tahiganbpjs.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

pam.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

telep.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

wifi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});
voucgame.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

emon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

tokenlis.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

etoll.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

ff.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

playg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

asuransi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

zakat.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

multi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

pubgiq.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});

itune.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
        if (intent != null){
            startActivity(intent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.badjoemu.apps")));
        }
    }
});


        return view;
    }

    private void cekadarumah(String nike) {
        String UrlLogin="http://gccestatemanagement.online/public/api/get/"+nike;
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
                                    String id = data.getString("id");
                                    putstatustingggal();
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private void putstatustingggal() {

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
                                    leveling=level;
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Username Belum Terdaftar" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
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
                                        icname.setText("Nama Ketua RT "+name+"\nKelurahan "+kelurah);
                                    }else {
                                        icname.setText("Nama Ketua RT "+name+"\nDesa "+desa);
                                    }

                                    idrtoke = data.getString("id");
                                    String nike0   =data.getString("nik");
                                    SharedPreferences.Editor edit = sahre.edit();
                                    edit.putString("nikkx",nike0);
                                    edit.apply();


                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private void getdatart1(String isiid) {
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
                                    isi = data.getString("id").trim();
                                    String name = data.getString("nama_lengkap").trim();
                                    String desa = data.getString("desa").trim();
                                    icname.setText(name+" Desa "+desa);
                                    off(isi);
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }

    private void off(String isi) {
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
                                    Intent intent = new Intent(requireContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Maaf Jaringan Anda Bermasalah" , Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(volleyMultipartRequest);
    }


    private void getCode() {
        sahres = getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahre.getString("ide","empty");
        getdatart(isiid);
    }

    private void getdatart(String isiid) {
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
                                    isi = data.getString("id").trim();
                                    getcodebin();
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Username Belum Terdaftar" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }

    private void getcodebin() {
        String UrlLogin="http://gccestatemanagement.online/public/api/rtcode";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("Post Created")) {
                                if (success.equals("true")) {
                                    code = data.getString("kode_warga").trim();
                                    String isi ="Kode Register Kepala Keluarga\n"+"\b"+code+"\nSilahkan unduh Aplikasi pada Playstore anda";
                                    boolean instlaed = appInstalledornot("com.whatsapp");
                                    if (instlaed){
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.putExtra(Intent.EXTRA_TEXT, isi); intent.setType("text/plain");
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(requireContext(), "WhatsApp Tidak Terpasang", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Data Belum lengkap mohon lengkapi data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Jaringan Error mohon refresh jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_rt",isi);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private boolean appInstalledornot(String s) {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean app_instaled;
        try {
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            app_instaled = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_instaled = false;
        }
        return app_instaled;
    }

    private void cek() {
    if (sahre.getString("ada","Ada").equals("Kosong")){
        aku.setChecked(true);
    }
    }
}