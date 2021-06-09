package sistem.Smarta.grandcikarangcity2.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;

public class adapterimagelaporanwarga  extends RecyclerView.Adapter<adapterimagelaporanwarga.MyViewHolder>{
    private List<imagelapwargadarirt> datahistoriesl;
    private Context context;

    public adapterimagelaporanwarga(Context context, ArrayList<imagelapwargadarirt> datahistories) {
        this.datahistoriesl = datahistories;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_image_detaillaporan,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     imagelapwargadarirt isi =datahistoriesl.get(position);
        String gambarpasang ="http://gccestatemanagement.online/public/gallery/"+isi.getPath();
        Picasso.get() .load(gambarpasang)
                .fit()
                .into(holder.one);

    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView one;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
          one = itemView.findViewById(R.id.imggambar);
        }
    }
}
