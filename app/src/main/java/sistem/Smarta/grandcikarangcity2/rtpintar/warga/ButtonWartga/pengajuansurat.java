package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.iuranwargapost;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.SuratRT;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.Wargaregisterone;

import static android.content.Context.MODE_PRIVATE;


public class pengajuansurat extends Fragment {
    String eko,dwi,qlue, tanggallahir="kosong";
    Spinner spinner1,spinner2;
    TextView aku,pengajuan,pakai;
    Button button;
    DatePickerDialog.OnDateSetListener datell;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.fragment_pengajuansurat, container, false);
        final Spinner spinner = view.findViewById(R.id.spinner2);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.pengajuan,R.layout.itemoke);
        adapter.setDropDownViewResource(R.layout.itemoke);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dwi = spinner.getSelectedItem().toString();
                cekisi();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner1 = view.findViewById(R.id.spinners);
        spinner2 = view.findViewById(R.id.spinner6);
        aku = view.findViewById(R.id.te);
        pengajuan = view.findViewById(R.id.nikpengaju);
        button = view.findViewById(R.id.ajukanoke);
        pakai= view.findViewById(R.id.pakaipengajuan);
        pakai.setVisibility(View.GONE);
        final ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(requireContext(), R.array.Pengaju,R.layout.itemoke);
        adapter.setDropDownViewResource(R.layout.itemoke);
        spinner1.setAdapter(adapters);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eko = spinner1.getSelectedItem().toString();
                cekdata();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tanggallahir.equals("kosong")){
                    Toast.makeText(requireContext(),"Tanggal Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                }
              else if (eko.equals("Pengajuan Sendiri")){
                    SharedPreferences oh = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
                    String kk = oh.getString("nik",null);
                    getdatanikwarga(kk);
              }else {
                  pakai.setVisibility(View.VISIBLE);
                  postpengajuankeluarga();
              }

            }
        });
        pakai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year,mont,day;
                year=cal.get(Calendar.YEAR);
                mont=cal.get(Calendar.MONTH);
                day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(requireContext(), datell,year,mont,day);

                dialog.show();
                datell = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int bulan = month+1;
                        String data = year+"-"+bulan+"-"+dayOfMonth;
                        pakai.setText(data);
                        pakai.setTextColor(Color.parseColor("#000000"));
                        tanggallahir = data;
                    }
                };
            }
        });


        return view;
    }

    private void postpengajuankeluarga() {
        SharedPreferences oh = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
        String kk = oh.getString("nik",null);
        if (kk.equals(qlue)){
            Toast.makeText(getContext(),"Maaf NIK hanya bisa digunakan untuk pengajuan sendiri",Toast.LENGTH_LONG).show();
        }else {
            if (qlue.equals("Belum Memiliki Ktp")){
                Toast.makeText(getContext(),"Surat Pengantar Tidak dapat di Proses \n  Umur Belum Mencukupi Mohon Hubungi Ketua RT",Toast.LENGTH_LONG).show();
            }else {
                getdatanikwarga(qlue);
            }

        }
    }

    private void getdatanikwarga(final String kk) {
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaktp/"+kk;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (status.equals("true")) {
                                if (message.equals("get data warga")) {
                                      String nama = data.getString("nama_lengkap");
                                      String nkks = data.getString("nkk");
                                     postkeluarga(nama,kk,nkks);
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
private void postkeluarga(final String nama, final String nik,final String kkid){
    SharedPreferences yea = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
    final String rtid=yea.getString("id_rt",null);
    String UrlLogin="http://gccestatemanagement.online/public/api/suratwarga";
    StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        String success  = jsonObject.getString("success");
                        JSONObject data    = jsonObject.getJSONObject("data");

                        if (message.equals("data tersimpan")) {
                            if (success.equals("true")) {
                                Toast.makeText(requireContext(),"Data terkirim",Toast.LENGTH_LONG).show();
                                getActivity().finish();
                            }
                        }else {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Jaringan Bermasalah Mohon Periksa Jaringan Ana"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(requireContext(), "Periksa Jaringan Anda"+error.toString(), Toast.LENGTH_SHORT).show();
            Log.d("nik",nik);
            Log.d("id_rt", rtid);
            Log.d("tanggal pakai", tanggallahir);
            Log.d("namapengajuan", dwi);
            Log.d("namawarga", nama);
        }
    }){
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("nik", nik);
            params.put("id_rt", rtid);
            params.put("kk",kkid);
            params.put("tanggal_pakai",tanggallahir);
            params.put("namapengajuan",dwi);
            params.put("nama_warga",nama);
            params.put("status","Proses");
            return params;
        }};
    RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
    requestQueue.add(stringRequest);

}

    private void cekdata() {
        if (eko.isEmpty()){
            button.setVisibility(View.GONE);
            pakai.setVisibility(View.GONE);
        }
        else if (eko.equals("Silahkan Pilih Status Pengajuan")){
            pakai.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        } else if (eko.equals("Pengajuan Sendiri")){
            pakai.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            pengajuan.setVisibility(View.GONE);
            spinner2.setVisibility(View.GONE);

        }else {
            pakai.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            getdatakak();

        }
    }

    private void getdatakak() {
        SharedPreferences oh = getActivity().getSharedPreferences("Kepala",MODE_PRIVATE);
        String kk = oh.getString("nkk",null);
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
                                    button.setVisibility(View.VISIBLE);
                                    pengajuan.setVisibility(View.VISIBLE);
                                    spinner2.setVisibility(View.VISIBLE);
                                    ArrayAdapter<String> adapte = new ArrayAdapter<>(requireContext(),R.layout.itemoke,nik);
                                    adapte.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                    spinner2.setAdapter(adapte);
                                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            qlue = spinner2.getSelectedItem().toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
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

    private void cekisi() {
        if (dwi.equals("Silahkan Pilih Surat Pengajuan")){
         aku.setVisibility(View.GONE);
         pakai.setVisibility(View.GONE);
         spinner1.setVisibility(View.GONE);
        pengajuan.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        }
        else {
            pakai.setVisibility(View.VISIBLE);
            aku.setVisibility(View.VISIBLE);
            spinner1.setVisibility(View.VISIBLE);
        }
    }

}