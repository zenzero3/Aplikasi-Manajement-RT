package sistem.Smarta.grandcikarangcity2.rtpintar.warga.kontenwarga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.fragmentrt.adpteruser;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Iuran;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.InformasiWarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.SuratWarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.iuranwarga;

import static android.content.Context.MODE_PRIVATE;

public class wargahome extends Fragment {
    RecyclerView mrecycleview;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter adapter;
    ImageButton satu,dua,tiga;
    String nik;
    ImageButton urusl,iur,surt,lawarga,polwarga,more,less,pullsa,paketdata,tagihanlis,tahiganbpjs,pam,telep,wifi,voucgame,emon,tokenlis,etoll,ff,playg,
            asuransi,zakat,multi,pubgiq,itune;
    LinearLayout lay;
    TextView show,not;
    TextView adaisi;
    CheckBox akudua;
    TextView namarts,desa,rumahtidak;
    SharedPreferences sahre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_wargahome, container, false);
        satu= view.findViewById(R.id.warga_hutang);
        dua = view.findViewById(R.id.suratwarga);
        tiga = view.findViewById(R.id.pengu);
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
        mrecycleview= view.findViewById(R.id.onlinbro);
        sahre = this.getActivity().getSharedPreferences("blood", MODE_PRIVATE);
         adaisi = view.findViewById(R.id.namawarga);
        nik=sahre.getString("ide","null");
        wargadata(nik);
        mrecycleview.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        adapter = new adpteruser();
        mrecycleview.setLayoutManager(mLayout);
        mrecycleview.setAdapter(adapter);
        akudua = view.findViewById(R.id.checkBox);
        cek();
        satu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), iuranwarga.class);
                startActivity(intent);
            }
        });
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

        dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SuratWarga.class);
                startActivity(intent);

            }
        });

        tiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), InformasiWarga.class);
                startActivity(intent);
            }
        });
        akudua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor edit = sahre.edit();
                    edit.putString("ada","Kosong");
                    edit.apply();
                } else {
                    SharedPreferences.Editor edit = sahre.edit();
                    edit.putString("ada","Ada");
                    edit.apply();
                }
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
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.imdvlpr.agenppob");
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
                                        SharedPreferences oh = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = oh.edit();
                                        editor.putString("nik",data.getString("nik"));
                                        editor.putString("nkk",data.getString("nkk"));
                                        editor.putString("id_rt",data.getString("id_rt"));
                                        SharedPreferences yea = getActivity().getSharedPreferences("loss",MODE_PRIVATE);
                                        SharedPreferences.Editor yaiks = yea.edit();
                                        yaiks.putString("fulluname",data.getString("nama_lengkap"));
                                        editor.apply();
                                        yaiks.commit();
                                        yaiks.apply();
                                        adaisi.setText(data.getString("nama_lengkap"));
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

    private void cek() {
        if (sahre.getString("ada","Kosong").isEmpty()){
            akudua.setChecked(false);

        }
        if (sahre.getString("ada","Ada").equals("Kosong")){
            akudua.setChecked(true);

        }
    }

}