package sistem.Smarta.grandcikarangcity2.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.Adapter_history;
import sistem.Smarta.grandcikarangcity2.R;

public class Adapteriuran extends RecyclerView.Adapter<Adapteriuran.MyViewHolder>{
    private List<iurannamadesa> datahistoriesl;
    private Context context;

    public Adapteriuran(Context context, ArrayList<iurannamadesa> datahistories) {
        this.datahistoriesl = datahistories;
        this.context = context;
    }
    @NonNull
    @Override
    public Adapteriuran.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isihistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapteriuran.MyViewHolder holder, int position) {
        iurannamadesa isi =datahistoriesl.get(position);
        holder.satu.setText(isi.getTanggalstart());
        holder.dua.setText(isi.getNama());
        holder.tiga.setText(isi.getNominal());

    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView satu,dua,tiga;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            satu =itemView.findViewById(R.id.isi1);
            dua = itemView.findViewById(R.id.isi2);
            tiga = itemView.findViewById(R.id.isi3);
        }
    }
}
