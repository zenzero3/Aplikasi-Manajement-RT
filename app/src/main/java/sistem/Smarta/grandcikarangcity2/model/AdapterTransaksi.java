package sistem.Smarta.grandcikarangcity2.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.kasbutton.Bayar;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.kasbutton.Cekbayar;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.kasbutton.Lunas;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.Datatagihan;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.Pembayarantagihan;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.PendingTagihan;

public class AdapterTransaksi  extends RecyclerView.Adapter<AdapterTransaksi.MyviewHolder> {
  private List<Transaksi> transaksis;
  private Context context;


  public AdapterTransaksi(Context context, ArrayList<Transaksi> datahistories){
      this.context=context;
      this.transaksis=datahistories;
  }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isihistory,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Transaksi transaksi =transaksis.get(position);
        String pending = transaksi.getStatus();
        holder.satum.setText(transaksi.getNamatransaksi()+" ");
        holder.dua.setText("  "+transaksi.getNominal());
        holder.tiga.setText(transaksi.getStatus());
        if (pending.equals("STATUS_PENDING")|| pending.equals("PENDING")){
            holder.tiga.setText("PENDING");
            holder.staut = holder.tiga.getText().toString();
        }else {
            holder.tiga.setText("SUCCESS");
            holder.staut = holder.tiga.getText().toString();
        }

    }

    @Override
    public int getItemCount() {
        return transaksis.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView satum,dua,tiga;
        String staut;
        public MyviewHolder(@NonNull final View itemView) {
            super(itemView);
            satum = itemView.findViewById(R.id.isi1);
            dua   = itemView.findViewById(R.id.isi2);
            tiga  =itemView.findViewById(R.id.isi3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (staut.equals("SUCCESS")){
                        final Intent intent;
                       Transaksi iuranwargapost = transaksis.get(getAdapterPosition());
                        context = itemView.getContext();
                        intent =  new Intent(context, Lunas.class);
                        intent.putExtra("id", iuranwargapost.getId());
                        context.startActivity(intent);
                    }
                    else if (staut.equals("STATUS_PENDING")||staut.equals("PENDING")){
                        final Intent intent;
                        Transaksi iuranwargapost = transaksis.get(getAdapterPosition());
                        context = itemView.getContext();
                        intent =  new Intent(context, Cekbayar.class);
                        intent.putExtra("id", iuranwargapost.getId());
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
