package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.plaza19.sharelife.R;

import Utils.UserManager;


public class EditarPerfilFragment extends Fragment {

    private String nombre_usuario = "";
    private String correo = "";
    private View view;
    private EditText edit_usuario, edit_correo;
    private Button btn_guardar;
    private FirebaseAuth auth;
    private UserManager userManager;



    public EditarPerfilFragment() {
        // Required empty public constructor
    }

    public EditarPerfilFragment(String correo, String nombre_usuario) {

        this.nombre_usuario = nombre_usuario;
        this.correo = correo;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        edit_usuario = view.findViewById(R.id.editText_usuario_editar_perfil);
        edit_correo = view.findViewById(R.id.editText_coreo_editar_perfil);
        btn_guardar = view.findViewById(R.id.btn_guardar_editar_perfil);
        edit_usuario.setText(this.nombre_usuario);
        edit_correo.setText(this.correo);
        btn_guardar.setEnabled(false);
        auth = FirebaseAuth.getInstance();
        userManager = new UserManager();

        edit_usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("USERNAME", s.toString());
                if (nombre_usuario.equals(s.toString())) {
                    btn_guardar.setEnabled(false);
                    Log.d("USERNAME", s.toString());
                }else {
                    btn_guardar.setEnabled(true);
                }

            }
        });

        edit_correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("CORREO", s.toString());
                if (correo.equals(s.toString())) {
                    btn_guardar.setEnabled(false);
                    Log.d("CORREO", s.toString());
                }else {
                    btn_guardar.setEnabled(true);
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_correo.getText().toString().matches(correo)) {
                    auth.getCurrentUser().updateEmail("user@example.com").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Correo actualizado con éxito", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "No se ha podido actualizar el correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                if (!edit_usuario.getText().toString().matches(nombre_usuario)) {
                    userManager.UpdateUser(auth.getCurrentUser().getUid(), "user_name",  edit_usuario.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getContext(), "Nombre de usuario actualizado con éxito", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "No se ha podido actualizar el nombre se usuario", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        return view;
    }
}