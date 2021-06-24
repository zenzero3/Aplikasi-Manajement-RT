package sistem.Smarta.grandcikarangcity2.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sistem.Smarta.grandcikarangcity2.R;

public class Adapterserverinfo extends RecyclerView.Adapter<Adapterserverinfo.MyviewHolder> {
    private List<Infoservermodel> datahistoriesl;
    private Context context;

    public Adapterserverinfo(Context requireContext, ArrayList<Infoservermodel> dataPengajuans){
        this.datahistoriesl = dataPengajuans;
        this.context = requireContext;
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_infoisi,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Infoservermodel ids = datahistoriesl.get(position);
        holder.namainfo.setText(ids.getName());
        holder.detailinfo.setText(ids.getDeskripsi());
        Picasso.get()
                .load("http://gccestatemanagement.online/public/gallery/"+ids.getImage())
                .into(holder.gambarifo);

    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        CircleImageView gambarifo;
        TextView namainfo,detailinfo;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            gambarifo = itemView.findViewById(R.id.oneinfo);
            namainfo = itemView.findViewById(R.id.nama_info);
            detailinfo=itemView.findViewById(R.id.deskripsi);

        }
    }
}
