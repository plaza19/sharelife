package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import activity.ChatActivity;
import adapters.GridAdapter;
import modelos.Usuario;


public class PerfilFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore store;
    private View view;
    private TextView nombre_perfil, num_publicaciones, correo, num_seguidores;
    private UserManager userManager;
    private PublicacionManager publicacionManager;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private String user_name;
    private Usuario user;
    private LinearLayout edit_perfil;
    private ImageView profile_foto;
    private Button btn_seguir;
    private FloatingActionButton fab_chat;


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
        fab_chat = view.findViewById(R.id.fab_chat);
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        userManager = new UserManager();
        publicacionManager = new PublicacionManager();
        gridView = view.findViewById(R.id.grid_view_perfil);
        ArrayList<String> list_publications = new ArrayList<String>();
        edit_perfil = view.findViewById(R.id.TextView_editar_perfil);
        profile_foto = view.findViewById(R.id.profile_foto_perfil);
        num_seguidores = view.findViewById(R.id.textView_num_seguidores_perfil);
        btn_seguir = view.findViewById(R.id.btn_seguir_perfil);
        nombre_perfil = view.findViewById(R.id.TextView_nombre_perfil);
        num_publicaciones = view.findViewById(R.id.textView_num_publicaciones_perfil);
        correo = view.findViewById(R.id.TextView_correo_perfil);
        String id_user = "";

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

        fab_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatActivity();
            }
        });




        if (this.user_name.matches("")) {
            btn_seguir.setVisibility(View.GONE);
            fab_chat.setVisibility(View.GONE);
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
            correo.setVisibility(view.GONE);
            edit_perfil.setVisibility(View.GONE);
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


        if (this.user_name.matches("")) {

            store.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        nombre_perfil.setText(task.getResult().get("user_name").toString());
                        correo.setText(auth.getCurrentUser().getEmail());
                        if (task.getResult().get("profile_image").toString() != "") {
                            Picasso.with(getContext()).load(task.getResult().get("profile_image").toString()).into(profile_foto);
                        }

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
                        if (task.getResult().getDocuments().get(0).get("profile_image").toString() != "") {
                            Picasso.with(getContext()).load(task.getResult().getDocuments().get(0).get("profile_image").toString()).into(profile_foto);
                        }
                    }else {
                        Toast.makeText(getContext(), "No se ha podido obtener la información del usuario", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }// si se abre el fragment desde el perfil del usuario - nombre y correo (Los else son cuando se abre desde la lista de users)

        if (this.user_name.matches("")) {

            publicacionManager.getNumPublications(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        publicacionManager.getNumPublications(userUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        //Número de seguidores
        if (this.user_name.matches("")) {
            userManager.getCountFollowers(auth.getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<String> follower_list = (ArrayList<String>)task.getResult().get("followed_by");
                        num_seguidores.setText(String.valueOf(follower_list.size() -1));
                    }
                }
            });
        }else {
            userManager.getUserByUserName(user_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        userManager.getCountFollowers(task.getResult().getDocuments().get(0).getId()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<String> follower_list = (ArrayList<String>)task.getResult().get("followed_by");
                                    num_seguidores.setText(String.valueOf(follower_list.size() -1));
                                }
                            }
                        });
                    }
                }
            });
        }

        btn_seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.getUserByUserName(user_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            Log.d("MIRAAA", task.getResult().getDocuments().get(0).get("user_name").toString());
                            userManager.updateFollowers_final(auth.getCurrentUser().getUid(), task.getResult().getDocuments().get(0).getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                        }
                    }
                });//Update follower_list

                userManager.getUserByUserName(user_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            publicacionManager.getAllFromUser(task.getResult().getDocuments().get(0).getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (int i=0; i<task.getResult().getDocuments().size(); i++) {
                                            publicacionManager.updateViewersUser(task.getResult().getDocuments().get(i).getId(), auth.getCurrentUser().getUid());
                                        }
                                    }

                                }
                            });
                        }

                    }
                });
            }
        });





        return view;
    }

    private void goToChatActivity() {

        userManager.getUserByUserName(user_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                i.putExtra("idUser1", auth.getCurrentUser().getUid());
                i.putExtra("idUser2", queryDocumentSnapshots.getDocuments().get(0).getId());
                i.putExtra("idChat",  auth.getCurrentUser().getUid()+queryDocumentSnapshots.getDocuments().get(0).getId());
                startActivity(i);
            }
        });

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}