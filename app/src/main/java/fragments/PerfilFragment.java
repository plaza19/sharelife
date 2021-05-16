package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.plaza19.sharelife.R;

import java.util.ArrayList;
import java.util.List;

import Utils.PublicacionManager;
import Utils.UserManager;
import adapters.GridAdapter;


public class PerfilFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore store;
    private View view;
    private TextView nombre_perfil, num_publicaciones, correo;
    private UserManager userManager;
    private PublicacionManager publicacionManager;
    private GridView gridView;
    private GridAdapter gridAdapter;


    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        userManager = new UserManager();
        publicacionManager = new PublicacionManager();
        gridView = view.findViewById(R.id.grid_view_perfil);
        ArrayList<String> list_publications = new ArrayList<String>();

        publicacionManager.getAllFromUser().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> list = task.getResult().getDocuments();
                    for (int i=0; i<list.size(); i++) {
                        Log.d("TEST", list.get(i).get("image").toString());
                        list_publications.add(list.get(i).get("image").toString());
                        gridAdapter = new GridAdapter(getContext(), list_publications);
                        gridView.setAdapter(gridAdapter);
                    }
                }
            }
        });




        nombre_perfil = view.findViewById(R.id.TextView_nombre_perfil);
        num_publicaciones = view.findViewById(R.id.textView_num_publicaciones_perfil);
        correo = view.findViewById(R.id.TextView_correo_perfil);

        store.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    nombre_perfil.setText(task.getResult().get("user_name").toString());
                }else {
                    Toast.makeText(getContext(), "No se ha podido obtener la información del usuario", Toast.LENGTH_LONG).show();
                }

            }
        });
        correo.setText(auth.getCurrentUser().getEmail());


        userManager.getNumPublications().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    num_publicaciones.setText(String.valueOf(task.getResult().size()));
                    Log.d("TEST", task.getResult().size() + "");
                }else {
                    Toast.makeText(getContext(), "No se ha podido obtener el numero de publicaciones", Toast.LENGTH_LONG).show();
                }

            }
        });




        return view;
    }
}