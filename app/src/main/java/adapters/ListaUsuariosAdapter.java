package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.plaza19.sharelife.R;

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
        user_name.setText(user.getUsername());

        return view;
    }
}
