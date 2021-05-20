package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Utils.PublicacionManager;
import Utils.UserManager;
import adapters.GridAdapter;
import modelos.Usuario;


public class PerfilFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore store;
    private View view;
    private TextView nombre_perfil, num_publicaciones, correo;
    private UserManager userManager;
    private PublicacionManager publicacionManager;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private String user_name;
    private Usuario user;
    private LinearLayout edit_perfil;
    private ImageView profile_foto;


    public PerfilFragment() {
        this.user_name = "";
    }

    public PerfilFragment(String user_name) {
        this.user_name = user_name;
        this.user = new Usuario();
        user.setUsername(user_name);
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
        edit_perfil = view.findViewById(R.id.TextView_editar_perfil);
        profile_foto = view.findViewById(R.id.profile_foto_perfil);

        edit_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.getUser(auth.getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("USUR", task.getResult().get("user_name").toString());
                            openFragment(new EditarPerfilFragment(auth.getCurrentUser().getEmail(), task.getResult().get("user_name").toString(), task.getResult().get("profile_image").toString()));
                        }else {
                            Toast.makeText(getContext(), "No se puede editar el perfil", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });



        if (this.user_name.matches("")) {

            publicacionManager.getAllFromUser(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> list = task.getResult().getDocuments();
                        for (int i = 0; i < list.size(); i++) {
                            Log.d("TEST", list.get(i).get("image").toString());
                            list_publications.add(list.get(i).get("image").toString());
                            gridAdapter = new GridAdapter(getContext(), list_publications);
                            gridView.setAdapter(gridAdapter);
                        }
                    }
                }
            });

        }else {
            userManager.getUserByUserName(this.user_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String userUid = task.getResult().getDocuments().get(0).getId();
                    publicacionManager.getAllFromUser(userUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> list = task.getResult().getDocuments();
                                for (int i = 0; i < list.size(); i++) {
                                    Log.d("TEST", list.get(i).get("image").toString());
                                    list_publications.add(list.get(i).get("image").toString());
                                    gridAdapter = new GridAdapter(getContext(), list_publications);
                                    gridView.setAdapter(gridAdapter);
                                }
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "No se ha podido obtener la información del usuario", Toast.LENGTH_LONG).show();
                }
                }
            });
        }


        nombre_perfil = view.findViewById(R.id.TextView_nombre_perfil);
        num_publicaciones = view.findViewById(R.id.textView_num_publicaciones_perfil);
        correo = view.findViewById(R.id.TextView_correo_perfil);

        if (this.user_name.matches("")) {

            store.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        nombre_perfil.setText(task.getResult().get("user_name").toString());
                        correo.setText(auth.getCurrentUser().getEmail());
                        Picasso.with(getContext()).load(task.getResult().get("profile_image").toString()).into(profile_foto);
                    } else {
                        Toast.makeText(getContext(), "No se ha podido obtener la información del usuario", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }else {
            userManager.getUserByUserName(this.user_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        nombre_perfil.setText(task.getResult().getDocuments().get(0).get("user_name").toString());
                        correo.setText(task.getResult().getDocuments().get(0).get("email").toString());
                        Picasso.with(getContext()).load(task.getResult().getDocuments().get(0).get("profile_image").toString()).into(profile_foto);
                    }else {
                        Toast.makeText(getContext(), "No se ha podido obtener la información del usuario", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }// si se abre el fragment desde el perfil del usuario - nombre y correo (Los else son cuando se abre desde la lista de users)

        if (this.user_name.matches("")) {

            userManager.getNumPublications(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        num_publicaciones.setText(String.valueOf(task.getResult().size()));
                        Log.d("TEST", task.getResult().size() + "");
                    } else {
                        Toast.makeText(getContext(), "No se ha podido obtener el numero de publicaciones", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }else {

            userManager.getUserByUserName(this.user_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        String userUid = task.getResult().getDocuments().get(0).getId();
                        userManager.getNumPublications(userUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    }else {
                        Toast.makeText(getContext(), "No se ha podido obtener el numero de publicaciones", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }//se se abre el fragment desde la lista de contactos




        return view;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}