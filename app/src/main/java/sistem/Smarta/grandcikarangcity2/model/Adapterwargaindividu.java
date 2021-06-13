package sistem.Smarta.grandcikarangcity2.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.Datatagihan;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.Pembayarantagihan;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.PendingTagihan;

public class Adapterwargaindividu extends RecyclerView.Adapter<Adapterwargaindividu.MyViewHolder>{
    private List<iuranwargapost> datahistoriesl;
    private Context context;

    public Adapterwargaindividu(Context requireContext, ArrayList<iuranwargapost> iuranwargaposts) {
        this.datahistoriesl = iuranwargaposts;
        this.context = requireContext;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_danpembayaraniuran,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        iuranwargapost iuranwargapost = datahistoriesl.get(position);
        holder.satu.setText(iuranwargapost.getNamaiuran());
        if (iuranwargapost.getStatus().equals("STATUS_SUCCESS")||iuranwargapost.getStatus().equals("SUCCESS")){
            holder.aku.setBackground(ContextCompat.getDrawable(context,R.drawable.bgoutok));
            holder.dua.setText("SUCCESS");
            holder.staut=iuranwargapost.getStatus();
        }else if (iuranwargapost.getStatus().equals("STATUS_PENDING")||iuranwargapost.getStatus().equals("PENDING")){
            holder.aku.setBackground(ContextCompat.getDrawable(context,R.drawable.bgbutton));
            holder.dua.setText("PENDING");
            holder.staut=iuranwargapost.getStatus();

        }else {
            holder.aku.setBackground(ContextCompat.getDrawable(context,R.drawable.bgbutton));
            holder.dua.setText(iuranwargapost.getStatus());
            holder.staut=iuranwargapost.getStatus();
        }

        holder.tiga.setText("Rp. "+iuranwargapost.getNominal());

    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView satu,dua,tiga;
        String staut;
        public LinearLayout aku;
        public MyViewHolder(final View itemView) {
            super(itemView);
            aku = itemView.findViewById(R.id.statuslinear);
            satu = itemView.findViewById(R.id.namaiuran);
            dua = itemView.findViewById(R.id.status);
            tiga = itemView.findViewById(R.id.nominalbayar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String eko = dua.getText().toString();
                        if (eko.equals("SUCCESS")){
                            final Intent intent;
                            iuranwargapost iuranwargapost = datahistoriesl.get(getAdapterPosition());
                            context = itemView.getContext();
                            intent =  new Intent(context, Datatagihan.class);
                            intent.putExtra("id", iuranwargapost.getId());
                            context.startActivity(intent);
                        }
                    else if (staut.equals("STATUS_PENDING")||staut.equals("PENDING")){
                            final Intent intent;
                            iuranwargapost iuranwargapost = datahistoriesl.get(getAdapterPosition());
                            context = itemView.getContext();
                            intent =  new Intent(context, PendingTagihan.class);
                            intent.putExtra("id", iuranwargapost.getId());
                            context.startActivity(intent);
                    }else {
                        final Intent intent;
                        iuranwargapost iuranwargapost = datahistoriesl.get(getAdapterPosition());
                        context = itemView.getContext();
                        intent =  new Intent(context, Pembayarantagihan.class);
                        intent.putExtra("id", iuranwargapost.getId());
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
