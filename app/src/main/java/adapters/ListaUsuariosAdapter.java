package adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import modelos.Usuario;

public class ListaUsuariosAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private int resource;
    private List<Usuario> lista_usuarios;



    public ListaUsuariosAdapter(@NonNull Context context, int resource, @NonNull List<Usuario> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.lista_usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(resource, null);
        }

        Usuario user = lista_usuarios.get(position);
        TextView user_name = view.findViewById(R.id.nombre_lista_usuario);
        ImageView profile_photo = view.findViewById(R.id.foto_lista_usuarios);
        profile_photo.setImageResource(R.drawable.ic_baseline_person_24);

        user_name.setText(user.getUsername());
        Log.d("MIRA2", user.getUsername() + user.getUrl_profile_photo());
        if (!user.getUrl_profile_photo().matches("")) {

            Picasso.with(getContext()).load(user.getUrl_profile_photo()).into(profile_photo);

        }


        return view;
    }

}
