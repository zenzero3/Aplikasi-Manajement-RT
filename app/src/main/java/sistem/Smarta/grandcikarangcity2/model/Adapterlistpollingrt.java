package sistem.Smarta.grandcikarangcity2.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Pollingwarga;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmentlistpolling.Polling_Button;

import static android.content.ContentValues.TAG;

public class Adapterlistpollingrt extends RecyclerView.Adapter<Adapterlistpollingrt.MyViewHolder> {
    private List<pollingrtmodule> datahistoriesl;
    private Context context;

    public Adapterlistpollingrt(Context context, ArrayList<pollingrtmodule> datahistories) {
        this.datahistoriesl = datahistories;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listpollingrecycle,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        pollingrtmodule isi =datahistoriesl.get(position);
        holder.satu.setText(isi.getNamapolling());
        holder.dua.setText(isi.getDeskripsipolling());
        Picasso.get()
                .load("http://gccestatemanagement.online/public/gallery/"+isi.getImage())
                .resize(50, 50)
                .into(holder.wee);
    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView satu,dua,tiga,empat;
        ImageView wee;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();
             wee = itemView.findViewById(R.id.imageView);
            satu = itemView.findViewById(R.id.textView7);
            dua = itemView.findViewById(R.id.textView10);
           tiga = itemView.findViewById(R.id.textView11);
            empat = itemView.findViewById(R.id.textView12);
            final Button satuoke = itemView.findViewById(R.id.cekpolling);
            empat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    satuoke.setVisibility(View.GONE);
                    empat.setVisibility(View.GONE);
                    tiga.setVisibility(View.VISIBLE);
                    dua.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 35));
                }
            });
            tiga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tiga.setVisibility(View.GONE);
                    satuoke.setVisibility(View.GONE);
                    empat.setVisibility(View.VISIBLE);
                    dua.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
            });

            satuoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Polling_Button.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
