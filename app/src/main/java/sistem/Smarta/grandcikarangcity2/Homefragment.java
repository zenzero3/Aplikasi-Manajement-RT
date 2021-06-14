package sistem.Smarta.grandcikarangcity2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sistem.Smarta.grandcikarangcity2.rt.MainHomeRt;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtpintar_RT;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtpintar_warga;
import sistem.Smarta.grandcikarangcity2.rtpintar.Rtpintarklik;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.HomeWarga;

import static android.content.Context.MODE_PRIVATE;

public class Homefragment extends Fragment {
    ImageButton laporan,pan,pintarklik,history;
TextView user;
    SharedPreferences sahre;
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homewarga, container, false);
        history=view.findViewById(R.id.histrotylaporan);
        pan = view.findViewById(R.id.panic);
         laporan = view.findViewById(R.id.lapor);
         handler = new Handler();
         ImageButton bayarke = view.findViewById(R.id.pembayaran);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
         pintarklik = view.findViewById(R.id.pinter);
         user= view.findViewById(R.id.usernameok);
        onResume();
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.iklan));
        slideModels.add(new SlideModel(R.drawable.gjj));
        slideModels.add(new SlideModel(R.drawable.iklancoy));
        imageSlider.setImageList(slideModels,false);
        bayarke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Pembayaran.class);
                startActivity(intent);
            }
        });
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
               if (i==0){
                   Toast.makeText(requireActivity(),"iklan1",Toast.LENGTH_LONG).show();
               }else  if (i==1){
                   Toast.makeText(requireActivity(),"iklan2",Toast.LENGTH_LONG).show();
               }else  if (i==2){
                   Toast.makeText(requireActivity(),"iklan3",Toast.LENGTH_LONG).show();
               }


            }
        });
history.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), historylaporan.class);
        startActivity(intent);
    }
});
         laporan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent intent = new Intent(getActivity(), Laporankerusakan.class);
                 startActivity(intent);

             }
         });
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), Panic.class);
                startActivity(intent);

            }
        });

        pintarklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   cekdata();
            }
        });



        return view;
    }
    public void onResume() {
        super.onResume();
        getdatax();
        getPrefs();
    }
    private void getPrefs() {
        getdatax();

    }
    private void getdatax(){
        SharedPreferences preferences = getActivity().getSharedPreferences("blood", MODE_PRIVATE);
        String id = preferences.getString("ide","Empty");
        getdatauser(id);

    }
    private void getdatauser(String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+id;
        final ProgressDialog progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Prosess");
        progressBar.show();
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
                                    String usere = data.getString("username").trim();
                                    String fulluser=data.getString("nama").trim();
                                    String level   = data.getString("id_level").trim();
                                    String hp   = data.getString("nohp").trim();
                                    String passw = data.getString("password").trim();
                                    String email = data.getString("email").trim();
                                    String emage = data.getString("id_image").trim();

                                    user.setText("Username \n"+usere);

                                    Log.d("isi",emage);
                                    progressBar.dismiss();

                                }
                            }else {
                                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireActivity(), "Mohon Periksa jaringan anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(requireContext(),"Mohon Periksa jaringan anda",
                            Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                } else if (error instanceof AuthFailureError) {

                } else if (error instanceof ServerError) {
                    onPause();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onResume();
                        }
                    },30000);
                    Toast.makeText(requireContext(),"Maaf Jaringan sibut mohon menunggu beberapa saat",
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }


    private void cekdata() {
        sahre = getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
        String eko = sahre.getString("ide","empty");
        getdata(eko);
    }

    private void getdata(final String eko) {
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setMessage(" Loading \nMohon Menunggu sejenak");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+eko;
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
                                    progressBar.dismiss();
                                    String level   = data.getString("id_level").trim();
                                    if (level.equals("3")){
                                        Intent intent = new Intent(requireContext(), Registerrtpintar_RT.class);
                                        startActivity(intent);
                                    }else  if (level.equals("4")){
                                        getuserwarga();
                                        Intent intent = new Intent(requireContext(), HomeWarga.class);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(requireContext(), Rtpintarklik.class);
                                        startActivity(intent);
                                    }
                                }
                            }else {
                                progressBar.dismiss();
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(requireContext(), "Jaringan Error Mohon Ulangi beberapa saat lagi" , Toast.LENGTH_SHORT).show();

            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private void getuserwarga() {

    }

}