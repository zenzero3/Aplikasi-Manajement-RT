package sistem.Smarta.grandcikarangcity2;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    ArrayList<Bitmap> image;
    public Adapter(ArrayList<Bitmap> img) {
        image=img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewpoto,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.satu.setImageBitmap(image.get(position));
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
      ImageButton cancel;
      ImageView satu;
      int hapus;
      int update =0;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            cancel=itemView.findViewById(R.id.hapusimage);
            satu = itemView.findViewById(R.id.tampilimage);
            cancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    if (image.size()==0){
                    }
                    int pos = getAdapterPosition();
                    hapus =pos;
                    image.remove(hapus);
                    update =1;
                    notifyItemRemoved(hapus);
                    notifyItemRangeChanged(hapus, image.size());
                    itemView.setVisibility(View.GONE);
                }
            });
        }
    }
}
