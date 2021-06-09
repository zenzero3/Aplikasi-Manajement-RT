package sistem.Smarta.grandcikarangcity2.rt.fragmentrt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sistem.Smarta.grandcikarangcity2.R;

public class adpteruser extends RecyclerView.Adapter<adpteruser.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onlineuser,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 3;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

        }
    }
}

