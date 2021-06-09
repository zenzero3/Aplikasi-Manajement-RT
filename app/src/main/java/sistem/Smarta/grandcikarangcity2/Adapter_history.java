package sistem.Smarta.grandcikarangcity2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.model.datahistory;


public class Adapter_history extends RecyclerView.Adapter<Adapter_history.Myviewholder>{
    private List<datahistory> datahistoriesl;
    private Context context;
    public Adapter_history(Context context, ArrayList<datahistory> datahistories) {
    this.datahistoriesl = datahistories;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isihistory,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
     datahistory isi= datahistoriesl.get(position);
        holder.satu.setText(isi.getTanggal());
        holder.dua.setText(isi.getDeskripsi());
        holder.tiga.setText(isi.getStatus());
    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{
    public TextView satu,dua,tiga;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            satu =itemView.findViewById(R.id.isi1);
            dua = itemView.findViewById(R.id.isi2);
            tiga = itemView.findViewById(R.id.isi3);
        }
    }
}
