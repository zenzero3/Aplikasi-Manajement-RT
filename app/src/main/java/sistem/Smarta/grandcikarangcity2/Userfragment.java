package sistem.Smarta.grandcikarangcity2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import sistem.Smarta.grandcikarangcity2.model.SessionManager;
import sistem.Smarta.grandcikarangcity2.rtpintar.Rtpintarklik;

import static android.content.Context.MODE_PRIVATE;

public class Userfragment extends Fragment {
    String gambarpasang;
    String path,idem;
    CircleImageView aku;
    Handler handler ;
    ProgressDialog progressBar;
    TextView emails,user,hps,namalengkaps;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.userfragment, container, false);
        final SessionManager sessionManager= new SessionManager(requireContext());
        Button out = root.findViewById(R.id.lout);
        ImageView create= root.findViewById(R.id.ganti);
        aku= root.findViewById(R.id.profile_image);
        emails= root.findViewById(R.id.mail);
         user = root.findViewById(R.id.username);
        handler= new Handler();
         hps = root.findViewById(R.id.hps);
         namalengkaps = root.findViewById(R.id.namalengpa);
        progressBar = new ProgressDialog(getActivity());
        onResume();
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Keluar")
                        .setMessage("Apa kamu yakin Ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.logout();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .setIcon(android.R.drawable.ic_menu_help)
                        .show();

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Usereditclass.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void getdata(){
    SharedPreferences preferences = getActivity().getSharedPreferences("blood", MODE_PRIVATE);
    String id = preferences.getString("ide","Empty");
        getdatauser(id);

    }

    private void getdatauser(String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/register/"+id;
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
                                    emails.setText(email);
                                    user.setText(usere);
                                    namalengkaps.setText(fulluser);
                                    hps.setText(hp);
                                    getImageurl(emage);
                                    Log.d("isi",emage);

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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    public void onResume() {
        super.onResume();
        getdata();
        getPrefs();
    }
    private void getPrefs() {
    getdata();

    }

    private void getImageurl(String image) {
        String UrlLogin="http://gccestatemanagement.online/public/api/image/"+image;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject json = new JSONObject(response);
                            JSONArray jsonarray = json.getJSONArray("data");

                            if (status.equals("get")) {
                                if (message.equals("Get data")) {
                                    int iko=jsonarray.length();
                                    if (iko==0){
                                        progressBar.dismiss();
                                    }else {
                                    for(int i=0; i < jsonarray.length(); i++) {
                                        JSONObject jonobject = jsonarray.getJSONObject(i);
                                        idem=jonobject.getString("id").trim();
                                        path=jonobject.getString("path").trim();
                                        gambarpasang ="http://gccestatemanagement.online/public/gallery/"+path;
                                        Picasso.get()
                                                .load(gambarpasang)
                                                .into(aku);
                                        Log.d("gambarpasang",gambarpasang);
                                        progressBar.dismiss();
                                    }

                                }
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
}
