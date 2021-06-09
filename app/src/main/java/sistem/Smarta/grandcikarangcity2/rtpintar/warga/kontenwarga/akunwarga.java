package sistem.Smarta.grandcikarangcity2.rtpintar.warga.kontenwarga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.ButtonEditakun;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.KartuKeluargaWarga;

import static android.content.Context.MODE_PRIVATE;

public class akunwarga extends Fragment {
Button edit,kk;
String desaku, gambarpasang,id,keplaoke,idnkk,id_rtx;
CircleImageView awas;
TextView nmdesa,nix,namaleng,mailx,jk,hp,agam,lahir,tgllhir,statusper,kerja,pendik,tinggal,almleng;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_akunwarga, container, false);
       edit=view.findViewById(R.id.edit1);
       awas=view.findViewById(R.id.profile_image);
       nmdesa =view.findViewById(R.id.namadesa);
       nix =view.findViewById(R.id.nik);
       namaleng=view.findViewById(R.id.namalengkap);
       mailx =view.findViewById(R.id.mail);
       jk = view.findViewById(R.id.jeniskelamin);
       hp = view.findViewById(R.id.nohp);
       agam = view.findViewById(R.id.agama);
       lahir = view.findViewById(R.id.tempatlahir);
       tgllhir =view.findViewById(R.id.tanggallahir);
       statusper = view.findViewById(R.id.statuspernikahan);
       kerja = view.findViewById(R.id.pekerjaan);
       pendik = view.findViewById(R.id.pendidikanterakhir);
       tinggal = view.findViewById(R.id.statustempattinggal);
       almleng = view.findViewById(R.id.alamlengkap);
       kk = view.findViewById(R.id.edit);
        onResume();
       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences wau= getActivity().getSharedPreferences("lengkap diri",MODE_PRIVATE);
               SharedPreferences.Editor editor = wau.edit();
               editor.putString( "id_rt",id_rtx);
               editor.putString("idkepala",keplaoke);
               editor.putString("idnkk",idnkk);
               editor.putString("namadesa",desaku);
               editor.putString("nik",nix.getText().toString());
               editor.putString("namalengkap",namaleng.getText().toString());
               editor.putString("mail",mailx.getText().toString());
               editor.putString("jk",jk.getText().toString());
               editor.putString("hp",hp.getText().toString());
               editor.putString("agama",agam.getText().toString());
               editor.putString("tempatlahir",lahir.getText().toString());
               editor.putString("tanggallahir",tgllhir.getText().toString());
               editor.putString("statuspernikahan",statusper.getText().toString());
               editor.putString("pekerjaan",kerja.getText().toString());
               editor.putString("pendidikan",pendik.getText().toString());
               editor.putString("tinggalstatus",tinggal.getText().toString());
               editor.putString("alamlengkap",almleng.getText().toString());
               editor.putString("gambar",gambarpasang);
               editor.putString("iduserone",id);
               editor.commit();
               editor.apply();
               Intent intent = new Intent(getActivity(), ButtonEditakun.class);
               startActivity(intent);
           }
       });
       kk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences wau= getActivity().getSharedPreferences("lengkap diri",MODE_PRIVATE);
               SharedPreferences.Editor editor = wau.edit();
               editor.putString( "id_rt",id_rtx);
               editor.putString("idkepala",keplaoke);
               editor.putString("idnkk",idnkk);
               editor.putString("namadesa",desaku);
               editor.putString("nik",nix.getText().toString());
               editor.putString("namalengkap",namaleng.getText().toString());
               editor.putString("mail",mailx.getText().toString());
               editor.putString("jk",jk.getText().toString());
               editor.putString("hp",hp.getText().toString());
               editor.putString("agama",agam.getText().toString());
               editor.putString("tempatlahir",lahir.getText().toString());
               editor.putString("tanggallahir",tgllhir.getText().toString());
               editor.putString("statuspernikahan",statusper.getText().toString());
               editor.putString("pekerjaan",kerja.getText().toString());
               editor.putString("pendidikan",pendik.getText().toString());
               editor.putString("tinggalstatus",tinggal.getText().toString());
               editor.putString("alamlengkap",almleng.getText().toString());
               editor.putString("gambar",gambarpasang);
               editor.putString("iduserone",id);
               editor.commit();
               editor.apply();
               Intent intent = new Intent(getActivity(), KartuKeluargaWarga.class);
               startActivity(intent);
           }
       });
        return view;
    }
    public void onResume() {
        super.onResume();
        getPrefs();
    }
    private void getPrefs() {
        SharedPreferences lol = getActivity().getSharedPreferences("blood", MODE_PRIVATE);
        String nik = lol.getString("ide",null);
        SharedPreferences oh = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
        String desa=oh.getString("id_rt",null);
        String nixe = oh.getString("nik",null);
        wargadata(nik);
        desaget(desa);
        ktp(nixe);
        getdata();
    }

    private void ktp(String nik) {
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaktp/"+nik;
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
                                    agam.setText(data.getString("agama"));
                                    lahir.setText(data.getString("tempatlahir"));
                                    tgllhir.setText(data.getString("tanggallahir"));
                                    statusper.setText(data.getString("status_pernikahan"));
                                    pendik.setText(data.getString("pendidikan"));
                                    tinggal.setText(data.getString("status_tempat"));
                                    jk.setText(data.getString("jenis_kelamin"));
                                    String kerja = data.getString("id_kerja");
                                    getkerja(kerja);
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

    private void getkerja(final String kerjas) {
        String UrlLogin="http://gccestatemanagement.online/public/api/kerja/"+kerjas;
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
                                if (message.equals("get data iuran")) {
                                    kerja.setText(data.getString("nama_pekerjaan"));
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

    private void desaget(String desa) {
        String UrlLogin="http://gccestatemanagement.online/public/api/wargadesa/"+desa;
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
                                    nmdesa.setText("Desa "+data.getString("desa"));
                                    desaku=data.getString("desa");
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
                                    keplaoke = data.getString("id");
                                    idnkk = data.getString("nkk");
                                    id_rtx = data.getString( "id_rt");
                                    nix.setText(data.getString("nik"));
                                    namaleng.setText(data.getString("nama_lengkap"));
                                    mailx.setText(data.getString("mail"));
                                    hp.setText(data.getString("nohp"));
                                    almleng.setText(data.getString("alamat"));

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
    private void getdata(){
        SharedPreferences preferences = getActivity().getSharedPreferences("blood", MODE_PRIVATE);
        id = preferences.getString("ide","Empty");
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
                                       String idem=jonobject.getString("id").trim();
                                        String path=jonobject.getString("path").trim();
                                     gambarpasang ="http://gccestatemanagement.online/public/gallery/"+path;
                                        Picasso.get()
                                                .load(gambarpasang)
                                                .into(awas);
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