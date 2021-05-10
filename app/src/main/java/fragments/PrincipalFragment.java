package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;
import com.plaza19.sharelife.MainActivity;
import com.plaza19.sharelife.R;

import Utils.PublicacionManager;
import activity.PublicacionActivity;
import adapters.PublicacionAdapter;
import modelos.Publicacion;

public class PrincipalFragment extends Fragment {

    View view;
    FloatingActionButton fab_publicacion;
    Toolbar toolbar;
    RecyclerView recyclerView;
    PublicacionManager manager_publicacion;
    PublicacionAdapter publicacionAdapter;

    public PrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_principal, container, false);
        fab_publicacion = view.findViewById(R.id.fab_publucacion);
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerview_fragment_principal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        manager_publicacion = new PublicacionManager();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Publicaciones");
        setHasOptionsMenu(true);

        fab_publicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PublicacionActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_menu_cerrar_sesion:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = manager_publicacion.getAll();
        FirestoreRecyclerOptions<Publicacion> opciones = new FirestoreRecyclerOptions.Builder<Publicacion>()
                .setQuery(query, Publicacion.class).build();

        publicacionAdapter = new PublicacionAdapter(opciones, getContext());
        recyclerView.setAdapter(publicacionAdapter);
        publicacionAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        publicacionAdapter.stopListening();
    }
}