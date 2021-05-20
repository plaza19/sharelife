package com.plaza19.sharelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yanzhenjie.loading.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class RegistroActivity extends AppCompatActivity {

    private EditText edit_user,edit_email, edit_password, edit_password_confirm;
    private Button btn_register;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private final String COLLECTION_USERS = "Users";
    private LoadingDialog loading_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edit_user = findViewById(R.id.edit_user);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        edit_password_confirm = findViewById(R.id.edit_password_confirm);
        btn_register = findViewById(R.id.button_register);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();



        edit_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //comprobamos que el correo sea valido

                checkFields();

                //Toast.makeText(RegistroActivity.this, "desactivao", Toast.LENGTH_SHORT).show();
                //checkMail();
                //checkPassword();
                //checkUser();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkFields();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loading_dialog.hide();
    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(edit_email.getText().toString(), edit_password_confirm.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loading_dialog = new LoadingDialog(RegistroActivity.this);
                    loading_dialog.show();
                    Toast.makeText(RegistroActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> data_map = new HashMap<>();
                    data_map.put("email", edit_email.getText().toString());
                    data_map.put("user_name", edit_user.getText().toString());
                    data_map.put("followed_by", new ArrayList<String>().add(auth.getCurrentUser().getUid()));
                    data_map.put("profile_image", "");
                    //Documento usuario con el uid que se genera en firebase-auth
                    firestore.collection(COLLECTION_USERS).document(auth.getCurrentUser().getUid()).set(data_map);
                }else {
                    Toast.makeText(RegistroActivity.this, "No se ha podido crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkMail() {

        if (edit_email.getText().toString().matches(".*@.*\\..*[{a-z}]")) {
            //Toast.makeText(RegistroActivity.this, "mail bien", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            //Toast.makeText(RegistroActivity.this, "mail mal", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void checkFields() {

        if (checkMail() && checkUser() && checkPassword()) {
            btn_register.setEnabled(true);
            //Toast.makeText(RegistroActivity.this, "activo", Toast.LENGTH_SHORT).show();
        } else {
            btn_register.setEnabled(false);
        }

    }

    private boolean checkUser() {
        //TODO: CHECK USERS
        return true;
    }

    private boolean checkPassword() {
        if (edit_password.getText().toString().equals(edit_password_confirm.getText().toString()) && !(edit_password.getText().toString().equals("") && edit_password_confirm.getText().toString().equals(""))) {
            //Toast.makeText(RegistroActivity.this, "contra bein", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            //Toast.makeText(RegistroActivity.this, "contra mal", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}