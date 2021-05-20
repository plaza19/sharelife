package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;

import Utils.UserManager;
import modelos.Publicacion;
import modelos.Usuario;

public class PublicacionAdapter extends FirestoreRecyclerAdapter<Publicacion, PublicacionAdapter.ViewHolder> {

    private Context context;
    private UserManager userManager;

    public PublicacionAdapter(FirestoreRecyclerOptions<Publicacion> opciones, Context context) {
        super(opciones);
        this.context = context;
        this.userManager = new UserManager();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Publicacion model) {
        holder.comentario.setText(model.getComentario());
        Picasso.with(this.context).load(model.getImage()).into(holder.foto);

        userManager.getUser(model.getId_usuario()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    Picasso.with(context).load(task.getResult().get("profile_image").toString()).into(holder.foto_perfil);

                }


            }
        });
        //obtenemos el nombre del usuario a partir del id

        FirebaseFirestore store = FirebaseFirestore.getInstance();
        store.collection("Users").document(model.getId_usuario()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                holder.usuario.setText(task.getResult().get("user_name").toString());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacion_layout, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView comentario, usuario;
        ImageView foto, foto_perfil;

        public ViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.imageView_publicacion_home);
            comentario = view.findViewById(R.id.text_comentario_home);
            usuario = view.findViewById(R.id.textView_usuario_publi);
            foto_perfil = view.findViewById(R.id.idPhoto_user);
        }



    }


}
