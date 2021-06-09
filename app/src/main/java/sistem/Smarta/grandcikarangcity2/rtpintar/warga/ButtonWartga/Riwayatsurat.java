package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import sistem.Smarta.grandcikarangcity2.Adapter;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Adapterwargaindividu;
import sistem.Smarta.grandcikarangcity2.model.iuranwargapost;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton.Adapterhistory;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton.datawarga;

import static android.content.Context.MODE_PRIVATE;

public class Riwayatsurat extends Fragment {
    LottieAnimationView lottieAnimationView;
    List<datawarga> datawargaList;
    RecyclerView dua;
    LinearLayout pp;
  RecyclerView.Adapter adapter;

    RecyclerView.LayoutManager layoutManager;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.riwayatsurat, container, false);
        lottieAnimationView=view.findViewById(R.id.anime);
        layoutManager = new LinearLayoutManager(requireContext());
        SharedPreferences oh = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
        String kk = oh.getString("nkk",null);
        getkk(kk);
        return view;
    }

    private void getkk(String kk) {
        datawargaList = new ArrayList<>();
        lottieAnimationView.setVisibility(View.GONE);
        final List<String>nik=new ArrayList<>();
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
                                        getdataiuran(nike);
                                    }
                                    pp = getActivity().findViewById(R.id.paseone);
                                    dua = getActivity().findViewById(R.id.repas);
                                    pp.setVisibility(View.VISIBLE);
                                    dua.setVisibility(View.VISIBLE);

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

    private void getdataiuran(String nik) {
        lottieAnimationView.setVisibility(View.GONE);
        String UrlLogin="http://gccestatemanagement.online/public/api/suratwargas/"+nik;
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
                                if (message.equals("get_Pengajuan")) {
                                    String ex = String.valueOf(jsonArray.length());
                                    if (ex.equals("")){
                                        lottieAnimationView.setVisibility(View.VISIBLE);
                                    }else {
                                        lottieAnimationView.setVisibility(View.GONE);
                                    for (int i = 0;i<jsonArray.length();i++){
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        datawarga datawargas= new datawarga();
                                        datawargas.setId(data.getString("id"));
                                        datawargas.setNama_warga(data.getString("nama_warga"));
                                        datawargas.setNamapengajuan(data.getString("namapengajuan"));
                                        datawargas.setStatus(data.getString("status"));
                                        datawargaList.add(datawargas);
                                    }
                                        dua.setLayoutManager(layoutManager);
                                        adapter = new Adapterhistory(requireContext(),(ArrayList<datawarga>) datawargaList);
                                        dua.setAdapter(adapter);

                                    }
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
