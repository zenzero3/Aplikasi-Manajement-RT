package sistem.Smarta.grandcikarangcity2.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.detaillaporanwarga;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.PendingTagihan;

public class AdapterLaporanrt extends RecyclerView.Adapter<AdapterLaporanrt.MyViewHolder> {
    private List<datalaporanwarga> datahistoriesl;
    private Context context;

    public AdapterLaporanrt(Context context, ArrayList<datalaporanwarga> datahistories) {
        this.datahistoriesl = datahistories;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_isilaporanwarga,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final datalaporanwarga ds = datahistoriesl.get(position);
        holder.detail.setText("Detail laporan "+ds.getStatu());
        holder.lokasi.setText("Lokasi  "+ds.getDeskripsi());
        holder.judul.setText("Nama Laporan "+ds.getJudul());
        holder.date.setText(ds.getDate());
        holder.status.setText("Status "+ds.getStatus());
        if (ds.getStatus().equals("proses")){
            holder.tanggapi.setText("Tanggapi");
        }else {
            holder.tanggapi.setText("Cek Detail Laporan");
        }
        holder.tanggapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ds.getStatus().equals("proses")){
                   getupdate(ds.getId(),position);
                }else {
                        goondetail(ds.getId());
                }
            }
        });


    }

    private void goondetail(String id) {
       Intent intent =  new Intent(context, detaillaporanwarga.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private void getupdate(String id, int position) {
        final datalaporanwarga datalaporanwarga =  datahistoriesl.get(position);
        String Ur="http://gccestatemanagement.online/public/api/updatelaporan/"+id;
        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.PUT, Ur,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject result = new JSONObject(response);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data=result.getJSONObject("data");

                            if (message.equals("Data berhasil diubah")) {
                                if (success.equals("true")) {
                                    String idrt= data.getString("id_rt");
                                    datalaporanwarga.setStatus("Ditanggapi RT");

                                    notifyDataSetChanged();
                                }
                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(context, "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sta_laporan","Ditanggapi RT");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(volleyMultipartRequest);
    }


    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button tanggapi;
        TextView date,judul,status,lokasi,detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggapi= itemView.findViewById(R.id.textView17);
            date =itemView.findViewById(R.id.textView16);
            judul= itemView.findViewById(R.id.textView13);
            detail = itemView.findViewById(R.id.textView15);
            status = itemView.findViewById(R.id.textView18);
            lokasi = itemView.findViewById(R.id.lokasilap);
        }
    }
}
