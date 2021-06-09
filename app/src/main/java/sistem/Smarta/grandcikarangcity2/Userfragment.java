package sistem.Smarta.grandcikarangcity2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
         hps = root.findViewById(R.id.hps);
         namalengkaps = root.findViewById(R.id.namalengpa);
        getdata();
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
                                    emails.setText(email);
                                    user.setText(usere);
                                    namalengkaps.setText(fulluser);
                                    hps.setText(hp);
                                    getImageurl(emage);

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
                Toast.makeText(requireActivity().getApplicationContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Prosess");
        progressBar.show();
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
                Toast.makeText(requireActivity().getApplicationContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}
