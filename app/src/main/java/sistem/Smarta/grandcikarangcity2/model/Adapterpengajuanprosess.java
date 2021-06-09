package sistem.Smarta.grandcikarangcity2.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;

public class Adapterpengajuanprosess extends RecyclerView.Adapter<Adapterpengajuanprosess.MyViewHolder> {
    private List<DataPengajuan> datahistoriesl;
    private Context context;

    public Adapterpengajuanprosess(Context requireContext, ArrayList<DataPengajuan> dataPengajuans) {
        this.datahistoriesl = dataPengajuans;
        this.context = requireContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_semuasuratfragment,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        DataPengajuan isi =datahistoriesl.get(position);
        holder.dua.setText(isi.getNamasurat());
        holder.satu.setText(isi.getNamawarga());
        holder.id=isi.getId();
        if (isi.getStatus().equals("terkirim")){
            holder.aku.setBackground(ContextCompat.getDrawable(context,R.drawable.bgoutok));
            holder.tiga.setText(isi.getStatus());
        }else {
            holder.aku.setBackground(ContextCompat.getDrawable(context,R.drawable.bgbutton));
            holder.tiga.setText(isi.getStatus());
        }
        holder.aku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tiga.getText().toString().equals("terkirim")){

                }else {
                    holder.updatedata(holder.id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView satu,dua,tiga;
        List<DataPengajuan> dataPengajuans;
        String id;
        public LinearLayout aku;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            aku = itemView.findViewById(R.id.statuslinear);
            satu =itemView.findViewById(R.id.oid);
            dua = itemView.findViewById(R.id.namawarga);
            tiga = itemView.findViewById(R.id.status);

        }

        private void updatedata(String id) {
            final ProgressDialog progressBar = new ProgressDialog(itemView.getContext());
            progressBar.setMessage("Mengaubah Data Mohon tunggu Sejenak");
            progressBar.show();
            String Ur="http://gccestatemanagement.online/public/api/update/"+id;
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
                                        String isi = data.getString("id").trim();

                                        progressBar.dismiss();
                                    }
                                }else {
                                    Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(itemView.getContext(), "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(itemView.getContext(), "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("status","terkirim");
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
            requestQueue.add(volleyMultipartRequest);
        }

    }
}