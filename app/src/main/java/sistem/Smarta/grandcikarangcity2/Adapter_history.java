package sistem.Smarta.grandcikarangcity2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.model.datahistory;


public class Adapter_history extends RecyclerView.Adapter<Adapter_history.Myviewholder>{
    private List<datahistory> datahistoriesl;
    private Context context;
    Date date ;
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
        date = new Date();
     datahistory isi= datahistoriesl.get(position);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat convetDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = dateFormat.parse(isi.getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.satu.setText(convetDateFormat.format(date)+"   ");
        holder.dua.setText("      "+isi.getDeskripsi());
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
