package sistem.Smarta.grandcikarangcity2.rt.fragmentrt;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
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
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Adapteriuran;
import sistem.Smarta.grandcikarangcity2.model.Adapterwargaindividu;
import sistem.Smarta.grandcikarangcity2.model.iurannamadesa;
import sistem.Smarta.grandcikarangcity2.model.iuranwargapost;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.iuranwarga;

public class Pembayaran extends Fragment {
    SharedPreferences sahres;
    String isi;
    RecyclerView dua;
    LinearLayout lottie,header,isidalam;
    List<iuranwargapost>iuranwargaposts;
    RecyclerView.Adapter adapter;
   ProgressDialog progressBar;
   SwipeRefreshLayout one;
    Handler handler;
    RecyclerView.LayoutManager getLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tagihan, container, false);
        sahres = getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
        final String isiid=sahres.getString("ide","empty");
        lottie = view.findViewById(R.id.tagihankosong);
        getLayoutManager =  new LinearLayoutManager(getContext());
        header = view.findViewById(R.id.tagihantitle);
        isidalam = view.findViewById(R.id.tagihanisi);
        one = view.findViewById(R.id.reload);

        dua= view.findViewById(R.id.kas);
        getdata(isiid);
        one.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata(isiid);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        one.setRefreshing(false);
                    }
                },1000);
            }
        });
        return view;

    }



    private void getdata(String isiid) {
        progressBar  = new ProgressDialog(getActivity());
        progressBar.setMessage("Prosess");
        progressBar.setCancelable(true);
        progressBar.setCanceledOnTouchOutside(true);
        progressBar.show();
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
                                    isi = data.getString("nik").trim();
                                    getiurang(isi);
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

    private void getiurang(String isi) {
        iuranwargaposts = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaspesifik/"+isi;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("get data warga")) {

                                    if (jsonArray.length()==0){
                                            lottie.setVisibility(View.VISIBLE);
                                    }else {
                                        header.setVisibility(View.VISIBLE);
                                        isidalam.setVisibility(View.VISIBLE);
                                        lottie.setVisibility(View.GONE);
                                        for (int i = 0;i<jsonArray.length();i++){
                                            iuranwargapost iurandesax = new iuranwargapost();
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            iurandesax.setId(data.getString("id"));
                                            iurandesax.setNamaiuran(data.getString("namaiuran"));
                                            iurandesax.setStatus(data.getString("statu"));
                                            iurandesax.setNominal(data.getString("nominaliuran"));
                                            iuranwargaposts.add(iurandesax);
                                        }

                                        dua.setLayoutManager(getLayoutManager);
                                        adapter = new Adapterwargaindividu(requireContext(),(ArrayList<iuranwargapost>) iuranwargaposts);
                                        dua.setAdapter(adapter);

                                    }
                                    progressBar.dismiss();

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
}
