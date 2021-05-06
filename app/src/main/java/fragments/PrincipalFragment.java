package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.plaza19.sharelife.R;

import activity.PublicacionActivity;

public class PrincipalFragment extends Fragment {

    View view;
    FloatingActionButton fab_publicacion;
    public PrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_principal, container, false);
        fab_publicacion = view.findViewById(R.id.fab_publucacion);
        fab_publicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PublicacionActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}