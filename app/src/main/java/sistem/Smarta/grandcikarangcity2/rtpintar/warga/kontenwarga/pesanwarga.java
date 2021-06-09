package sistem.Smarta.grandcikarangcity2.rtpintar.warga.kontenwarga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.SuratWarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.HomeWarga;

import static android.content.Context.MODE_PRIVATE;

public class pesanwarga extends Fragment {
    String nik;
    ArrayList<String> agus,ferdian;
    LinearLayout one,two,three;
    SwipeRefreshLayout oke;
    TextView surat,laporan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_pesanwarga, container, false);
        one = view.findViewById(R.id.surat);
        two = view.findViewById(R.id.laporan);
        three = view.findViewById(R.id.notfound);
        surat = view.findViewById(R.id.oid);
        laporan = view.findViewById(R.id.lap);
        oke= view.findViewById(R.id.referesgh);
        onResume();
        oke.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getnotif(nik);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      oke.setRefreshing(false);
                    }
                },1000);
            }
        });
        return view;


    }
    public void onResume() {
        SharedPreferences oh =getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
        nik = oh.getString("nik",null);
        super.onResume();
        getnotif(nik);

    }

    private void getnotiflap(String nik) {
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setMessage("Prosess");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/shownotifwarga/"+nik;
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
                                        two.setVisibility(View.GONE);
                                        three.setVisibility(View.VISIBLE);
                                        progressBar.dismiss();
                                    }else {
                                        progressBar.dismiss();
                                        two.setVisibility(View.VISIBLE);
                                        three.setVisibility(View.GONE);
                                        laporan.setText(ek+" Laporan Anda Telah di tanggapi Ketua RT");
                                    }
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                                progressBar.dismiss();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressBar.dismiss();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(requireContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private void getnotif(final String nik) {
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setMessage("Prosess");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/datapengajuaniddua/"+nik;
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
                                        one.setVisibility(View.GONE);
                                        three.setVisibility(View.VISIBLE);
                                        progressBar.dismiss();
                                    }else {
                                        progressBar.dismiss();
                                        one.setVisibility(View.VISIBLE);
                                        three.setVisibility(View.GONE);
                                        getnotiflap(nik);
                                        surat.setText("Jumlah Surat Pengajuan yang telah di setujui oleh Ketu RT: "+ek);
                                        surat.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(getActivity(), SuratWarga.class);
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                                progressBar.dismiss();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressBar.dismiss();
                            Toast.makeText(requireContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(requireContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }

}