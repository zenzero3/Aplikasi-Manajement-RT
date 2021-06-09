package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.Keluargakk;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.EditKK;

public class Adapterkeluarga extends RecyclerView.Adapter<Adapterkeluarga.Myviewholder> {
    private List<Keluargakk> datahistoriesl;
    private Context context;

    public Adapterkeluarga(Context context, ArrayList<Keluargakk> datahistories){
        this.datahistoriesl = datahistories;
        this.context = context;
    }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutkk,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        Keluargakk ds= datahistoriesl.get(position);
        holder.satu.setText(ds.getNik());
        holder.tiga.setText(ds.getStatuskk());
        holder.idku=ds.getId();
    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        String idku,tahta;
        public TextView satu,tiga;
        public Myviewholder(@NonNull final View itemView) {
            super(itemView);
            satu =itemView.findViewById(R.id.isi1);
            tiga = itemView.findViewById(R.id.isi3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tahta=tiga.getText().toString();
                    if (tahta.equals("Kepala Keluarga")){
                        Toast.makeText(context,"Hanya Dapat Mengubah Anggota Keluarga Saja",Toast.LENGTH_LONG).show();
                    }else{
                    Keluargakk ds= datahistoriesl.get(getAdapterPosition());
                    Intent intent = new Intent(context, EditKK.class);
                    intent.putExtra("id", ds.getId());
                    context.startActivity(intent);
                    ((Activity)context).finish();}
                }
            });
        }
    }
}
