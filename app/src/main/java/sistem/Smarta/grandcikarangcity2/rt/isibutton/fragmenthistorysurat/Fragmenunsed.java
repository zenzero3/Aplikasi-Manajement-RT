package sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmenthistorysurat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AdapterPengajuanall;
import sistem.Smarta.grandcikarangcity2.model.DataPengajuan;

public class Fragmenunsed extends Fragment {
    RecyclerView re;
    RecyclerView.LayoutManager layoutManager;
    List<DataPengajuan> dataPengajuans;
    String getId;
    private Handler handler;
   LottieAnimationView lottieAnimationView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmeentunusedhistory, container, false);
        ; SharedPreferences share = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        getId=share.getString("id",null);
        lottieAnimationView = v.findViewById(R.id.notfound);
        layoutManager = new LinearLayoutManager(requireContext());
        getRt(getId);
        this.handler = new Handler();
        this.handler.postDelayed(m_Runnable,4000);
        return v;
    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            getRt(getId);

            handler.postDelayed(m_Runnable, 10000);
        }

    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(m_Runnable);
        getActivity().finish();

    }

    private void getRt(final String getId) {
        String UrlLogin="http://gccestatemanagement.online/public/api/getrt/"+getId;
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
                                    getsurat(isi);
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

    private void getsurat(String isi) {
        dataPengajuans= new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/suratwes/"+isi;
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
                                if (message.equals("get_Pengajuan")) {
                                    int op= jsonArray.length();
                                    if (op==0){
                                        lottieAnimationView.setVisibility(View.VISIBLE);
                                    }else {
                                        lottieAnimationView.setVisibility(View.GONE);
                                        for (int i = 0;i<jsonArray.length();i++){
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            DataPengajuan iurandesa = new DataPengajuan();
                                            iurandesa.setId(data.getString("id"));
                                            iurandesa.setNamasurat(data.getString("namapengajuan"));
                                            iurandesa.setNamawarga(data.getString("nama_warga"));
                                            iurandesa.setStatus(data.getString("status"));
                                            dataPengajuans.add(iurandesa);
                                        }
                                    }

                                    layoutManager = new LinearLayoutManager(getContext());
                                    re= getActivity().findViewById(R.id.iniones1);
                                    re.setLayoutManager(layoutManager);
                                    final AdapterPengajuanall adapterPengajuanall= new AdapterPengajuanall(requireContext(),(ArrayList<DataPengajuan>)dataPengajuans);
                                    adapterPengajuanall.notifyDataSetChanged();
                                    re.setAdapter(adapterPengajuanall);

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
