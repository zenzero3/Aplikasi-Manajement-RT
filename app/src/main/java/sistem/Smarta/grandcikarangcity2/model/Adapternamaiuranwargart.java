package sistem.Smarta.grandcikarangcity2.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;

public class Adapternamaiuranwargart extends RecyclerView.Adapter<Adapternamaiuranwargart.MyViewHolder> {
    private List<iurannamawarga> iurannamawargaList;
    private Context context;

    public Adapternamaiuranwargart(Context context, ArrayList<iurannamawarga> datahistories) {
        this.iurannamawargaList = datahistories;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isihistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      iurannamawarga aku = iurannamawargaList.get(position);
      holder.satu.setText(aku.getNamawarga());
      holder.dua.setText(aku.getNamaiuran());
      holder.tiga.setText(aku.getStatus());
    }

    @Override
    public int getItemCount() {
        return iurannamawargaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView satu,dua,tiga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            satu =itemView.findViewById(R.id.isi1);
            dua = itemView.findViewById(R.id.isi2);
            tiga = itemView.findViewById(R.id.isi3);
        }
    }
}
