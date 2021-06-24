package sistem.Smarta.grandcikarangcity2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.model.Adapterserverinfo;
import sistem.Smarta.grandcikarangcity2.model.Infoservermodel;
import sistem.Smarta.grandcikarangcity2.model.datahistory;
import sistem.Smarta.grandcikarangcity2.model.pollingrtmodule;

public class Infofragment extends Fragment {
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Infoservermodel> pollingrtmodules;
    LottieAnimationView lottieAnimationView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.infofragment, container, false);
        recyclerView=root.findViewById(R.id.inforecy);
        progressBar = new ProgressDialog(getActivity());
        layoutManager = new LinearLayoutManager(requireContext());
        swipeRefreshLayout = root.findViewById(R.id.loli);
        lottieAnimationView = root.findViewById(R.id.isikosonginfoserver);
        getdata();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });
        return root;
    }

    private void getdata() {
        pollingrtmodules = new ArrayList<>();
        progressBar.setMessage("Prosess");
        progressBar.show();
        String url = "http://gccestatemanagement.online/public/api/info";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("data");
                            if (message.equals("get_info")) {
                                if (status.equals("true")) {
                                    swipeRefreshLayout.setRefreshing(false);
                                    if (jsonArray.length()==0){
                                        recyclerView.setVisibility(View.GONE);
                                        lottieAnimationView.setVisibility(View.VISIBLE);
                                        progressBar.dismiss();
                                    }else {
                                        mAdapter.notifyDataSetChanged();
                                        for (int i = 0;i<jsonArray.length();i++){
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            Infoservermodel ids = new Infoservermodel();
                                            ids.setDeskripsi(data.getString("deskripsi"));
                                            ids.setImage(data.getString("image"));
                                            ids.setName(data.getString("nama_info"));
                                            pollingrtmodules.add(ids);
                                        }
                                        recyclerView.setVisibility(View.VISIBLE);
                                        lottieAnimationView.setVisibility(View.GONE);
                                        recyclerView.setLayoutManager(layoutManager);
                                        mAdapter = new Adapterserverinfo(requireContext(),(ArrayList<Infoservermodel>)pollingrtmodules);
                                        recyclerView.setAdapter(mAdapter);
                                        progressBar.dismiss();
                                    }



                                }
                            }else {
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(requireContext(), "Data Kosong" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(requireContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    Toast.makeText(requireContext(),"Maaf Jaringan sibuk mohon menunggu beberapa saat",
                            Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(requireContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                } else if (error instanceof ParseError) {
                    progressBar.dismiss();
                }
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}
