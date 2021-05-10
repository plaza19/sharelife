package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;

import modelos.Publicacion;

public class PublicacionAdapter extends FirestoreRecyclerAdapter<Publicacion, PublicacionAdapter.ViewHolder> {

    private Context context;

    public PublicacionAdapter(FirestoreRecyclerOptions<Publicacion> opciones, Context context) {
        super(opciones);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Publicacion model) {
        holder.comentario.setText(model.getComentario());
        Picasso.with(this.context).load(model.getImage()).into(holder.foto);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacion_layout, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView comentario;
        ImageView foto;

        public ViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.imageView_publicacion_home);
            comentario = view.findViewById(R.id.text_comentario_home);
        }



    }


}
